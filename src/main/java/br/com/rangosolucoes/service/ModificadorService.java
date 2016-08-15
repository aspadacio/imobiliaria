package br.com.rangosolucoes.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.rangosolucoes.model.TbModificador;
import br.com.rangosolucoes.repository.ModificadorRepository;
import br.com.rangosolucoes.util.jpa.Transacional;

public class ModificadorService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ModificadorRepository modificadorRepository;
	
	@Transacional
	public TbModificador salvar(TbModificador modificador){
		return modificadorRepository.salvar(modificador);
	}

}
