package br.com.rangosolucoes.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.rangosolucoes.model.TbPessoa;

@Named
@SessionScoped
public class LocatarioPesquisarBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private List<TbPessoa> locatarios;

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
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<TbPessoa> getLocatarios() {
		return locatarios;
	}

	public void setLocatarios(List<TbPessoa> locatarios) {
		this.locatarios = locatarios;
	}
}
