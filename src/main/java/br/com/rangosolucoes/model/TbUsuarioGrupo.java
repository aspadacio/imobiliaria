package br.com.rangosolucoes.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_usuario_grupo", catalog = "imobiliaria")
public class TbUsuarioGrupo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private TbUsuarioGrupoId id;
	private TbUsuario tbUsuario;
	private TbGrupo tbGrupo;

	public TbUsuarioGrupo() {
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "usuarioId", column = @Column(name = "USUARIO_ID") ),
			@AttributeOverride(name = "grupoId", column = @Column(name = "GRUPO_ID") ) })
	public TbUsuarioGrupoId getId() {
		return this.id;
	}

	public void setId(TbUsuarioGrupoId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USUARIO_ID", insertable = false, updatable = false)
	public TbUsuario getTbUsuario() {
		return this.tbUsuario;
	}

	public void setTbUsuario(TbUsuario tbUsuario) {
		this.tbUsuario = tbUsuario;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "GRUPO_ID", insertable = false, updatable = false)
	public TbGrupo getTbGrupo() {
		return this.tbGrupo;
	}

	public void setTbGrupo(TbGrupo tbGrupo) {
		this.tbGrupo = tbGrupo;
	}

}
