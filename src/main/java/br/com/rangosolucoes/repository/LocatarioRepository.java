package br.com.rangosolucoes.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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
	 * Método responsável por salvar um Locatário {@link TbLocatario} com suas devidas entidades {@link TbPessoa}, {@link TbPessoaFisica}, {@link TbPessoaJuridica}, {@link TbPessoaTelefone} e {@link TbMunicipio}
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
		//Tabelas usadas neste método
		TbPessoa pessoaPersisted = null;
		TbPessoaFisica pfPersisted = null;
		TbPessoaJuridica pjPersisted = null;
		TbPessoaTelefone phonePersisted = null;
		TbEnderecoPessoa enderecoPersisted = null;
		TbMunicipio municipioPersisted = null;
		TbBairro bairroPersisted = null;
		TbLocatario locatarioPersisted = null;
		TbLocatario locatario = new TbLocatario();
		
		//Persistindo Pessoa Física / Jurídica
		if(isPessoaFisica){
			//Pessoa Física
			pfPersisted = manager.find(TbPessoaFisica.class, pessoa.getTbPessoaFisica().getNuCpf()); //check if exist
			//Check if already exist. If yes then it´s an editing process.
			if(pfPersisted != null){
				//Already exist. So it´s an editing process.
				pessoaPersisted = findPessoaByCpf(pfPersisted.getNuCpf());
				pessoa.getTbPessoaFisica().setNuCpf(pfPersisted.getNuCpf()); //set Primary Key
				pessoa.setIdPessoa(pessoaPersisted.getIdPessoa()); //set Primary Key
			}
			manager.merge(pessoa.getTbPessoaFisica()); //TbPessoaFisica			
		}else{
			//Pessoa Jurídica
			pjPersisted = manager.find(TbPessoaJuridica.class, pessoa.getTbPessoaJuridica().getNuCnpj()); //check if exist
			//Check if already exist. If yes then it´s an editing process.
			if(pjPersisted != null){
				//Already exist. So it´s an editing process.
				pessoaPersisted = findPessoaByCnpj(pjPersisted.getNuCnpj());
				pessoa.getTbPessoaJuridica().setNuCnpj(pjPersisted.getNuCnpj()); //set Primary Key
				pessoa.setIdPessoa(pessoaPersisted.getIdPessoa()); //set Primary Key
			}
			manager.merge(pessoa.getTbPessoaJuridica()); //TbPessoaJuridica		
		}
		//Persistindo Pessoa
		pessoaPersisted = manager.merge(pessoa); //TbPessoa
		//END-Persistindo Pessoa Física / Jurídica
		
		//Persistindo Telefone(s)
		for(TbPessoaTelefone telefone : phones){
			//check if Telefone exist.
			try{
				phonePersisted = findPhoneByIdTelDdd(pessoaPersisted.getIdPessoa(), telefone.getNuTelefone(), telefone.getNuTelefoneDdd());
			}catch(NoResultException e){ } //ignorar se não tiver resultado = 0 row(s) returned
			if(phonePersisted != null){
				//Already exist. So it´s an editing process.
				telefone.setIdPessoaTelefone(phonePersisted.getIdPessoaTelefone()); //set Primary Key
			}
			telefone.setTbPessoa(pessoaPersisted); //Usado para setar a FK_PESSOATELEFONE_PESSOA
			manager.merge(telefone); //TbPessoaTelefone
		}
		//END-Persistindo Telefone(s)
		
		//Persistindo Endereço completo: Municipio, Bairro e Endereço
		if(!isSameAddress){
			//Diferentes dados em "Endereço" e "Endereço Cobrança"
			for (int i=0; i<enderecos.size(); i++) {
				//Municipio
				//check if Municipio exist.
				try{
					municipioPersisted = findMunicipioByName(municipios.get(i).getNoMunicipio());
				}catch(NoResultException e){} //ignorar se não tiver resultado = 0 row(s) returned
				if(municipioPersisted != null){
					//Already exist. So it´s an editing process.
					municipios.get(i).setIdMunicipio(municipioPersisted.getIdMunicipio()); //set Primary Key
				}
				municipioPersisted = manager.merge(municipios.get(i)); //TbMunicipio
				
				//Bairro
				//check if Bairro exist.
				try{
					bairroPersisted = findBairroByMunicipioIdNome(municipioPersisted.getIdMunicipio(), bairros.get(i).getNoBairro());					
				}catch(NoResultException e){} //ignorar se não tiver resultado = 0 row(s) returned
				if(bairroPersisted != null){
					//Already exist. So it´s an editing process.
					bairros.get(i).setIdBairro(bairroPersisted.getIdBairro()); //set Primary Key
				}
				bairros.get(i).setTbMunicipio(municipioPersisted);
				bairroPersisted = manager.merge(bairros.get(i)); //TbBairro
				
				//Endereco
				//check if Endereco exist.
				try{
					enderecoPersisted = findEnderecoByIdTipo(pessoaPersisted.getIdPessoa(), enderecos.get(i).getTpEndereco());
				}catch(NoResultException e){} //ignorar se não tiver resultado = 0 row(s) returned
				if(enderecoPersisted != null){
					//Already exist. So it´s an editing process.
					enderecos.get(i).setIdEnderecoPessoa(enderecoPersisted.getIdEnderecoPessoa());
				}
				enderecos.get(i).setTbMunicipio(municipioPersisted);
				enderecos.get(i).setTbBairro(bairroPersisted);
				enderecos.get(i).setTbPessoa(pessoaPersisted);
				manager.merge(enderecos.get(i)); //TbEnderecoPesso
			}
		}else{
			//**Mesmo Endereço - então não muda o Municipio (TbMunicipio) e o Bairro (TbBairro)**//
			//municipio
			//check if Municipio exist.
			try{
				municipioPersisted = findMunicipioByName(municipios.get(0).getNoMunicipio());
			}catch(NoResultException e){} //ignorar se não tiver resultado = 0 row(s) returned
			if(municipioPersisted != null){
				//Already exist. So it´s an editing process.
				municipios.get(0).setIdMunicipio(municipioPersisted.getIdMunicipio()); //set Primary Key
			}
			municipioPersisted = manager.merge(municipios.get(0)); //TbMunicipio
			
			//bairro
			//check if Bairro exist.
			try{
				bairroPersisted = findBairroByMunicipioIdNome(municipioPersisted.getIdMunicipio(), bairros.get(0).getNoBairro());					
			}catch(NoResultException e){} //ignorar se não tiver resultado = 0 row(s) returned
			if(bairroPersisted != null){
				//Already exist. So it´s an editing process.
				bairros.get(0).setIdBairro(bairroPersisted.getIdBairro()); //set Primary Key
			}
			bairros.get(0).setTbMunicipio(municipioPersisted);
			bairroPersisted = manager.merge(bairros.get(0)); //TbBairro
			
			for (int i=0; i<enderecos.size(); i++) {
				//endereco
				//check if Endereco exist.
				try{
					enderecoPersisted = findEnderecoByIdTipo(pessoaPersisted.getIdPessoa(), enderecos.get(i).getTpEndereco());
				}catch(NoResultException e){} //ignorar se não tiver resultado = 0 row(s) returned
				if(enderecoPersisted != null){
					//Already exist. So it´s an editing process.
					enderecos.get(i).setIdEnderecoPessoa(enderecoPersisted.getIdEnderecoPessoa());
				}
				enderecos.get(i).setTbMunicipio(municipioPersisted);
				enderecos.get(i).setTbBairro(bairroPersisted);
				enderecos.get(i).setTbPessoa(pessoaPersisted);
				manager.merge(enderecos.get(i)); //TbEnderecoPesso
			}
		}
		//END-Persistindo Endereço completo: Municipio, Bairro e Endereço
		
		//Pesistindo o locatário
		//check if locatário exist.
		try{
			locatarioPersisted = findLocatarioById(pessoaPersisted.getIdPessoa());
		}catch(NoResultException e){} //ignorar se não tiver resultado = 0 row(s) returned
		if(locatarioPersisted != null){
			//Already exist. So it´s an editing process.
			locatario.setIdLocatario(locatarioPersisted.getIdLocatario()); //set Primary Key
		}
		locatario.setTbPessoa(pessoaPersisted);
		locatario.setDtCadastro(new Date());
		manager.merge(locatario);
	}

	/**
	 * Método responsável por retornar o Locatário {@link TbLocatario} a partir do id ID_PESSOA
	 * */
	private TbLocatario findLocatarioById(Long idPessoa) {
		return manager
				.createQuery(
						"FROM TbLocatario WHERE tbPessoa.idPessoa = :idPessoa",
						TbLocatario.class)
				.setParameter("idPessoa", idPessoa)
				.getSingleResult();
	}

	/**
	 * Método responsável por retornar o Endereco {@link TbEnderecoPessoa} a partir dos parâmetros abaixo.
	 * @param idPessoa referente a tabela {@link TbPessoa}
	 * @param tpEndereco tipo do endereço: 'R' = Residencial; 'C' = Cobrança
	 * @return {@link TbEnderecoPessoa}
	 */
	private TbEnderecoPessoa findEnderecoByIdTipo(Long idPessoa, char tpEndereco) {
		return manager
				.createQuery(
						"FROM TbEnderecoPessoa WHERE tbPessoa.idPessoa = :idPessoa AND tpEndereco = :tpEndereco",
						TbEnderecoPessoa.class)
				.setParameter("idPessoa", idPessoa)
				.setParameter("tpEndereco", tpEndereco)
				.getSingleResult();
	}

	/**
	 * @param idMunicipio
	 * @param noBairro
	 * @return
	 */
	private TbBairro findBairroByMunicipioIdNome(Long idMunicipio, String noBairro) {
		return manager
				.createQuery(
						"FROM TbBairro WHERE tbMunicipio.idMunicipio = :idMunicipio AND noBairro = :noBairro",
						TbBairro.class)
				.setParameter("idMunicipio", idMunicipio)
				.setParameter("noBairro", noBairro)
				.getSingleResult();
	}

	/**
	 * Método responsável por, se encontar no DB, retornar a {@link TbMunicipio} já inserida no banco.
	 * @param noMunicipio
	 * @return {@link TbMunicipio}
	 */
	private TbMunicipio findMunicipioByName(String noMunicipio) {
		return manager
				.createQuery(
						"FROM TbMunicipio WHERE noMunicipio = :noMunicipio",
						TbMunicipio.class)
				.setParameter("noMunicipio", noMunicipio)
				.getSingleResult();
	}

	/**
	 * Método responsável por retornar um telefone {@link TbPessoaTelefone} a partir dos parâmetros abaixo.
	 * @param idPessoa corresponde a PRIMARY_KEY da tabela {@link TbPessoa}
	 * @param nuTelefone
	 * @param nuTelefoneDdd
	 * @return
	 */
	private TbPessoaTelefone findPhoneByIdTelDdd(Long idPessoa, int nuTelefone,
			String nuTelefoneDdd) {
		return manager
				.createQuery(
						"FROM TbPessoaTelefone WHERE tbPessoa.idPessoa = :idPessoa AND nuTelefone = :nuTelefone AND nuTelefoneDdd = :nuTelefoneDdd",
						TbPessoaTelefone.class)
				.setParameter("idPessoa", idPessoa)
				.setParameter("nuTelefone", nuTelefone)
				.setParameter("nuTelefoneDdd", nuTelefoneDdd)
				.getSingleResult();
	}

	/**
	 * Método responsável por retornar uma pessoa {@link TbPessoa} a partir do cpf da {@link TbPessoaFisica}
	 * @param número do cpf do Locatário
	 * @return {@link TbPessoa}
	 */
	private TbPessoa findPessoaByCpf(String cpf) {
		return manager
				.createQuery(
						"FROM TbPessoa WHERE tbPessoaFisica.nuCpf = :cpf",
						TbPessoa.class)
				.setParameter("cpf", cpf)
				.getSingleResult();
	}

	/**
	 * Método responsável por retornar uma pessoa {@link TbPessoa} a partir do CNPJ da {@link TbPessoaJuridica}
	 * @param número do cnpj do Locatário
	 * @return {@link TbPessoa}
	 */
	private TbPessoa findPessoaByCnpj(String cnpj) {
		return manager
				.createQuery(
						"FROM TbPessoa WHERE tbPessoaJuridica.nuCnpj = :cnpj",
						TbPessoa.class)
				.setParameter("cnpj", cnpj)
				.getSingleResult();
	}

	/**
	 * Método responsável por pegar todos os CPFs
	 * inseridos no DB e retornar.
	 * */
	@SuppressWarnings("unchecked")
	public List<String> retornarCpfs() {
		return manager.createQuery("SELECT PF.nuCpf FROM TbPessoaFisica PF").getResultList();
	}

	/**
	 * Método responsável por pegar todos os CNPJs de Locatários
	 * inseridos no DB e retornar.
	 * */
	@SuppressWarnings("unchecked")
	public List<String> retornarCnpjs() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbLocatario.class);		
			
		//Alias para pegar todos os CNPJs apenas dos Locatários
		criteria.createAlias("tbPessoa", "pessoa");
		criteria.createAlias("pessoa.tbPessoaJuridica", "pj");
		
		//retornar apenas os CNPJs
		criteria.setProjection(Projections.property("pj.nuCnpj"));
		
		return criteria.list();
	}

	/**
	 * Método responsável por pegar todos os Municipios
	 * inseridos no DB e retornar.
	 * */
	@SuppressWarnings("unchecked")
	public List<String> retornarMunicipios() {
		return manager.createQuery("SELECT MN.noMunicipio FROM TbMunicipio MN").getResultList();
	}

	/**
	 * Método responsável por pegar todos as UFs
	 * inseridos no DB e retornar.
	 * */
	@SuppressWarnings("unchecked")
	public List<String> retornarUfs() {
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
		
		return criteria.addOrder(Order.asc("idPessoa")).list();
	}

	/**
	 * Método responsável por remover Locatário {@link TbLocatario} e suas dependências.
	 * @param locatarioSelecionado referente a {@link TbPessoa} ID_PESSOA
	 * */
	public void remover(TbPessoa locatarioSelecionado) {
		boolean isPessoaFisica = false;
		TbPessoa pessoaPersisted = null;
		TbPessoaFisica pFisicaPersisted = null;
		TbPessoaJuridica pJuridicaPersisted = null;
		List<TbEnderecoPessoa> enderecosPersisted = null;
		List<TbPessoaTelefone> telefonesPersisted = null;
		
		//buscar Pessoa salva na tabela TbPessoa
		pessoaPersisted = findPessoaById(locatarioSelecionado.getIdPessoa());
		
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
	public List<TbPessoaTelefone> findPhonesById(Long idPessoa) {
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
	public List<TbEnderecoPessoa> findEnderecosById(Long idPessoa) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbEnderecoPessoa.class);
		
		if(idPessoa != null){
			criteria.add(Restrictions.eq("tbPessoa.idPessoa", idPessoa));			
		}
		
		return criteria.list();
	}
	
	public List<TbLocatario> consultaTodosLocatarios(){
		return manager.createQuery("from TbLocatario", TbLocatario.class).getResultList();
	}
	
}