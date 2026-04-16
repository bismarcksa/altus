package com.altus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.altus.model.EstadoCivil;
import com.altus.model.Funcionario;
import com.altus.model.Sexo;
import com.altus.model.TipoContrato;
import com.altus.repository.Estados;
import com.altus.service.CadastroFuncionarioService;

import jakarta.validation.Valid;

@Controller
public class FuncionarioController {
	
	@Autowired
	private Estados estados;
	
	@Autowired
	private CadastroFuncionarioService cadastroFuncionarioService;
	
	@RequestMapping("/funcionario/novo")
	public ModelAndView novo(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("funcionario/CadastroFuncionario");
	    
		mv.addObject("sexos", Sexo.values());
		mv.addObject("estadoCivis", EstadoCivil.values());
		mv.addObject("tipoContratos", TipoContrato.values());
		mv.addObject("estados", estados.findAll());
		
		return mv;
	}
	
	@RequestMapping(value = "/funcionario/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			result.getAllErrors().forEach(e -> System.out.println("VEJA O ERRO: " + e));
			return novo(funcionario);
		}
		
		cadastroFuncionarioService.salvar(funcionario);
		attributes.addFlashAttribute("mensagem", "Funcionário salvo com sucesso.");	
		return new ModelAndView("redirect:/funcionario/novo");
	}
	
	
}
