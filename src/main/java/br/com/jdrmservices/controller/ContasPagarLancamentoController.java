package br.com.jdrmservices.controller;

import static br.com.jdrmservices.util.Constants.INFORMACOES_SALVAS_SUCESSO;
import static br.com.jdrmservices.util.Constants.VIEW_PESQUISAR_CONTAPAGAR_LANCAMENTO;

import java.util.Optional;

import static br.com.jdrmservices.util.Constants.VIEW_CONTAPAGAR_LANCAMENTO_NOVO;
import static br.com.jdrmservices.util.Constants.VIEW_CONTAPAGAR_LANCAMENTO_REDIRECT;

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
import br.com.jdrmservices.model.ContaPagarLancamento;
import br.com.jdrmservices.repository.ContasPagar;
import br.com.jdrmservices.repository.ContasPagarLancamento;
import br.com.jdrmservices.repository.filter.ContaPagarLancamentoFilter;
import br.com.jdrmservices.service.ContaPagarLancamentoService;

@Controller
@RequestMapping("/contaspagarlancamento")
public class ContasPagarLancamentoController {

	@Autowired
	private ContaPagarLancamentoService contaPagarLancamentoService;
	
	@Autowired
	private ContasPagarLancamento contasPagarLancamento;
	
	@Autowired
	private ContaPagarDTO contaPagarDTO;
	
	@Autowired
	private ContasPagar contasPagar;
	
	@GetMapping("/novo")
	public ModelAndView novo(ContaPagarLancamento contapagarlancamento) {
		ModelAndView mv = new ModelAndView(VIEW_CONTAPAGAR_LANCAMENTO_NOVO);
		mv.addObject("contasPagarLancamento", contasPagarLancamento.findAllByOrderByDataPagamentoAsc());
		mv.addObject(contapagarlancamento);
		
		Optional<ContaPagar> contaPagar = contasPagar.findById(contaPagarDTO.getCodigoContaPagar());	
		contapagarlancamento.setContaPagar(contaPagar.orElse(null));
		
		return mv;
	}
	
	@PostMapping
	public ModelAndView cadastrar(@Valid ContaPagarLancamento contapagarlancamento, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(contapagarlancamento);
		}
		
		try {
			contaPagarLancamentoService.cadastrar(contapagarlancamento);
		} catch (GlobalException e) {
			result.rejectValue("dataPagamento", e.getMessage(), e.getMessage());
			return novo(contapagarlancamento);		
		}
		
		attributes.addFlashAttribute("successMessage", INFORMACOES_SALVAS_SUCESSO);
		
		return new ModelAndView(VIEW_CONTAPAGAR_LANCAMENTO_REDIRECT);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") ContaPagarLancamento contapagarlancamento) {	
		ModelAndView mv = novo(contapagarlancamento);
		mv.addObject(contapagarlancamento);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") ContaPagarLancamento contapagarlancamento) {
		try {
			contaPagarLancamentoService.excluir(contapagarlancamento);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();	
	}
	
	@GetMapping
	public ModelAndView pesquisar(ContaPagarLancamentoFilter contapagarlancamentoFilter, BindingResult result, @PageableDefault(size = 10) Pageable pageable) {
		ModelAndView mv = new ModelAndView(VIEW_PESQUISAR_CONTAPAGAR_LANCAMENTO);
		mv.addObject("contasPagarLancamento", contasPagarLancamento.findAllByOrderByDataPagamentoAsc());
		
		Page<ContaPagarLancamento> pagina = contasPagarLancamento.filtrar(contapagarlancamentoFilter, pageable);
		mv.addObject("pagina", pagina);
		
		return mv;
	}
}