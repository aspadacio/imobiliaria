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
import br.com.rangosolucoes.repository.filter.LocadorFilter;
import br.com.rangosolucoes.service.LocadorService;
import br.com.rangosolucoes.util.jsf.FacesUtil;

@Named("locadorPesquisarBean")
@ConversationScoped
public class LocadorPesquisarBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	LocadorService locadorService;
	
	@Getter @Setter LocadorFilter locadorFilter;
	@Getter @Setter List<TbPessoa> locadores;
	
	@Getter @Setter private int idlocadorSelecionada; //pega Id do locador selecionado na tela.
	
	//Listas de dados buscados no banco para serem usados como pesquisa
	@Getter @Setter private List<String> nomeObss; //representa o conteúdo dentro de tbPessoa.dsObservaca. p.ex.: "LOCADOR TESTE (PROPRIETÁRIO)1"
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
		locadorFilter = new LocadorFilter();
		locadores = new ArrayList<TbPessoa>();
		
		nomeObss = new ArrayList<String>();
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
		FacesContext.getCurrentInstance().getExternalContext().redirect("LocadorCadastrar.xhtml"); //change context
		return; //to the method's invoking stops
	}
	
	/**
	 * Método responsável excluir um Locatário.
	 *  
	 * */
	public void excluir(){
		//TODO
	}
	
	/**
	 * Método responsável por buscar no banco de dados
	 * as informações inseridas pelo usuário que serão usadas
	 * nas combos para facilitar a pesquisa.
	 * */
	private void retornarFiltrosDB() {
		nomeObss = locadorService.retornarNomeObss();
		cnpjs = locadorService.retornarCnpjs();
		municipios = locadorService.retornarMunicipios();
		ufs = locadorService.retornarUfs();
	}

	
	/**
	 * Método responsável por pesquisar um(ns) Locatário(s) conforme filtro {@link LocadorFilter}
	 * */
	public void pesquisar(){
		validarFiltro();
		prepararFiltro();
		locadores = locadorService.buscaPessoas(this.locadorFilter);
	}

	/**
	 * Método responsável por validar se o usuário preencheu ao menos um campo de {@link LocadorFilter} para prosseguir com a pesquisa.
	 * */
	private void validarFiltro() {
		if(locadorFilter.getNomeObs() == null || locadorFilter.getNomeObs() == "" &&
				locadorFilter.getUf() == null || locadorFilter.getUf() == "" &&
				locadorFilter.getCnpj() == null || locadorFilter.getCnpj() == "" &&
				locadorFilter.getMunicipio() == null || locadorFilter.getMunicipio() == ""){
			FacesUtil.addErrorMessage("É necessário informar ao menos um parâmetro para realizar a pesquisa.");
		}
	}

	/**
	 * Método responsável por alterar {@link String} para lowerCase e tirar caracteres indevidos.
	 * */
	private void prepararFiltro() {
		if(locadorFilter.getNomeObs() != null && locadorFilter.getNomeObs() != ""){
			locadorFilter.setNomeObs(locadorFilter.getNomeObs().toUpperCase());			
		}
		if(locadorFilter.getUf() != null && locadorFilter.getUf() != ""){
			locadorFilter.setUf(locadorFilter.getUf().toUpperCase());			
		}
	}
}
