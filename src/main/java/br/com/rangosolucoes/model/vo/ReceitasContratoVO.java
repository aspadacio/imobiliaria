package br.com.rangosolucoes.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ReceitasContratoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeModificadorReceita;
	private String descModificadorReceita;
	private Date nuMesAnoInicial;
	private Date nuMesAnoFinal;
	private BigDecimal txReajuste;
	private BigDecimal vlValor;

	public String getNomeModificadorReceita() {
		return nomeModificadorReceita;
	}

	public void setNomeModificadorReceita(String nomeModificadorReceita) {
		this.nomeModificadorReceita = nomeModificadorReceita;
	}

	public String getDescModificadorReceita() {
		return descModificadorReceita;
	}

	public void setDescModificadorReceita(String descModificadorReceita) {
		this.descModificadorReceita = descModificadorReceita;
	}

	public Date getNuMesAnoInicial() {
		return nuMesAnoInicial;
	}

	public void setNuMesAnoInicial(Date nuMesAnoInicial) {
		this.nuMesAnoInicial = nuMesAnoInicial;
	}

	public Date getNuMesAnoFinal() {
		return nuMesAnoFinal;
	}

	public void setNuMesAnoFinal(Date nuMesAnoFinal) {
		this.nuMesAnoFinal = nuMesAnoFinal;
	}

	public BigDecimal getTxReajuste() {
		return txReajuste;
	}

	public void setTxReajuste(BigDecimal txReajuste) {
		this.txReajuste = txReajuste;
	}

	public BigDecimal getVlValor() {
		return vlValor;
	}

	public void setVlValor(BigDecimal vlValor) {
		this.vlValor = vlValor;
	}

}
