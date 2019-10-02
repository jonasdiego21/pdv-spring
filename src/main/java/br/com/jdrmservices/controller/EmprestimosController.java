package br.com.jdrmservices.controller;

import static br.com.jdrmservices.util.Constants.INFORMACOES_SALVAS_SUCESSO;
import static br.com.jdrmservices.util.Constants.VIEW_PESQUISAR_EMPRESTIMO;
import static br.com.jdrmservices.util.Constants.VIEW_EMPRESTIMO_NOVO;
import static br.com.jdrmservices.util.Constants.VIEW_EMPRESTIMO_REDIRECT;

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
import br.com.jdrmservices.model.Emprestimo;
import br.com.jdrmservices.repository.Emprestimos;
import br.com.jdrmservices.repository.Estados;
import br.com.jdrmservices.repository.filter.EmprestimoFilter;
import br.com.jdrmservices.service.EmprestimoService;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimosController {

	@Autowired
	private EmprestimoService emprestimoService;
	
	@Autowired
	private Emprestimos emprestimos;
	
	@Autowired
	private Estados estados;
	
	@GetMapping("/novo")
	public ModelAndView novo(Emprestimo emprestimo) {
		ModelAndView mv = new ModelAndView(VIEW_EMPRESTIMO_NOVO);
		mv.addObject("emprestimos", emprestimos.findAllByOrderByNomeAsc());
		mv.addObject("estados", estados.findAllByOrderByNomeAsc());
		mv.addObject(emprestimo);
		
		return mv;
	}
	
	@PostMapping
	public ModelAndView cadastrar(@Valid Emprestimo emprestimo, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(emprestimo);
		}
		
		try {
			emprestimoService.cadastrar(emprestimo);
		} catch (GlobalException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(emprestimo);		
		}
		
		attributes.addFlashAttribute("successMessage", INFORMACOES_SALVAS_SUCESSO);
		
		return new ModelAndView(VIEW_EMPRESTIMO_REDIRECT);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Emprestimo emprestimo) {	
		ModelAndView mv = novo(emprestimo);
		mv.addObject(emprestimo);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Emprestimo emprestimo) {
		try {
			emprestimoService.excluir(emprestimo);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();	
	}
	
	@GetMapping
	public ModelAndView pesquisar(EmprestimoFilter emprestimoFilter, BindingResult result, @PageableDefault(size = 10) Pageable pageable) {
		ModelAndView mv = new ModelAndView(VIEW_PESQUISAR_EMPRESTIMO);
		mv.addObject("emprestimos", emprestimos.findAllByOrderByNomeAsc());
		
		Page<Emprestimo> pagina = emprestimos.filtrar(emprestimoFilter, pageable);
		mv.addObject("pagina", pagina);
		
		return mv;
	}
}