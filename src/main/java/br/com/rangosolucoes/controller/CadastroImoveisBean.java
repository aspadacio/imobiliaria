package br.com.rangosolucoes.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.rangosolucoes.enumeration.Estados;
import br.com.rangosolucoes.model.TbBairro;
import br.com.rangosolucoes.model.TbImovel;
import br.com.rangosolucoes.model.TbLocador;
import br.com.rangosolucoes.model.TbMunicipio;
import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.model.TbPessoaFisica;
import br.com.rangosolucoes.service.BairroService;
import br.com.rangosolucoes.service.ImovelService;
import br.com.rangosolucoes.service.LocadorService;
import br.com.rangosolucoes.service.MunicipioService;
import br.com.rangosolucoes.util.jsf.FacesUtil;

@Named("cadastroImoveisBean")
@SessionScoped
public class CadastroImoveisBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LocadorService locadorService;
	
	@Inject
	private BairroService bairroService;
	
	@Inject
	private MunicipioService municipioService;
	
	@Inject
	private ImovelService imovelService;
	
	private TbImovel imovel;
	private TbLocador locador;
	private TbPessoa pessoa;
	private TbPessoaFisica pessoaFisica;
	private TbBairro bairro;
	private TbMunicipio municipio;
	
	private List<TbLocador> locadores;
	
	private String sgUF;
	private String nuCep;
	private String nuEndereco;
	private String nomeBairro;
	private String nomeMunicipio;
	
	@PostConstruct
	public void init(){
		limpar();
	}
	
	public boolean isEditando(){
		return this.imovel.getIdImovel() != null;
	}
	
	public String novoCadastro(){
		limpar();
		return "/imoveis/CadastroImoveis?faces-redirect=true";
	}
	
	public void salvar(){
		if(camposPreenchidos()){
			municipio.setSgUf(sgUF);
			municipio.setNoMunicipio(nomeMunicipio);
			municipio = municipioService.salvar(municipio);
			
			bairro.setTbMunicipio(municipio);
			bairro.setNoBairro(nomeBairro);
			bairro = bairroService.salvar(bairro);
			
			boolean existeImovel = imovelService.porId(imovel.getIdImovel()) != null;
			
			if(existeImovel){
				FacesUtil.addInfoMessage("Imóvel alterado com sucesso!");
			}else{
				FacesUtil.addInfoMessage("Imóvel cadastrado com sucesso!");
			}
			
			imovel.setTbLocador(locador);
			imovel.setNuEndereco(Integer.parseInt(nuEndereco));
			imovel.setTbBairro(bairro);
			imovel.setTbMunicipio(municipio);
			imovelService.salvar(imovel);
			
			limpar();
		}
	}
	
	public void limpar(){
		imovel = new TbImovel();
		locador = new TbLocador();
		pessoa = new TbPessoa();
		pessoaFisica = new TbPessoaFisica();
		
		sgUF = "";
		nuCep = "";
		nuEndereco = "";
		nomeBairro = "";
		nomeMunicipio = "";
	}
	
	public boolean camposPreenchidos(){
		boolean preenchido = true;
		
		if(locador == null){
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Locador é obrigatório.");
		}
		
		if(imovel.getDsImovel() == null || imovel.getDsImovel() == ""){
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Descrição é obrigatório.");
		}
		
		if(imovel.getNuCep() == null || imovel.getNuCep() == ""){
			preenchido = false;
			FacesUtil.addErrorMessage("O campo CEP é obrigatório.");
		}
		
		if(imovel.getDsEndereco() == null || imovel.getDsEndereco() == ""){
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Endereço é obrigatório.");
		}
		
		if(nuEndereco == null || nuEndereco == ""){
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Número é obrigatório.");
		}
		
		if(nomeMunicipio == null || nomeMunicipio == ""){
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Município é obrigatório.");
		}
		
		if(sgUF == null || sgUF == ""){
			preenchido = false;
			FacesUtil.addErrorMessage("O campo UF é obrigatório.");
		}
		
		return preenchido;
	}
	
	public void alteraImovelSelecionado(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		/*Valida se é um postBack ou um ValidationFailed. Só entra se for uma requisição.
		 * Valisa também se está enviando o atributo 'imovel' */
		
		if(!facesContext.isPostback() && !facesContext.isValidationFailed() &&
				facesContext.getExternalContext().getRequestParameterMap().get("imovel") != null){
			
		}
	}
	
	public Estados[] getEstados(){
		return Estados.values();
	}

	public TbImovel getImovel() {
		return imovel;
	}

	public void setImovel(TbImovel imovel) {
		this.imovel = imovel;
	}

	public TbPessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(TbPessoa pessoa) {
		this.pessoa = pessoa;
	}

	public TbPessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(TbPessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public TbLocador getLocador() {
		return locador;
	}

	public void setLocador(TbLocador locador) {
		this.locador = locador;
	}

	public String getSgUF() {
		return sgUF;
	}

	public void setSgUF(String sgUF) {
		this.sgUF = sgUF;
	}

	public String getNuCep() {
		return nuCep;
	}

	public void setNuCep(String nuCep) {
		this.nuCep = nuCep;
	}

	public String getNuEndereco() {
		return nuEndereco;
	}

	public void setNuEndereco(String nuEndereco) {
		this.nuEndereco = nuEndereco;
	}

	public List<TbLocador> getLocadores() {
		if(locadores == null){
			locadores = new ArrayList<>();
		}
		if(locadores.size() == 0){
			if(FacesUtil.isNotPostBack()){
				locadores = locadorService.consultaTodosLocadores();
			}
		}
		return locadores;
	}

	public void setLocadores(List<TbLocador> locadores) {
		this.locadores = locadores;
	}

	public String getNomeBairro() {
		return nomeBairro;
	}

	public void setNomeBairro(String nomeBairro) {
		this.nomeBairro = nomeBairro;
	}

	public String getNomeMunicipio() {
		return nomeMunicipio;
	}

	public void setNomeMunicipio(String nomeMunicipio) {
		this.nomeMunicipio = nomeMunicipio;
	}

	public TbBairro getBairro() {
		return bairro;
	}

	public void setBairro(TbBairro bairro) {
		this.bairro = bairro;
	}

	public TbMunicipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(TbMunicipio municipio) {
		this.municipio = municipio;
	}

}
