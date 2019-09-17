package br.com.jdrmservices.controller;

import static br.com.jdrmservices.util.Constants.INFORMACOES_SALVAS_SUCESSO;
import static br.com.jdrmservices.util.Constants.VIEW_PESQUISAR_CONTAPAGAR;
import static br.com.jdrmservices.util.Constants.VIEW_CONTAPAGAR_NOVO;
import static br.com.jdrmservices.util.Constants.VIEW_CONTAPAGAR_REDIRECT;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.jdrmservices.dto.ContaPagarDTO;
import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.model.ContaPagar;
import br.com.jdrmservices.model.enumeration.Status;
import br.com.jdrmservices.repository.Fornecedores;
import br.com.jdrmservices.repository.ContasPagar;
import br.com.jdrmservices.repository.filter.ContaPagarFilter;
import br.com.jdrmservices.service.ContaPagarService;

@Controller
@RequestMapping("/contaspagar")
public class ContasPagarController {

	@Autowired
	private ContaPagarService contaPagarService;
	
	@Autowired
	private ContasPagar contasPagar;
	
	@Autowired
	private Fornecedores fornecedores;
	
	@Autowired
	private ContaPagarDTO contaPagarDTO;
	
	@GetMapping("/novo")
	public ModelAndView novo(ContaPagar contaPagar) {
		ModelAndView mv = new ModelAndView(VIEW_CONTAPAGAR_NOVO);	
		mv.addObject("fornecedores", fornecedores.findAllByOrderByNomeAsc());
		mv.addObject("status", Status.values());
		mv.addObject("contaspagar", contasPagar.findAllByOrderByFornecedorAsc());
		mv.addObject(contaPagar);
		
		return mv;
	}
	
	@PostMapping
	public ModelAndView cadastrar(@Valid ContaPagar contaPagar, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(contaPagar);
		}
		
		try {
			contaPagarService.cadastrar(contaPagar);
		} catch (GlobalException e) {
			result.rejectValue("cliente", e.getMessage(), e.getMessage());
			return novo(contaPagar);		
		}
		
		attributes.addFlashAttribute("successMessage", INFORMACOES_SALVAS_SUCESSO);
		
		return new ModelAndView(VIEW_CONTAPAGAR_REDIRECT);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") ContaPagar contaPagar) {	
		ModelAndView mv = novo(contaPagar);
		mv.addObject(contaPagar);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") ContaPagar contaPagar) {
		try {
			contaPagarService.excluir(contaPagar);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();	
	}
	
	@GetMapping
	public ModelAndView pesquisar(ContaPagarFilter contaPagarFilter, BindingResult result, @PageableDefault(size = 10) Pageable pageable) {
		ModelAndView mv = new ModelAndView(VIEW_PESQUISAR_CONTAPAGAR);
		mv.addObject("fornecedores", fornecedores.findAllByOrderByNomeAsc());
		mv.addObject("status", Status.values());
		mv.addObject("contaspagar", contasPagar.findAllByOrderByFornecedorAsc());
		
		Page<ContaPagar> pagina = contasPagar.filtrar(contaPagarFilter, pageable);
		mv.addObject("pagina", pagina);
		
		return mv;
	}
	
	@GetMapping("/lancamentos/{codigoContaPagar}")
	public @ResponseBody ResponseEntity<?> pesquisaContaPagar(@PathVariable Long codigoContaPagar) {
		contaPagarDTO.setCodigoContaPagar(codigoContaPagar);
		
		return ResponseEntity.ok().build();
	}
}