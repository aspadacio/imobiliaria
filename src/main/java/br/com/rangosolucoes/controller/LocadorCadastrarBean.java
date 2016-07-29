package br.com.rangosolucoes.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import br.com.rangosolucoes.model.TbBairro;
import br.com.rangosolucoes.model.TbEnderecoPessoa;
import br.com.rangosolucoes.model.TbLocador;
import br.com.rangosolucoes.model.TbMunicipio;
import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.model.TbPessoaJuridica;
import br.com.rangosolucoes.service.LocadorService;
import br.com.rangosolucoes.util.jsf.FacesUtil;

@Named("locadorCadastrarBean")
@ConversationScoped
public class LocadorCadastrarBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	LocadorService locadorService;
	
	@Getter @Setter private String cnpj;
	@Getter @Setter private String dsObservacao; //usar este atribudo para colocar a "Descrição" do Locador. Será setado em tbPessoa.dsObservacao
	@Getter @Setter private String inscEstadual;
	
	@Getter @Setter private TbPessoa pessoa;
	@Getter @Setter private TbLocador locador;
	@Getter @Setter private TbEnderecoPessoa endereco;
	@Getter @Setter private TbPessoaJuridica locadPesJuridica;
	@Getter @Setter private List<TbLocador> locadores;
	
	//Endereco
	@Getter @Setter @NonNull private String endCep;
	@Getter @Setter @NonNull private String endRua;
	@Getter @Setter @NonNull private String endNr;
	@Getter @Setter @NonNull private String endBairro;
	@Getter @Setter @NonNull private String endComplemento;
	@Getter @Setter @NonNull private String endMunicipio;
	@Getter @Setter @NonNull private String endUf;
	
	@PostConstruct
	private void init(){
		initClean();
	}
	
	//Método responsável por limpar/inicializar os atributos locais.
	private void initClean() {
		pessoa = new TbPessoa();
		locador = new TbLocador();
		endereco = new TbEnderecoPessoa();
		locadPesJuridica = new TbPessoaJuridica();
		locadores = new ArrayList<TbLocador>();
	}
	
	/**
	 * Método responsável por pesquisar um Locador
	 * Retorna para página de pesquisa
	 * @throws IOException 
	 * */
	public void pesquisar() throws IOException{
		initClean();
		FacesContext.getCurrentInstance().getExternalContext().redirect("LocadorListar.xhtml"); //change context
		return;
	}
	
	/**
	 * Método responsável por cadastrar um novo Locatário
	 *
	 * */
	public void cadastrar(){
		//Chamar service para cadastrar Pessoa com os dados da tela LocadorCadastrar
		locadorService.salvarPessoa(pessoa, locador, locadPesJuridica, endereco);
	}
	
	/**
	 * Método responsável por validar o preenchimento antes
	 * de realizar o cadastro de um {@link TbPessoa} e {@link TbPessoaJuridica}
	 * */
	public void validar(){
		//Validacoes dos campos obrigatórios
		if(dsObservacao == null || dsObservacao == ""){
			FacesUtil.addErrorMessage("É necessário informar a Descrição.");
		}
		if(endRua == null || endRua == ""){
			FacesUtil.addErrorMessage("É necessário informar a Rua.");
		}
		if(endNr == null || endNr == ""){
			FacesUtil.addErrorMessage("É necessário informar o Número.");
		}
		if(endBairro == null || endBairro == ""){
			FacesUtil.addErrorMessage("É necessário informar o Bairro.");
		}
		if(endMunicipio == null || endMunicipio == ""){
			FacesUtil.addErrorMessage("É necessário informar o Município.");
		}
		if(endUf == null || endUf == ""){
			FacesUtil.addErrorMessage("É necessário informar a UF.");
		}
		if(cnpj == "" || cnpj.isEmpty()){
				FacesUtil.addErrorMessage("É necessário informar o CNPJ.");
		}
		
		//--Preparando os objetos para inserção
		locadPesJuridica.setNuCnpj(cnpj.replace(".", "").replace("-", "").replace("/", ""));
		locadPesJuridica.setNuInscricaoEstadual(inscEstadual.replace(".", "").replace("-", ""));
		locadPesJuridica.setNoRazaoSocial("");
		locadPesJuridica.setNoFantasia("");
		locadPesJuridica.setNoContato(""); //Nome Contato.
		pessoa.setTbPessoaJuridica(locadPesJuridica);
		
		//Endereço
		endereco = new TbEnderecoPessoa();
		TbMunicipio municipio = new TbMunicipio();
		TbBairro bairro = new TbBairro();
		
		municipio.setNoMunicipio(endMunicipio.toUpperCase());
		municipio.setSgUf(endUf.toUpperCase());
		
		bairro.setNoBairro(endBairro.toUpperCase());
		
		endereco.setNuCep(Integer.parseInt(endCep.replace("-", "").toUpperCase()));
		endereco.setDsEndereco(endRua.toUpperCase());
		endereco.setNuEndereco(Integer.parseInt(endNr));
		endereco.setDsComplemento(endComplemento.toUpperCase());
		endereco.setTpEndereco('R'); //Indefinido, por hora
		endereco.setTbMunicipio(municipio);
		endereco.setTbBairro(bairro);
		
		//Locador
		locador.setDtCadastro(new Date());
		
		locadores.add(locador);
		
		//Pessoa
		pessoa.setDsEmail(""); //Por hora, ao Locador não é pedido o email
		pessoa.setDsObservacao(dsObservacao.toUpperCase()); //p.ex.: "LOCADOR TESTE (PROPRIETÁRIO)1"
		pessoa.setTbLocadors(locadores);
		
		cadastrar();
	}
}
