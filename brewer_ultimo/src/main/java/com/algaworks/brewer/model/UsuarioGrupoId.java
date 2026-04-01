package com.algaworks.brewer.model;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Embeddable
public class UsuarioGrupoId implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "codigo_usuario" )
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "codigo_grupo")
    private Grupo grupo;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioGrupoId)) return false;
        UsuarioGrupoId that = (UsuarioGrupoId) o;
        return Objects.equals(getUsuario(), that.getUsuario()) &&
                Objects.equals(getGrupo(), that.getGrupo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsuario(), getGrupo());
    }
}
