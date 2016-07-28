package br.com.rangosolucoes.model;

import static javax.persistence.GenerationType.IDENTITY;

// Generated 22/07/2016 17:00:37 by Hibernate Tools 3.4.0.CR1

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
@Table(name = "tb_endereco_pessoa", catalog = "imobiliaria")
public class TbEnderecoPessoa implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long idEnderecoPessoa;
	private TbPessoa tbPessoa;
	private TbBairro tbBairro;
	private TbMunicipio tbMunicipio;
	private int nuCep;
	private String dsEndereco;
	private Integer nuEndereco;
	private String dsComplemento;
	private String nuDdd;
	private Integer nuTelefone;
	private char tpEndereco;

	public TbEnderecoPessoa() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID_ENDERECO_PESSOA", unique = true, nullable = false)
	public Long getIdEnderecoPessoa() {
		return this.idEnderecoPessoa;
	}

	public void setIdEnderecoPessoa(Long idEnderecoPessoa) {
		this.idEnderecoPessoa = idEnderecoPessoa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PESSOA", nullable = false)
	@NotNull
	public TbPessoa getTbPessoa() {
		return this.tbPessoa;
	}

	public void setTbPessoa(TbPessoa tbPessoa) {
		this.tbPessoa = tbPessoa;
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

	@Column(name = "NU_CEP", nullable = false)
	public int getNuCep() {
		return this.nuCep;
	}

	public void setNuCep(int nuCep) {
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
	public Integer getNuEndereco() {
		return this.nuEndereco;
	}

	public void setNuEndereco(Integer nuEndereco) {
		this.nuEndereco = nuEndereco;
	}

	@Column(name = "DS_COMPLEMENTO", length = 200)
	public String getDsComplemento() {
		return this.dsComplemento;
	}

	public void setDsComplemento(String dsComplemento) {
		this.dsComplemento = dsComplemento;
	}

	@Column(name = "NU_DDD", length = 2)
	public String getNuDdd() {
		return this.nuDdd;
	}

	public void setNuDdd(String nuDdd) {
		this.nuDdd = nuDdd;
	}

	@Column(name = "NU_TELEFONE")
	public Integer getNuTelefone() {
		return this.nuTelefone;
	}

	public void setNuTelefone(Integer nuTelefone) {
		this.nuTelefone = nuTelefone;
	}

	@Column(name = "TP_ENDERECO", nullable = false, length = 1)
	public char getTpEndereco() {
		return this.tpEndereco;
	}

	public void setTpEndereco(char tpEndereco) {
		this.tpEndereco = tpEndereco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEnderecoPessoa == null) ? 0 : idEnderecoPessoa.hashCode());
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
		TbEnderecoPessoa other = (TbEnderecoPessoa) obj;
		if (idEnderecoPessoa == null) {
			if (other.idEnderecoPessoa != null)
				return false;
		} else if (!idEnderecoPessoa.equals(other.idEnderecoPessoa))
			return false;
		return true;
	}

}
