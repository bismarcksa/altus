package com.algaworks.brewer.repository.helper.estilo;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.filter.EstiloFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EstiloRepositoryQueries {

    public Page<Estilo> filtrar(EstiloFilter estiloFilter, Pageable pageable);
  
}
