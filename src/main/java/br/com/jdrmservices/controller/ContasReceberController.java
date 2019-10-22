package br.com.jdrmservices.controller;

import static br.com.jdrmservices.util.Constants.INFORMACOES_SALVAS_SUCESSO;
import static br.com.jdrmservices.util.Constants.VIEW_PESQUISAR_CONTARECEBER;
import static br.com.jdrmservices.util.Constants.VIEW_CONTARECEBER_NOVO;
import static br.com.jdrmservices.util.Constants.VIEW_CONTARECEBER_REDIRECT;

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

import br.com.jdrmservices.dto.ContaReceberDTO;
import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.model.ContaReceber;
import br.com.jdrmservices.model.enumeration.Status;
import br.com.jdrmservices.repository.Clientes;
import br.com.jdrmservices.repository.ContasReceber;
import br.com.jdrmservices.repository.filter.ContaReceberFilter;
import br.com.jdrmservices.service.ContaReceberService;

@Controller
@RequestMapping("/contasreceber")
public class ContasReceberController {

	@Autowired
	private ContaReceberService contaReceberService;
	
	@Autowired
	private ContasReceber contasReceber;
	
	@Autowired
	private Clientes clientes;
	
	@Autowired
	private ContaReceberDTO contaReceberDTO;
	
	@GetMapping("/novo")
	public ModelAndView novo(ContaReceber contaReceber) {
		ModelAndView mv = new ModelAndView(VIEW_CONTARECEBER_NOVO);	
		mv.addObject("clientes", clientes.findAllByOrderByNomeAsc());
		mv.addObject("status", Status.values());
		mv.addObject("contasreceber", contasReceber.findAllByOrderByClienteAsc());
		mv.addObject(contaReceber);
		
		return mv;
	}
	
	@PostMapping
	public ModelAndView cadastrar(@Valid ContaReceber contaReceber, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(contaReceber);
		}
		
		try {
			contaReceberService.cadastrar(contaReceber);
		} catch (GlobalException e) {
			result.rejectValue("cliente", e.getMessage(), e.getMessage());
			return novo(contaReceber);		
		}
		
		attributes.addFlashAttribute("successMessage", INFORMACOES_SALVAS_SUCESSO);
		
		return new ModelAndView(VIEW_CONTARECEBER_REDIRECT);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") ContaReceber contaReceber) {	
		ModelAndView mv = novo(contaReceber);
		mv.addObject(contaReceber);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") ContaReceber contaReceber) {
		try {
			contaReceberService.excluir(contaReceber);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();	
	}
	
	@GetMapping
	public ModelAndView pesquisar(ContaReceberFilter contaReceberFilter, BindingResult result, @PageableDefault(size = 50) Pageable pageable) {
		ModelAndView mv = new ModelAndView(VIEW_PESQUISAR_CONTARECEBER);
		mv.addObject("clientes", clientes.findAllByOrderByNomeAsc());
		mv.addObject("status", Status.values());
		mv.addObject("contasreceber", contasReceber.findAllByOrderByClienteAsc());
		
		Page<ContaReceber> pagina = contasReceber.filtrar(contaReceberFilter, pageable);
		mv.addObject("pagina", pagina);
		
		return mv;
	}
	
	@GetMapping("/lancamentos/{codigoContaReceber}")
	public @ResponseBody ResponseEntity<?> pesquisaContaReceber(@PathVariable Long codigoContaReceber) {
		contaReceberDTO.setCodigoContaReceber(codigoContaReceber);
		
		return ResponseEntity.ok().build();
	}
}