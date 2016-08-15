package br.com.rangosolucoes.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DespesasContratoVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nomeModificadorDespesa;
	private String descModificadorDespesa;
	private Date nuMesAnoInicial;
	private Date nuMesAnoFinal;
	private BigDecimal vlValor;

	public String getNomeModificadorDespesa() {
		return nomeModificadorDespesa;
	}

	public void setNomeModificadorDespesa(String nomeModificadorDespesa) {
		this.nomeModificadorDespesa = nomeModificadorDespesa;
	}

	public String getDescModificadorDespesa() {
		return descModificadorDespesa;
	}

	public void setDescModificadorDespesa(String descModificadorDespesa) {
		this.descModificadorDespesa = descModificadorDespesa;
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

	public BigDecimal getVlValor() {
		return vlValor;
	}

	public void setVlValor(BigDecimal vlValor) {
		this.vlValor = vlValor;
	}

}
