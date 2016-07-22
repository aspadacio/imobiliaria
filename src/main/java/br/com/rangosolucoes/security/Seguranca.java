package br.com.rangosolucoes.security;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Named
@RequestScoped
public class Seguranca {
	
	public String getNomeUsuario(){
		String nome = null;
		
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		/*if(usuarioLogado != null){
			nome = usuarioLogado.getUsuario().getNome();
		}*/
		return nome;
	}

	@Produces
	@UsuarioLogado
	public UsuarioSistema getUsuarioLogado() {
		UsuarioSistema usuarioSistema = null;
		
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) 
				FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		
		if(auth != null && auth.getPrincipal() != null){
			usuarioSistema = (UsuarioSistema) auth.getPrincipal();
		}
		
		return usuarioSistema;
	}
	
	public boolean isEmitirPedidoPermitido(){
		return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("ADMINISTRADORES")
				|| FacesContext.getCurrentInstance().getExternalContext().isUserInRole("VENDEDORES");
	}
	
	public boolean isCancelarPedidoPermitido(){
		return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("ADMINISTRADORES")
				|| FacesContext.getCurrentInstance().getExternalContext().isUserInRole("VENDEDORES");
	}

}
