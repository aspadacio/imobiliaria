package br.com.rangosolucoes.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.rangosolucoes.model.TbPessoaJuridica;
import br.com.rangosolucoes.service.ImobiliariaService;
import br.com.rangosolucoes.util.jsf.FacesUtil;

@Named("pesquisaImobiliariasBean")
@SessionScoped
public class PesquisaImobiliariasBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ImobiliariaService imobiliariaService;
	
	private List<TbPessoaJuridica> pessoasJuridicas;
	private TbPessoaJuridica filtroPessoaJuridica;
	
	private TbPessoaJuridica pessoaJuridicaSelecionada;
	
	@PostConstruct
	public void init(){
		filtroPessoaJuridica = new TbPessoaJuridica();
		pessoasJuridicas = new ArrayList<>();
	}
	
	public void pesquisar(){
		pessoasJuridicas = imobiliariaService.filtrados(filtroPessoaJuridica);
	}
	
	public void excluir(){
		imobiliariaService.excluir(pessoaJuridicaSelecionada);
		pessoasJuridicas.remove(pessoaJuridicaSelecionada);
		
		FacesUtil.addInfoMessage("Imobiliária " + pessoaJuridicaSelecionada.getNoRazaoSocial() + " excluída com sucesso.");
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
