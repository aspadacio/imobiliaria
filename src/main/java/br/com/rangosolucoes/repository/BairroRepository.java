package br.com.rangosolucoes.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.rangosolucoes.model.TbBairro;

public class BairroRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public TbBairro salvar(TbBairro bairro){
		return bairro = manager.merge(bairro);
	}
	
	public TbBairro porNomeDoBairro(String nome){
		try {
			return manager.createQuery("from TbBairro where upper(noBairro) = :nome", TbBairro.class)
					.setParameter("nome", nome.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
