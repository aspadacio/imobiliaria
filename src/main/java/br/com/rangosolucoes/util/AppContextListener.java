package br.com.rangosolucoes.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	/*
	 * Faz com que as variaveis do tipo Long ou Integer nao sejam convertidas
	 * automaticamente para zero quando nao tem um valor atribuido a elas.
	 * (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.setProperty("org.apache.el.parser.COERCE_TO_ZERO", "false");
	}

}
