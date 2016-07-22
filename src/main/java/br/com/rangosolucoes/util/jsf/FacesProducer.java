package br.com.rangosolucoes.util.jsf;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * FIXME
 * Classe inutil
 */
public class FacesProducer {
	
	public FacesContext getFacesContext(){
		return FacesContext.getCurrentInstance();
	}
	
	public ExternalContext getExternalContext(){
		return getFacesContext().getExternalContext();
	}
	
	public HttpServletRequest getHttpServletRequest(){
		return (HttpServletRequest) getExternalContext().getRequest();
	}
	
	public HttpServletResponse getHttpServletResponse(){
		return (HttpServletResponse) getExternalContext().getResponse();
	}

}
