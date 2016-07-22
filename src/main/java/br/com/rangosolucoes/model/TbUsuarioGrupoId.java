package br.com.rangosolucoes.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TbUsuarioGrupoId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long usuarioId;
	private Long grupoId;

	public TbUsuarioGrupoId() {
	}

	public TbUsuarioGrupoId(Long usuarioId, Long grupoId) {
		this.usuarioId = usuarioId;
		this.grupoId = grupoId;
	}

	@Column(name = "USUARIO_ID")
	public Long getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	@Column(name = "GRUPO_ID")
	public Long getGrupoId() {
		return this.grupoId;
	}

	public void setGrupoId(Long grupoId) {
		this.grupoId = grupoId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbUsuarioGrupoId))
			return false;
		TbUsuarioGrupoId castOther = (TbUsuarioGrupoId) other;

		return ((this.getUsuarioId() == castOther.getUsuarioId()) || (this.getUsuarioId() != null
				&& castOther.getUsuarioId() != null && this.getUsuarioId().equals(castOther.getUsuarioId())))
				&& ((this.getGrupoId() == castOther.getGrupoId()) || (this.getGrupoId() != null
						&& castOther.getGrupoId() != null && this.getGrupoId().equals(castOther.getGrupoId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getUsuarioId() == null ? 0 : this.getUsuarioId().hashCode());
		result = 37 * result + (getGrupoId() == null ? 0 : this.getGrupoId().hashCode());
		return result;
	}

}
