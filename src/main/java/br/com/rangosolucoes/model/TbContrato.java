package br.com.rangosolucoes.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_contrato", catalog = "imobiliaria")
public class TbContrato implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long idContrato;
	private TbPessoa tbPessoa;
	private TbLocatario tbLocatario;
	private TbLocador tbLocador;
	private Date dtInicio;
	private int nuDuracao;
	private int nuDiaVencimento;
	private BigDecimal txMultaPorAtraso;
	private int nuParcelaAnterior;
	private BigDecimal txComissao;
	private char stContratoAtivo;

	public TbContrato() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID_CONTRATO", unique = true, nullable = false)
	public Long getIdContrato() {
		return this.idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PESSOA_FIADOR", nullable = false)
	@NotNull
	public TbPessoa getTbPessoa() {
		return this.tbPessoa;
	}

	public void setTbPessoa(TbPessoa tbPessoa) {
		this.tbPessoa = tbPessoa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_LOCATARIO", nullable = false)
	@NotNull
	public TbLocatario getTbLocatario() {
		return this.tbLocatario;
	}

	public void setTbLocatario(TbLocatario tbLocatario) {
		this.tbLocatario = tbLocatario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_LOCADOR", nullable = false)
	@NotNull
	public TbLocador getTbLocador() {
		return this.tbLocador;
	}

	public void setTbLocador(TbLocador tbLocador) {
		this.tbLocador = tbLocador;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_INICIO", nullable = false, length = 19)
	@NotNull
	public Date getDtInicio() {
		return this.dtInicio;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	@Column(name = "NU_DURACAO", nullable = false)
	public int getNuDuracao() {
		return this.nuDuracao;
	}

	public void setNuDuracao(int nuDuracao) {
		this.nuDuracao = nuDuracao;
	}

	@Column(name = "NU_DIA_VENCIMENTO", nullable = false)
	public int getNuDiaVencimento() {
		return this.nuDiaVencimento;
	}

	public void setNuDiaVencimento(int nuDiaVencimento) {
		this.nuDiaVencimento = nuDiaVencimento;
	}

	@Column(name = "TX_MULTA_POR_ATRASO", nullable = false, precision = 5)
	@NotNull
	public BigDecimal getTxMultaPorAtraso() {
		return this.txMultaPorAtraso;
	}

	public void setTxMultaPorAtraso(BigDecimal txMultaPorAtraso) {
		this.txMultaPorAtraso = txMultaPorAtraso;
	}

	@Column(name = "NU_PARCELA_ANTERIOR", nullable = false)
	public int getNuParcelaAnterior() {
		return this.nuParcelaAnterior;
	}

	public void setNuParcelaAnterior(int nuParcelaAnterior) {
		this.nuParcelaAnterior = nuParcelaAnterior;
	}

	@Column(name = "TX_COMISSAO", nullable = false, precision = 5)
	@NotNull
	public BigDecimal getTxComissao() {
		return this.txComissao;
	}

	public void setTxComissao(BigDecimal txComissao) {
		this.txComissao = txComissao;
	}

	@Column(name = "ST_CONTRATO_ATIVO", nullable = false, length = 1)
	public char getStContratoAtivo() {
		return this.stContratoAtivo;
	}

	public void setStContratoAtivo(char stContratoAtivo) {
		this.stContratoAtivo = stContratoAtivo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idContrato == null) ? 0 : idContrato.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TbContrato other = (TbContrato) obj;
		if (idContrato == null) {
			if (other.idContrato != null)
				return false;
		} else if (!idContrato.equals(other.idContrato))
			return false;
		return true;
	}

}
