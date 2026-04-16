package com.altus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altus.model.Funcionario;
import com.altus.repository.Funcionarios;

@Service
public class CadastroFuncionarioService {
	
	@Autowired
	private Funcionarios funcionarios;
	
	@Transactional
	public void salvar(Funcionario funcionario) {
		funcionarios.save(funcionario);
	}
}
