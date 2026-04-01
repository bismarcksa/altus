package com.algaworks.brewer.repository;

import com.algaworks.brewer.model.Cliente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.algaworks.brewer.repository.helper.cliente.ClienteRepositoryQueries;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteRepositoryQueries{

	public Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpj);
	
	List<Cliente> findByNomeStartingWithIgnoreCase(String nome);

}
