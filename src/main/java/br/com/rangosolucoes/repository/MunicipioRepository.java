package br.com.rangosolucoes.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.rangosolucoes.model.TbMunicipio;

public class MunicipioRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public TbMunicipio salvar(TbMunicipio municipio){
		return municipio = manager.merge(municipio);
	}
	
	public TbMunicipio porNomeDoMunicipio(String nome){
		try {
			return manager.createQuery("from TbMunicipio where upper(noMunicipio) = :nome", TbMunicipio.class)
					.setParameter("nome", nome.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
