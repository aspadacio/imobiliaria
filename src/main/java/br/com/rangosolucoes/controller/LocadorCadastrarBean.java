package br.com.rangosolucoes.controller;

import java.io.IOException;
import java.io.Serializable;
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
import br.com.rangosolucoes.repository.filter.LocadorFilter;
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
	
	//Aciona quando é clicado em NOVO para cadastrar um novo Locador
	public void novoCadastro() throws IOException{
		initClean();
		FacesContext.getCurrentInstance().getExternalContext().redirect("LocadorCadastrar.xhtml"); //change context
	}
	
	//Método responsável por limpar/inicializar os atributos locais.
	private void initClean() {
		pessoa = new TbPessoa();
		locador = new TbLocador();
		endereco = new TbEnderecoPessoa();
		locadPesJuridica = new TbPessoaJuridica();
		
		//Limpar atributos
		cnpj			= "";
		dsObservacao	= "";
		inscEstadual	= "";
		endCep			= "";
		endRua			= "";
		endNr			= "";
		endBairro		= "";
		endComplemento	= "";
		endMunicipio	= "";
		endUf			= "";
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
		FacesUtil.addInfoMessage("Locador " + pessoa.getDsObservacao() + " foi salvo com sucesso.");
		initClean();
	}
	
	/**
	 * Método responsável por validar o preenchimento antes
	 * de realizar o cadastro de um {@link TbPessoa} e {@link TbPessoaJuridica}
	 * */
	public void validar(){
		boolean isAddError = false; //"True" = Se faltou algum preenchimento
		
		//Validacoes dos campos obrigatórios
		if(dsObservacao == null || dsObservacao == ""){
			FacesUtil.addErrorMessage("É necessário informar a Descrição.");
			isAddError = true;
		}
		if(endRua == null || endRua == ""){
			FacesUtil.addErrorMessage("É necessário informar a Rua.");
			isAddError = true;
		}
		if(endCep == null || endCep == ""){
			FacesUtil.addErrorMessage("É necessário informar o CEP.");
			isAddError = true;
		}
		if(endNr == null || endNr == ""){
			FacesUtil.addErrorMessage("É necessário informar o Número.");
			isAddError = true;
		}
		if(endBairro == null || endBairro == ""){
			FacesUtil.addErrorMessage("É necessário informar o Bairro.");
			isAddError = true;
		}
		if(endMunicipio == null || endMunicipio == ""){
			FacesUtil.addErrorMessage("É necessário informar o Município.");
			isAddError = true;
		}
		if(endUf == null || endUf == ""){
			FacesUtil.addErrorMessage("É necessário informar a UF.");
			isAddError = true;
		}
		if(cnpj == "" || cnpj.isEmpty()){
				FacesUtil.addErrorMessage("É necessário informar o CNPJ.");
				isAddError = true;
		}
		
		if(isAddError){ return; } //necessário para não estourar exception na página.
		
		//--Preparando os objetos para inserção
		locadPesJuridica.setNuCnpj(cnpj.replace(".", "").replace("-", "").replace("/", ""));
		locadPesJuridica.setNuInscricaoEstadual(""); //inscEstadual.replace(".", "").replace("-", "")
		locadPesJuridica.setNoRazaoSocial(dsObservacao.toUpperCase());
		locadPesJuridica.setNoFantasia(dsObservacao.toUpperCase());
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
		
		//Pessoa
		pessoa.setDsEmail(""); //Por hora, ao Locador não é pedido o email
		pessoa.setDsObservacao(dsObservacao.toUpperCase()); //p.ex.: "LOCADOR TESTE (PROPRIETÁRIO)1"
		pessoa.setDtUltimaAlteracao(new Date());
		
		cadastrar();
	}
	
	/**
	 * Método chamado no f:event quando é necessário editar um Locador vindo da página LocadorListar.
	 * Método responsável por preencher a tela com a pessoa {@link TbPessoa} a ser editada.
	 * @param cnpj vindo através da requisição GET
	 */
	public void fillLocador2Edit(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		//Valida se é um postBack ou um ValidationFailed. Só entra se for uma requisição
	    if (!facesContext.isPostback() && !facesContext.isValidationFailed()) {
	       //buscando dados a partir do CNPJ
	    	LocadorFilter filtro = new LocadorFilter();
	    	filtro.setCnpj(cnpj);
	    	
	    	List<TbPessoa> pessoas = locadorService.buscaPessoas(filtro);
	    	TbPessoa pessoa = pessoas.get(0);//Haverá apenas uma pessoa com aquele CNPJ
	    	
	    	this.dsObservacao = pessoa.getDsObservacao();
	    	TbEnderecoPessoa endereco = locadorService.findEnderecoById(pessoa.getIdPessoa());
	    	
	    	this.endCep = Integer.toString(endereco.getNuCep());
	    	this.endRua = endereco.getDsEndereco();
	    	this.endNr = Integer.toString(endereco.getNuEndereco());
	    	this.endComplemento = endereco.getDsComplemento();
	    	this.endMunicipio = endereco.getTbMunicipio().getNoMunicipio();
	    	this.endBairro = endereco.getTbBairro().getNoBairro();
	    	this.endUf = endereco.getTbMunicipio().getSgUf();
	    }
	}
}
