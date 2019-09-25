package br.com.jdrmservices.controller;

import static br.com.jdrmservices.util.Constants.INFORMACOES_SALVAS_SUCESSO;
import static br.com.jdrmservices.util.Constants.VIEW_PESQUISAR_PRODUTO;
import static br.com.jdrmservices.util.Constants.VIEW_PRODUTO_NOVO;
import static br.com.jdrmservices.util.Constants.VIEW_PRODUTO_REDIRECT;
import static br.com.jdrmservices.util.Constants.VIEW_PRODUTO_ENTRADA;
import static br.com.jdrmservices.util.Constants.VIEW_PRODUTO_ENTRADA_REDIRECT;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.jdrmservices.dto.ProdutoDTO;
import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.model.Produto;
import br.com.jdrmservices.model.enumeration.UnidadeMedida;
import br.com.jdrmservices.repository.Categorias;
import br.com.jdrmservices.repository.Fornecedores;
import br.com.jdrmservices.repository.Produtos;
import br.com.jdrmservices.repository.filter.ProdutoFilter;
import br.com.jdrmservices.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private Produtos produtos;
	
	@Autowired
	private Categorias categorias;
	
	@Autowired
	private Fornecedores fornecedores;
	
	@GetMapping("/novo")
	public ModelAndView novo(Produto produto) {
		ModelAndView mv = new ModelAndView(VIEW_PRODUTO_NOVO);
		mv.addObject("produtos", produtos.findAllByOrderByNomeAsc());
		mv.addObject("categorias", categorias.findAllByOrderByNomeAsc());
		mv.addObject("fornecedores", fornecedores.findAllByOrderByNomeAsc());
		mv.addObject("medidas", UnidadeMedida.values());
		mv.addObject(produto);
		
		return mv;
	}
	
	@GetMapping("/entrada")
	private ModelAndView produtoEntradaNovo(Produto produto) {
		ModelAndView mv = new ModelAndView(VIEW_PRODUTO_ENTRADA);
		
		mv.addObject("produtos", produtos.findAllByOrderByNomeAsc());
		mv.addObject("categorias", categorias.findAllByOrderByNomeAsc());
		mv.addObject("fornecedores", fornecedores.findAllByOrderByNomeAsc());
		mv.addObject("medidas", UnidadeMedida.values());
		mv.addObject(produto);
		
		return mv;
	}
	
	@PostMapping("/entrada")
	private ModelAndView produtoEntradaSalvar(Produto produto, RedirectAttributes attributes) {
		
		try {
			produtoService.adicionarEntrada(produto);
		} catch (GlobalException e) {
			return produtoEntradaNovo(produto);		
		}
		
		attributes.addFlashAttribute("successMessage", INFORMACOES_SALVAS_SUCESSO);
		
		return new ModelAndView(VIEW_PRODUTO_ENTRADA_REDIRECT);
	}
	
	@PostMapping
	public ModelAndView cadastrar(@Valid Produto produto, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(produto);
		}
		
		try {
			produtoService.cadastrar(produto);
		} catch (GlobalException e) {
			result.rejectValue("codigoBarras", e.getMessage(), e.getMessage());
			return novo(produto);		
		}
		
		attributes.addFlashAttribute("successMessage", INFORMACOES_SALVAS_SUCESSO);
		
		return new ModelAndView(VIEW_PRODUTO_REDIRECT);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Produto produto) {	
		ModelAndView mv = novo(produto);
		mv.addObject(produto);
		
		return mv;
	}
	
	@GetMapping("/entrada/{codigo}")
	public ModelAndView editarEntrada(@PathVariable("codigo") Produto produto) {	
		ModelAndView mv = produtoEntradaNovo(produto);
		mv.addObject(produto);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Produto produto) {
		try {
			produtoService.excluir(produto);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();	
	}
	
	@GetMapping
	public ModelAndView pesquisar(ProdutoFilter produtoFilter, BindingResult result, @PageableDefault(size = 10) Pageable pageable) {
		ModelAndView mv = new ModelAndView(VIEW_PESQUISAR_PRODUTO);
		mv.addObject("produtos", produtos.findAllByOrderByNomeAsc());
		
		Page<Produto> pagina = produtos.filtrar(produtoFilter, pageable);
		mv.addObject("pagina", pagina);
		
		return mv;
	}
	
	@GetMapping("/filtro")
	public List<ProdutoDTO> pesquisarProduto(String codigoOuCodigoBarras) {
		return produtos.porCodigoOuCodigoBarras(codigoOuCodigoBarras);	
	}
}
