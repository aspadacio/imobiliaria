package br.com.rangosolucoes.repository;

import java.io.Serializable;

public class UsuarioRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
/*	@Inject
	private EntityManager manager;
	
	public Usuario porId(Long id){
		return this.manager.find(Usuario.class, id);
	}
	
	public List<Usuario> vendedores(){
		// filtrar apenas vendedores por um grupo especifico
		return this.manager.createQuery("from Usuario", Usuario.class)
				.getResultList();
	}

	public Usuario porEmail(String email) {
		Usuario usuario = null;
		
		try {
			usuario = this.manager.createQuery("from Usuario where lower(email) = :email", Usuario.class)
					.setParameter("email", email.toLowerCase())
					.getSingleResult();
		} catch (NoResultException e) {
			// nenhum usuario encontrado com o email informado
		}
		
		return usuario;
	}*/

}
