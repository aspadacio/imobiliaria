package br.com.rangosolucoes.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_bairro", catalog = "imobiliaria")
public class TbBairro implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long idBairro;
	private TbMunicipio tbMunicipio;
	private String noBairro;
	private List<TbImovel> tbImovels = new ArrayList<>();
	private List<TbEnderecoPessoa> tbEnderecoPessoas = new ArrayList<>();

	public TbBairro() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID_BAIRRO", unique = true, nullable = false)
	public Long getIdBairro() {
		return this.idBairro;
	}

	public void setIdBairro(Long idBairro) {
		this.idBairro = idBairro;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MUNICIPIO", nullable = false)
	@NotNull
	public TbMunicipio getTbMunicipio() {
		return this.tbMunicipio;
	}

	public void setTbMunicipio(TbMunicipio tbMunicipio) {
		this.tbMunicipio = tbMunicipio;
	}

	@Column(name = "NO_BAIRRO", nullable = false, length = 100)
	@NotNull
	public String getNoBairro() {
		return this.noBairro;
	}

	public void setNoBairro(String noBairro) {
		this.noBairro = noBairro;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbBairro")
	public List<TbImovel> getTbImovels() {
		return this.tbImovels;
	}

	public void setTbImovels(List<TbImovel> tbImovels) {
		this.tbImovels = tbImovels;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbBairro")
	public List<TbEnderecoPessoa> getTbEnderecoPessoas() {
		return this.tbEnderecoPessoas;
	}

	public void setTbEnderecoPessoas(List<TbEnderecoPessoa> tbEnderecoPessoas) {
		this.tbEnderecoPessoas = tbEnderecoPessoas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idBairro == null) ? 0 : idBairro.hashCode());
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
		TbBairro other = (TbBairro) obj;
		if (idBairro == null) {
			if (other.idBairro != null)
				return false;
		} else if (!idBairro.equals(other.idBairro))
			return false;
		return true;
	}

}
