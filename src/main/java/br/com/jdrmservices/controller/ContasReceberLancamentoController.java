package br.com.jdrmservices.controller;

import static br.com.jdrmservices.util.Constants.INFORMACOES_SALVAS_SUCESSO;
import static br.com.jdrmservices.util.Constants.VIEW_PESQUISAR_CONTARECEBER_LANCAMENTO;

import java.util.Optional;

import static br.com.jdrmservices.util.Constants.VIEW_CONTARECEBER_LANCAMENTO_NOVO;
import static br.com.jdrmservices.util.Constants.VIEW_CONTARECEBER_LANCAMENTO_REDIRECT;

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
import br.com.jdrmservices.model.ContaReceberLancamento;
import br.com.jdrmservices.repository.ContasReceber;
import br.com.jdrmservices.repository.ContasReceberLancamento;
import br.com.jdrmservices.repository.filter.ContaReceberLancamentoFilter;
import br.com.jdrmservices.service.ContaReceberLancamentoService;

@Controller
@RequestMapping("/contasreceberlancamento")
public class ContasReceberLancamentoController {

	@Autowired
	private ContaReceberLancamentoService contaReceberLancamentoService;
	
	@Autowired
	private ContasReceberLancamento contasReceberLancamento;
	
	@Autowired
	private ContaReceberDTO contaReceberDTO;
	
	@Autowired
	private ContasReceber contasReceber;
	
	@GetMapping("/novo")
	public ModelAndView novo(ContaReceberLancamento contareceberlancamento) {
		ModelAndView mv = new ModelAndView(VIEW_CONTARECEBER_LANCAMENTO_NOVO);
		mv.addObject("contasReceberLancamento", contasReceberLancamento.findAllByOrderByDataRecebimentoAsc());
		mv.addObject(contareceberlancamento);
		
		Optional<ContaReceber> contaReceber = contasReceber.findById(contaReceberDTO.getCodigoContaReceber());	
		contareceberlancamento.setContaReceber(contaReceber.orElse(null));
		
		return mv;
	}
	
	@PostMapping
	public ModelAndView cadastrar(@Valid ContaReceberLancamento contareceberlancamento, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(contareceberlancamento);
		}
		
		try {
			contaReceberLancamentoService.cadastrar(contareceberlancamento);
		} catch (GlobalException e) {
			result.rejectValue("dataPagamento", e.getMessage(), e.getMessage());
			return novo(contareceberlancamento);		
		}
		
		attributes.addFlashAttribute("successMessage", INFORMACOES_SALVAS_SUCESSO);
		
		return new ModelAndView(VIEW_CONTARECEBER_LANCAMENTO_REDIRECT);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") ContaReceberLancamento contareceberlancamento) {	
		ModelAndView mv = novo(contareceberlancamento);
		mv.addObject(contareceberlancamento);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") ContaReceberLancamento contareceberlancamento) {
		try {
			contaReceberLancamentoService.excluir(contareceberlancamento);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();	
	}
	
	@GetMapping
	public ModelAndView pesquisar(ContaReceberLancamentoFilter contareceberlancamentoFilter, BindingResult result, @PageableDefault(size = 50) Pageable pageable) {
		ModelAndView mv = new ModelAndView(VIEW_PESQUISAR_CONTARECEBER_LANCAMENTO);
		mv.addObject("contasReceberLancamento", contasReceberLancamento.findAllByOrderByDataRecebimentoAsc());
		
		Page<ContaReceberLancamento> pagina = contasReceberLancamento.filtrar(contareceberlancamentoFilter, pageable);
		mv.addObject("pagina", pagina);
		
		return mv;
	}
}