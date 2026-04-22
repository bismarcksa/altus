package com.altus.controller;

import com.altus.model.Cidade;
import com.altus.repository.Cidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/cidades")
public class CidadeController {	
	
    @Autowired
    private Cidades cidades;   
    
//    @RequestMapping("/nova")
//    public String nova() {
//    		return "cidade/CaadastroCidade";
//    }
    
//    @Cacheable(value = "cidades", key= "#p0") //ESSE "#p0" É O PRIMEIRO PARAMETRO DE CACHE/FUNÇÃO
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisarPorIdEstado(@RequestParam(name = "estado", defaultValue = "-1") Long idEstado) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {	}
		
		return cidades.findByEstadoId(idEstado);
	}
}
