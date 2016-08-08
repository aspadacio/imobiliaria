package br.com.rangosolucoes.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.rangosolucoes.model.TbContrato;
import br.com.rangosolucoes.model.TbContratoModificador;
import br.com.rangosolucoes.model.TbImovel;
import br.com.rangosolucoes.model.TbLocatario;
import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.service.ImovelService;
import br.com.rangosolucoes.service.LocatarioService;
import br.com.rangosolucoes.service.PessoaService;
import br.com.rangosolucoes.util.jsf.FacesUtil;

@Named("cadastroContratosBean")
@SessionScoped
public class CadastroContratosBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LocatarioService locatarioService;
	
	@Inject
	private ImovelService imovelService;
	
	@Inject
	private PessoaService pessoaService;
	
	private TbContrato contrato;
	private TbContratoModificador contratoModificador;
	private TbContratoModificador contratoModificadorDespesas;
	
	private Long idLocatario;
	private Long idContrato;
	private Long idPessoaFiador;
	private String nomeModificador;
	private String descricaoModificador;
	private String nomeModificadorDespesas;
	private String descricaoModificadorDespesas;
	
	private List<TbLocatario> locatarios;
	private List<TbImovel> imoveis;
	private List<TbPessoa> fiadores;
	private List<TbContratoModificador> contratosModificador;
	private List<TbContratoModificador> contratosModificadorDespesas; 
	
	@PostConstruct
	public void init(){
		
	}
	
	public boolean isEditando(){
		return false;
	}
	
	public String novoCadastro(){
		limpar();
		return "/contratos/CadastroContratos?faces-redirect=true";
	}
	
	public void salvar(){
		if(camposPreenchidos()){
			
		}
	}
	
	public void limpar(){
		
	}
	
	public boolean camposPreenchidos(){
		boolean preenchido = true;
		return preenchido;
	}
	
	public void alteraContratoSelecionado(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		/*Valida se é um postBack ou um ValidationFailed. Só entra se for uma requisição.
		  Valida também se está enviando o atributo 'contrato'*/
		
		if(!facesContext.isPostback() && !facesContext.isValidationFailed() &&
				facesContext.getExternalContext().getRequestParameterMap().get("contrato") != null){
			
		}
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public List<TbImovel> getImoveis() {
		if(imoveis == null){
			imoveis = new ArrayList<>();
		}
		if(imoveis.size() == 0){
			if(FacesUtil.isNotPostBack()){
				imoveis = imovelService.consultaTodosImoveis();
			}
		}
		return imoveis;
	}

	public void setImoveis(List<TbImovel> imoveis) {
		this.imoveis = imoveis;
	}
	
	public List<TbPessoa> getFiadores() {
		if(fiadores == null){
			fiadores = new ArrayList<>();
		}
		if(fiadores.size() == 0){
			if(FacesUtil.isNotPostBack()){
				fiadores = pessoaService.consultaTodosFiadores();
			}
		}
		return fiadores;
	}

	public void setFiadores(List<TbPessoa> fiadores) {
		this.fiadores = fiadores;
	}

	public Long getIdPessoaFiador() {
		return idPessoaFiador;
	}

	public void setIdPessoaFiador(Long idPessoaFiador) {
		this.idPessoaFiador = idPessoaFiador;
	}

	public TbContrato getContrato() {
		return contrato;
	}

	public void setContrato(TbContrato contrato) {
		this.contrato = contrato;
	}

	public String getNomeModificador() {
		return nomeModificador;
	}

	public void setNomeModificador(String nomeModificador) {
		this.nomeModificador = nomeModificador;
	}

	public String getDescricaoModificador() {
		return descricaoModificador;
	}

	public void setDescricaoModificador(String descricaoModificador) {
		this.descricaoModificador = descricaoModificador;
	}

	public TbContratoModificador getContratoModificador() {
		return contratoModificador;
	}

	public void setContratoModificador(TbContratoModificador contratoModificador) {
		this.contratoModificador = contratoModificador;
	}

	public TbContratoModificador getContratoModificadorDespesas() {
		return contratoModificadorDespesas;
	}

	public void setContratoModificadorDespesas(TbContratoModificador contratoModificadorDespesas) {
		this.contratoModificadorDespesas = contratoModificadorDespesas;
	}

	public String getNomeModificadorDespesas() {
		return nomeModificadorDespesas;
	}

	public void setNomeModificadorDespesas(String nomeModificadorDespesas) {
		this.nomeModificadorDespesas = nomeModificadorDespesas;
	}

	public String getDescricaoModificadorDespesas() {
		return descricaoModificadorDespesas;
	}

	public void setDescricaoModificadorDespesas(String descricaoModificadorDespesas) {
		this.descricaoModificadorDespesas = descricaoModificadorDespesas;
	}

	public List<TbContratoModificador> getContratosModificador() {
		return contratosModificador;
	}

	public void setContratosModificador(List<TbContratoModificador> contratosModificador) {
		this.contratosModificador = contratosModificador;
	}

	public List<TbContratoModificador> getContratosModificadorDespesas() {
		return contratosModificadorDespesas;
	}

	public void setContratosModificadorDespesas(List<TbContratoModificador> contratosModificadorDespesas) {
		this.contratosModificadorDespesas = contratosModificadorDespesas;
	}

	public Long getIdLocatario() {
		return idLocatario;
	}

	public void setIdLocatario(Long idLocatario) {
		this.idLocatario = idLocatario;
	}

	public List<TbLocatario> getLocatarios() {
		if(locatarios == null){
			locatarios = new ArrayList<>();
		}
		if(locatarios.size() == 0){
			if(FacesUtil.isNotPostBack()){
				locatarios = locatarioService.consultaTodosLocatarios();
			}
		}
		return locatarios;
	}

	public void setLocatarios(List<TbLocatario> locatarios) {
		this.locatarios = locatarios;
	}

}
