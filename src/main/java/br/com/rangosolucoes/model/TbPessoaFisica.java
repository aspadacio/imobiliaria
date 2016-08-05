package br.com.rangosolucoes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_pessoa_fisica", catalog = "imobiliaria")
public class TbPessoaFisica implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String nuCpf;
	private String noPessoaFisica;
	private String nuRg;
	private String orgExp;
	private String dsNacionalidade;
	private String dsProfissao;
	private String dsEstadoCivil;

	public TbPessoaFisica() {
	}

	@Id
	@Column(name = "NU_CPF", unique = true, nullable = false, length = 11)
	@NotNull
	public String getNuCpf() {
		return this.nuCpf;
	}

	public void setNuCpf(String nuCpf) {
		this.nuCpf = nuCpf;
	}

	@Column(name = "NO_PESSOA_FISICA", nullable = false, length = 200)
	@NotNull
	public String getNoPessoaFisica() {
		return this.noPessoaFisica;
	}

	public void setNoPessoaFisica(String noPessoaFisica) {
		this.noPessoaFisica = noPessoaFisica;
	}

	@Column(name = "NU_RG", nullable = false, length = 200)
	public String getNuRg() {
		return nuRg;
	}

	public void setNuRg(String nuRg) {
		this.nuRg = nuRg;
	}

	@Column(name = "ORG_EXP", nullable = false, length = 50)
	public String getOrgExp() {
		return orgExp;
	}

	public void setOrgExp(String orgExp) {
		this.orgExp = orgExp;
	}

	@Column(name = "DS_NACIONALIDADE", nullable = false, length = 50)
	public String getDsNacionalidade() {
		return dsNacionalidade;
	}

	public void setDsNacionalidade(String dsNacionalidade) {
		this.dsNacionalidade = dsNacionalidade;
	}

	@Column(name = "DS_PROFISSAO", nullable = false, length = 100)
	public String getDsProfissao() {
		return dsProfissao;
	}

	public void setDsProfissao(String dsProfissao) {
		this.dsProfissao = dsProfissao;
	}

	@Column(name = "DS_ESTADO_CIVIL", nullable = false, length = 50)
	public String getDsEstadoCivil() {
		return dsEstadoCivil;
	}

	public void setDsEstadoCivil(String dsEstadoCivil) {
		this.dsEstadoCivil = dsEstadoCivil;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nuCpf == null) ? 0 : nuCpf.hashCode());
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
		TbPessoaFisica other = (TbPessoaFisica) obj;
		if (nuCpf == null) {
			if (other.nuCpf != null)
				return false;
		} else if (!nuCpf.equals(other.nuCpf))
			return false;
		return true;
	}

}
