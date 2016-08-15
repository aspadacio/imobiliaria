package br.com.rangosolucoes.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.rangosolucoes.model.TbContrato;

public class ContratoRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public TbContrato porId(Long idContrato){
		return entityManager.createQuery("from TbContrato where idContrato = :idContrato", TbContrato.class)
				.setParameter("idContrato", idContrato)
				.getSingleResult();
	}

	public TbContrato salvar(TbContrato contrato) {
		return contrato = entityManager.merge(contrato);
	}
	
	@SuppressWarnings("unchecked")
	public List<TbContrato> filtrados(String nomeLocatario, String descImovel){
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbContrato.class, "contrato");
		criteria.createAlias("contrato.tbLocatario", "locatario");
		criteria.createAlias("locatario.tbPessoa", "pessoa");
		criteria.createAlias("pessoa.tbPessoaFisica", "pessoaFisica");
		
		if(StringUtils.isNotBlank(nomeLocatario)){
			criteria.add(Restrictions.ilike("pessoaFisica.noPessoaFisica", nomeLocatario, MatchMode.ANYWHERE));
		}
		
		return criteria.list();
	}

}
