package com.altus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altus.model.Cidade;
import com.altus.model.Empresa;
import com.altus.model.Endereco;
import com.altus.model.RegimeTributario;
import com.altus.model.Tipo;
import com.altus.repository.Estados;
import com.altus.service.EmpresaService;

import jakarta.validation.Valid;

@Controller
public class EmpresaController {
	
	@Autowired
	private Estados estados;
	
	@Autowired
	private EmpresaService cadastroEmpresaService;
	
	@RequestMapping("/empresa/novo")
	public ModelAndView novo(Empresa empresa) {
		ModelAndView mv = new ModelAndView("empresa/CadastroEmpresa");
	    
		
		mv.addObject("regimes", RegimeTributario.values());
		mv.addObject("tipos", Tipo.values());
		mv.addObject("estados", estados.findAll());
		
		return mv;
	}
	
	@RequestMapping(value = "/empresa/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Empresa empresa, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			result.getAllErrors().forEach(e -> System.out.println("VEJA O ERRO: " + e));
			return novo(empresa);
		}
		
		cadastroEmpresaService.salvar(empresa);
		attributes.addFlashAttribute("mensagem", "Empresa salva com sucesso.");	
		return new ModelAndView("redirect:/empresa/novo");
	}
}
