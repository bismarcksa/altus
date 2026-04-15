package com.altus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altus.model.Empresa;
import com.altus.repository.Empresas;

@Service
public class CadastroEmpresaService {
	
	@Autowired
	private Empresas empresas;
	
	@Transactional
	public void salvar(Empresa empresa) {
		empresas.save(empresa);
	}
}
