package br.com.rangosolucoes.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.rangosolucoes.model.TbMunicipio;
import br.com.rangosolucoes.repository.MunicipioRepository;
import br.com.rangosolucoes.util.jpa.Transacional;

public class MunicipioService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MunicipioRepository municipioRepository;
	
	@Transacional
	public TbMunicipio salvar(TbMunicipio municipio){
		TbMunicipio municipioExistente = municipioRepository.porNomeDoMunicipio(municipio.getNoMunicipio());
		
		if(municipioExistente != null && municipioExistente.equals(municipio)){
			throw new NegocioException("Já existe um Município com o nome informado.");
		}
		
		return municipioRepository.salvar(municipio);
	}

}
