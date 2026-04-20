package com.altus.repository.helper;

import com.altus.model.Funcionario;
import com.altus.repository.filter.FuncionarioFilter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FuncionariosQueries {

    public Page<Funcionario> filtrar(FuncionarioFilter funcionarioFilter, Pageable pageable);
    
//    List<CervejaDTO> porSkuOuNome(String skuOuNome);
  
}
