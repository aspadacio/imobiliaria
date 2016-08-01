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
		TbPessoaJuridica pessoaJuridicaExistente = imobiliariaRepository.porCNPJ(pessoaJuridica.getNuCnpj());
		
		if(pessoaJuridicaExistente != null && pessoaJuridicaExistente.equals(pessoaJuridica)){
			throw new NegocioException("Já existe uma Pessoa Jurídica com o CNPJ informado.");
		}
		
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