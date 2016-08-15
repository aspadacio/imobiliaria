package br.com.rangosolucoes.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.rangosolucoes.model.TbContratoModificador;

public class ContratoModificadorRepository implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	public TbContratoModificador salvar(TbContratoModificador contratoModificador) {
		return contratoModificador = entityManager.merge(contratoModificador);
	}

}
