package com.altus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.altus.model.Empresa;

import jakarta.validation.Valid;

@Controller
public class EmpresaController {
	
	@RequestMapping("/empresa/novo")
	public String novo(Empresa empresa) {
		return "empresa/CadastroEmpresa";
	}
	
	@RequestMapping(value = "/empresa/novo", method = RequestMethod.POST)
	public String cadastrar(@Valid Empresa empresa, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			model.addAttribute(empresa);
			return novo(empresa);
		}
		
		//salvar
		attributes.addFlashAttribute("mensagem", "Empresa salva com sucesso.");
		return "redirect:/empresa/novo";
	}
	
	@RequestMapping("/empresa/cadastro")
	public String cadastro() {
		return "empresa/CadastroEmpresa";
	}
}
