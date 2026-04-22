package com.altus.repository.helper.funcionario;

import com.altus.model.Funcionario;
import com.altus.repository.filter.FuncionarioFilter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FuncionarioQueries {

    public Page<Funcionario> filtrar(FuncionarioFilter funcionarioFilter, Pageable pageable);
    
//    List<CervejaDTO> porSkuOuNome(String skuOuNome);
  
}
