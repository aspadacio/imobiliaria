package br.com.rangosolucoes.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.rangosolucoes.model.TbPessoaJuridica;
import br.com.rangosolucoes.repository.ImobiliariaRepository;
import br.com.rangosolucoes.util.jpa.Transacional;

public class ImobiliariaService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ImobiliariaRepository imobiliariaRepository;
	
	@Transacional
	public TbPessoaJuridica salvar(TbPessoaJuridica pessoaJuridica){
		return imobiliariaRepository.salvar(pessoaJuridica);
	}
	
	@Transacional
	public void excluir(TbPessoaJuridica pessoaJuridica){
		imobiliariaRepository.excluir(pessoaJuridica);
	}
	
	public List<TbPessoaJuridica> filtrados(TbPessoaJuridica pessoaJuridica){
		return imobiliariaRepository.filtrados(pessoaJuridica);
	}

}
