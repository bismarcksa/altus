package com.algaworks.brewer.repository.helper.estado;

import com.algaworks.brewer.model.Estado;
import com.algaworks.brewer.repository.filter.EstadoFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EstadoRepositoryQueries {

    public Page<Estado> filtrar(EstadoFilter estadoFilter, Pageable pageable);
  
}
