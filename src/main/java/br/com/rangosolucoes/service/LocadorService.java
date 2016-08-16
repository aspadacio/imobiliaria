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

	/**
	 * Método responsável por retornar todos os CNPJ dos Locadores cadastrados.
	 * */
	@Transacional
	public List<String> retornarCnpjs() {
		return locadorRepository.retornarCnpjs();
	}
	
	/**
	 * Método responsável por retornar todos os Municipios.
	 * */
	@Transacional
	public List<String> retornarMunicipios() {
		return locadorRepository.retornarMunicipios();
	}

	/**
	 * Método responsável por retornar todos as UFs.
	 * */
	@Transacional
	public List<String> retornarUfs() {
		return locadorRepository.retornarUfs();
	}

	/**
	 * Método responsável por remover Locador {@link TbLocador} e suas dependências.
	 * */
	@Transacional
	public void remover(TbPessoa locadorSelecionado) {
		locadorRepository.remover(locadorSelecionado);
	}
	
	/**
	 * Método responsável por retornar o Endereco {@link TbEnderecoPessoa} a partir do id ID_PESSOA
	 * */
	public TbEnderecoPessoa findEnderecoById(Long idPessoa){
		return locadorRepository.findEnderecoById(idPessoa);
	}

	/**
	 * Método responsável por retornar a pessoa {@link TbPessoa} a partir do id ID_PESSOA
	 * */
	@Transacional
	public TbPessoa findPessoaById(Long idLocatarioSelecionada) {
		return locadorRepository.findPessoaById(idLocatarioSelecionada);
	}
	
	public List<TbLocador> consultaTodosLocadores() {
		return locadorRepository.consultaTodosLocadores();
	}
	
	@Transacional
	public TbLocador locadorPorId(Long id){
		return locadorRepository.locadorPorId(id);
	}
	
	/**
	 * Método responsável por retornar a Pessoa Jurídica {@link TbPessoaJuridica} a partir do cnpj NU_CNPJ
	 * */
	@Transacional
	public TbPessoaJuridica findPessoaJuridicaById(String nuCnpj) {
		return locadorRepository.findPessoaJuridicaById(nuCnpj);
	}
}
