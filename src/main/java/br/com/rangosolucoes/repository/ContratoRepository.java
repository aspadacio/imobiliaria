package br.com.rangosolucoes.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

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

}
