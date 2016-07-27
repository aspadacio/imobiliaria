package br.com.rangosolucoes.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.rangosolucoes.model.TbBairro;
import br.com.rangosolucoes.repository.BairroRepository;

public class BairroService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private BairroRepository bairroRepository;
	
	public TbBairro salvar(TbBairro bairro){
		TbBairro bairroExistente = bairroRepository.porNomeDoBairro(bairro.getNoBairro());
		
		if(bairroExistente != null && bairroExistente.equals(bairro)){
			throw new NegocioException("Já existe um Bairro com o nome informado.");
		}
		
		return bairroRepository.salvar(bairro);
	}

}
