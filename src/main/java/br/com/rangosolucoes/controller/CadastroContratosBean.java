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
import br.com.rangosolucoes.model.vo.DespesasContratoVO;
import br.com.rangosolucoes.model.vo.ReceitasContratoVO;
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
	private boolean stContratoAtivo;
	
	private ReceitasContratoVO receitasContratoSelecionado;
	private DespesasContratoVO despesasContratoSelecionado;
	
	private List<TbLocatario> locatarios;
	private List<TbImovel> imoveis;
	private List<TbPessoa> fiadores;
	private List<TbContratoModificador> contratosModificador;
	private List<TbContratoModificador> contratosModificadorDespesas;
	private List<ReceitasContratoVO> receitasContratoVOs;
	private List<DespesasContratoVO> despesasContratoVOs;
	
	@PostConstruct
	public void init(){
		limpar();
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
			if(stContratoAtivo){
				contrato.setStContratoAtivo('S');
			}else{
				contrato.setStContratoAtivo('N');
			}
		}
	}
	
	public void limpar(){
		idLocatario = null;
		idContrato = null;
		idPessoaFiador = null;
		nomeModificador = "";
		nomeModificadorDespesas = "";
		descricaoModificador = "";
		descricaoModificadorDespesas = "";
		contrato = new TbContrato();
		contratoModificador = new TbContratoModificador();
		contratoModificadorDespesas = new TbContratoModificador();
		
		locatarios = new ArrayList<>();
		imoveis = new ArrayList<>();
		fiadores = new ArrayList<>();
		contratosModificador = new ArrayList<>();
		contratosModificadorDespesas = new ArrayList<>();
		receitasContratoVOs = new ArrayList<>();
		despesasContratoVOs = new ArrayList<>();
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
	
	public void adicionarReceita(){
		if(camposPreenchidosReceita()){
			receitasContratoSelecionado.setNomeModificadorReceita(nomeModificador);
			receitasContratoSelecionado.setDescModificadorReceita(descricaoModificador);
			receitasContratoSelecionado.setNuMesAnoInicial(contratoModificador.getNuMesAnoInicial());
			receitasContratoSelecionado.setNuMesAnoFinal(contratoModificador.getNuMesAnoFinal());
			receitasContratoSelecionado.setTxReajuste(contratoModificador.getTxReajuste());
			receitasContratoSelecionado.setVlValor(contratoModificador.getVlValor());
			
			receitasContratoVOs.add(receitasContratoSelecionado);
			receitasContratoSelecionado = new ReceitasContratoVO();
		}
	}
	
	public void excluirReceita(){
		receitasContratoVOs.remove(receitasContratoSelecionado);
		
		FacesUtil.addInfoMessage("Receita " + receitasContratoSelecionado.getNomeModificadorReceita() + " excluída com sucesso.");
	}
	
	public void adicionarDespesa(){
		if(camposPreenchidosDespesa()){
			despesasContratoSelecionado.setNomeModificadorDespesa(nomeModificador);
			despesasContratoSelecionado.setDescModificadorDespesa(descricaoModificador);
			despesasContratoSelecionado.setNuMesAnoInicial(contratoModificador.getNuMesAnoInicial());
			despesasContratoSelecionado.setNuMesAnoFinal(contratoModificador.getNuMesAnoFinal());
			despesasContratoSelecionado.setVlValor(contratoModificador.getVlValor());
			
			despesasContratoVOs.add(despesasContratoSelecionado);
			despesasContratoSelecionado = new DespesasContratoVO();
		}
	}
	
	public void excluirDespesa(){
		despesasContratoVOs.remove(despesasContratoSelecionado);
		
		FacesUtil.addInfoMessage("Despesa " + despesasContratoSelecionado.getNomeModificadorDespesa() + " excluída com sucesso.");
	}
	
	public boolean camposPreenchidosReceita(){
		boolean preenchido = true;
		
		if(nomeModificador == null || nomeModificador == ""){
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Receita é obrigatório.");
		}
		
		if(descricaoModificador == null || descricaoModificador == ""){
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Descrição da receita é obrigatório.");
		}
		
		if(contratoModificador.getNuMesAnoInicial() == null || contratoModificador.getNuMesAnoInicial() == ""){
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Período Inicial é obrigatório.");
		}
		
		if(contratoModificador.getNuMesAnoFinal() == null || contratoModificador.getNuMesAnoFinal() == ""){
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Período Final é obrigatório.");
		}
		
		if(contratoModificador.getTxReajuste() == null){
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Reajuste(%) é obrigatório.");
		}
		
		if(contratoModificador.getVlValor() == null){
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Valor é obrigatório.");
		}
		
		return preenchido;
	}
	
	public boolean camposPreenchidosDespesa(){
		boolean preenchido = true;
		
		if(nomeModificadorDespesas == null || nomeModificadorDespesas == ""){
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Despesa é obrigatório.");
		}
		
		if(descricaoModificadorDespesas == null || descricaoModificadorDespesas == ""){
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Descrição da despesa é obrigatório.");
		}
		
		if(contratoModificadorDespesas.getNuMesAnoInicial() == null || contratoModificadorDespesas.getNuMesAnoInicial() == ""){
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Período Inicial é obrigatório.");
		}
		
		if(contratoModificadorDespesas.getNuMesAnoFinal() == null || contratoModificadorDespesas.getNuMesAnoFinal() == ""){
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Período Final é obrigatório.");
		}
		
		if(contratoModificadorDespesas.getVlValor() == null){
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Valor é obrigatório.");
		}
		
		return preenchido;
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
		List<TbPessoa> pessoas = new ArrayList<>();
		if(fiadores == null){
			fiadores = new ArrayList<>();
		}
		if(fiadores.size() == 0){
			if(FacesUtil.isNotPostBack()){
				fiadores = pessoaService.consultaTodosFiadores();
				for (TbPessoa pessoa : fiadores) {
					if(pessoa.getTbPessoaFisica() != null){
						pessoas.add(pessoa);
					}
				}
				fiadores.clear();
				fiadores = pessoas;
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

	public boolean isStContratoAtivo() {
		return stContratoAtivo;
	}

	public void setStContratoAtivo(boolean stContratoAtivo) {
		this.stContratoAtivo = stContratoAtivo;
	}

	public List<ReceitasContratoVO> getReceitasContratoVOs() {
		return receitasContratoVOs;
	}

	public void setReceitasContratoVOs(List<ReceitasContratoVO> receitasContratoVOs) {
		this.receitasContratoVOs = receitasContratoVOs;
	}

	public List<DespesasContratoVO> getDespesasContratoVOs() {
		return despesasContratoVOs;
	}

	public void setDespesasContratoVOs(List<DespesasContratoVO> despesasContratoVOs) {
		this.despesasContratoVOs = despesasContratoVOs;
	}

	public ReceitasContratoVO getReceitasContratoSelecionado() {
		return receitasContratoSelecionado;
	}

	public void setReceitasContratoSelecionado(ReceitasContratoVO receitasContratoSelecionado) {
		this.receitasContratoSelecionado = receitasContratoSelecionado;
	}

	public DespesasContratoVO getDespesasContratoSelecionado() {
		return despesasContratoSelecionado;
	}

	public void setDespesasContratoSelecionado(DespesasContratoVO despesasContratoSelecionado) {
		this.despesasContratoSelecionado = despesasContratoSelecionado;
	}

}
