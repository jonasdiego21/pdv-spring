package br.com.jdrmservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static br.com.jdrmservices.util.Constants.VIEW_VISUALIZAR_ITENS_VENDA;
import static br.com.jdrmservices.util.Constants.VIEW_VENDA_REDIRECT;

import br.com.jdrmservices.model.ItemVenda;
import br.com.jdrmservices.model.Venda;
import br.com.jdrmservices.repository.Vendas;
import br.com.jdrmservices.service.VendaService;

@Controller
@RequestMapping("/itensvendas")
public class ItensVendasController {

	@Autowired
	private Vendas vendas;
	
	@Autowired
	private VendaService vendaService;
	
	@GetMapping("/{codigo}")
	public ModelAndView itemVenda(@PathVariable("codigo") Venda venda) {
		ModelAndView mv = new ModelAndView(VIEW_VISUALIZAR_ITENS_VENDA);
		mv.addObject("itens", vendas.findById(venda.getCodigo()).get().getItens());
		
		return mv;
	}
	
	@GetMapping("/devolver/{codigo}")
	public ModelAndView devolverItem(@PathVariable("codigo") ItemVenda itemVenda) {
		ModelAndView mv = new ModelAndView(VIEW_VENDA_REDIRECT);
		
		vendaService.devolverItem(itemVenda);
		
		return mv;
	}
}
