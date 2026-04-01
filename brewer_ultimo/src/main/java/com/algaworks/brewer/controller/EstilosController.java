package com.algaworks.brewer.controller;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.EstiloRepository;
import com.algaworks.brewer.repository.filter.EstiloFilter;
import com.algaworks.brewer.services.EstiloService;
import com.algaworks.brewer.services.exception.ObjetoJaExisteException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

//import com.algaworks.brewer.services.EstiloService;
//import com.algaworks.brewer.services.exception.ObjetoJaExisteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/estilos")
public class EstilosController {

	@Autowired
	private EstiloRepository estiloRepository;
	
    @Autowired
    private EstiloService estiloService;

    @RequestMapping("novo")
    public ModelAndView novo(Estilo estilo){
        return new ModelAndView("estilo/CadastroEstilo");
    }

    @RequestMapping(value = "/novo", method = RequestMethod.POST)
    public ModelAndView cadastrar(@Valid Estilo estilo, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            return novo(estilo);
        }
        
        try {
            estiloService.salvar(estilo);
        }catch (ObjetoJaExisteException ex){
            result.rejectValue("descricao", null, ex.getMessage());
            return novo(estilo);
        }
        attributes.addFlashAttribute("mensagem", "Estilo Salvo com Sucesso.");
        return new ModelAndView("redirect:/estilos/novo");
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<?> salvarEstiloRapido(@RequestBody @Valid Estilo estilo, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getFieldError("descricao").getDefaultMessage());
        }
        
        	estilo = estiloService.salvar(estilo); 	     
        return ResponseEntity.ok(estilo);
    }
    
    @GetMapping
    public ModelAndView pesquisar(EstiloFilter estiloFilter, BindingResult result, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest request){
        ModelAndView mv = new ModelAndView("estilo/PesquisaEstilos");
          
        PageWrapper<Estilo> pageWrapper = new PageWrapper<>(estiloRepository.filtrar(estiloFilter, pageable),request);
        mv.addObject("pagina", pageWrapper);
        return mv;
    }
}
