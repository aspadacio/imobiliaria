package br.com.rangosolucoes.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TbContratoModificadorId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long idModificador;
	private long idContrato;

	public TbContratoModificadorId() {
	}

	public TbContratoModificadorId(long idModificador, long idContrato) {
		this.idModificador = idModificador;
		this.idContrato = idContrato;
	}

	@Column(name = "ID_MODIFICADOR", nullable = false)
	public long getIdModificador() {
		return this.idModificador;
	}

	public void setIdModificador(long idModificador) {
		this.idModificador = idModificador;
	}

	@Column(name = "ID_CONTRATO", nullable = false)
	public long getIdContrato() {
		return this.idContrato;
	}

	public void setIdContrato(long idContrato) {
		this.idContrato = idContrato;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbContratoModificadorId))
			return false;
		TbContratoModificadorId castOther = (TbContratoModificadorId) other;

		return (this.getIdModificador() == castOther.getIdModificador())
				&& (this.getIdContrato() == castOther.getIdContrato());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getIdModificador();
		result = 37 * result + (int) this.getIdContrato();
		return result;
	}

}
