package br.com.rangosolucoes.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.repository.filter.LocatarioFilter;
import br.com.rangosolucoes.service.LocatarioService;
import br.com.rangosolucoes.util.jsf.FacesUtil;

@Named("locatarioPesquisarBean")
@ConversationScoped
public class LocatarioPesquisarBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	LocatarioService locatarioService;
	
	@Getter @Setter LocatarioFilter locatarioFilter;
	@Getter @Setter List<TbPessoa> locatarios;
	
	@Getter @Setter private int idLocatarioSelecionada; //pega Id do Locatario selecionado na tela.
	
	//Listas de dados buscados no banco para serem usados como pesquisa
	@Getter @Setter private List<String> cpfs;
	@Getter @Setter private List<String> cnpjs;
	@Getter @Setter private List<String> municipios;
	@Getter @Setter private List<String> ufs;
	
	@PostConstruct
	private void init(){
		initClean();
		
		//Buscando informações inseridas no DB
		retornarFiltrosDB();
	}
	
	//Método responsável por limpar/inicializar os atributos locais.
	private void initClean() {
		locatarioFilter = new LocatarioFilter();
		locatarios = new ArrayList<TbPessoa>();
		
		cpfs = new ArrayList<String>();
		cnpjs = new ArrayList<String>();
		municipios = new ArrayList<String>();
		ufs = new ArrayList<String>();
	}
	
	/**
	 * Método responsável por cadastrar um novo Locatário
	 * redireciona para página de cadastro
	 * @throws IOException 
	 * */
	public void cadastrar() throws IOException{
		FacesContext.getCurrentInstance().getExternalContext().redirect("LocatarioCadastrar.xhtml"); //change context
		return; //to the method's invoking stops
		//return "/imobiliaria/LocatarioCadastrar?faces-redirect=true";
	}
	
	/**
	 * Método responsável excluir um Locatário.
	 *  
	 * */
	public void excluir(){
		locatarioService.remover(idLocatarioSelecionada);
	}
	
	/**
	 * Método responsável por buscar no banco de dados
	 * as informações inseridas pelo usuário que serão usadas
	 * nas combos para facilitar a pesquisa.
	 * */
	private void retornarFiltrosDB() {
		cpfs = locatarioService.retornarCpfs();
		cnpjs = locatarioService.retornarCnpjs();
		municipios = locatarioService.retornarMunicipios();
		ufs = locatarioService.retornarUfs();
	}

	
	/**
	 * Método responsável por pesquisar um(ns) Locatário(s) conforme filtro {@link LocatarioFilter}
	 * */
	public void pesquisar(){
		validarFiltro();
		prepararFiltro();
		locatarios = locatarioService.buscaPessoas(this.locatarioFilter);
	}

	/**
	 * Método responsável por validar se o usuário preencheu ao menos um campo de {@link LocatarioFilter} para prosseguir com a pesquisa.
	 * */
	private void validarFiltro() {
		if(locatarioFilter.getNome() == null || locatarioFilter.getNome() == "" &&
				locatarioFilter.getCpf() == null || locatarioFilter.getCpf() == "" &&
				locatarioFilter.getUf() == null || locatarioFilter.getUf() == "" &&
				locatarioFilter.getCnpj() == null || locatarioFilter.getCnpj() == "" &&
				locatarioFilter.getMunicipio() == null || locatarioFilter.getMunicipio() == ""){
			FacesUtil.addErrorMessage("É necessário informar ao menos um parâmetro para realizar a pesquisa.");
		}
	}

	/**
	 * Método responsável por alterar {@link String} para lowerCase e tirar caracteres indevidos.
	 * */
	private void prepararFiltro() {
		if(locatarioFilter.getNome() != null && locatarioFilter.getNome() != ""){
			locatarioFilter.setNome(locatarioFilter.getNome().toLowerCase());			
		}
		if(locatarioFilter.getUf() != null && locatarioFilter.getUf() != ""){
			locatarioFilter.setUf(locatarioFilter.getUf().toLowerCase());			
		}
	}
}
