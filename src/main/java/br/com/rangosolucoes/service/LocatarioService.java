package br.com.rangosolucoes.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.repository.LocatarioRepository;
import br.com.rangosolucoes.util.jpa.Transacional;

public class LocatarioService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LocatarioRepository locatarioRepository;
	
	@Transacional
	public void salvarPessoa(TbPessoa pessoa, Boolean isPessoaFisica){
		locatarioRepository.salvarPessoa(pessoa, isPessoaFisica);
	}

	@Transacional
	public List<String> retornarCpfs() {
		return locatarioRepository.retonarCpfs();
	}

	public List<String> retornarCnpjs() {
		return locatarioRepository.retonarCnpjs();
	}

	public List<String> retornarMunicipios() {
		return locatarioRepository.retonarMunicipios();
	}

	public List<String> retornarUfs() {
		return locatarioRepository.retonarUfs();
	}
}
