package com.altus.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.altus.model.Funcionario;

public interface Funcionarios extends JpaRepository<Funcionario, Long>{
	public Optional<Funcionario> findById(Long idFuncionario);
	
	public Optional<Funcionario> findByCpf(String cpf);
}
