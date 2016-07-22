package br.com.rangosolucoes.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_pessoa", catalog = "imobiliaria")
public class TbPessoa implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPessoa;
	private TbPessoaJuridica tbPessoaJuridica;
	private TbPessoaFisica tbPessoaFisica;
	private String dsEmail;
	private Date dtUltimaAlteracao;
	private String dsObservacao;
	private List<TbLocador> tbLocadors = new ArrayList<>();
	private List<TbEnderecoPessoa> tbEnderecoPessoas = new ArrayList<>();
	private List<TbPessoaTelefone> tbPessoaTelefones = new ArrayList<>();
	private List<TbContrato> tbContratos = new ArrayList<>();
	private List<TbLocatario> tbLocatarios = new ArrayList<>();

	public TbPessoa() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID_PESSOA", unique = true, nullable = false)
	public Long getIdPessoa() {
		return this.idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_CNPJ")
	public TbPessoaJuridica getTbPessoaJuridica() {
		return this.tbPessoaJuridica;
	}

	public void setTbPessoaJuridica(TbPessoaJuridica tbPessoaJuridica) {
		this.tbPessoaJuridica = tbPessoaJuridica;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_CPF")
	public TbPessoaFisica getTbPessoaFisica() {
		return this.tbPessoaFisica;
	}

	public void setTbPessoaFisica(TbPessoaFisica tbPessoaFisica) {
		this.tbPessoaFisica = tbPessoaFisica;
	}

	@Column(name = "DS_EMAIL", nullable = false, length = 200)
	@NotNull
	public String getDsEmail() {
		return this.dsEmail;
	}

	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_ULTIMA_ALTERACAO", nullable = false, length = 19)
	@NotNull
	public Date getDtUltimaAlteracao() {
		return this.dtUltimaAlteracao;
	}

	public void setDtUltimaAlteracao(Date dtUltimaAlteracao) {
		this.dtUltimaAlteracao = dtUltimaAlteracao;
	}

	@Column(name = "DS_OBSERVACAO", length = 2000)
	public String getDsObservacao() {
		return this.dsObservacao;
	}

	public void setDsObservacao(String dsObservacao) {
		this.dsObservacao = dsObservacao;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbPessoa")
	public List<TbLocador> getTbLocadors() {
		return this.tbLocadors;
	}

	public void setTbLocadors(List<TbLocador> tbLocadors) {
		this.tbLocadors = tbLocadors;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbPessoa")
	public List<TbEnderecoPessoa> getTbEnderecoPessoas() {
		return this.tbEnderecoPessoas;
	}

	public void setTbEnderecoPessoas(List<TbEnderecoPessoa> tbEnderecoPessoas) {
		this.tbEnderecoPessoas = tbEnderecoPessoas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbPessoa")
	public List<TbPessoaTelefone> getTbPessoaTelefones() {
		return this.tbPessoaTelefones;
	}

	public void setTbPessoaTelefones(List<TbPessoaTelefone> tbPessoaTelefones) {
		this.tbPessoaTelefones = tbPessoaTelefones;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbPessoa")
	public List<TbContrato> getTbContratos() {
		return this.tbContratos;
	}

	public void setTbContratos(List<TbContrato> tbContratos) {
		this.tbContratos = tbContratos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbPessoa")
	public List<TbLocatario> getTbLocatarios() {
		return this.tbLocatarios;
	}

	public void setTbLocatarios(List<TbLocatario> tbLocatarios) {
		this.tbLocatarios = tbLocatarios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPessoa == null) ? 0 : idPessoa.hashCode());
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
		TbPessoa other = (TbPessoa) obj;
		if (idPessoa == null) {
			if (other.idPessoa != null)
				return false;
		} else if (!idPessoa.equals(other.idPessoa))
			return false;
		return true;
	}

}
