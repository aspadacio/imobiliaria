package br.com.rangosolucoes.util.jpa;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Interceptor
@Transacional
public class TransactionInterceptor implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	@AroundInvoke
	public Object invoke(InvocationContext context) throws Exception{
		EntityTransaction transaction = entityManager.getTransaction();
		boolean criador = false;
		
		try {
			if(!transaction.isActive()){
				/*
				 * truque para fazer rollback no que ja passou
				 * (senao, um futuro commit, confirmaria ate mesmo operacoes sem transacao)
				 */
				transaction.begin();
				transaction.rollback();
				
				//agora sim inicia a transacao
				transaction.begin();
				
				criador = true;
			}
			
			return context.proceed();
		} catch (Exception e) {
			if(transaction != null && criador){
				transaction.rollback();
			}
			throw e;
		} finally {
			if(transaction != null && transaction.isActive() && criador){
				transaction.commit();
			}
		}
	}
	
}
