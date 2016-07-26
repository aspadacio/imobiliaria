package br.com.rangosolucoes.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import br.com.rangosolucoes.model.TbPessoaJuridica;

@Named("pesquisaImobiliariasBean")
@ConversationScoped
public class PesquisaImobiliariasBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<TbPessoaJuridica> pessoasJuridicas;
	private TbPessoaJuridica filtroPessoaJuridica;
	
	private TbPessoaJuridica pessoaJuridicaSelecionada;
	
	@PostConstruct
	public void init(){
		filtroPessoaJuridica = new TbPessoaJuridica();
		pessoasJuridicas = new ArrayList<>();
	}
	
	public void pesquisar(){
		
	}
	
	public void excluir(){
		
	}
	
	public String novaPesquisa(){
		filtroPessoaJuridica = new TbPessoaJuridica();
		pessoasJuridicas = new ArrayList<>();
		
		return "/imobiliaria/PesquisaImobiliaria?faces-redirect=true";
	}

	public List<TbPessoaJuridica> getPessoasJuridicas() {
		return pessoasJuridicas;
	}

	public void setPessoasJuridicas(List<TbPessoaJuridica> pessoasJuridicas) {
		this.pessoasJuridicas = pessoasJuridicas;
	}

	public TbPessoaJuridica getFiltroPessoaJuridica() {
		return filtroPessoaJuridica;
	}

	public void setFiltroPessoaJuridica(TbPessoaJuridica filtroPessoaJuridica) {
		this.filtroPessoaJuridica = filtroPessoaJuridica;
	}

	public TbPessoaJuridica getPessoaJuridicaSelecionada() {
		return pessoaJuridicaSelecionada;
	}

	public void setPessoaJuridicaSelecionada(TbPessoaJuridica pessoaJuridicaSelecionada) {
		this.pessoaJuridicaSelecionada = pessoaJuridicaSelecionada;
	}

}
