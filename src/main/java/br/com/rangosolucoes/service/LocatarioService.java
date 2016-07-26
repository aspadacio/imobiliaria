package br.com.rangosolucoes.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.repository.LocatarioRepository;
import br.com.rangosolucoes.util.jpa.Transacional;

public class LocatarioService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LocatarioRepository locatarioRepository;
	
	@Transacional
	public void salvar(TbPessoa pessoa){
		locatarioRepository.salvar(pessoa);
	}
}
