package br.com.rangosolucoes.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
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
	
	@Getter @Setter @NonNull private String nome;
	@Getter @Setter @NonNull private String email;
	@Getter @Setter private String nmFantasia;
	@Getter @Setter private String cnpj;
	@Getter @Setter private String cpf;
	@Getter @Setter private String inscEstadual;
	@Getter @Setter private Boolean isPessoaFisica;
	@Getter @Setter private String nuTelefoneDdd;
	@Getter @Setter private String nuTelefone;
	@Getter @Setter private String tpTelefone;
	
	@Getter @Setter private TbPessoa locatario;
	@Getter @Setter private TbPessoaFisica locatPesFisica;
	@Getter @Setter private TbPessoaJuridica locatPesJuridica;
	@Getter @Setter private TbPessoaTelefone phone;
	@Getter @Setter @NonNull private List<TbPessoaTelefone> phones;
	@Getter @Setter @NonNull private List<TbEnderecoPessoa> enderecos;
	
	//Endereco
	@Getter @Setter @NonNull private String endRua;
	@Getter @Setter @NonNull private String endBairro;
	@Getter @Setter @NonNull private String endMunicipio;
	@Getter @Setter @NonNull private String endUf;
	@Getter @Setter @NonNull private String endComplemento;
	@Getter @Setter @NonNull private String endCep;
	@Getter @Setter @NonNull private String endNr;
	
	//Endereco de Cobranca
	@Getter @Setter @NonNull private String endCbRua;
	@Getter @Setter @NonNull private String endCbBairro;
	@Getter @Setter @NonNull private String endCbMunicipio;
	@Getter @Setter @NonNull private String endCbUf;
	@Getter @Setter @NonNull private String endCbComplemento;
	@Getter @Setter @NonNull private String endCbCep;
	@Getter @Setter @NonNull private String endCbNr;
	@Getter @Setter @NonNull private String endCbTel;
	
	@PostConstruct
	private void init(){
		locatario = new TbPessoa();
		locatPesFisica = new TbPessoaFisica();
		locatPesJuridica = new TbPessoaJuridica();
		phones = new ArrayList<TbPessoaTelefone>();
		phone = new TbPessoaTelefone();
		
		//inicializar
		nuTelefoneDdd = "";
		nuTelefone = "";
		tpTelefone = "";
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
		if(nuTelefone != "" && nuTelefone != null &&
				nuTelefoneDdd != "" && nuTelefoneDdd != null &&
				tpTelefone != "" && tpTelefone != null){
			
			phone.setNuTelefoneDdd(nuTelefoneDdd.replace("(", "").replace(")", ""));
			phone.setNuTelefone(Integer.valueOf(nuTelefone.replace("-", "")));
			phone.setTpTelefone(tpTelefone.charAt(0));
			phones.add(phone);
			
			nuTelefoneDdd = "";
			nuTelefone = "";
			tpTelefone = "";
			phone = new TbPessoaTelefone();
		}else{
			FacesUtil.addErrorMessage("É necessário informar todos os dados do telefone.");
		}
		return;
	}
	
	/**
	 * Método responsável por cadastrar um novo Locatário
	 *
	 * */
	public void cadastrar(){
		//Preparar o objeto
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
		//--Preparando os objetos para inserção
		//Pessoa
		if(isPessoaFisica){
			locatPesFisica.setNuCpf(cpf);
			locatPesFisica.setNoPessoaFisica(nome);
			locatario.setTbPessoaFisica(locatPesFisica);
		}else{
			locatPesJuridica.setNuCnpj(cnpj);
			locatPesJuridica.setNoRazaoSocial(nmFantasia);
			locatPesJuridica.setNuInscricaoEstadual(inscEstadual);
			locatario.setTbPessoaJuridica(locatPesJuridica);
		}
		locatario.setDsEmail(email);
		locatario.setDsObservacao("");
		locatario.setDtUltimaAlteracao(new Date());
		
		cadastrar();
	}
}
