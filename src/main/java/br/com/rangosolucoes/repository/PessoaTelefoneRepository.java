package br.com.rangosolucoes.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.rangosolucoes.model.TbPessoaTelefone;

public class PessoaTelefoneRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public TbPessoaTelefone salvar(TbPessoaTelefone pessoaTelefone){
		return pessoaTelefone = manager.merge(pessoaTelefone);
	}
	
	public TbPessoaTelefone porNumeroTelefone(int numero){
		try {
			return manager.createQuery("from TbPessoaTelefone where nuTelefone = :num", TbPessoaTelefone.class)
					.setParameter("num", numero).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
