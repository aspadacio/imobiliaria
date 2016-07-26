package br.com.rangosolucoes.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import br.com.rangosolucoes.model.TbBairro;
import br.com.rangosolucoes.model.TbEnderecoPessoa;
import br.com.rangosolucoes.model.TbMunicipio;
import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.model.TbPessoaJuridica;
import br.com.rangosolucoes.model.TbPessoaTelefone;

@Named("cadastroImobiliariasBean")
@ConversationScoped
public class CadastroImobiliariasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private TbPessoaJuridica pessoaJuridica;
	private TbPessoaTelefone pessoaTelefone;
	private TbPessoa pessoa;
	private TbEnderecoPessoa enderecoPessoa;
	private TbMunicipio municipio;
	private TbBairro bairro;
	
	private List<TbPessoaTelefone> telefones;

	@PostConstruct
	public void init() {
		pessoaJuridica = new TbPessoaJuridica();
		pessoaTelefone = new TbPessoaTelefone();
		pessoa = new TbPessoa();
		enderecoPessoa = new TbEnderecoPessoa();
		municipio = new TbMunicipio();
		bairro = new TbBairro();
		telefones = new ArrayList<>();
		
		limpar();
	}

	public boolean isEditando() {
		return this.pessoaJuridica.getNuCnpj() != null;
	}

	public String novoCadastro() {
		pessoaJuridica = new TbPessoaJuridica();
		limpar();

		return "/imobiliaria/CadastroImobiliaria?faces-redirect=true";
	}

	public String salvar() {
		return "";
	}

	public void limpar() {

	}
	
	public void adicinaTelefone(){
		
	}

	public TbPessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}

	public void setPessoaJuridica(TbPessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}

	public TbPessoaTelefone getPessoaTelefone() {
		return pessoaTelefone;
	}

	public void setPessoaTelefone(TbPessoaTelefone pessoaTelefone) {
		this.pessoaTelefone = pessoaTelefone;
	}

	public TbPessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(TbPessoa pessoa) {
		this.pessoa = pessoa;
	}

	public TbEnderecoPessoa getEnderecoPessoa() {
		return enderecoPessoa;
	}

	public void setEnderecoPessoa(TbEnderecoPessoa enderecoPessoa) {
		this.enderecoPessoa = enderecoPessoa;
	}

	public TbMunicipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(TbMunicipio municipio) {
		this.municipio = municipio;
	}

	public TbBairro getBairro() {
		return bairro;
	}

	public void setBairro(TbBairro bairro) {
		this.bairro = bairro;
	}

	public List<TbPessoaTelefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<TbPessoaTelefone> telefones) {
		this.telefones = telefones;
	}

}
