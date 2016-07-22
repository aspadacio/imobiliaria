package br.com.rangosolucoes.util.mail;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class Mailer implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/*@Inject
	private SessionConfig config;
	
	public MailMessage novaMensagem(){
		return new MailMessageImpl(config);
	}*/

}
