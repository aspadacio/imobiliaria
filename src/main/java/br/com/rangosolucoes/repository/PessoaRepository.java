package br.com.rangosolucoes.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.rangosolucoes.model.TbPessoa;

public class PessoaRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public TbPessoa salvar(TbPessoa pessoa){
		return pessoa = manager.merge(pessoa);
	}
	
	public TbPessoa porCNPJ(String cnpj){
		try {
			return manager.createQuery("from TbPessoa where tbPessoaJuridica.nuCnpj = :cnpj", TbPessoa.class)
					.setParameter("cnpj", cnpj).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
