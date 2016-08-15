package br.com.rangosolucoes.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.rangosolucoes.model.TbContratoModificador;
import br.com.rangosolucoes.repository.ContratoModificadorRepository;
import br.com.rangosolucoes.util.jpa.Transacional;

public class ContratoModificadorService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ContratoModificadorRepository contratoModificadorRepository;
	
	@Transacional
	public TbContratoModificador salvar(TbContratoModificador contratoModificador){
		return contratoModificadorRepository.salvar(contratoModificador);
	}

}
