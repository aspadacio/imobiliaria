package br.com.rangosolucoes.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.rangosolucoes.model.TbEnderecoPessoa;
import br.com.rangosolucoes.repository.EnderecoPessoaRepository;
import br.com.rangosolucoes.util.jpa.Transacional;

public class EnderecoPessoaService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EnderecoPessoaRepository enderecoPessoaRepository;
	
	@Transacional
	public TbEnderecoPessoa salvar(TbEnderecoPessoa enderecoPessoa){
		return enderecoPessoaRepository.salvar(enderecoPessoa);
	}

}
