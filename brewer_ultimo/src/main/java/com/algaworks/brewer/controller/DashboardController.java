package com.algaworks.brewer.controller;

import com.algaworks.brewer.repository.CervejaRepository;
import com.algaworks.brewer.repository.ClienteRepository;
import com.algaworks.brewer.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {

	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CervejaRepository cervejaRepository;

	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("Dashboard");
		mv.addObject("valorTotalNoAno", vendaRepository.valorTotalNoAno());
		mv.addObject("valorTotalNoMes", vendaRepository.valorTotalNoMes());
		mv.addObject("ticketMedio", vendaRepository.valorTicketMedioNoAno());

		mv.addObject("valorItensEstoque", cervejaRepository.valorItensEstoque());
		mv.addObject("totalClientes", clienteRepository.count());

		return mv;
	}
	
}
