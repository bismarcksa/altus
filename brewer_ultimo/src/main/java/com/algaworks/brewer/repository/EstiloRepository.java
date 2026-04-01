package com.algaworks.brewer.repository;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.helper.estilo.EstiloRepositoryQueries;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstiloRepository extends JpaRepository<Estilo, Long>, EstiloRepositoryQueries{
    public Optional<Estilo> findByDescricaoIgnoreCase(String descricao);
}