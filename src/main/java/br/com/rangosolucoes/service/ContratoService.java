package br.com.rangosolucoes.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.rangosolucoes.model.TbContrato;
import br.com.rangosolucoes.repository.ContratoRepository;
import br.com.rangosolucoes.util.jpa.Transacional;

public class ContratoService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ContratoRepository contratoRepository;
	
	@Transacional
	public TbContrato salvar(TbContrato contrato){
		return contratoRepository.salvar(contrato);
	}
	
	public List<TbContrato> filtrados(String nomeLocatario, String descImovel){
		return contratoRepository.filtrados(nomeLocatario, descImovel);
	}

}
