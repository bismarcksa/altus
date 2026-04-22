package com.altus.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.altus.model.Departamento;
import com.altus.repository.helper.departamento.DepartamentosQueries;

@Repository
public interface Departamentos extends JpaRepository<Departamento, Long>, DepartamentosQueries{
    public Optional<Departamento> findByNomeIgnoreCaseAndEmpresaId(String nome, Long empresaId);
}