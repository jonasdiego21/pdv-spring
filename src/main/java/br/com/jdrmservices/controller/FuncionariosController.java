package br.com.jdrmservices.controller;

import static br.com.jdrmservices.util.Constants.INFORMACOES_SALVAS_SUCESSO;
import static br.com.jdrmservices.util.Constants.VIEW_PESQUISAR_FUNCIONARIO;
import static br.com.jdrmservices.util.Constants.VIEW_FUNCIONARIO_NOVO;
import static br.com.jdrmservices.util.Constants.VIEW_FUNCIONARIO_REDIRECT;

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
import br.com.jdrmservices.model.Funcionario;
import br.com.jdrmservices.repository.Funcionarios;
import br.com.jdrmservices.repository.filter.FuncionarioFilter;
import br.com.jdrmservices.service.FuncionarioService;

@Controller
@RequestMapping("/funcionarios")
public class FuncionariosController {

	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private Funcionarios funcionarios;
	
	@GetMapping("/novo")
	public ModelAndView novo(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView(VIEW_FUNCIONARIO_NOVO);
		mv.addObject("funcionarios", funcionarios.findAllByOrderByNomeAsc());
		mv.addObject(funcionario);
		
		return mv;
	}
	
	@PostMapping
	public ModelAndView cadastrar(@Valid Funcionario funcionario, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(funcionario);
		}
		
		try {
			funcionarioService.cadastrar(funcionario);
		} catch (GlobalException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(funcionario);		
		}
		
		attributes.addFlashAttribute("successMessage", INFORMACOES_SALVAS_SUCESSO);
		
		return new ModelAndView(VIEW_FUNCIONARIO_REDIRECT);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Funcionario funcionario) {	
		ModelAndView mv = novo(funcionario);
		mv.addObject(funcionario);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Funcionario funcionario) {
		try {
			funcionarioService.excluir(funcionario);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();	
	}
	
	@GetMapping
	public ModelAndView pesquisar(FuncionarioFilter funcionarioFilter, BindingResult result, @PageableDefault(size = 50) Pageable pageable) {
		ModelAndView mv = new ModelAndView(VIEW_PESQUISAR_FUNCIONARIO);
		mv.addObject("funcionarios", funcionarios.findAllByOrderByNomeAsc());
		
		Page<Funcionario> pagina = funcionarios.filtrar(funcionarioFilter, pageable);
		mv.addObject("pagina", pagina);
		
		return mv;
	}
}
