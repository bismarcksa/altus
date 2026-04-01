package com.algaworks.brewer.controller;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.model.TipoPessoa;
import com.algaworks.brewer.repository.ClienteRepository;
import com.algaworks.brewer.repository.EstadoRepository;
import com.algaworks.brewer.repository.filter.ClienteFilter;
import com.algaworks.brewer.services.ClienteService;
import com.algaworks.brewer.services.exception.CpfCnpjClienteJaCadastradoException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

   @Autowired
   private EstadoRepository estadoRepository;
	
   @Autowired
   private ClienteRepository clienteRepository;
   
   @Autowired
   private ClienteService clienteService;
   
   @RequestMapping("/novo")
   public ModelAndView novo(Cliente cliente){
       ModelAndView mv = new ModelAndView("cliente/CadastroCliente");
       mv.addObject("tiposPessoa", TipoPessoa.values());
       mv.addObject("estados", estadoRepository.findAll());
       return mv;
   }
    
   @PostMapping(value = "/novo")
   public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes) {
	   if(result.hasErrors()) {
		   return novo(cliente);
	   }
			
	   try { 
		   clienteService.salvar(cliente);
	   }catch (CpfCnpjClienteJaCadastradoException e){
		   result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
		   return novo(cliente);
	   }
	   
	   attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso.");
	   return new ModelAndView("redirect:/clientes/novo");
	}
   
   @GetMapping
   public ModelAndView pesquisar(ClienteFilter clienteFilter, @PageableDefault(size = 5) Pageable pageable, HttpServletRequest request) {
       ModelAndView mv = new ModelAndView("/cliente/PesquisaClientes");
       mv.addObject("tipoPessoa", TipoPessoa.values());
       
       PageWrapper<Cliente> pagina = new PageWrapper<>(clienteRepository.filtrar(clienteFilter, pageable), request);

       mv.addObject("pagina", pagina);
       return mv;
   }
   
   @GetMapping("/pesquisa")
   @ResponseBody
   public List<Cliente> pesquisar(@RequestParam("nome") String nome) {
	   validarTamanhoNome(nome);
       return clienteRepository.findByNomeStartingWithIgnoreCase(nome);
   }

   @SuppressWarnings("deprecation")
   private void validarTamanhoNome(String nome) {
       if(StringUtils.isEmpty(nome) || nome.length() < 3){
           throw new IllegalArgumentException();
       }
   }
 
   @ExceptionHandler(IllegalArgumentException.class)
   public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e){
       return ResponseEntity.badRequest().build();
   } 
}
