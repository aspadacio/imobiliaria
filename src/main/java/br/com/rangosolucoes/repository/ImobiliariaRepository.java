package br.com.rangosolucoes.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.rangosolucoes.model.TbPessoaJuridica;

public class ImobiliariaRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public TbPessoaJuridica salvar(TbPessoaJuridica pessoaJuridica){
		return pessoaJuridica = entityManager.merge(pessoaJuridica);
	}
	
	public TbPessoaJuridica porId(String id){
		return entityManager.find(TbPessoaJuridica.class, id);
	}
	
	public TbPessoaJuridica porCNPJ(String cnpj){
		try {
			return entityManager.createQuery("from TbPessoaJuridica where nuCnpj = :cnpj", TbPessoaJuridica.class)
					.setParameter("cnpj", cnpj).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
