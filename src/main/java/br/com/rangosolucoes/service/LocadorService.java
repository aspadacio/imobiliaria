package br.com.rangosolucoes.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.rangosolucoes.model.TbBairro;
import br.com.rangosolucoes.model.TbEnderecoPessoa;
import br.com.rangosolucoes.model.TbLocador;
import br.com.rangosolucoes.model.TbMunicipio;
import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.model.TbPessoaJuridica;
import br.com.rangosolucoes.repository.LocadorRepository;
import br.com.rangosolucoes.repository.filter.LocadorFilter;
import br.com.rangosolucoes.util.jpa.Transacional;

public class LocadorService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LocadorRepository locadorRepository;
	
	/**
	 * Método responsável por salvar um Locador com suas devidas entidades {@link TbPesso}, {@link TbPessoaJuridica}, {@link TbMunicipio}, {@link TbBairro}
	 * @param endereco 
	 * */
	@Transacional
	public void salvarPessoa(TbPessoa pessoa, TbLocador locador, TbPessoaJuridica locadPesJuridica, TbEnderecoPessoa endereco){
		locadorRepository.salvarPessoa(pessoa, locador, locadPesJuridica, endereco);
	}
	
	/**
	 * Método responsável por retornar um Locador a partir dos filtros de pesquisa.
	 * */
	@Transacional
	public List<TbPessoa> buscaPessoas(LocadorFilter locadorFilter) {
		return locadorRepository.buscaPessoas(locadorFilter);
	}

	public List<String> retornarNomeObss() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> retornarCnpjs() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> retornarMunicipios() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> retornarUfs() {
		// TODO Auto-generated method stub
		return null;
	}
}
