package com.algaworks.brewer.repository.helper.cerveja;

import com.algaworks.brewer.dto.CervejaDTO;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.filter.CervejaFilter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CervejaRepositoryQueries {

    public Page<Cerveja> filtrar(CervejaFilter cervejaFilter, Pageable pageable);
    
    List<CervejaDTO> porSkuOuNome(String skuOuNome);
  
}
