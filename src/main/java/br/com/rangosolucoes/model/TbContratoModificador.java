package br.com.rangosolucoes.model;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_contrato_modificador", catalog = "imobiliaria")
public class TbContratoModificador implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private TbContratoModificadorId id;
	private TbContrato tbContrato;
	private TbModificador tbModificador;
	private String nuMesAnoInicial;
	private String nuMesAnoFinal;
	private BigDecimal txReajuste;
	private BigDecimal vlValor;
	private char tpModificador;

	public TbContratoModificador() {
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idModificador", column = @Column(name = "ID_MODIFICADOR", nullable = false) ),
			@AttributeOverride(name = "idContrato", column = @Column(name = "ID_CONTRATO", nullable = false) ) })
	@NotNull
	public TbContratoModificadorId getId() {
		return this.id;
	}

	public void setId(TbContratoModificadorId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO", nullable = false, insertable = false, updatable = false)
	@NotNull
	public TbContrato getTbContrato() {
		return this.tbContrato;
	}

	public void setTbContrato(TbContrato tbContrato) {
		this.tbContrato = tbContrato;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MODIFICADOR", nullable = false, insertable = false, updatable = false)
	@NotNull
	public TbModificador getTbModificador() {
		return this.tbModificador;
	}

	public void setTbModificador(TbModificador tbModificador) {
		this.tbModificador = tbModificador;
	}

	@Column(name = "NU_MES_ANO_INICIAL", nullable = false, length = 6)
	@NotNull
	public String getNuMesAnoInicial() {
		return this.nuMesAnoInicial;
	}

	public void setNuMesAnoInicial(String nuMesAnoInicial) {
		this.nuMesAnoInicial = nuMesAnoInicial;
	}

	@Column(name = "NU_MES_ANO_FINAL", nullable = false, length = 6)
	@NotNull
	public String getNuMesAnoFinal() {
		return this.nuMesAnoFinal;
	}

	public void setNuMesAnoFinal(String nuMesAnoFinal) {
		this.nuMesAnoFinal = nuMesAnoFinal;
	}

	@Column(name = "TX_REAJUSTE", precision = 5)
	public BigDecimal getTxReajuste() {
		return this.txReajuste;
	}

	public void setTxReajuste(BigDecimal txReajuste) {
		this.txReajuste = txReajuste;
	}

	@Column(name = "VL_VALOR", nullable = false, precision = 8)
	@NotNull
	public BigDecimal getVlValor() {
		return this.vlValor;
	}

	public void setVlValor(BigDecimal vlValor) {
		this.vlValor = vlValor;
	}

	@Column(name = "TP_MODIFICADOR", nullable = false, length = 1)
	public char getTpModificador() {
		return this.tpModificador;
	}

	public void setTpModificador(char tpModificador) {
		this.tpModificador = tpModificador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		TbContratoModificador other = (TbContratoModificador) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
