package br.com.rangosolucoes.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.rangosolucoes.model.TbPessoaJuridica;
import br.com.rangosolucoes.repository.ImobiliariaRepository;

public class CadastroImobiliariaService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ImobiliariaRepository imobiliariaRepository;
	
	public TbPessoaJuridica salvar(TbPessoaJuridica pessoaJuridica){
		return null;
	}

}
