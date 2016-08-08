package br.com.rangosolucoes.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.rangosolucoes.model.TbImovel;
import br.com.rangosolucoes.service.NegocioException;

public class ImovelRepository implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public TbImovel salvar(TbImovel imovel){
		return imovel = entityManager.merge(imovel);
	}
	
	public TbImovel porId(Long id){
		try {
			return entityManager.createQuery("from TbImovel where idImovel = :id", TbImovel.class)
					.setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TbImovel> filtrados(String nomeLocador, String descricaoImovel) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbImovel.class, "imovel");
		criteria.createAlias("imovel.tbLocador", "locador");
		criteria.createAlias("locador.tbPessoa", "pessoa");
		criteria.createAlias("pessoa.tbPessoaJuridica", "pessoajuridica");
		
		if(StringUtils.isNotBlank(nomeLocador)){
			criteria.add(Restrictions.ilike("pessoajuridica.noRazaoSocial", nomeLocador, MatchMode.ANYWHERE));
		}
		
		if(StringUtils.isNotBlank(descricaoImovel)){
			criteria.add(Restrictions.ilike("dsImovel", descricaoImovel, MatchMode.ANYWHERE));
		}
		
		return criteria.list();
	}
	
	public void excluir(TbImovel imovel){
		try {
			entityManager.remove(entityManager.contains(imovel) ? imovel : entityManager.merge(imovel));
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException("O imóvel não pode ser excluido.");
		}
	}
	
	public List<TbImovel> consultaTodosImoveis() {
		return entityManager.createQuery("from TbImovel", TbImovel.class).getResultList();
	}

}
