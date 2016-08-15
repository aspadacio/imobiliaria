package br.com.rangosolucoes.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.rangosolucoes.model.TbModificador;

public class ModificadorRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public TbModificador salvar(TbModificador modificador){
		return modificador = entityManager.merge(modificador);
	}

}
