package br.com.rangosolucoes.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.rangosolucoes.model.TbPessoa;

public class LocatarioRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public void salvar(TbPessoa pessoa) {
		// TODO Auto-generated method stub
	}
	
	
}
