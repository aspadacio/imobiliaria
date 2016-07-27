package br.com.rangosolucoes.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import br.com.rangosolucoes.repository.filter.LocatarioFilter;
import br.com.rangosolucoes.service.LocatarioService;

@Named
@SessionScoped
public class LocatarioPesquisarBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	LocatarioService locatarioService;
	
	@Getter @Setter LocatarioFilter locatarioFilter;
	
	//Listas de dados buscados no banco para serem usados como pesquisa
	@Getter @Setter private List<String> cpfs;
	@Getter @Setter private List<String> cnpjs;
	@Getter @Setter private List<String> municipios;
	@Getter @Setter private List<String> ufs;
	
	@PostConstruct
	private void init(){
		locatarioFilter = new LocatarioFilter();
		
		cpfs = new ArrayList<String>();
		cnpjs = new ArrayList<String>();
		municipios = new ArrayList<String>();
		ufs = new ArrayList<String>();
		
		//Buscando informações inseridas no DB
		retornarFiltrosDB();
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
	 * Método responsável por cadastrar um novo Locatário
	 * redireciona para página de cadastro
	 * @throws IOException 
	 * */
	public void cadastrar() throws IOException{
		FacesContext.getCurrentInstance().getExternalContext().redirect("LocatarioCadastrar.xhtml"); //change context
		return; //to the method's invoking stops
	}
	
	/**
	 * Método responsável por pesquisar um Locatário
	 * */
	public void pesquisar(){
		
	}
}
