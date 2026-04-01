package com.algaworks.brewer.repository;

import com.algaworks.brewer.model.Estado;
import com.algaworks.brewer.repository.helper.estado.EstadoRepositoryQueries;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>, EstadoRepositoryQueries{
	public Optional<Estado> findByNomeIgnoreCase(String nome);
}
