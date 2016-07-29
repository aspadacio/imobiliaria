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
@Table(name = "tb_locador", catalog = "imobiliaria")
public class TbLocador implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long idLocador;
	private TbPessoa tbPessoa;
	private Date dtCadastro;

	public TbLocador() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID_LOCADOR", unique = true, nullable = false)
	public Long getIdLocador() {
		return this.idLocador;
	}

	public void setIdLocador(Long idLocador) {
		this.idLocador = idLocador;
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
		result = prime * result + ((idLocador == null) ? 0 : idLocador.hashCode());
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
		TbLocador other = (TbLocador) obj;
		if (idLocador == null) {
			if (other.idLocador != null)
				return false;
		} else if (!idLocador.equals(other.idLocador))
			return false;
		return true;
	}

}
