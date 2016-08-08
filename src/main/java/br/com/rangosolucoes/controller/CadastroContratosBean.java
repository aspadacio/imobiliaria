package br.com.rangosolucoes.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("cadastroContratosBean")
@SessionScoped
public class CadastroContratosBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@PostConstruct
	public void init(){
		
	}
	
	public boolean isEditando(){
		return false;
	}
	
	public String novoCadastro(){
		limpar();
		return "/contratos/CadastroContratos?faces-redirect=true";
	}
	
	public void salvar(){
		if(camposPreenchidos()){
			
		}
	}
	
	public void limpar(){
		
	}
	
	public boolean camposPreenchidos(){
		boolean preenchido = true;
		return preenchido;
	}
	
	public void alteraContratoSelecionado(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		/*Valida se é um postBack ou um ValidationFailed. Só entra se for uma requisição.
		  Valida também se está enviando o atributo 'contrato'*/
		
		if(!facesContext.isPostback() && !facesContext.isValidationFailed() &&
				facesContext.getExternalContext().getRequestParameterMap().get("contrato") != null){
			
		}
	}

}
