package br.com.rangosolucoes.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.rangosolucoes.model.TbContrato;

@Named("pesquisaContratosBean")
@SessionScoped
public class PesquisaContratosBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<TbContrato> contratos;
	private TbContrato contratoSelecionado;
	
	@PostConstruct
	public void init(){
		
	}
	
	public void pesquisar(){
		
	}
	
	public void excluir(){
		
	}
	
	public String novaPesquisa(){
		return "";
	}

	public List<TbContrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<TbContrato> contratos) {
		this.contratos = contratos;
	}

	public TbContrato getContratoSelecionado() {
		return contratoSelecionado;
	}

	public void setContratoSelecionado(TbContrato contratoSelecionado) {
		this.contratoSelecionado = contratoSelecionado;
	}

}
