package br.com.rangosolucoes.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//import br.com.rangosolucoes.model.Grupo;
//import br.com.rangosolucoes.model.Usuario;
import br.com.rangosolucoes.repository.UsuarioRepository;
import br.com.rangosolucoes.util.cdi.CDIServiceLocator;

public class AppUserDetailsService {//implements UserDetailsService{

/*	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UsuarioRepository usuarioRepository = CDIServiceLocator.getBean(UsuarioRepository.class);
		
		Usuario usuario =  usuarioRepository.porEmail(email);
		UsuarioSistema usuarioSistema = null;
		
		if(usuario != null){
			usuarioSistema =  new UsuarioSistema(usuario, getGrupos(usuario));
		}
		
		return usuarioSistema;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for(Grupo grupo : usuario.getGrupo()){
			authorities.add(new SimpleGrantedAuthority(grupo.getNome().toUpperCase()));
		}
		
		return authorities;
	}*/

}
