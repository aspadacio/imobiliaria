package br.com.rangosolucoes.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.rangosolucoes.model.TbUsuario;

public class UsuarioRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public TbUsuario porId(Long id){
		return this.manager.find(TbUsuario.class, id);
	}
	
	public List<TbUsuario> usuarios(){
		// filtrar apenas por usuarios de um grupo especifico
		return this.manager.createQuery("from TbUsuario", TbUsuario.class)
				.getResultList();
	}

	public TbUsuario porEmail(String email) {
		TbUsuario usuario = null;
		
		try {
			usuario = this.manager.createQuery("from TbUsuario where lower(email) = :email", TbUsuario.class)
					.setParameter("email", email.toLowerCase())
					.getSingleResult();
		} catch (NoResultException e) {
			// nenhum usuario encontrado com o email informado
			System.out.println("UsuarioRepository::porEmail - Erro ao retornar usuário da base.");
		} catch(Exception e){
			System.out.println("UsuarioRepository::porEmail - Erro ao conectar-se a base.");
			System.out.println(e.getCause().getMessage());
		}
		
		return usuario;
	}

}
