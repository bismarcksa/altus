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
import com.altus.model.EstadoCivil;
import com.altus.model.Funcionario;
import com.altus.model.Sexo;
import com.altus.model.Situacao;
import com.altus.model.TipoContrato;
import com.altus.repository.Estados;
import com.altus.repository.Funcionarios;
import com.altus.repository.filter.FuncionarioFilter;
import com.altus.service.CadastroFuncionarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class FuncionarioController {
	
	@Autowired
	private Estados estados;
	
	@Autowired
	private Funcionarios funcionarios;
	
	@Autowired
	private CadastroFuncionarioService cadastroFuncionarioService;
	
	@RequestMapping("/funcionario/novo")
	public ModelAndView novo(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("funcionario/CadastroFuncionario");
	    
		mv.addObject("sexos", Sexo.values());
		mv.addObject("estadoCivis", EstadoCivil.values());
		mv.addObject("tipoContratos", TipoContrato.values());
		mv.addObject("situacoes", Situacao.values());
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
	
	
	@GetMapping("/funcionarios")
    public ModelAndView pesquisar(FuncionarioFilter funcionarioFilter, BindingResult result, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest request){
        ModelAndView mv = new ModelAndView("funcionario/ListagemFuncionario");

          
//        PageWrapper<Funcionario> pageWrapper = new PageWrapper<>(funcionarios.filtrar(funcionarioFilter, pageable),request);
//        mv.addObject("pagina", pageWrapper);
        return mv;
    }
	
	
}
