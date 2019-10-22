package br.com.jdrmservices.controller;

import static br.com.jdrmservices.util.Constants.INFORMACOES_SALVAS_SUCESSO;
import static br.com.jdrmservices.util.Constants.VIEW_PESQUISAR_CATEGORIA;
import static br.com.jdrmservices.util.Constants.VIEW_CATEGORIA_NOVO;
import static br.com.jdrmservices.util.Constants.VIEW_CATEGORIA_REDIRECT;

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
import br.com.jdrmservices.model.Categoria;
import br.com.jdrmservices.repository.Categorias;
import br.com.jdrmservices.repository.filter.CategoriaFilter;
import br.com.jdrmservices.service.CategoriaService;

@Controller
@RequestMapping("/categorias")
public class CategoriasController {

	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private Categorias categorias;
	
	@GetMapping("/novo")
	public ModelAndView novo(Categoria categoria) {
		ModelAndView mv = new ModelAndView(VIEW_CATEGORIA_NOVO);
		mv.addObject("categorias", categorias.findAllByOrderByNomeAsc());
		mv.addObject(categoria);
		
		return mv;
	}
	
	@PostMapping
	public ModelAndView cadastrar(@Valid Categoria categoria, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(categoria);
		}
		
		try {
			categoriaService.cadastrar(categoria);
		} catch (GlobalException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(categoria);		
		}
		
		attributes.addFlashAttribute("successMessage", INFORMACOES_SALVAS_SUCESSO);
		
		return new ModelAndView(VIEW_CATEGORIA_REDIRECT);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Categoria categoria) {	
		ModelAndView mv = novo(categoria);
		mv.addObject(categoria);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Categoria categoria) {
		try {
			categoriaService.excluir(categoria);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();	
	}
	
	@GetMapping
	public ModelAndView pesquisar(CategoriaFilter categoriaFilter, BindingResult result, @PageableDefault(size = 50) Pageable pageable) {
		ModelAndView mv = new ModelAndView(VIEW_PESQUISAR_CATEGORIA);
		mv.addObject("categorias", categorias.findAllByOrderByNomeAsc());
		
		Page<Categoria> pagina = categorias.filtrar(categoriaFilter, pageable);
		mv.addObject("pagina", pagina);
		
		return mv;
	}
}
