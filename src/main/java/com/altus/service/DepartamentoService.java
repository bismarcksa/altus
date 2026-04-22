package com.altus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altus.model.Departamento;
import com.altus.repository.Departamentos;
import com.altus.service.exception.ObjetoJaExisteException;

import java.util.Optional;

@Service
public class DepartamentoService {

    @Autowired
    private Departamentos departamentos;

    @Transactional
    public Departamento salvar(Departamento departamento){
        Optional<Departamento> departamentoOptional = departamentos.findByNomeIgnoreCaseAndEmpresaId(departamento.getNome(), departamento.getEmpresa().getId());
        if(departamentoOptional.isPresent()){
            throw new ObjetoJaExisteException("Nome do departamento já está cadastrado nessa empresa.");
        }
        return departamentos.saveAndFlush(departamento);
    }

}
