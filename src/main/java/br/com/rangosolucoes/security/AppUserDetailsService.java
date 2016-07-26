package br.com.rangosolucoes.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.rangosolucoes.model.TbUsuario;
import br.com.rangosolucoes.model.TbUsuarioGrupo;
//import br.com.rangosolucoes.model.Grupo;
//import br.com.rangosolucoes.model.Usuario;
import br.com.rangosolucoes.repository.UsuarioRepository;
import br.com.rangosolucoes.util.cdi.CDIServiceLocator;

public class AppUserDetailsService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UsuarioRepository usuarioRepository = CDIServiceLocator.getBean(UsuarioRepository.class);
		
		TbUsuario usuario =  usuarioRepository.porEmail(email);
		UsuarioSistema usuarioSistema = null;
		
		if(usuario != null){
			usuarioSistema =  new UsuarioSistema(usuario, getGrupos(usuario, usuarioRepository));
		}
		
		return usuarioSistema;
	}

	private Collection<? extends GrantedAuthority> getGrupos(TbUsuario usuario, UsuarioRepository repository) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		List<TbUsuarioGrupo> listaUsuariosGrupos = repository.gruposDoUsuario(usuario);
		
		for(TbUsuarioGrupo usuarioGrupo : listaUsuariosGrupos){
			authorities.add(new SimpleGrantedAuthority(usuarioGrupo.getTbGrupo().getNome().toUpperCase()));
		}
		
		return authorities;
	}

}
