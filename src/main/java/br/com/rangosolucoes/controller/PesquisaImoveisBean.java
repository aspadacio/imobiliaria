package br.com.rangosolucoes.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.rangosolucoes.model.TbImovel;
import br.com.rangosolucoes.service.ImovelService;
import br.com.rangosolucoes.util.jsf.FacesUtil;

@Named("pesquisaImoveisBean")
@SessionScoped
public class PesquisaImoveisBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ImovelService imovelService;
	
	private List<TbImovel> imoveis;
	private String nomeLocatario;
	private String descricaoImovel;
	
	private TbImovel imovelSelecionado;
	
	@PostConstruct
	public void init(){
		imoveis = new ArrayList<>();
	}
	
	public void pesquisar(){
		imoveis = imovelService.filtrados(this.nomeLocatario, this.descricaoImovel);
	}
	
	public void excluir(){
		imovelService.excluir(imovelSelecionado);
		imoveis.remove(imovelSelecionado);
		
		FacesUtil.addInfoMessage("Imóvel " + imovelSelecionado.getDsImovel() + " excluído com sucesso.");
	}
	
	public String novaPesquisa(){
		return "/imoveis/PesquisaImoveis?faces-redirect=true";
	}

	public List<TbImovel> getImoveis() {
		return imoveis;
	}

	public void setImoveis(List<TbImovel> imoveis) {
		this.imoveis = imoveis;
	}

	public TbImovel getImovelSelecionado() {
		return imovelSelecionado;
	}

	public void setImovelSelecionado(TbImovel imovelSelecionado) {
		this.imovelSelecionado = imovelSelecionado;
	}

	public String getNomeLocatario() {
		return nomeLocatario;
	}

	public void setNomeLocatario(String nomeLocatario) {
		this.nomeLocatario = nomeLocatario;
	}

	public String getDescricaoImovel() {
		return descricaoImovel;
	}

	public void setDescricaoImovel(String descricaoImovel) {
		this.descricaoImovel = descricaoImovel;
	}

}
