package br.com.rangosolucoes.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.rangosolucoes.model.TbContrato;
import br.com.rangosolucoes.service.ContratoService;

@Named("pesquisaContratosBean")
@SessionScoped
public class PesquisaContratosBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ContratoService contratoService;
	
	private List<TbContrato> contratos;
	private TbContrato contratoSelecionado;
	
	private String nomeLocatario;
	private String descImovel;
	
	@PostConstruct
	public void init(){
		contratos = new ArrayList<>();
	}
	
	public void pesquisar(){
		contratos = contratoService.filtrados(nomeLocatario, descImovel);
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

	public String getNomeLocatario() {
		return nomeLocatario;
	}

	public void setNomeLocatario(String nomeLocatario) {
		this.nomeLocatario = nomeLocatario;
	}

	public String getDescImovel() {
		return descImovel;
	}

	public void setDescImovel(String descImovel) {
		this.descImovel = descImovel;
	}

}
