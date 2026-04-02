package com.altus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altus.model.Funcionario;

import jakarta.validation.Valid;

@Controller
public class FuncionarioController {
	
	@RequestMapping("/funcionario/novo")
	public String novo(Funcionario funcionario) {
		return "funcionario/CadastroFuncionario";
	}
	
	@RequestMapping(value = "/funcionario/novo", method = RequestMethod.POST)
	public String cadastrar(@Valid Funcionario funcionario, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			model.addAttribute(funcionario);
			return novo(funcionario);
		}
		
		//salvar
		attributes.addFlashAttribute("mensagem", "Funcionario salvo com sucesso.");
		return "redirect:/funcionario/novo";
	}
	
	@RequestMapping("/funcionario/cadastro")
	public String cadastro() {
		return "funcionario/funcionario-cadastro";
	}
}
