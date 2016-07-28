package br.com.rangosolucoes.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_pessoa_juridica", catalog = "imobiliaria")
public class TbPessoaJuridica implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String nuCnpj;
	private String noRazaoSocial;
	private String noFantasia;
	private String nuInscricaoEstadual;
	private String noContato;
	private List<TbPessoa> tbPessoas = new ArrayList<>();

	public TbPessoaJuridica() {
	}

	@Id
	@Column(name = "NU_CNPJ", unique = true, nullable = false, length = 14)
	@NotNull
	public String getNuCnpj() {
		return this.nuCnpj;
	}

	public void setNuCnpj(String nuCnpj) {
		this.nuCnpj = nuCnpj;
	}

	@Column(name = "NO_RAZAO_SOCIAL", nullable = false, length = 200)
	@NotNull
	public String getNoRazaoSocial() {
		return this.noRazaoSocial;
	}

	public void setNoRazaoSocial(String noRazaoSocial) {
		this.noRazaoSocial = noRazaoSocial;
	}

	@Column(name = "NO_FANTASIA", nullable = false, length = 200)
	@NotNull
	public String getNoFantasia() {
		return this.noFantasia;
	}

	public void setNoFantasia(String noFantasia) {
		this.noFantasia = noFantasia;
	}

	@Column(name = "NU_INSCRICAO_ESTADUAL", nullable = false, length = 13)
	@NotNull
	public String getNuInscricaoEstadual() {
		return this.nuInscricaoEstadual;
	}

	public void setNuInscricaoEstadual(String nuInscricaoEstadual) {
		this.nuInscricaoEstadual = nuInscricaoEstadual;
	}

	@Column(name = "NO_CONTATO", nullable = false, length = 200)
	@NotNull
	public String getNoContato() {
		return this.noContato;
	}

	public void setNoContato(String noContato) {
		this.noContato = noContato;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbPessoaJuridica")
	public List<TbPessoa> getTbPessoas() {
		return this.tbPessoas;
	}

	public void setTbPessoas(List<TbPessoa> tbPessoas) {
		this.tbPessoas = tbPessoas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nuCnpj == null) ? 0 : nuCnpj.hashCode());
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
		TbPessoaJuridica other = (TbPessoaJuridica) obj;
		if (nuCnpj == null) {
			if (other.nuCnpj != null)
				return false;
		} else if (!nuCnpj.equals(other.nuCnpj))
			return false;
		return true;
	}

}
