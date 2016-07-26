package br.com.rangosolucoes.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.model.TbPessoaTelefone;

public class LocatarioRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public void salvarPessoa(TbPessoa pessoa, Boolean isPessoaFisica) {
		//Persistindo Pessoa Física / Jurídica
		if(isPessoaFisica){
			manager.merge(pessoa.getTbPessoaFisica());
		}else{
			manager.merge(pessoa.getTbPessoaJuridica());
		}
		
		//Persistindo Pessoa
		TbPessoa pessoaPesisted = manager.merge(pessoa);
		
		//Persistindo Telefone(s)
		for(TbPessoaTelefone telefone : pessoa.getTbPessoaTelefones()){
			telefone.setTbPessoa(pessoaPesisted); //Usado para setar a FK_PESSOATELEFONE_PESSOA
			manager.merge(telefone);
		}
	}
	
}