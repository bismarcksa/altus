package com.algaworks.brewer.repository;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.helper.usuario.UsuarioRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>, UsuarioRepositoryQueries {
    
	public Optional<Usuario> findByEmail(String email);
    
	public List<Usuario> findByCodigoIn(Long codigos[]);
}
