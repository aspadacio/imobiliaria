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

public class ImovelRepository implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public TbImovel porId(Long id){
		try {
			return entityManager.createQuery("from TbImovel where idImovel = :id", TbImovel.class)
					.setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TbImovel> filtrados(String nomeLocatario, String descricaoImovel) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbImovel.class, "imovel");
		criteria.createAlias("imovel.tbLocatario", "locatario");
		criteria.createAlias("locatario.tbPessoa", "pessoa");
		criteria.createAlias("pessoa.tbPessoaFisica", "pessoafisica");
		
		if(StringUtils.isNotBlank(nomeLocatario)){
			criteria.add(Restrictions.ilike("pessoafisica.noPessoaFisica", nomeLocatario, MatchMode.ANYWHERE));
		}
		
		if(StringUtils.isNotBlank(descricaoImovel)){
			criteria.add(Restrictions.ilike("dsImovel", descricaoImovel, MatchMode.ANYWHERE));
		}
		
		return criteria.list();
	}

}
