package com.altus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.altus.model.Departamento;
import com.altus.repository.Departamentos;
import com.altus.repository.Empresas;
import com.altus.repository.filter.DepartamentoFilter;
import com.altus.service.DepartamentoService;
import com.altus.service.exception.ObjetoJaExisteException;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/departamento")
public class DepartamentoController {
	
	@Autowired
	private Empresas empresas;
	
	@Autowired
	private Departamentos departamentos;
	
	@Autowired
	private DepartamentoService departamentoService;
	
/*	@RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<?> salvarDepartamentoRapido(@RequestBody @Valid Departamento departamento, BindingResult result){
        if(result.hasErrors()){
            List<String> erros = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).toList();
            return ResponseEntity.badRequest().body(erros);
        }
        
        try {   
        	// Retorna o fragmento da tabela atualizado
            ModelAndView mv = new ModelAndView("departamento/PesquisarDepartamento :: listaDepartamentos");
        	departamento = departamentoService.salvar(departamento);
        }catch(ObjetoJaExisteException e) {
        	return ResponseEntity.badRequest().body(e.getMessage());
        	
        }
        
        return ResponseEntity.ok(departamento);
    }*/
	
	@RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
	// 1. Removemos o @ResponseBody porque queremos retornar HTML (ModelAndView) no sucesso
	public Object salvarDepartamentoRapido(@RequestBody @Valid Departamento departamento, BindingResult result) {	    
	    // 2. Se houver erro de validação, retornamos o JSON com os erros (o jQuery cai no 'error:')
	    if (result.hasErrors()) {
	        List<String> erros = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).toList();
	        return ResponseEntity.badRequest().body(erros);
	    }
	    
	    try {   
	        departamentoService.salvar(departamento);
	        
	        // 4. Prepara o fragmento de HTML com a lista atualizada
	        ModelAndView mv = new ModelAndView("departamento/PesquisarDepartamento :: listaDepartamentos");	      
	        
	        mv.addObject("departamentos", departamentos.findAll());

	        // 5. Retorna o ModelAndView (o jQuery cai no 'success:' com o HTML pronto)
	        return mv;
	        
	    } catch(ObjetoJaExisteException e) {
	        // 6. Erro de negócio também retorna JSON 400
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}
	
	@GetMapping
    public ModelAndView pesquisar(DepartamentoFilter departamentoFilter, BindingResult result, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest request){
        ModelAndView mv = new ModelAndView("departamento/PesquisarDepartamento");
        
        
        mv.addObject("departamentos", departamentos.findAll());
        mv.addObject("empresas", empresas.findAll());

        return mv;
    }
}
