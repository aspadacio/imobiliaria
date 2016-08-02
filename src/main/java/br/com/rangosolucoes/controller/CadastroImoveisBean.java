package br.com.rangosolucoes.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.rangosolucoes.enumeration.Estados;
import br.com.rangosolucoes.model.TbImovel;
import br.com.rangosolucoes.model.TbLocador;
import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.model.TbPessoaFisica;

@Named("cadastroImoveisBean")
@SessionScoped
public class CadastroImoveisBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private TbImovel imovel;
	private TbLocador locador;
	private TbPessoa pessoa;
	private TbPessoaFisica pessoaFisica;
	
	private String sgUF;
	private String nuCep;
	
	@PostConstruct
	public void init(){
		limpar();
	}
	
	public boolean isEditando(){
		return this.imovel.getIdImovel() != null;
	}
	
	public String novoCadastro(){
		limpar();
		return "/imoveis/CadastroImoveis?faces-redirect=true";
	}
	
	public void salvar(){
		
	}
	
	public void limpar(){
		imovel = new TbImovel();
		locador = new TbLocador();
		pessoa = new TbPessoa();
		pessoaFisica = new TbPessoaFisica();
	}
	
	public boolean camposPreenchidos(){
		boolean preenchido = true;
		return preenchido;
	}
	
	public void alteraImovelSelecionado(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		/*Valida se é um postBack ou um ValidationFailed. Só entra se for uma requisição.
		 * Valisa também se está enviando o atributo 'imovel' */
		
		if(!facesContext.isPostback() && !facesContext.isValidationFailed() &&
				facesContext.getExternalContext().getRequestParameterMap().get("imovel") != null){
			
		}
	}
	
	public Estados[] getEstados(){
		return Estados.values();
	}

	public TbImovel getImovel() {
		return imovel;
	}

	public void setImovel(TbImovel imovel) {
		this.imovel = imovel;
	}

	public TbPessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(TbPessoa pessoa) {
		this.pessoa = pessoa;
	}

	public TbPessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(TbPessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public TbLocador getLocador() {
		return locador;
	}

	public void setLocador(TbLocador locador) {
		this.locador = locador;
	}

	public String getSgUF() {
		return sgUF;
	}

	public void setSgUF(String sgUF) {
		this.sgUF = sgUF;
	}

	public String getNuCep() {
		return nuCep;
	}

	public void setNuCep(String nuCep) {
		this.nuCep = nuCep;
	}

}
