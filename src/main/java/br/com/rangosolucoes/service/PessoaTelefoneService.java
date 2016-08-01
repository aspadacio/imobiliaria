package br.com.rangosolucoes.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.rangosolucoes.model.TbPessoaTelefone;
import br.com.rangosolucoes.repository.PessoaTelefoneRepository;
import br.com.rangosolucoes.util.jpa.Transacional;

public class PessoaTelefoneService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PessoaTelefoneRepository pessoaTelefoneRepository;
	
	@Transacional
	public TbPessoaTelefone salvar(TbPessoaTelefone pessoaTelefone){
		return pessoaTelefoneRepository.salvar(pessoaTelefone);
	}
}
