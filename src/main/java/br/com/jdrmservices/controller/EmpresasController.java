package br.com.jdrmservices.controller;

import static br.com.jdrmservices.util.Constants.INFORMACOES_SALVAS_SUCESSO;
import static br.com.jdrmservices.util.Constants.VIEW_PESQUISAR_EMPRESA;
import static br.com.jdrmservices.util.Constants.VIEW_EMPRESA_NOVO;
import static br.com.jdrmservices.util.Constants.VIEW_EMPRESA_REDIRECT;

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
import br.com.jdrmservices.model.Empresa;
import br.com.jdrmservices.repository.Empresas;
import br.com.jdrmservices.repository.Estados;
import br.com.jdrmservices.repository.filter.EmpresaFilter;
import br.com.jdrmservices.service.EmpresaService;

@Controller
@RequestMapping("/empresas")
public class EmpresasController {

	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private Empresas empresas;
	
	@Autowired
	private Estados estados;
	
	@GetMapping("/novo")
	public ModelAndView novo(Empresa empresa) {
		ModelAndView mv = new ModelAndView(VIEW_EMPRESA_NOVO);
		mv.addObject("empresas", empresas.findAllByOrderByNomeAsc());
		mv.addObject("estados", estados.findAllByOrderByNomeAsc());
		mv.addObject(empresa);
		
		return mv;
	}
	
	@PostMapping
	public ModelAndView cadastrar(@Valid Empresa empresa, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(empresa);
		}
		
		try {
			empresaService.cadastrar(empresa);
		} catch (GlobalException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(empresa);		
		}
		
		attributes.addFlashAttribute("successMessage", INFORMACOES_SALVAS_SUCESSO);
		
		return new ModelAndView(VIEW_EMPRESA_REDIRECT);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Empresa empresa) {	
		ModelAndView mv = novo(empresa);
		mv.addObject(empresa);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Empresa empresa) {
		try {
			empresaService.excluir(empresa);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();	
	}
	
	@GetMapping
	public ModelAndView pesquisar(EmpresaFilter empresaFilter, BindingResult result, @PageableDefault(size = 10) Pageable pageable) {
		ModelAndView mv = new ModelAndView(VIEW_PESQUISAR_EMPRESA);
		mv.addObject("empresas", empresas.findAllByOrderByNomeAsc());
		
		Page<Empresa> pagina = empresas.filtrar(empresaFilter, pageable);
		mv.addObject("pagina", pagina);
		
		return mv;
	}
}