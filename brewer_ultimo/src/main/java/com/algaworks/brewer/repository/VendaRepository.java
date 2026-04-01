package com.algaworks.brewer.repository;

import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.helper.venda.VendaRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long>, VendaRepositoryQueries{


}

