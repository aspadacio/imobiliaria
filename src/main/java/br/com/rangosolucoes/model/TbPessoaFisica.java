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
