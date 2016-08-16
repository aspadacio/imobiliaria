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
	
	@Transacional
	public TbImovel salvar(TbImovel imovel){
		return imovelRepository.salvar(imovel);
	}
	
	public List<TbImovel> filtrados(String nomeLocador, String descricaoImovel){
		return imovelRepository.filtrados(nomeLocador, descricaoImovel);
	}
	
	@Transacional
	public void excluir(TbImovel imovel){
		imovelRepository.excluir(imovel);
	}
	
	@Transacional
	public TbImovel porId(Long id){
		return imovelRepository.porId(id);
	}
	
	public List<TbImovel> consultaTodosImoveis() {
		return imovelRepository.consultaTodosImoveis();
	}

	/**
	 * Método responsável por retornar o objeto {@link TbImovel} a partir do parâmetro
	 * @param locatarioId ID_LOCATARIO
	 * */
	@Transacional
	public TbImovel findByLocatarioId(Long locatarioId) {
		return imovelRepository.findByLocatarioId(locatarioId);
	}

}
