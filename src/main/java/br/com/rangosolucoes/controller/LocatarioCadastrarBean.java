package br.com.rangosolucoes.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.rangosolucoes.model.TbEnderecoPessoa;
import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.model.TbPessoaFisica;
import br.com.rangosolucoes.model.TbPessoaJuridica;
import br.com.rangosolucoes.model.TbPessoaTelefone;
import br.com.rangosolucoes.util.jsf.FacesUtil;

@Named
@SessionScoped
public class LocatarioCadastrarBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String email;
	private String nmFantasia;
	private String cnpj;
	private String cpf;
	private String inscEstadual;
	private String nuTelefoneDdd;
	private String nuTelefone;
	
	//Endereco
	private String endRua;
	private String endBairro;
	private String endMunicipio;
	private String endUf;
	private String endComplemento;
	private String endCep;
	private String endNr;
	
	//Endereco de Cobranca
	private String endCbRua;
	private String endCbBairro;
	private String endCbMunicipio;
	private String endCbUf;
	private String endCbComplemento;
	private String endCbCep;
	private String endCbNr;
	private String endCbTel;
	
	private Boolean isPessoaFisica;
	private TbPessoa locatario;
	private TbPessoaFisica locatPesFisica;
	private TbPessoaJuridica locatPesJuridica;
	private TbPessoaTelefone phone;
	private List<TbPessoaTelefone> phones;
	private List<TbEnderecoPessoa> enderecos;
	
	@PostConstruct
	private void init(){
		locatario = new TbPessoa();
		locatPesFisica = new TbPessoaFisica();
		locatPesJuridica = new TbPessoaJuridica();
		phone = new TbPessoaTelefone();
		phones = new ArrayList<TbPessoaTelefone>();
		
		nuTelefone = "";
		nuTelefoneDdd = "";
	}
	
	/**
	 * Método responsável por pesquisar um Locatário
	 * Retorna para página de pesquisa
	 * @throws IOException 
	 * */
	public void pesquisar() throws IOException{
		FacesContext.getCurrentInstance().getExternalContext().redirect("LocatarioListar.xhtml"); //change context
		return; //to the method's invoking stops
	}
	
	/**
	 * Método responsável por adicionar um {@link TbPessoaTelefone}
	 * no locatário que está sendo cadastrado.
	 * */
	public void addTelefone(){
		phones.add(phone);
		phone = new TbPessoaTelefone();
		return;
	}
	
	/**
	 * Método responsável por validar o preenchimento antes
	 * de realizar o cadastro de um {@link TbPessoa}
	 * */
	public void validar(){
		//Validacoes dos campos obrigatórios
		if( nome == null || nome.equals("") ){
			FacesUtil.addErrorMessage("É necessário informar o nome.");
			return;
		}
		if( email == null || email.equals("") ){
			FacesUtil.addErrorMessage("É necessário informar o e-mail.");
			return;
		}
		if(phones.isEmpty() || !(phones.size() > 0)){
			FacesUtil.addErrorMessage("É necessário informar ao menos um telefone.");
			return;
		}
		//verificar CNPJ ou CPF
		if( isPessoaFisica ){
			if(cpf == "" || cpf.isEmpty()){
				FacesUtil.addErrorMessage("É necessário informar o CPF.");
				return;
			}
		}else{
			if(cnpj == "" || cnpj.isEmpty()){
				FacesUtil.addErrorMessage("É necessário informar o CNPJ.");
				return;
			}
		}
		cadastrar();
	}
	
	/**
	 * Método responsável por cadastrar um novo Locatário
	 *
	 * */
	public void cadastrar(){
		//Preparar o objeto
	}

	/**
	 * GETTERS & SETTERS
	 * */
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsPessoaFisica() {
		return isPessoaFisica;
	}

	public void setIsPessoaFisica(Boolean isPessoaFisica) {
		this.isPessoaFisica = isPessoaFisica;
	}

	public List<TbPessoaTelefone> getPhones() {
		return phones;
	}

	public void setPhones(List<TbPessoaTelefone> phone) {
		this.phones = phone;
	}

	public String getNmFantasia() {
		return nmFantasia;
	}

	public void setNmFantasia(String nmFantasia) {
		this.nmFantasia = nmFantasia;
	}

	public List<TbEnderecoPessoa> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<TbEnderecoPessoa> enderecos) {
		this.enderecos = enderecos;
	}

	public String getEndRua() {
		return endRua;
	}

	public void setEndRua(String endRua) {
		this.endRua = endRua;
	}

	public String getEndBairro() {
		return endBairro;
	}

	public void setEndBairro(String endBairro) {
		this.endBairro = endBairro;
	}

	public String getEndMunicipio() {
		return endMunicipio;
	}

	public void setEndMunicipio(String endMunicipio) {
		this.endMunicipio = endMunicipio;
	}

	public String getEndComplemento() {
		return endComplemento;
	}

	public void setEndComplemento(String endComplemento) {
		this.endComplemento = endComplemento;
	}

	public String getEndCbRua() {
		return endCbRua;
	}

	public void setEndCbRua(String endCbRua) {
		this.endCbRua = endCbRua;
	}

	public String getEndCbBairro() {
		return endCbBairro;
	}

	public void setEndCbBairro(String endCbBairro) {
		this.endCbBairro = endCbBairro;
	}

	public String getEndCbMunicipio() {
		return endCbMunicipio;
	}

	public void setEndCbMunicipio(String endCbMunicipio) {
		this.endCbMunicipio = endCbMunicipio;
	}

	public String getEndCbComplemento() {
		return endCbComplemento;
	}

	public void setEndCbComplemento(String endCbComplemento) {
		this.endCbComplemento = endCbComplemento;
	}

	public TbPessoa getLocatario() {
		return locatario;
	}

	public void setLocatario(TbPessoa locatario) {
		this.locatario = locatario;
	}

	public TbPessoaFisica getLocatPesFisica() {
		return locatPesFisica;
	}

	public void setLocatPesFisica(TbPessoaFisica locatPesFisica) {
		this.locatPesFisica = locatPesFisica;
	}

	public TbPessoaJuridica getLocatPesJuridica() {
		return locatPesJuridica;
	}

	public void setLocatPesJuridica(TbPessoaJuridica locatPesJuridica) {
		this.locatPesJuridica = locatPesJuridica;
	}

	public TbPessoaTelefone getPhone() {
		return phone;
	}

	public void setPhone(TbPessoaTelefone phone) {
		this.phone = phone;
	}

	public String getNuTelefoneDdd() {
		return nuTelefoneDdd;
	}

	public void setNuTelefoneDdd(String nuTelefoneDdd) {
		this.nuTelefoneDdd = nuTelefoneDdd;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getInscEstadual() {
		return inscEstadual;
	}

	public void setInscEstadual(String inscEstadual) {
		this.inscEstadual = inscEstadual;
	}

	public String getNuTelefone() {
		return nuTelefone;
	}

	public void setNuTelefone(String nuTelefone) {
		this.nuTelefone = nuTelefone;
	}

	public String getEndCep() {
		return endCep;
	}

	public void setEndCep(String endCep) {
		this.endCep = endCep;
	}

	public String getEndNr() {
		return endNr;
	}

	public void setEndNr(String endNr) {
		this.endNr = endNr;
	}

	public String getEndCbCep() {
		return endCbCep;
	}

	public void setEndCbCep(String endCbCep) {
		this.endCbCep = endCbCep;
	}

	public String getEndCbNr() {
		return endCbNr;
	}

	public void setEndCbNr(String endCbNr) {
		this.endCbNr = endCbNr;
	}

	public String getEndCbTel() {
		return endCbTel;
	}

	public void setEndCbTel(String endCbTel) {
		this.endCbTel = endCbTel;
	}

	public String getEndUf() {
		return endUf;
	}

	public void setEndUf(String endUf) {
		this.endUf = endUf;
	}

	public String getEndCbUf() {
		return endCbUf;
	}

	public void setEndCbUf(String endCbUf) {
		this.endCbUf = endCbUf;
	}
}
