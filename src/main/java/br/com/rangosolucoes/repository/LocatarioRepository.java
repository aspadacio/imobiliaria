package br.com.rangosolucoes.repository;

import java.io.Serializable;
import java.util.List;

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

	/**
	 * Método responsável por pegar todos os CPFs
	 * inseridos no DB e retornar.
	 * */
	@SuppressWarnings("unchecked")
	public List<String> retonarCpfs() {
		return manager.createQuery("SELECT PF.nuCpf FROM TbPessoaFisica PF").getResultList();
	}

	/**
	 * Método responsável por pegar todos os CNPJs
	 * inseridos no DB e retornar.
	 * */
	@SuppressWarnings("unchecked")
	public List<String> retonarCnpjs() {
		return manager.createQuery("SELECT PJ.nuCnpj FROM TbPessoaJuridica PJ").getResultList();
	}

	/**
	 * Método responsável por pegar todos os Municipios
	 * inseridos no DB e retornar.
	 * */
	@SuppressWarnings("unchecked")
	public List<String> retonarMunicipios() {
		return manager.createQuery("SELECT MN.noMunicipio FROM TbMunicipio MN").getResultList();
	}

	/**
	 * Método responsável por pegar todos as UFs
	 * inseridos no DB e retornar.
	 * */
	@SuppressWarnings("unchecked")
	public List<String> retonarUfs() {
		return manager.createQuery("SELECT MN.sgUf FROM TbMunicipio MN").getResultList();
	}
	
}