package br.com.rangosolucoes.repository;

import java.io.Serializable;

public class ProdutoRepository implements Serializable {

	private static final long serialVersionUID = 1L;

/*	@Inject
	private EntityManager manager;

	public Produto guardar(Produto produto) {
		return produto = manager.merge(produto);
	}
	
	@Transacional
	public void remover(Produto produto){
		try {
			produto = porId(produto.getId());
			manager.remove(produto);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Produto não pode ser excluído.");
		}
	}

	public Produto porSku(String sku) {
		try {
			return (Produto) manager.createQuery("from Produto where upper(sku) = :sku", Produto.class)
					.setParameter("sku", sku.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> filtrados(ProdutoFilter filter){
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Produto.class);
		
		if(StringUtils.isNotBlank(filter.getSku())){
			criteria.add(Restrictions.eq("sku", filter.getSku()));			
		}
		
		if(StringUtils.isNotBlank(filter.getNome())){
			criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
		}
		
		return criteria.addOrder(Order.asc("nome")).list();
	}

	public Produto porId(Long id) {
		return manager.find(Produto.class, id);
	}

	public List<Produto> porNome(String nome) {
		return this.manager.createQuery("from Produto where upper(nome) like :nome", Produto.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}*/

}
