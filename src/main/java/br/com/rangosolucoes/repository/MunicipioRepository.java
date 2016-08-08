package br.com.rangosolucoes.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
			List<TbMunicipio> municipios = new ArrayList<>();
			municipios = manager.createQuery("from TbMunicipio where upper(noMunicipio) = :nome", TbMunicipio.class)
					.setParameter("nome", nome.toUpperCase()).getResultList();
			for (TbMunicipio tbMunicipio : municipios) {
				return tbMunicipio;
			}
		} catch (NoResultException e) {
			return null;
		}
		return null;
	}

}
