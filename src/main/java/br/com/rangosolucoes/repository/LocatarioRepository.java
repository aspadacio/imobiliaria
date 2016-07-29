package br.com.rangosolucoes.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.rangosolucoes.model.TbBairro;
import br.com.rangosolucoes.model.TbEnderecoPessoa;
import br.com.rangosolucoes.model.TbLocatario;
import br.com.rangosolucoes.model.TbMunicipio;
import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.model.TbPessoaFisica;
import br.com.rangosolucoes.model.TbPessoaJuridica;
import br.com.rangosolucoes.model.TbPessoaTelefone;
import br.com.rangosolucoes.repository.filter.LocatarioFilter;
import br.com.rangosolucoes.service.NegocioException;

public class LocatarioRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	/**
	 * Método responsável por salvar um Locatário com suas devidas entidades {@link TbPesso}, {@link TbPessoaFisica}, {@link TbPessoaJuridica}, {@link TbPessoaTelefone} e {@link TbMunicipio}
	 * @param bairros 
	 * @param municipios 
	 * @param enderecos 
	 * @param phones 
	 * @param isSameAddress - diz se "Endereco" e "Endereco Cobranca" são iguais. 
	 * 
	 * */
	public void salvarPessoa(TbPessoa pessoa, List<TbPessoaTelefone> phones,
			List<TbEnderecoPessoa> enderecos, List<TbMunicipio> municipios,
			List<TbBairro> bairros, Boolean isPessoaFisica,
			Boolean isSameAddress) {
		//Persistindo Pessoa Física / Jurídica
		if(isPessoaFisica){
			manager.merge(pessoa.getTbPessoaFisica()); //TbPessoaFisica
		}else{
			manager.merge(pessoa.getTbPessoaJuridica()); //TbPessoaJuridica
		}
		
		//Persistindo Pessoa
		TbPessoa pessoaPersisted = manager.merge(pessoa); //TbPessoa
		
		//Persistindo Endereço completo: Municipio, Bairro e Endereço
		TbMunicipio municipioPersisted = null;
		TbBairro bairroPersisted = null;
		if(!isSameAddress){
			//Diferentes dados em "Endereço" e "Endereço Cobrança"
			for (int i=0; i<enderecos.size(); i++) {
				//municipio
				municipioPersisted = manager.merge(municipios.get(i)); //TbMunicipio
				
				//bairro
				bairros.get(i).setTbMunicipio(municipioPersisted);
				bairroPersisted = manager.merge(bairros.get(i)); //TbBairro
				
				//endereco
				enderecos.get(i).setTbMunicipio(municipioPersisted);
				enderecos.get(i).setTbBairro(bairroPersisted);
				enderecos.get(i).setTbPessoa(pessoaPersisted);
				manager.merge(enderecos.get(i)); //TbEnderecoPesso
			}
		}else{
			//**Mesmo Endereço - então não muda o Municipio (TbMunicipio) e o Bairro (TbBairro)**//
			//municipio
			municipioPersisted = manager.merge(municipios.get(0)); //TbMunicipio
			
			//bairro
			bairros.get(0).setTbMunicipio(municipioPersisted);
			bairroPersisted = manager.merge(bairros.get(0)); //TbBairro
			
			for (int i=0; i<enderecos.size(); i++) {
				//endereco
				enderecos.get(i).setTbMunicipio(municipioPersisted);
				enderecos.get(i).setTbBairro(bairroPersisted);
				enderecos.get(i).setTbPessoa(pessoaPersisted);
				manager.merge(enderecos.get(i)); //TbEnderecoPesso
			}
		}
		
		
		//Persistindo Telefone(s)
		for(TbPessoaTelefone telefone : phones){
			telefone.setTbPessoa(pessoaPersisted); //Usado para setar a FK_PESSOATELEFONE_PESSOA
			manager.merge(telefone); //TbPessoaTelefone
		}
	}

	/**
	 * Método responsável por pegar todos os CPFs
	 * inseridos no DB e retornar.
	 * */
	@SuppressWarnings("unchecked")
	public List<String> retonarCpfs() {
		return manager.createQuery("SELECT PF.nuCpf FROM TbPessoaFisica PF").getResultList();
	}

	/**
	 * Método responsável por pegar todos os CNPJs
	 * inseridos no DB e retornar.
	 * */
	@SuppressWarnings("unchecked")
	public List<String> retonarCnpjs() {
		return manager.createQuery("SELECT PJ.nuCnpj FROM TbPessoaJuridica PJ").getResultList();
	}

	/**
	 * Método responsável por pegar todos os Municipios
	 * inseridos no DB e retornar.
	 * */
	@SuppressWarnings("unchecked")
	public List<String> retonarMunicipios() {
		return manager.createQuery("SELECT MN.noMunicipio FROM TbMunicipio MN").getResultList();
	}

	/**
	 * Método responsável por pegar todos as UFs
	 * inseridos no DB e retornar.
	 * */
	@SuppressWarnings("unchecked")
	public List<String> retonarUfs() {
		return manager.createQuery("SELECT MN.sgUf FROM TbMunicipio MN").getResultList();
	}

	/**
	 * Método responsável por retornar Pessoas (locatários) conforme filtro {@link LocatarioFilter}
	 * */
	@SuppressWarnings("unchecked")
	public List<TbPessoa> buscaPessoas(LocatarioFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPessoa.class);
		
		//Criando os alias
		//criteria.createAlias("tbEnderecoPessoas", "enderecos");
		criteria.createAlias("tbPessoaFisica", "pessoaFisica");
		
		//add restrictions conforme filtro
		if(filtro.getNome() != null && filtro.getNome() != ""){
			criteria.add(Restrictions.like("pessoaFisica.noPessoaFisica", "%" + filtro.getNome().toUpperCase() + "%"));
		}
		if(filtro.getCpf() != null && filtro.getCpf() != ""){
			criteria.add(Restrictions.eq("pessoaFisica.nuCpf", filtro.getCpf()));
		}
		if(filtro.getCnpj() != null && filtro.getCnpj() != ""){
			criteria.createAlias("tbPessoaJuridica", "pessoaJuridica");
			criteria.add(Restrictions.eq("pessoaJuridica.nuCnpj", filtro.getCnpj()));
		}
		/*if(filtro.getMunicipio() != null && filtro.getMunicipio() != ""){
			criteria.add(Restrictions);
		}
		if(filtro.getUf() != null && filtro.getUf() != ""){
			criteria.add(Restrictions);
		}*/
		
		return criteria.addOrder(Order.asc("idPessoa")).list();
	}

	/**
	 * Método responsável por remover Locatário {@link TbLocatario} e suas dependências.
	 * @param idLocatarioSelecionada referente a {@link TbPessoa} ID_PESSOA
	 * */
	public void remover(int idLocatarioSelecionada) {
		boolean isPessoaFisica = false;
		TbPessoa pessoaPersisted = null;
		TbPessoaFisica pFisicaPersisted = null;
		TbPessoaJuridica pJuridicaPersisted = null;
		List<TbEnderecoPessoa> enderecosPersisted = null;
		List<TbPessoaTelefone> telefonesPersisted = null;
		
		//buscar Pessoa salva na tabela TbPessoa
		pessoaPersisted = findPessoaById(new Long(idLocatarioSelecionada));
		
		//Buscar ou pessoaJuridica ou pessoaFisica que foi cadastrada
		if(pessoaPersisted.getTbPessoaFisica() != null){
			//Pessoa Física
			pFisicaPersisted = findPessoaFisicaById(new String(pessoaPersisted.getTbPessoaFisica().getNuCpf()));
			isPessoaFisica = true;
		}else{
			//Pessoa Jurídica
			pJuridicaPersisted = findPessoaJuridicaById(new String(pessoaPersisted.getTbPessoaJuridica().getNuCnpj()));
		}
		
		//Buscar Endereço a partir do idPessoa
		enderecosPersisted = findEnderecosById(pessoaPersisted.getIdPessoa());
		
		//Bucar Telefones a partir do idPessoa
		telefonesPersisted = findPhonesById(pessoaPersisted.getIdPessoa());
		
		try{
			//Excluindo respeitando as FKs
			for (TbPessoaTelefone telefone : telefonesPersisted) {
				manager.remove(telefone);
			}
			for (TbEnderecoPessoa endereco : enderecosPersisted) {
				manager.remove(endereco);
			}
			
			manager.remove(pessoaPersisted);
			
			if(isPessoaFisica){
				manager.remove(pFisicaPersisted);
			}else{
				manager.remove(pJuridicaPersisted);
			}
			
			manager.flush();
		}catch(Exception e){
			e.printStackTrace();
			throw new NegocioException("LocatarioRepository::remover :: Erro ao excluir Locatário.");
		}
	}
	
	/**
	 * Método responsável por retornar a pessoa {@link TbPessoa} a partir do id ID_PESSOA
	 * */
	private TbPessoa findPessoaById(Long idLocatarioSelecionada) {
		return manager
				.createQuery(
						"FROM TbPessoa WHERE idPessoa = :idLocatarioSelecionada",
						TbPessoa.class)
				.setParameter("idLocatarioSelecionada", idLocatarioSelecionada)
				.getSingleResult();
	}

	/**
	 * Método responsável por retornar a Pessoa Jurídica {@link TbPessoaJuridica} a partir do cnpj NU_CNPJ
	 * */
	private TbPessoaJuridica findPessoaJuridicaById(String nuCnpj) {
		return manager
				.createQuery(
						"FROM TbPessoaJuridica WHERE nuCnpj = :cnpj",
						TbPessoaJuridica.class)
				.setParameter("cnpj", nuCnpj)
				.getSingleResult();
	}

	/**
	 * Método responsável por retornar a Pessoa Física {@link TbPessoaFisica} a partir do cpf NU_CPF
	 * */
	private TbPessoaFisica findPessoaFisicaById(String nuCpf) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPessoaFisica.class);
		
		if(nuCpf != null && nuCpf != ""){
			criteria.add(Restrictions.eq("nuCpf", nuCpf));			
		}
		
		return (TbPessoaFisica) criteria.list().get(0);
	}

	/**
	 * Método responsável por retornar os Telefones {@link TbPessoaTelefone} a partir do id ID_PESSOA
	 * */
	@SuppressWarnings("unchecked")
	private List<TbPessoaTelefone> findPhonesById(Long idPessoa) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPessoaTelefone.class);
		
		if(idPessoa != null){
			criteria.add(Restrictions.eq("tbPessoa.idPessoa", idPessoa));			
		}
		
		return criteria.list();
	}

	/**
	 * Método responsável por retornar o Endereco {@link TbEnderecoPessoa} a partir do id ID_PESSOA
	 * */
	@SuppressWarnings("unchecked")
	private List<TbEnderecoPessoa> findEnderecosById(Long idPessoa) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbEnderecoPessoa.class);
		
		if(idPessoa != null){
			criteria.add(Restrictions.eq("tbPessoa.idPessoa", idPessoa));			
		}
		
		return criteria.list();
	}
}