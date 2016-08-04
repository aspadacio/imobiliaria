package br.com.rangosolucoes.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.rangosolucoes.model.TbImovel;
import br.com.rangosolucoes.repository.ImovelRepository;
import br.com.rangosolucoes.util.jpa.Transacional;

public class ImovelService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private ImovelRepository imovelRepository;
	
	public List<TbImovel> filtrados(String nomeLocatario, String descricaoImovel){
		return imovelRepository.filtrados(nomeLocatario, descricaoImovel);
	}
	
	@Transacional
	public void excluir(TbImovel imovel){
		imovelRepository.excluir(imovel);
	}

}