package br.com.rangosolucoes.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_municipio", catalog = "imobiliaria")
public class TbMunicipio implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long idMunicipio;
	private String noMunicipio;
	private String sgUf;

	public TbMunicipio() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID_MUNICIPIO", unique = true, nullable = false)
	public Long getIdMunicipio() {
		return this.idMunicipio;
	}

	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	@Column(name = "NO_MUNICIPIO", nullable = false, length = 100)
	@NotNull
	public String getNoMunicipio() {
		return this.noMunicipio;
	}

	public void setNoMunicipio(String noMunicipio) {
		this.noMunicipio = noMunicipio;
	}

	@Column(name = "SG_UF", nullable = false, length = 2)
	@NotNull
	public String getSgUf() {
		return this.sgUf;
	}

	public void setSgUf(String sgUf) {
		this.sgUf = sgUf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMunicipio == null) ? 0 : idMunicipio.hashCode());
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
		TbMunicipio other = (TbMunicipio) obj;
		if (idMunicipio == null) {
			if (other.idMunicipio != null)
				return false;
		} else if (!idMunicipio.equals(other.idMunicipio))
			return false;
		return true;
	}

}
