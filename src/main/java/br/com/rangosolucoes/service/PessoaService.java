package br.com.rangosolucoes.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.repository.PessoaRepository;
import br.com.rangosolucoes.util.jpa.Transacional;

public class PessoaService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PessoaRepository pessoaRepository;
	
	@Transacional
	public TbPessoa salvar(TbPessoa pessoa){
		return pessoaRepository.salvar(pessoa);
	}
	
	public List<TbPessoa> consultaTodosFiadores(){
		return pessoaRepository.consultaTodosFiadores();
	}
	
	@Transacional
	public TbPessoa porId(Long idPessoa){
		return pessoaRepository.porId(idPessoa);
	}

}
