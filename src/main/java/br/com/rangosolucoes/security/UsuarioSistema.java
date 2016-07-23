package br.com.rangosolucoes.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.rangosolucoes.model.TbUsuario;;

public class UsuarioSistema extends User {

	private static final long serialVersionUID = 1L;

	private TbUsuario usuario;

	public UsuarioSistema(TbUsuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getSenha(), authorities);
		this.usuario = usuario;
	}

	public TbUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(TbUsuario usuario) {
		this.usuario = usuario;
	}

}
