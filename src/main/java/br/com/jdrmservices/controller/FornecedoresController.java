package br.com.jdrmservices.controller;

import static br.com.jdrmservices.util.Constants.INFORMACOES_SALVAS_SUCESSO;
import static br.com.jdrmservices.util.Constants.VIEW_PESQUISAR_FORNECEDOR;
import static br.com.jdrmservices.util.Constants.VIEW_FORNECEDOR_NOVO;
import static br.com.jdrmservices.util.Constants.VIEW_FORNECEDOR_REDIRECT;

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

import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.model.Fornecedor;
import br.com.jdrmservices.repository.Estados;
import br.com.jdrmservices.repository.Fornecedores;
import br.com.jdrmservices.repository.filter.FornecedorFilter;
import br.com.jdrmservices.service.FornecedorService;

@Controller
@RequestMapping("/fornecedores")
public class FornecedoresController {

	@Autowired
	private FornecedorService fornecedorService;
	
	@Autowired
	private Fornecedores fornecedores;
	
	@Autowired
	private Estados estados;
	
	@GetMapping("/novo")
	public ModelAndView novo(Fornecedor fornecedor) {
		ModelAndView mv = new ModelAndView(VIEW_FORNECEDOR_NOVO);
		mv.addObject("fornecedores", fornecedores.findAllByOrderByNomeAsc());
		mv.addObject("estados", estados.findAllByOrderByNomeAsc());
		mv.addObject(fornecedor);
		
		return mv;
	}
	
	@PostMapping
	public ModelAndView cadastrar(@Valid Fornecedor fornecedor, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(fornecedor);
		}
		
		try {
			fornecedorService.cadastrar(fornecedor);
		} catch (GlobalException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(fornecedor);		
		}
		
		attributes.addFlashAttribute("successMessage", INFORMACOES_SALVAS_SUCESSO);
		
		return new ModelAndView(VIEW_FORNECEDOR_REDIRECT);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Fornecedor fornecedor) {	
		ModelAndView mv = novo(fornecedor);
		mv.addObject(fornecedor);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Fornecedor fornecedor) {
		try {
			fornecedorService.excluir(fornecedor);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();	
	}
	
	@GetMapping
	public ModelAndView pesquisar(FornecedorFilter fornecedorFilter, BindingResult result, @PageableDefault(size = 50) Pageable pageable) {
		ModelAndView mv = new ModelAndView(VIEW_PESQUISAR_FORNECEDOR);
		mv.addObject("fornecedores", fornecedores.findAllByOrderByNomeAsc());
		
		Page<Fornecedor> pagina = fornecedores.filtrar(fornecedorFilter, pageable);
		mv.addObject("pagina", pagina);
		
		return mv;
	}
}
