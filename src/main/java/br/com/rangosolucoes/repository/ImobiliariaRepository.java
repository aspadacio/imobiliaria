package br.com.rangosolucoes.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.rangosolucoes.model.TbPessoaJuridica;

public class ImobiliariaRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public TbPessoaJuridica porId(String id){
		return entityManager.find(TbPessoaJuridica.class, id);
	}

}
