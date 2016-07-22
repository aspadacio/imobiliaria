package br.com.rangosolucoes.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import br.com.rangosolucoes.util.jsf.FacesUtil;

@Named
@SessionScoped
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String email;
	
	public void preRender(){
		if(((ServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("invalid") != null
				&& ((ServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("invalid").equals("true")){
			FacesUtil.addErrorMessage("Usuário ou senha inválido!");
		}
	}
	
	public void login() throws ServletException, IOException{
		RequestDispatcher dispatcher = ((ServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward((ServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest(), (ServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse());
		
		FacesContext.getCurrentInstance().responseComplete();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
