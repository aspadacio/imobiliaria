package br.com.rangosolucoes.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_usuario", catalog = "imobiliaria")
public class TbUsuario implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long idUsuario;
	private String nome;
	private String email;
	private String senha;
	private List<TbGrupo> tbGrupos = new ArrayList<>();

	public TbUsuario() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID_USUARIO", unique = true, nullable = false)
	public Long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Column(name = "NOME", length = 100)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "EMAIL", length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "SENHA", length = 100)
	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	//@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbUsuario")
	@OneToMany
	public List<TbGrupo> getTbGrupos() {
		return this.tbGrupos;
	}

	public void setTbGrupos(List<TbGrupo> tbGrupos) {
		this.tbGrupos = tbGrupos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
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
		TbUsuario other = (TbUsuario) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}

}
