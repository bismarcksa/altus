package com.altus.repository;

import com.altus.model.Estado;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Estados extends JpaRepository<Estado, Long>{
	public Optional<Estado> findByNomeIgnoreCase(String nome);
}
