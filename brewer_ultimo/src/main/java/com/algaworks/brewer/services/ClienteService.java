package com.algaworks.brewer.services;

import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.repository.ClienteRepository;
import com.algaworks.brewer.services.exception.CpfCnpjClienteJaCadastradoException;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public void salvar(Cliente cliente){
    	Optional<Cliente> clienteExistente = clienteRepository.findByCpfOuCnpj(cliente.getCpfOuCnpjSemFormatacao());
    	if(clienteExistente.isPresent()) {
    		throw new CpfCnpjClienteJaCadastradoException("CPF/CNPJ Já cadastrado");
    	}
        clienteRepository.save(cliente);
    }

}
