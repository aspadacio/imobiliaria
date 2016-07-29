package br.com.rangosolucoes.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import br.com.rangosolucoes.model.TbBairro;
import br.com.rangosolucoes.model.TbEnderecoPessoa;
import br.com.rangosolucoes.model.TbLocatario;
import br.com.rangosolucoes.model.TbMunicipio;
import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.model.TbPessoaFisica;
import br.com.rangosolucoes.model.TbPessoaJuridica;
import br.com.rangosolucoes.model.TbPessoaTelefone;
import br.com.rangosolucoes.service.LocatarioService;
import br.com.rangosolucoes.util.jsf.FacesUtil;

@Named("locatarioCadastrarBean")
//@ConversationScoped :: qndo chama addTelefone() perde o conteúdo de phones
@SessionScoped
public class LocatarioCadastrarBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	LocatarioService locatarioService;
	
	@Getter @Setter private String nome;
	@Getter @Setter private String email;
	@Getter @Setter private String nmFantasia;
	@Getter @Setter private String cnpj;
	@Getter @Setter private String cpf;
	@Getter @Setter private String inscEstadual;
	@Getter @Setter private String nuTelefoneDdd;
	@Getter @Setter private String nuTelefone;
	@Getter @Setter private String tpTelefone;
	@Getter @Setter private Boolean isPessoaFisica; //"True" = Pessoa Física; "False" = Pessoa Jurídica
	@Getter @Setter private Boolean isSameAddress; //Referente 'Endereço' e 'Endereço Cobrança'. "True" = Mesmo Endereço, "False" = Endereços Diferentes.
	
	@Getter @Setter private TbPessoa pessoa;
	@Getter @Setter private TbLocatario locatario;
	@Getter @Setter private TbPessoaFisica locatPesFisica;
	@Getter @Setter private TbPessoaJuridica locatPesJuridica;
	@Getter @Setter private TbPessoaTelefone phone;
	@Getter @Setter @NonNull private List<TbLocatario> locatarios; //@nota: não faz sentido ter uma List de Locatarios dentro de TbPessoa.
	@Getter @Setter @NonNull private List<TbPessoaTelefone> phones;
	@Getter @Setter @NonNull private List<TbEnderecoPessoa> enderecos;
	@Getter @Setter @NonNull private List<TbMunicipio> municipios;
	@Getter @Setter @NonNull private List<TbBairro> bairros;
	
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
		initClean();
	}
	
	//Aciona quando é clicado em NOVO para cadastrar um novo Locatario
	public void novoCadastro() throws IOException{
		initClean();
		FacesContext.getCurrentInstance().getExternalContext().redirect("LocatarioCadastrar.xhtml"); //change context
	}
	
	//Método responsável por limpar/inicializar os atributos locais.
	private void initClean() {
		pessoa = new TbPessoa();
		locatario = new TbLocatario();
		locatPesFisica = new TbPessoaFisica();
		locatPesJuridica = new TbPessoaJuridica();
		locatarios = new ArrayList<TbLocatario>();
		enderecos = new ArrayList<TbEnderecoPessoa>();
		municipios = new ArrayList<TbMunicipio>();
		bairros = new ArrayList<TbBairro>();
		phones = new ArrayList<TbPessoaTelefone>();
		phone = new TbPessoaTelefone();
		
		//Limpar
		nome				= "";
		email				= "";
		nmFantasia			= "";
		cnpj				= "";
		cpf					= "";
		inscEstadual		= "";
		nuTelefoneDdd		= "";
		nuTelefone			= "";
		tpTelefone			= "";
		endRua				= "";
		endBairro			= "";
		endMunicipio		= "";
		endUf				= "";
		endComplemento		= "";
		endCep				= "";
		endNr				= "";
		endCbRua			= "";
		endCbBairro			= "";
		endCbMunicipio		= "";
		endCbUf				= "";	
		endCbComplemento	= "";
		endCbCep			= "";
		endCbNr				= "";
		endCbTel			= "";
	}
	
	/**
	 * Método responsável por pesquisar um Locatário
	 * Retorna para página de pesquisa
	 * @throws IOException 
	 * */
	public void pesquisar() throws IOException{
		initClean();
		FacesContext.getCurrentInstance().getExternalContext().redirect("LocatarioListar.xhtml"); //change context
		return; //to the method's invoking stops
		//return "/imobiliaria/LocatarioListar?faces-redirect=true";
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
	}
	
	/**
	 * Método responsável por copiar todos os dados inserido em "Endereço" para "Endereço Cobrança"
	 *
	 * */
	public void copyEndereco2EnderecoCob(){
		endCbCep = endCep;
		endCbRua = endRua;
		endCbNr = endNr;
		endCbBairro = endBairro;
		endCbMunicipio = endMunicipio;
		endCbUf = endUf;
		endCbComplemento = endComplemento;
		
		isSameAddress = true; //Identifica que o "Endereço" é semelhante ao "Endereço Cobrança"
	}
	
	/**
	 * Método responsável por cadastrar um novo Locatário
	 *
	 * */
	public void cadastrar(){
		//Chamar service para cadastrar Pessoa com os dados da tela LocatarioCadastrar
		locatarioService.salvarPessoa(pessoa, phones, enderecos, municipios, bairros, isPessoaFisica, isSameAddress);
	}
	
	/**
	 * Método responsável por validar o preenchimento antes
	 * de realizar o cadastro de um {@link TbPessoa}
	 * */
	public void validar(){
		boolean isAddError = false; //"True" = Se faltou algum preenchimento
		
		//Validacoes dos campos obrigatórios
		if( nome == null || nome.equals("") ){
			FacesUtil.addErrorMessage("É necessário informar o nome.");
			isAddError = true;
		}
		if( email == null || email.equals("") ){
			FacesUtil.addErrorMessage("É necessário informar o e-mail.");
			isAddError = true;
		}
		if(phones.isEmpty() || !(phones.size() > 0)){
			FacesUtil.addErrorMessage("É necessário informar ao menos um telefone.");
			isAddError = true;
		}
		if(endRua == null || endRua == "" ||
				endCbRua == null || endCbRua == ""){
			FacesUtil.addErrorMessage("É necessário informar a Rua.");
			isAddError = true;
		}
		if(endNr == null || endNr == "" ||
				endCbNr == null || endCbNr == ""){
			FacesUtil.addErrorMessage("É necessário informar o Número.");
			isAddError = true;
		}
		if(endBairro == null || endBairro == "" ||
				endCbBairro == null || endCbBairro == ""){
			FacesUtil.addErrorMessage("É necessário informar o Bairro.");
			isAddError = true;
		}
		if(endMunicipio == null || endMunicipio == "" ||
				endCbMunicipio == null || endCbMunicipio == ""){
			FacesUtil.addErrorMessage("É necessário informar o Município.");
			isAddError = true;
		}
		if(endUf == null || endUf == "" ||
				endCbUf == null || endCbUf == ""){
			FacesUtil.addErrorMessage("É necessário informar a UF.");
			isAddError = true;
		}
		
		if(isPessoaFisica == null){
			FacesUtil.addErrorMessage("É necessário informar se é Pessoa ou Empresa.");
			isAddError = true;
		}else{
			//verificar CNPJ ou CPF
			if( isPessoaFisica ){
				if(cpf == "" || cpf.isEmpty()){
					FacesUtil.addErrorMessage("É necessário informar o CPF.");
					isAddError = true;
				}
			}else{
				if(cnpj == "" || cnpj.isEmpty()){
					FacesUtil.addErrorMessage("É necessário informar o CNPJ.");
					isAddError = true;
				}
			}
		}
		
		if(isAddError){ return; } //necessário para não estourar exception na página.
		
		//--Preparando os objetos para inserção
		//Pessoa
		if(isPessoaFisica){
			locatPesFisica.setNuCpf(cpf.replace(".", "").replace("-", ""));
			locatPesFisica.setNoPessoaFisica(nome.toUpperCase());
			pessoa.setTbPessoaFisica(locatPesFisica);
		}else{
			locatPesJuridica.setNuCnpj(cnpj.replace(".", "").replace("-", "").replace("/", ""));
			locatPesJuridica.setNuInscricaoEstadual(inscEstadual.replace(".", "").replace("-", ""));
			locatPesJuridica.setNoRazaoSocial(nmFantasia.toUpperCase());
			locatPesJuridica.setNoFantasia(nmFantasia.toUpperCase());
			locatPesJuridica.setNoContato(nmFantasia.toUpperCase()); //Nome Contato. Setar a nmFantasia por hora.
			pessoa.setTbPessoaJuridica(locatPesJuridica);
		}
		
		//--Preparando os endereços para colocar no objeto Pesso
		//Endereço
		TbEnderecoPessoa endereco = new TbEnderecoPessoa();
		TbMunicipio municipio = new TbMunicipio();
		TbBairro bairro = new TbBairro();
		
		municipio.setNoMunicipio(endMunicipio.toUpperCase());
		municipio.setSgUf(endUf.toUpperCase());
		
		bairro.setNoBairro(endBairro.toUpperCase());
		
		endereco.setNuCep(Integer.parseInt(endCep.replace("-", "").toUpperCase()));
		endereco.setDsEndereco(endRua.toUpperCase());
		endereco.setNuEndereco(Integer.parseInt(endNr));
		endereco.setDsComplemento(endComplemento.toUpperCase());
		endereco.setTpEndereco('R');
		endereco.setTbMunicipio(municipio);
		endereco.setTbBairro(bairro);
		
		enderecos.add(endereco);
		municipios.add(municipio);
		bairros.add(bairro);
		
		//Endereço Cobrança
		endereco = new TbEnderecoPessoa();
		municipio = new TbMunicipio();
		bairro = new TbBairro();
		
		municipio.setNoMunicipio(endCbMunicipio.toUpperCase());
		municipio.setSgUf(endCbUf.toUpperCase());
		
		bairro.setNoBairro(endCbBairro.toUpperCase());
		
		endereco.setNuCep(Integer.parseInt(endCbCep.replace("-", "")));
		endereco.setDsEndereco(endCbRua.toUpperCase());
		endereco.setNuEndereco(Integer.parseInt(endCbNr));
		endereco.setDsComplemento(endCbComplemento.toUpperCase());
		endereco.setTpEndereco('C'); //Indefinido, por hora
		endereco.setTbMunicipio(municipio);
		endereco.setTbBairro(bairro);
		
		enderecos.add(endereco);
		municipios.add(municipio);
		bairros.add(bairro);
		//--END-Preparando os endereços para colocar no objeto Pesso
		
		//Locatario
		locatario.setDtCadastro(new Date());
		locatarios.add(locatario);
		
		//Pessoa
		pessoa.setDsEmail(email.toUpperCase());
		pessoa.setDtUltimaAlteracao(new Date());
		//pessoa.setTbEnderecoPessoas(enderecos);
		//pessoa.setTbPessoaTelefones(phones);
		//pessoa.setTbLocatarios(locatarios);
		
		cadastrar();
	}
}
