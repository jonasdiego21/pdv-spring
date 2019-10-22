package br.com.jdrmservices.controller;

import static br.com.jdrmservices.util.Constants.INFORMACOES_SALVAS_SUCESSO;
import static br.com.jdrmservices.util.Constants.VIEW_PESQUISAR_USUARIO;
import static br.com.jdrmservices.util.Constants.VIEW_USUARIO_NOVO;
import static br.com.jdrmservices.util.Constants.VIEW_USUARIO_REDIRECT;

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
import br.com.jdrmservices.model.Usuario;
import br.com.jdrmservices.repository.Funcionarios;
import br.com.jdrmservices.repository.Grupos;
import br.com.jdrmservices.repository.Usuarios;
import br.com.jdrmservices.repository.filter.UsuarioFilter;
import br.com.jdrmservices.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private Grupos grupos;
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private Funcionarios funcionarios;
	
	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView(VIEW_USUARIO_NOVO);
		mv.addObject("funcionarios", funcionarios.findAll());
		mv.addObject("usuarios", usuarios.findAll());
		mv.addObject("grupos", grupos.findAll());
		mv.addObject(usuario);
		
		return mv;
	}
	
	@PostMapping
	public ModelAndView cadastrar(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(usuario);
		}
		
		try {
			usuarioService.cadastrar(usuario);
		} catch (GlobalException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);		
		}
		
		attributes.addFlashAttribute("successMessage", INFORMACOES_SALVAS_SUCESSO);
		
		return new ModelAndView(VIEW_USUARIO_REDIRECT);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Usuario usuario) {	
		ModelAndView mv = novo(usuario);
		mv.addObject(usuario);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Usuario usuario) {
		try {
			usuarioService.excluir(usuario);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();	
	}
	
	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter, BindingResult result, @PageableDefault(size = 50) Pageable pageable) {
		ModelAndView mv = new ModelAndView(VIEW_PESQUISAR_USUARIO);
		mv.addObject("usuarios", usuarios.findAllByOrderByNomeAsc());
		
		Page<Usuario> pagina = usuarios.filtrar(usuarioFilter, pageable);
		mv.addObject("pagina", pagina);
		
		return mv;
	}
}