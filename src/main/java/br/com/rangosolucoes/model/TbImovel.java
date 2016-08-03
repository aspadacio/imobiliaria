package br.com.rangosolucoes.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_imovel", catalog = "imobiliaria")
public class TbImovel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long idImovel;
	private TbLocatario tbLocatario;
	private TbLocador tbLocador;
	private TbBairro tbBairro;
	private TbMunicipio tbMunicipio;
	private String dsImovel;
	private String nuCep;
	private String dsEndereco;
	private int nuEndereco;
	private String dsComplemento;
	private String dsObservacoes;

	public TbImovel() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID_IMOVEL", unique = true, nullable = false)
	public Long getIdImovel() {
		return this.idImovel;
	}

	public void setIdImovel(Long idImovel) {
		this.idImovel = idImovel;
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
		return tbLocador;
	}

	public void setTbLocador(TbLocador tbLocador) {
		this.tbLocador = tbLocador;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BAIRRO")
	public TbBairro getTbBairro() {
		return this.tbBairro;
	}

	public void setTbBairro(TbBairro tbBairro) {
		this.tbBairro = tbBairro;
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

	@Column(name = "DS_IMOVEL", nullable = false, length = 200)
	@NotNull
	public String getDsImovel() {
		return this.dsImovel;
	}

	public void setDsImovel(String dsImovel) {
		this.dsImovel = dsImovel;
	}

	@Column(name = "NU_CEP", nullable = false, length = 8)
	@NotNull
	public String getNuCep() {
		return this.nuCep;
	}

	public void setNuCep(String nuCep) {
		this.nuCep = nuCep;
	}

	@Column(name = "DS_ENDERECO", nullable = false, length = 200)
	@NotNull
	public String getDsEndereco() {
		return this.dsEndereco;
	}

	public void setDsEndereco(String dsEndereco) {
		this.dsEndereco = dsEndereco;
	}

	@Column(name = "NU_ENDERECO", nullable = false)
	public int getNuEndereco() {
		return this.nuEndereco;
	}

	public void setNuEndereco(int nuEndereco) {
		this.nuEndereco = nuEndereco;
	}

	@Column(name = "DS_COMPLEMENTO", length = 200)
	public String getDsComplemento() {
		return this.dsComplemento;
	}

	public void setDsComplemento(String dsComplemento) {
		this.dsComplemento = dsComplemento;
	}

	@Column(name = "DS_OBSERVACOES", length = 2000)
	public String getDsObservacoes() {
		return this.dsObservacoes;
	}

	public void setDsObservacoes(String dsObservacoes) {
		this.dsObservacoes = dsObservacoes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idImovel == null) ? 0 : idImovel.hashCode());
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
		TbImovel other = (TbImovel) obj;
		if (idImovel == null) {
			if (other.idImovel != null)
				return false;
		} else if (!idImovel.equals(other.idImovel))
			return false;
		return true;
	}

}
