package br.com.rangosolucoes.repository;

import java.io.Serializable;
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
	 *  Método responsável por salvar um Locador com suas devidas dependências {@link TbPesso}, {@link TbPessoaJuridica}, {@link TbMunicipio}, {@link TbBairro}
	 * @param endereco 
	 * 
	 * */
	public void salvarPessoa(TbPessoa pessoa, TbLocador locador,
			TbPessoaJuridica locadPesJuridica, TbEnderecoPessoa endereco) {
		//Tabelas usadas neste método
		TbPessoa pessoaPersisted = null;
		TbPessoaJuridica pjPersisted = null;
		TbMunicipio municipioPersisted = null;
		TbBairro bairroPersisted = null;
		TbLocador locadorPersistedLocador = null;
		TbEnderecoPessoa enderecoPersisted = null;
		
		//Persistindo Pessoa
		pjPersisted = manager.find(TbPessoaJuridica.class, pessoa.getTbPessoaJuridica().getNuCnpj()); //Find by primary key. Search for an entity of the specified class and primary key
		manager.merge(pessoa.getTbPessoaJuridica()); //TbPessoaJuridica
		//Check if already exist. If yes then it´s an editing process.
		if(pjPersisted != null){
			//Already exist. So it´s an editing process.
			pessoaPersisted = findPessoaByCnpj(pjPersisted.getNuCnpj());
			pessoa.setIdPessoa(pessoaPersisted.getIdPessoa());//set id da pessoa. Portanto será feito o UPDATE
			pessoaPersisted = manager.merge(pessoa); //TbPessoa updating
		}else{
			//Doesnt exist. So it´s an insert process.
			pessoaPersisted = manager.merge(pessoa); //TbPessoa
		}
		
		//Persistindo Endereço completo: Municipio, Bairro e Endereço
		//check if Municipio exist. If yes, so get them and sets to 'endereco' below.
		try{
			municipioPersisted = findMunicipioByName(endereco.getTbMunicipio().getNoMunicipio());
		}catch(NoResultException e){
			//ignorar se não tiver resultado = 0 row(s) returned
		}
		if(municipioPersisted != null){
			//Already exist. So it´s an editing process.
			try{
				bairroPersisted = findBairroByMunicipioIdNome(municipioPersisted.getIdMunicipio(), endereco.getTbBairro().getNoBairro());
			}catch(NoResultException e){
				//ignorar se não tiver resultado = 0 row(s) returned
			}
			//if Bairro with that MunicipioId doesnt exist, so save it.
			if(bairroPersisted == null){
				endereco.getTbBairro().getTbMunicipio().setIdMunicipio(municipioPersisted.getIdMunicipio());
				bairroPersisted = manager.merge(endereco.getTbBairro()); //TbBairro
			}
		}else{
			//Doesnt exist. So it´s an insert process.
			//municipio
			municipioPersisted = manager.merge(endereco.getTbMunicipio()); //TbMunicipio
			endereco.getTbBairro().setTbMunicipio(municipioPersisted);
			bairroPersisted = manager.merge(endereco.getTbBairro()); //TbBairro
		}
		//END - Persistindo Endereço completo: Municipio, Bairro e Endereço
			
		//Locador
		locador.setTbPessoa(pessoaPersisted);
		//check if Locador exist.
		try{
			locadorPersistedLocador = findLocadorById(pessoaPersisted.getIdPessoa());
		}catch(NoResultException e){
			//ignorar se não tiver resultado = 0 row(s) returned
		}
		if(locadorPersistedLocador != null){
			//Already exist. So it´s an editing process.
			locador.setIdLocador(locadorPersistedLocador.getIdLocador());
		}
		manager.merge(locador);
		
		//endereco
		//check if Endereco exist.
		try{
			enderecoPersisted = findEnderecoById(pessoaPersisted.getIdPessoa());
		}catch(NoResultException e){
			//ignorar se não tiver resultado = 0 row(s) returned
		}
		if(enderecoPersisted != null){
			//Already exist. So it´s an editing process.
			endereco.setIdEnderecoPessoa(enderecoPersisted.getIdEnderecoPessoa());
		}
		endereco.setTbMunicipio(municipioPersisted);
		endereco.setTbBairro(bairroPersisted);
		endereco.setTbPessoa(pessoaPersisted);
		manager.merge(endereco); //TbEnderecoPesso
		
	}

	/**
	 * Método responsável por, se encontar no DB, retornar a {@link TbBairro} já inserida no banco.
	 * @param idMunicipio
	 * @return {@link TbBairro}
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
	 * Método responsável por retornar o locador {@link TbLocador} a partir do id ID_PESSOA
	 * */
	public TbLocador findLocadorById(Long idPessoa) {
		return manager
				.createQuery(
						"FROM TbLocador WHERE tbPessoa.idPessoa = :idPessoa",
						TbLocador.class)
				.setParameter("idPessoa", idPessoa)
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
	 * Método responsável por retornar uma pessoa {@link TbPessoa} a partir do CNPJ da {@link TbPessoaJuridica}
	 * @param número do cnpj do locador
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
	 * Método responsável por retornar a pessoa {@link TbPessoa} a partir do id ID_PESSOA
	 * */
	public TbPessoa findPessoaById(Long idLocatarioSelecionada) {
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
	public TbPessoaJuridica findPessoaJuridicaById(String nuCnpj) {
		return manager
				.createQuery(
						"FROM TbPessoaJuridica WHERE nuCnpj = :cnpj",
						TbPessoaJuridica.class)
				.setParameter("cnpj", nuCnpj)
				.getSingleResult();
	}
	
	public List<TbLocador> consultaTodosLocadores() {
		return manager.createQuery("from TbLocador", TbLocador.class).getResultList();
	}
	
	public TbLocador locadorPorId(Long id){
		return manager.createQuery("from TbLocador where idLocador = :idLocador", TbLocador.class)
				.setParameter("idLocador", id).getSingleResult();
	}
}