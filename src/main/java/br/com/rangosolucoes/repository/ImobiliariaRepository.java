package br.com.rangosolucoes.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.rangosolucoes.model.TbEnderecoPessoa;
import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.model.TbPessoaJuridica;
import br.com.rangosolucoes.model.TbPessoaTelefone;
import br.com.rangosolucoes.service.NegocioException;

public class ImobiliariaRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public TbPessoaJuridica salvar(TbPessoaJuridica pessoaJuridica){
		return pessoaJuridica = entityManager.merge(pessoaJuridica);
	}
	
	public void excluir(TbPessoaJuridica pessoaJuridica){
		try {
			pessoaJuridica = porCNPJ(pessoaJuridica.getNuCnpj());
			TbPessoa pessoa = consultaPessoa(pessoaJuridica.getNuCnpj());
			TbEnderecoPessoa enderecoPessoa = consultaEnderecoPessoa(pessoa.getIdPessoa());
			List<TbPessoaTelefone> telefones = consultaTelefonesPessoa(pessoa.getIdPessoa());
			for (TbPessoaTelefone tbPessoaTelefone : telefones) {
				entityManager.remove(tbPessoaTelefone);
			}
			if(enderecoPessoa != null){
				entityManager.remove(enderecoPessoa);
			}
			entityManager.remove(pessoa);
			entityManager.remove(pessoaJuridica);
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException("A imobiliária não pode ser excluída.");
		}
	}
	
	public TbEnderecoPessoa consultaEnderecoPessoa(Long idPessoa){
		try {
			return entityManager.createQuery("from TbEnderecoPessoa where tbPessoa.idPessoa = :idPessoa", TbEnderecoPessoa.class)
					.setParameter("idPessoa", idPessoa).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public TbPessoa consultaPessoa(String cnpj){
		try {
			return entityManager.createQuery("from TbPessoa where tbPessoaJuridica.nuCnpj = :cnpj", TbPessoa.class)
					.setParameter("cnpj", cnpj).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TbPessoaTelefone> consultaTelefonesPessoa(Long idPessoa){
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPessoaTelefone.class);
		
		if(idPessoa != null){
			criteria.add(Restrictions.eq("tbPessoa.idPessoa", idPessoa));
		}
		
		return criteria.list();
	}
	
	public TbPessoaJuridica porCNPJ(String cnpj){
		try {
			return entityManager.createQuery("from TbPessoaJuridica where nuCnpj = :cnpj", TbPessoaJuridica.class)
					.setParameter("cnpj", cnpj).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TbPessoaJuridica> filtrados(TbPessoaJuridica pessoaJuridica){
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPessoaJuridica.class);
		
		if(StringUtils.isNotBlank(pessoaJuridica.getNoRazaoSocial())){
			criteria.add(Restrictions.ilike("noRazaoSocial", pessoaJuridica.getNoRazaoSocial(), MatchMode.ANYWHERE));
		}
		
		if(StringUtils.isNotBlank(pessoaJuridica.getNuCnpj())){
			criteria.add(Restrictions.eq("nuCnpj", pessoaJuridica.getNuCnpj()));
		}
		
		return criteria.addOrder(Order.asc("noRazaoSocial")).list();
	}

}
