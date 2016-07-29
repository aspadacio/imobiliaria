package br.com.rangosolucoes.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.rangosolucoes.model.TbBairro;
import br.com.rangosolucoes.model.TbEnderecoPessoa;
import br.com.rangosolucoes.model.TbLocatario;
import br.com.rangosolucoes.model.TbMunicipio;
import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.model.TbPessoaFisica;
import br.com.rangosolucoes.model.TbPessoaJuridica;
import br.com.rangosolucoes.model.TbPessoaTelefone;
import br.com.rangosolucoes.repository.LocatarioRepository;
import br.com.rangosolucoes.repository.filter.LocatarioFilter;
import br.com.rangosolucoes.util.jpa.Transacional;

public class LocatarioService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LocatarioRepository locatarioRepository;
	
	/**
	 * Método responsável por salvar um Locatário com suas devidas entidades {@link TbPesso}, {@link TbPessoaFisica}, {@link TbPessoaJuridica}, {@link TbPessoaTelefone} e {@link TbMunicipio}
	 * */
	@Transacional
	public void salvarPessoa(TbPessoa pessoa, List<TbPessoaTelefone> phones, List<TbEnderecoPessoa> enderecos, List<TbMunicipio> municipios, List<TbBairro> bairros, Boolean isPessoaFisica, Boolean isSameAddress){
		locatarioRepository.salvarPessoa(pessoa, phones, enderecos, municipios, bairros, isPessoaFisica, isSameAddress);
	}

	@Transacional
	public List<String> retornarCpfs() {
		return locatarioRepository.retonarCpfs();
	}

	@Transacional
	public List<String> retornarCnpjs() {
		return locatarioRepository.retonarCnpjs();
	}

	@Transacional
	public List<String> retornarMunicipios() {
		return locatarioRepository.retonarMunicipios();
	}

	@Transacional
	public List<String> retornarUfs() {
		return locatarioRepository.retonarUfs();
	}

	/**
	 * Método responsável por retornar Pessoas (locatários) conforme filtro {@link LocatarioFilter}
	 * */
	@Transacional
	public List<TbPessoa> buscaPessoas(LocatarioFilter locatarioFilter) {
		return locatarioRepository.buscaPessoas(locatarioFilter);
	}
	
	/**
	 * Método responsável por remover Locatário {@link TbLocatario} e suas dependências.
	 * */
	@Transacional
	public void remover(int idLocatarioSelecionada) {
		locatarioRepository.remover(idLocatarioSelecionada);
	}
}
