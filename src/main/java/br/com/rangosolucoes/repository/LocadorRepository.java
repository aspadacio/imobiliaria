package br.com.rangosolucoes.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.rangosolucoes.model.TbBairro;
import br.com.rangosolucoes.model.TbEnderecoPessoa;
import br.com.rangosolucoes.model.TbLocador;
import br.com.rangosolucoes.model.TbMunicipio;
import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.model.TbPessoaJuridica;
import br.com.rangosolucoes.repository.filter.LocadorFilter;
import br.com.rangosolucoes.service.NegocioException;

public class LocadorRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	/**
	 *  Método responsável por salvar um Locador com suas devidas entidades {@link TbPesso}, {@link TbPessoaJuridica}, {@link TbMunicipio}, {@link TbBairro}
	 * @param endereco 
	 * 
	 * */
	public void salvarPessoa(TbPessoa pessoa, TbLocador locador,
			TbPessoaJuridica locadPesJuridica, TbEnderecoPessoa endereco) {
		manager.merge(pessoa.getTbPessoaJuridica()); //TbPessoaJuridica
		
		//Persistindo Pessoa
		TbPessoa pessoaPersisted = manager.merge(pessoa); //TbPessoa
		
		//TODO check if Municipio & Bairro exists. If yes, so get them and sets to 'endereco' below.
		//Persistindo Endereço completo: Municipio, Bairro e Endereço
		TbMunicipio municipioPersisted = null;
		TbBairro bairroPersisted = null;
		//municipio
		municipioPersisted = manager.merge(endereco.getTbMunicipio()); //TbMunicipio
			
		//bairro
		endereco.getTbBairro().setTbMunicipio(municipioPersisted);
		bairroPersisted = manager.merge(endereco.getTbBairro()); //TbBairro
			
		//Locador
		locador.setTbPessoa(pessoaPersisted);
		manager.merge(locador);
		
		//endereco
		endereco.setTbMunicipio(municipioPersisted);
		endereco.setTbBairro(bairroPersisted);
		endereco.setTbPessoa(pessoaPersisted);
		manager.merge(endereco); //TbEnderecoPesso
		
	}

	/**
	 * Método responsável por retornar Pessoas (locador) conforme filtro {@link LocadorFilter}
	 * */
	@SuppressWarnings("unchecked")
	public List<TbPessoa> buscaPessoas(LocadorFilter filtro) {
		if(filtro != null){
			Session session = manager.unwrap(Session.class);
			Criteria criteria = session.createCriteria(TbPessoa.class);
			
			//Criando os alias
			criteria.createAlias("tbPessoaJuridica", "pessoaJuridica");
			
			//add restrictions conforme filtro
			if(filtro.getNomeObs() != null && filtro.getNomeObs() != ""){
				criteria.add(Restrictions.like("dsObservacao", "%" + filtro.getNomeObs().toUpperCase() + "%"));
			}
			if(filtro.getCnpj() != null && filtro.getCnpj() != ""){
				criteria.add(Restrictions.eq("pessoaJuridica.nuCnpj", filtro.getCnpj()));
			}
			
			return criteria.addOrder(Order.asc("idPessoa")).list();
		}else{
			return null;
		}
	}

	/**
	 * Método responsável por retornar os CNPJs dos Locadores {@link TbLocador} cadastrados.
	 * */
	@SuppressWarnings("unchecked")
	public List<String> retornarCnpjs() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbLocador.class);
		
		//Alias para pegar todos os CNPJs apenas dos Locadores
		criteria.createAlias("tbPessoa", "pessoa");
		criteria.createAlias("pessoa.tbPessoaJuridica", "pj");
		
		//Retornar apenas a coluna cnpj
		criteria.setProjection(Projections.property("pj.nuCnpj"));
		
		return criteria.list();
	}

	/**
	 * Método responsável por retornar todos os Municipios.
	 * */
	@SuppressWarnings("unchecked")
	public List<String> retornarMunicipios() {
		return manager.createQuery("SELECT MN.noMunicipio FROM TbMunicipio MN").getResultList();
	}

	/**
	 * Método responsável por retornar todos as UFs.
	 * */
	@SuppressWarnings("unchecked")
	public List<String> retornarUfs() {
		return manager.createQuery("SELECT MN.sgUf FROM TbMunicipio MN").getResultList();
	}

	/**
	 * Método responsável por remover Locador {@link TbLocador} e suas dependências.
	 * */
	public void remover(TbPessoa locadorSelecionado) {
		TbPessoa pessoaPersisted = null;
		TbLocador locadorPersisted = null;
		TbPessoaJuridica pJuridicaPersisted = null;
		TbEnderecoPessoa enderecoPersisted = null;
		
		//buscar Pessoa salva na tabela TbPessoa
		pessoaPersisted = findPessoaById(locadorSelecionado.getIdPessoa());
		
		//Buscar na pessoaJuridica que foi cadastrada
		pJuridicaPersisted = findPessoaJuridicaById(new String(pessoaPersisted.getTbPessoaJuridica().getNuCnpj()));
		
		//Bucsar o Locador que foi cadastrado
		locadorPersisted = findLocadorById(locadorSelecionado.getIdPessoa());
		
		//Buscar Endereço a partir do idPessoa
		enderecoPersisted = findEnderecoById(pessoaPersisted.getIdPessoa());
		
		try{
			//Excluindo respeitando as FKs		
			manager.remove(enderecoPersisted);
			manager.remove(locadorPersisted);
			manager.remove(pessoaPersisted);
			manager.remove(pJuridicaPersisted);
			manager.flush();
		}catch(Exception e){
			throw new NegocioException("LocadorRepository::remover :: Erro ao excluir Locador.: " + e.getCause().getCause());
		}
	}

	/**
	 * Método responsável por retornar o locador {@link TbLocador} a partir do id ID_PESSOA
	 * */
	private TbLocador findLocadorById(Long idPessoa) {
		return manager
				.createQuery(
						"FROM TbLocador WHERE tbPessoa.idPessoa = :idPessoa",
						TbLocador.class)
				.setParameter("idPessoa", idPessoa)
				.getSingleResult();
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
	 * Método responsável por retornar o Endereco {@link TbEnderecoPessoa} a partir do id ID_PESSOA
	 * */
	public TbEnderecoPessoa findEnderecoById(Long idPessoa) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbEnderecoPessoa.class);
		
		if(idPessoa != null){
			criteria.add(Restrictions.eq("tbPessoa.idPessoa", idPessoa));			
		}
		
		return (TbEnderecoPessoa) criteria.uniqueResult();
	}
}