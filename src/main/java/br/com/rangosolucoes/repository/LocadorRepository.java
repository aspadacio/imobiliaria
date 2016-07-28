package br.com.rangosolucoes.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.rangosolucoes.model.TbBairro;
import br.com.rangosolucoes.model.TbEnderecoPessoa;
import br.com.rangosolucoes.model.TbLocador;
import br.com.rangosolucoes.model.TbMunicipio;
import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.model.TbPessoaJuridica;
import br.com.rangosolucoes.repository.filter.LocadorFilter;
import br.com.rangosolucoes.repository.filter.LocatarioFilter;

public class LocadorRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	/**
	 *  Método responsável por salvar um Locador com suas devidas entidades {@link TbPesso}, {@link TbPessoaJuridica}, {@link TbMunicipio}, {@link TbBairro}
	 * @param endereco 
	 * 
	 * */
	public void salvarPessoa(TbPessoa pessoa, TbLocador locador,
			TbPessoaJuridica locadPesJuridica, TbEnderecoPessoa endereco) {
		manager.merge(pessoa.getTbPessoaJuridica()); //TbPessoaJuridica
		
		//Persistindo Pessoa
		TbPessoa pessoaPersisted = manager.merge(pessoa); //TbPessoa
		
		//Persistindo Endereço completo: Municipio, Bairro e Endereço
		TbMunicipio municipioPersisted = null;
		TbBairro bairroPersisted = null;
		//municipio
		municipioPersisted = manager.merge(endereco.getTbMunicipio()); //TbMunicipio
			
		//bairro
		endereco.getTbBairro().setTbMunicipio(municipioPersisted);
		bairroPersisted = manager.merge(endereco.getTbBairro()); //TbBairro
			
		//endereco
		endereco.setTbMunicipio(municipioPersisted);
		endereco.setTbBairro(bairroPersisted);
		endereco.setTbPessoa(pessoaPersisted);
		manager.merge(endereco); //TbEnderecoPesso
		
	}

	/**
	 * Método responsável por retornar Pessoas (locador) conforme filtro {@link LocadorFilter}
	 * */
	@SuppressWarnings("unchecked")
	public List<TbPessoa> buscaPessoas(LocadorFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPessoa.class);
		
		//Criando os alias
		criteria.createAlias("tbPessoaJuridica", "pessoaJuridica");
		
		//add restrictions conforme filtro
		if(filtro.getNomeObs() != null && filtro.getNomeObs() != ""){
			criteria.add(Restrictions.like("dsObservacao", "%" + filtro.getNomeObs().toUpperCase() + "%"));
		}
		if(filtro.getCnpj() != null && filtro.getCnpj() != ""){
			criteria.add(Restrictions.eq("pessoaJuridica.nuCnpj", filtro.getCnpj()));
		}
		/*if(filtro.getMunicipio() != null && filtro.getMunicipio() != ""){
			criteria.add(Restrictions);
		}
		if(filtro.getUf() != null && filtro.getUf() != ""){
			criteria.add(Restrictions);
		}*/
		
		return criteria.addOrder(Order.asc("idPessoa")).list();
	}

	
}