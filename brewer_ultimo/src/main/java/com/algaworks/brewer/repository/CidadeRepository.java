package com.algaworks.brewer.repository;

import  com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.model.Estado;
import com.algaworks.brewer.repository.helper.cidade.CidadeRepositoryQueries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>, CidadeRepositoryQueries{

	public List<Cidade> findByEstadoCodigo(Long codigoEstado);

	public Optional<Cidade> findByNomeAndEstado(String nome, Estado estado);
		
}