package com.algaworks.brewer.repository;

import com.algaworks.brewer.dto.ValorItensEstoque;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.helper.cerveja.CervejaRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CervejaRepository extends JpaRepository<Cerveja, Long>, CervejaRepositoryQueries{
	
	@Query(value = "select new com.algaworks.brewer.dto.ValorItensEstoque(sum(valor * quantidadeEstoque), sum(quantidadeEstoque)) from Cerveja")
    ValorItensEstoque valorItensEstoque();

}
