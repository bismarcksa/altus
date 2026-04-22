package com.altus.repository.helper.departamento;

import com.altus.model.Departamento;
import com.altus.repository.filter.DepartamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartamentosQueries {

    public Page<Departamento> filtrar(DepartamentoFilter departamentoFilter, Pageable pageable);
  
}
