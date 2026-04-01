package com.algaworks.brewer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.dto.CervejaDTO;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.Origem;
import com.algaworks.brewer.model.Sabor;
import com.algaworks.brewer.repository.EstiloRepository;
import com.algaworks.brewer.repository.filter.CervejaFilter;
import com.algaworks.brewer.services.CervejaService;
import com.algaworks.brewer.services.exception.ImpossivelExcluirEntidadeException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import com.algaworks.brewer.repository.CervejaRepository;


@Controller
@RequestMapping("/cervejas")
public class CervejasController {
	
	@Autowired
	private EstiloRepository estiloRepository;
	
	@Autowired
	private CervejaRepository cervejaRepository;
	
	@Autowired
	private CervejaService cervejaService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cerveja cerveja) {		
		ModelAndView mv = new ModelAndView("cerveja/CadastroCerveja");
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", estiloRepository.findAll());
		mv.addObject("origens", Origem.values());		
		return mv;
	}
	
	@RequestMapping(value = {"/novo", "/{codigo}"}, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(cerveja);
		}
			
		cervejaService.salvar(cerveja);		
		attributes.addFlashAttribute("mensagem", "Cerveja salva com sucesso.");
		return new ModelAndView("redirect:/cervejas/novo");
	}
	
	@GetMapping
    public ModelAndView pesquisar(CervejaFilter cervejaFilter, BindingResult result, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest request){
        ModelAndView mv = new ModelAndView("cerveja/PesquisaCervejas");
        mv.addObject("sabores", Sabor.values());
        mv.addObject("origens", Origem.values());
        mv.addObject("estilos", estiloRepository.findAll());
          
        PageWrapper<Cerveja> pageWrapper = new PageWrapper<>(cervejaRepository.filtrar(cervejaFilter, pageable),request);
        mv.addObject("pagina", pageWrapper);
        return mv;
    }

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<CervejaDTO> pesquisar(@RequestParam("skuOuNome") String skuOuNome) {
        return cervejaRepository.porSkuOuNome(skuOuNome);
    }
	
	@DeleteMapping("/{codigo}")
    public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Cerveja cerveja) {
        try {
        		cervejaService.excluir(cerveja);
        } catch (ImpossivelExcluirEntidadeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Cerveja cerveja){
        ModelAndView mv = novo(cerveja);
        mv.addObject(cerveja);
        return mv;
    }
}
