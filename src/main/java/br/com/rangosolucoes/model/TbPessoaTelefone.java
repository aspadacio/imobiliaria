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
@Table(name = "tb_pessoa_telefone", catalog = "imobiliaria")
public class TbPessoaTelefone implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPessoaTelefone;
	private TbPessoa tbPessoa;
	private String nuTelefoneDdd;
	private int nuTelefone;
	private char tpTelefone;

	public TbPessoaTelefone() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID_PESSOA_TELEFONE", unique = true, nullable = false)
	public Long getIdPessoaTelefone() {
		return this.idPessoaTelefone;
	}

	public void setIdPessoaTelefone(Long idPessoaTelefone) {
		this.idPessoaTelefone = idPessoaTelefone;
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

	@Column(name = "NU_TELEFONE_DDD", nullable = false, length = 2)
	@NotNull
	public String getNuTelefoneDdd() {
		return this.nuTelefoneDdd;
	}

	public void setNuTelefoneDdd(String nuTelefoneDdd) {
		this.nuTelefoneDdd = nuTelefoneDdd;
	}

	@Column(name = "NU_TELEFONE", nullable = false)
	public int getNuTelefone() {
		return this.nuTelefone;
	}

	public void setNuTelefone(int nuTelefone) {
		this.nuTelefone = nuTelefone;
	}

	@Column(name = "TP_TELEFONE", nullable = false, length = 1)
	public char getTpTelefone() {
		return this.tpTelefone;
	}

	public void setTpTelefone(char tpTelefone) {
		this.tpTelefone = tpTelefone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPessoaTelefone == null) ? 0 : idPessoaTelefone.hashCode());
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
		TbPessoaTelefone other = (TbPessoaTelefone) obj;
		if (idPessoaTelefone == null) {
			if (other.idPessoaTelefone != null)
				return false;
		} else if (!idPessoaTelefone.equals(other.idPessoaTelefone))
			return false;
		return true;
	}

}
