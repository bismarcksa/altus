package com.altus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altus.service.exception.ObjetoJaExisteException;
import com.altus.model.Funcionario;
import com.altus.repository.Funcionarios;

@Service
public class FuncionarioService {
	
	@Autowired
	private Funcionarios funcionarios;
	
	@Transactional
	public void salvar(Funcionario funcionario) {
		Optional<Funcionario> funcionarioOptional = funcionarios.findByCpf(funcionario.getCpf());
        
		if(funcionarioOptional.isPresent()){
            throw new ObjetoJaExisteException("CPF já está cadastrado.");
        }
        	
		funcionarios.saveAndFlush(funcionario);
	}
}
