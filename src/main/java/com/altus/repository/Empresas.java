package com.altus.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.altus.model.Empresa;

public interface Empresas extends JpaRepository<Empresa, Long>{
	public Optional<Empresa> findById(Long idEmpresa);
}
