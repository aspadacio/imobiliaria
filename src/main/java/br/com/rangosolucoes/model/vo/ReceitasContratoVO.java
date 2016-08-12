package br.com.rangosolucoes.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReceitasContratoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeModificadorReceita;
	private String descModificadorReceita;
	private String nuMesAnoInicial;
	private String nuMesAnoFinal;
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

	public String getNuMesAnoInicial() {
		return nuMesAnoInicial;
	}

	public void setNuMesAnoInicial(String nuMesAnoInicial) {
		this.nuMesAnoInicial = nuMesAnoInicial;
	}

	public String getNuMesAnoFinal() {
		return nuMesAnoFinal;
	}

	public void setNuMesAnoFinal(String nuMesAnoFinal) {
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
