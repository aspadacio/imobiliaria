package br.com.rangosolucoes.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_grupo", catalog = "imobiliaria")
public class TbGrupo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long idGrupo;
	private String nome;
	private String descricao;
	private List<TbUsuarioGrupo> tbUsuarioGrupos = new ArrayList<>();

	public TbGrupo() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID_GRUPO", unique = true, nullable = false)
	public Long getIdGrupo() {
		return this.idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	@Column(name = "NOME", length = 100)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "DESCRICAO", length = 100)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbGrupo")
	public List<TbUsuarioGrupo> getTbUsuarioGrupos() {
		return this.tbUsuarioGrupos;
	}

	public void setTbUsuarioGrupos(List<TbUsuarioGrupo> tbUsuarioGrupos) {
		this.tbUsuarioGrupos = tbUsuarioGrupos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idGrupo == null) ? 0 : idGrupo.hashCode());
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
		TbGrupo other = (TbGrupo) obj;
		if (idGrupo == null) {
			if (other.idGrupo != null)
				return false;
		} else if (!idGrupo.equals(other.idGrupo))
			return false;
		return true;
	}

}
