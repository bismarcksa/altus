package com.algaworks.brewer.services;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.StatusVenda;
import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.VendaRepository;
import com.algaworks.brewer.services.event.venda.VendaEvent;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;
    
    @Autowired
    private ApplicationEventPublisher publisher;

    @SuppressWarnings("deprecation")
	@Transactional
    public void salvar(Venda venda){
    	if(venda.isSalvarProibido()){
            throw new RuntimeException("Usuário tentando salvar uma venda proibida");
        }
    	
    	if(venda.isNova()) {
    		venda.setDataCriacao(LocalDateTime.now());
    	}else {
    		Venda vendaExistente = vendaRepository.getOne(venda.getCodigo());
            venda.setDataCriacao(vendaExistente.getDataCriacao());
    	}
    		
  	
    	if(venda.getDataEntrega() != null) {
    		venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(), venda.getHorarioEntrega() != null ? venda.getHorarioEntrega() : LocalTime.NOON));
    	}
    		
        vendaRepository.save(venda);
    }
    
    @Transactional
    public void emitir(Venda venda) {
        venda.setStatus(StatusVenda.EMITIDA);
        salvar(venda);

        publisher.publishEvent(new VendaEvent(venda));
    }

    //USANDO ESSE p0, PEGA O PRIMEIRO PARAMETRO DO CANCELAR NO CONTROLLER, QUE É O PARAMETRO DA VENDA
    @PreAuthorize("#p0.usuario == principal.usuario or hasRole('CANCELAR_VENDA')")
    @Transactional
    public void cancelar(Venda venda) {
//        System.out.println(venda.getUsuario().getNome());
        Venda vendaExistente = vendaRepository.getOne(venda.getCodigo());

        vendaExistente.setStatus(StatusVenda.CANCELADA);
        vendaRepository.save(vendaExistente);
    }
    

}
