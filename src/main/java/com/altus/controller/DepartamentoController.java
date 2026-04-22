package com.altus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altus.model.Departamento;
import com.altus.repository.Departamentos;
import com.altus.repository.Empresas;
import com.altus.repository.filter.DepartamentoFilter;
import com.altus.service.DepartamentoService;
import com.altus.service.exception.ObjetoJaExisteException;

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
	
	@RequestMapping("/novo")
	public ModelAndView novo(Departamento departamento) {
		ModelAndView mv = new ModelAndView("departamento/PesquisarDepartamento");
	    
		mv.addObject("empresas", empresas.findAll());		
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Departamento departamento, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			result.getAllErrors().forEach(e -> System.out.println("VEJA O ERRO: " + e));
			return novo(departamento);
		}
		
		try {
			departamentoService.salvar(departamento);
		}catch (ObjetoJaExisteException e){
			   result.rejectValue("nome", e.getMessage(), e.getMessage());
			   return novo(departamento);
		}
		
		attributes.addFlashAttribute("mensagem", "Departamento salvo com sucesso.");	
		return new ModelAndView("redirect:/departamento");
	}
	
	@GetMapping
    public ModelAndView pesquisar(DepartamentoFilter departamentoFilter, BindingResult result, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest request){
        ModelAndView mv = new ModelAndView("departamento/PesquisarDepartamento");
        
        mv.addObject("departamentos", departamentos.findAll());
          
//        PageWrapper<Funcionario> pageWrapper = new PageWrapper<>(funcionarios.filtrar(funcionarioFilter, pageable),request);
//        mv.addObject("pagina", pageWrapper);
        return mv;
    }
	
	
}
