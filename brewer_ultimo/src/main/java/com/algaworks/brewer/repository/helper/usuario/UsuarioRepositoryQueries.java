package com.algaworks.brewer.repository.helper.usuario;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.filter.UsuarioFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositoryQueries {

    public Optional<Usuario> porEmailEAtivo(String email);

    public List<String> permissoes(Usuario usuario);

    public Page<Usuario> filtrar(UsuarioFilter usuarioFiltro, Pageable pageable);
    
    public Usuario buscarUsuarioComGrupos(Long codigo);
    
}
