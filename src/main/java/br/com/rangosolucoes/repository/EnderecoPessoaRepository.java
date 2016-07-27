package br.com.rangosolucoes.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.rangosolucoes.model.TbEnderecoPessoa;
import br.com.rangosolucoes.model.TbMunicipio;
import br.com.rangosolucoes.model.TbPessoa;

public class EnderecoPessoaRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public TbEnderecoPessoa salvar(TbEnderecoPessoa enderecoPessoa){
		return enderecoPessoa = manager.merge(enderecoPessoa);
	}
	
	public TbEnderecoPessoa porPessoaEMunicipio(TbPessoa pessoa, TbMunicipio municipio){
		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbEnderecoPessoa.class);
		
		if(pessoa.getIdPessoa() != null){
			criteria.add(Restrictions.eq("tbPessoa.idPessoa", pessoa.getIdPessoa()));
		}
		
		if(municipio.getIdMunicipio() != null){
			criteria.add(Restrictions.eq("tbMunicipio.idMunicipio", municipio.getIdMunicipio()));
		}
		
		return (TbEnderecoPessoa) criteria.uniqueResult();
	}

}
