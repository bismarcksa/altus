package com.algaworks.brewer.model;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario_grupo")
public class UsuarioGrupo implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UsuarioGrupoId id;

    public UsuarioGrupoId getId() {
        return id;
    }

    public void setId(UsuarioGrupoId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioGrupo)) return false;
        UsuarioGrupo that = (UsuarioGrupo) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
