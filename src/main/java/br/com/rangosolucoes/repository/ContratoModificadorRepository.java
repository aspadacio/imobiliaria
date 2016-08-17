package br.com.rangosolucoes.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.rangosolucoes.model.TbContrato;
import br.com.rangosolucoes.model.TbContratoModificador;

public class ContratoModificadorRepository implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	public TbContratoModificador salvar(TbContratoModificador contratoModificador) {
		return contratoModificador = entityManager.merge(contratoModificador);
	}

	/**
	 * Método responsável por retornar um objeto {@link TbContratoModificador} a partir do(s) parametro(s)
	 * @param id from {@link TbContrato} ID_CONTRATO
	 * */
	public TbContratoModificador findByContratoId(Long id) {
		return entityManager.createQuery(
				"FROM TbContratoModificador WHERE tbContrato.idContrato = :idContrato",
				TbContratoModificador.class)
		.setParameter("idContrato", id)
		.getSingleResult();
	}

}
