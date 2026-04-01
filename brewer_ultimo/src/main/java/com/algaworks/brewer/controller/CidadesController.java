package com.algaworks.brewer.controller;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.CidadeRepository;
import com.algaworks.brewer.repository.EstadoRepository;
import com.algaworks.brewer.repository.filter.CidadeFilter;
import com.algaworks.brewer.services.exception.NomeCidadeJaCadastradaException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import com.algaworks.brewer.services.CidadeService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cidades")
public class CidadesController {

	@Autowired
	private EstadoRepository estadoRepository;	
	
    @Autowired
    private CidadeRepository cidadeRepository;
    
    @Autowired
    private CidadeService cidadeService;

    @RequestMapping("/novo")
    public ModelAndView novo(Cidade cidade){
        ModelAndView mv = new ModelAndView("cidade/CadastroCidade");
        mv.addObject("estados", estadoRepository.findAll());
        return mv;
    }
    
    @Cacheable(value = "cidades", key= "#p0") //ESSE "#p0" É O PRIMEIRO PARAMETRO DE CACHE/FUNÇÃO
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisarPorCodigoEstado(
			@RequestParam(name = "estado", defaultValue = "-1") Long codigoEstado) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {	}
		return cidadeRepository.findByEstadoCodigo(codigoEstado);
	}
	
	@PostMapping("/novo")
	@CacheEvict(value="cidades", key="#cidade.estado.codigo", condition = "#cidade != null && #cidade.temEstado()")
	public ModelAndView salvar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(cidade);
		}
		
		try {
			cidadeService.salvar(cidade);
		} catch (NomeCidadeJaCadastradaException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(cidade);
		}
		
		
		attributes.addFlashAttribute("mensagem", "Cidade salva com sucesso!");
		return new ModelAndView("redirect:/cidades/novo");
	}
	
	@GetMapping
    public ModelAndView filtrar(CidadeFilter cidadeFilter, @PageableDefault(size = 15)Pageable pageable, HttpServletRequest request){
        ModelAndView mv = new ModelAndView("cidade/PesquisaCidades");

        PageWrapper<Cidade> pagina = new PageWrapper<>(cidadeRepository.filtrar(cidadeFilter,pageable),request);
        mv.addObject("estados",estadoRepository.findAll());
        mv.addObject("pagina",pagina);
        return mv;
    }
}
