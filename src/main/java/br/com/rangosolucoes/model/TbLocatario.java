package br.com.rangosolucoes.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_locatario", catalog = "imobiliaria")
public class TbLocatario implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long idLocatario;
	private TbPessoa tbPessoa;
	private Date dtCadastro;

	public TbLocatario() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID_LOCATARIO", unique = true, nullable = false)
	public Long getIdLocatario() {
		return this.idLocatario;
	}

	public void setIdLocatario(Long idLocatario) {
		this.idLocatario = idLocatario;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_CADASTRO", nullable = false, length = 19)
	@NotNull
	public Date getDtCadastro() {
		return this.dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLocatario == null) ? 0 : idLocatario.hashCode());
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
		TbLocatario other = (TbLocatario) obj;
		if (idLocatario == null) {
			if (other.idLocatario != null)
				return false;
		} else if (!idLocatario.equals(other.idLocatario))
			return false;
		return true;
	}

}
