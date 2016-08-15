package br.com.rangosolucoes.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.rangosolucoes.model.TbContrato;
import br.com.rangosolucoes.model.TbContratoModificador;
import br.com.rangosolucoes.model.TbContratoModificadorId;
import br.com.rangosolucoes.model.TbImovel;
import br.com.rangosolucoes.model.TbLocatario;
import br.com.rangosolucoes.model.TbModificador;
import br.com.rangosolucoes.model.TbPessoa;
import br.com.rangosolucoes.model.vo.DespesasContratoVO;
import br.com.rangosolucoes.model.vo.ReceitasContratoVO;
import br.com.rangosolucoes.service.ContratoModificadorService;
import br.com.rangosolucoes.service.ContratoService;
import br.com.rangosolucoes.service.ImovelService;
import br.com.rangosolucoes.service.LocatarioService;
import br.com.rangosolucoes.service.ModificadorService;
import br.com.rangosolucoes.service.PessoaService;
import br.com.rangosolucoes.util.jsf.FacesUtil;

@Named("cadastroContratosBean")
@SessionScoped
public class CadastroContratosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private LocatarioService locatarioService;

	@Inject
	private ImovelService imovelService;

	@Inject
	private PessoaService pessoaService;
	
	@Inject
	private ContratoService contratoService;
	
	@Inject
	private ModificadorService modificadorService;
	
	@Inject
	private ContratoModificadorService contratoModificadorService;

	private TbContrato contrato;
	private TbContratoModificador contratoModificador;
	private TbContratoModificador contratoModificadorDespesas;

	// Informacoes Contrato
	private Date dtInicio;
	private BigDecimal multaPorAtraso;
	private BigDecimal comissao;

	private Long idLocatario;
	private Long idImovel;
	private Long idPessoaFiador;
	private String nomeModificador;
	private String descricaoModificador;
	private Date periodoInicial;
	private Date periodoFinal;
	private BigDecimal valor;

	private String nomeModificadorDespesas;
	private String descricaoModificadorDespesas;
	private Date periodoInicialDespesas;
	private Date periodoFinalDespesas;
	private BigDecimal valorDespesas;
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
	public void init() {
		limpar();
	}

	public boolean isEditando() {
		return false;
	}

	public String novoCadastro() {
		limpar();
		return "/contratos/CadastroContratos?faces-redirect=true";
	}

	public void salvar() {
		if (camposPreenchidos()) {
			if (stContratoAtivo) {
				contrato.setStContratoAtivo('S');
			} else {
				contrato.setStContratoAtivo('N');
			}
			contrato.setTbLocatario(locatarioService.porId(idLocatario));
			TbImovel imovel = imovelService.porId(idImovel);
			contrato.setTbLocador(imovel.getTbLocador());
			contrato.setTbPessoa(pessoaService.porId(idPessoaFiador));
			contrato.setDtInicio(dtInicio);
			contrato.setTxMultaPorAtraso(multaPorAtraso);
			contrato.setTxComissao(comissao);
			
			contrato = contratoService.salvar(contrato);
			
			//Receitas
			if(receitasContratoVOs.size() > 0){
				for(ReceitasContratoVO receitasContratoVO : receitasContratoVOs){
					TbModificador modificador = new TbModificador();
					modificador.setNoModificador(receitasContratoVO.getNomeModificadorReceita());
					modificador.setDsModificador(receitasContratoVO.getDescModificadorReceita());
					
					modificador = modificadorService.salvar(modificador);
					
					TbContratoModificador contratoModificador = new TbContratoModificador();
					TbContratoModificadorId contratoModificadorId = new TbContratoModificadorId();
					
					contratoModificadorId.setIdContrato(contrato.getIdContrato());
					contratoModificadorId.setIdModificador(modificador.getIdModificador());
					
					contratoModificador.setId(contratoModificadorId);
					contratoModificador.setTbContrato(contrato);
					contratoModificador.setTbModificador(modificador);
					contratoModificador.setNuMesAnoInicial(receitasContratoVO.getNuMesAnoInicial());
					contratoModificador.setNuMesAnoFinal(receitasContratoVO.getNuMesAnoFinal());
					contratoModificador.setTxReajuste(receitasContratoVO.getTxReajuste());
					contratoModificador.setVlValor(receitasContratoVO.getVlValor());
					
					contratoModificador = contratoModificadorService.salvar(contratoModificador);
				}
			}
			
			//Despesas
			if(despesasContratoVOs.size() > 0){
				for(DespesasContratoVO despesasContratoVO : despesasContratoVOs){
					TbModificador modificador = new TbModificador();
					modificador.setNoModificador(despesasContratoVO.getNomeModificadorDespesa());
					modificador.setDsModificador(despesasContratoVO.getDescModificadorDespesa());
					
					modificador = modificadorService.salvar(modificador);
					
					TbContratoModificador contratoModificador = new TbContratoModificador();
					contratoModificador.setTbContrato(contrato);
					contratoModificador.setTbModificador(modificador);
					contratoModificador.setNuMesAnoInicial(despesasContratoVO.getNuMesAnoInicial());
					contratoModificador.setNuMesAnoFinal(despesasContratoVO.getNuMesAnoFinal());
					contratoModificador.setVlValor(despesasContratoVO.getVlValor());
					
					contratoModificador = contratoModificadorService.salvar(contratoModificador);
				}
			}
			limpar();
			
			locatarios.clear();
			locatarios = getLocatarios();
			imoveis.clear();
			imoveis = getImoveis();
			fiadores.clear();
			fiadores = getFiadores();
		}
	}

	public void limpar() {
		idLocatario = null;
		idImovel = null;
		idPessoaFiador = null;
		nomeModificador = "";
		nomeModificadorDespesas = "";
		descricaoModificador = "";
		descricaoModificadorDespesas = "";
		contrato = new TbContrato();
		contratoModificador = new TbContratoModificador();
		contratoModificadorDespesas = new TbContratoModificador();
		receitasContratoSelecionado = new ReceitasContratoVO();
		despesasContratoSelecionado = new DespesasContratoVO();

		dtInicio = null;
		multaPorAtraso = null;
		comissao = null;
		stContratoAtivo = false;
		contratosModificador = new ArrayList<>();
		contratosModificadorDespesas = new ArrayList<>();
		receitasContratoVOs = new ArrayList<>();
		despesasContratoVOs = new ArrayList<>();
	}

	public boolean camposPreenchidos() {
		boolean preenchido = true;
		return preenchido;
	}

	public void alteraContratoSelecionado() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		/*
		 * Valida se é um postBack ou um ValidationFailed. Só entra se for uma
		 * requisição. Valida também se está enviando o atributo 'contrato'
		 */

		if (!facesContext.isPostback() && !facesContext.isValidationFailed()
				&& facesContext.getExternalContext().getRequestParameterMap().get("contrato") != null) {

		}
	}

	public void adicionarReceita() {
		if (camposPreenchidosReceita()) {
			receitasContratoSelecionado.setNomeModificadorReceita(nomeModificador);
			receitasContratoSelecionado.setDescModificadorReceita(descricaoModificador);
			receitasContratoSelecionado.setNuMesAnoInicial(periodoInicial);
			receitasContratoSelecionado.setNuMesAnoFinal(periodoFinal);
			receitasContratoSelecionado.setTxReajuste(contratoModificador.getTxReajuste());
			receitasContratoSelecionado.setVlValor(valor);

			receitasContratoVOs.add(receitasContratoSelecionado);
			receitasContratoSelecionado = new ReceitasContratoVO();
			nomeModificador = "";
			descricaoModificador = "";
			periodoInicial = null;
			periodoFinal = null;
			contratoModificador.setTxReajuste(null);
			valor = null;
		}
	}

	public void excluirReceita() {
		receitasContratoVOs.remove(receitasContratoSelecionado);
		if(receitasContratoVOs.size() == 0){
			receitasContratoVOs = new ArrayList<>();
		}
		FacesUtil.addInfoMessage(
				"Receita " + receitasContratoSelecionado.getNomeModificadorReceita() + " excluída com sucesso.");
	}

	public void adicionarDespesa() {
		if (camposPreenchidosDespesa()) {
			despesasContratoSelecionado.setNomeModificadorDespesa(nomeModificadorDespesas);
			despesasContratoSelecionado.setDescModificadorDespesa(descricaoModificadorDespesas);
			despesasContratoSelecionado.setNuMesAnoInicial(periodoInicialDespesas);
			despesasContratoSelecionado.setNuMesAnoFinal(periodoFinalDespesas);
			despesasContratoSelecionado.setVlValor(valorDespesas);

			despesasContratoVOs.add(despesasContratoSelecionado);
			despesasContratoSelecionado = new DespesasContratoVO();
			nomeModificadorDespesas = "";
			descricaoModificadorDespesas = "";
			periodoInicialDespesas = null;
			periodoFinalDespesas = null;
			valorDespesas = null;
		}
	}

	public void excluirDespesa() {
		despesasContratoVOs.remove(despesasContratoSelecionado);
		if(despesasContratoVOs.size() == 0){
			despesasContratoVOs = new ArrayList<>();
		}
		FacesUtil.addInfoMessage(
				"Despesa " + despesasContratoSelecionado.getNomeModificadorDespesa() + " excluída com sucesso.");
	}

	public boolean camposPreenchidosReceita() {
		boolean preenchido = true;

		if (nomeModificador == null || nomeModificador == "") {
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Receita é obrigatório.");
		}

		if (descricaoModificador == null || descricaoModificador == "") {
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Descrição da receita é obrigatório.");
		}

		if (periodoInicial == null || periodoInicial.toString() == "") {
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Período Inicial é obrigatório.");
		}

		if (periodoFinal == null || periodoFinal.toString() == "") {
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Período Final é obrigatório.");
		}

		if (contratoModificador.getTxReajuste() == null) {
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Reajuste(%) é obrigatório.");
		}

		if (valor == null) {
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Valor é obrigatório.");
		}

		return preenchido;
	}

	public boolean camposPreenchidosDespesa() {
		boolean preenchido = true;

		if (nomeModificadorDespesas == null || nomeModificadorDespesas == "") {
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Despesa é obrigatório.");
		}

		if (descricaoModificadorDespesas == null || descricaoModificadorDespesas == "") {
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Descrição da despesa é obrigatório.");
		}

		if (periodoInicialDespesas == null || periodoInicialDespesas.toString() == "") {
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Período Inicial é obrigatório.");
		}

		if (periodoFinalDespesas == null || periodoFinalDespesas.toString() == "") {
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Período Final é obrigatório.");
		}

		if (valorDespesas == null) {
			preenchido = false;
			FacesUtil.addErrorMessage("O campo Valor é obrigatório.");
		}

		return preenchido;
	}

	public List<TbImovel> getImoveis() {
		if (imoveis == null) {
			imoveis = new ArrayList<>();
		}
		if (imoveis.size() == 0) {
			if (FacesUtil.isNotPostBack()) {
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
		if (fiadores == null) {
			fiadores = new ArrayList<>();
		}
		if (fiadores.size() == 0) {
			if (FacesUtil.isNotPostBack()) {
				fiadores = pessoaService.consultaTodosFiadores();
				for (TbPessoa pessoa : fiadores) {
					if (pessoa.getTbPessoaFisica() != null) {
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
		if (locatarios == null) {
			locatarios = new ArrayList<>();
		}
		if (locatarios.size() == 0) {
			if (FacesUtil.isNotPostBack()) {
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

	public Date getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	public BigDecimal getMultaPorAtraso() {
		return multaPorAtraso;
	}

	public void setMultaPorAtraso(BigDecimal multaPorAtraso) {
		this.multaPorAtraso = multaPorAtraso;
	}

	public BigDecimal getComissao() {
		return comissao;
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}

	public Date getPeriodoInicial() {
		return periodoInicial;
	}

	public void setPeriodoInicial(Date periodoInicial) {
		this.periodoInicial = periodoInicial;
	}

	public Date getPeriodoFinal() {
		return periodoFinal;
	}

	public void setPeriodoFinal(Date periodoFinal) {
		this.periodoFinal = periodoFinal;
	}

	public Date getPeriodoInicialDespesas() {
		return periodoInicialDespesas;
	}

	public void setPeriodoInicialDespesas(Date periodoInicialDespesas) {
		this.periodoInicialDespesas = periodoInicialDespesas;
	}

	public Date getPeriodoFinalDespesas() {
		return periodoFinalDespesas;
	}

	public void setPeriodoFinalDespesas(Date periodoFinalDespesas) {
		this.periodoFinalDespesas = periodoFinalDespesas;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValorDespesas() {
		return valorDespesas;
	}

	public void setValorDespesas(BigDecimal valorDespesas) {
		this.valorDespesas = valorDespesas;
	}

	public Long getIdImovel() {
		return idImovel;
	}

	public void setIdImovel(Long idImovel) {
		this.idImovel = idImovel;
	}

}
