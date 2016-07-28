package br.com.rangosolucoes.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_modificador", catalog = "imobiliaria")
public class TbModificador implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long idModificador;
	private String noModificador;
	private String dsModificador;
	private List<TbContratoModificador> tbContratoModificadors = new ArrayList<>();

	public TbModificador() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID_MODIFICADOR", unique = true, nullable = false)
	public Long getIdModificador() {
		return this.idModificador;
	}

	public void setIdModificador(Long idModificador) {
		this.idModificador = idModificador;
	}

	@Column(name = "NO_MODIFICADOR", nullable = false, length = 100)
	@NotNull
	public String getNoModificador() {
		return this.noModificador;
	}

	public void setNoModificador(String noModificador) {
		this.noModificador = noModificador;
	}

	@Column(name = "DS_MODIFICADOR", length = 2000)
	public String getDsModificador() {
		return this.dsModificador;
	}

	public void setDsModificador(String dsModificador) {
		this.dsModificador = dsModificador;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbModificador")
	public List<TbContratoModificador> getTbContratoModificadors() {
		return this.tbContratoModificadors;
	}

	public void setTbContratoModificadors(List<TbContratoModificador> tbContratoModificadors) {
		this.tbContratoModificadors = tbContratoModificadors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idModificador == null) ? 0 : idModificador.hashCode());
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
		TbModificador other = (TbModificador) obj;
		if (idModificador == null) {
			if (other.idModificador != null)
				return false;
		} else if (!idModificador.equals(other.idModificador))
			return false;
		return true;
	}

}
