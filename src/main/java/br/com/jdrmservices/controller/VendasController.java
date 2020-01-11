package br.com.jdrmservices.controller;

import static br.com.jdrmservices.util.Constants.VIEW_FINALIZAR_VENDA;
import static br.com.jdrmservices.util.Constants.VIEW_ITENS_VENDA;
import static br.com.jdrmservices.util.Constants.VIEW_LIMITE_CREDITO_ATINGIDO;
import static br.com.jdrmservices.util.Constants.VIEW_PESQUISAR_VENDA;
import static br.com.jdrmservices.util.Constants.VIEW_VENDA_NOVO;
import static br.com.jdrmservices.util.Constants.VIEW_VENDA_REDIRECT;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.jdrmservices.dto.PdvDTO;
import br.com.jdrmservices.dto.TotalVendasAno;
import br.com.jdrmservices.dto.TotalVendasAnoCrediario;
import br.com.jdrmservices.dto.TotalVendasAnoGeral;
import br.com.jdrmservices.dto.TotalVendasDia;
import br.com.jdrmservices.dto.TotalVendasDiaCrediario;
import br.com.jdrmservices.dto.TotalVendasDiaGeral;
import br.com.jdrmservices.dto.TotalVendasMes;
import br.com.jdrmservices.dto.TotalVendasMesCrediario;
import br.com.jdrmservices.dto.TotalVendasMesGeral;
import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.impressora.GenericPrinter;
import br.com.jdrmservices.model.Cliente;
import br.com.jdrmservices.model.Produto;
import br.com.jdrmservices.model.Venda;
import br.com.jdrmservices.model.enumeration.StatusVenda;
import br.com.jdrmservices.repository.Clientes;
import br.com.jdrmservices.repository.Produtos;
import br.com.jdrmservices.repository.Vendas;
import br.com.jdrmservices.repository.filter.VendaFilter;
import br.com.jdrmservices.security.UsuarioSistema;
import br.com.jdrmservices.service.VendaService;
import br.com.jdrmservices.session.TabelasItensSession;

@RestController
@RequestMapping("/vendas")
public class VendasController {

	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private Vendas vendas;
	
	@Autowired
	private Produtos produtos;
	
	@Autowired
	private Clientes clientes;
	
	@Autowired
	private TabelasItensSession tabelasItensSession;

	private DecimalFormat decimalFormat;
	
	@Autowired
	private GenericPrinter genericPrinter;
	
	public VendasController() {
		decimalFormat = new DecimalFormat("#,##0.00");
	}
	
	@PostMapping("/item")
	public ModelAndView adicionarItem(String codigoOuCodigoBarras, BigDecimal quantidade, String uuid, Venda venda) {		
		ModelAndView mv = new ModelAndView(VIEW_ITENS_VENDA);	
		Produto produto = produtos.findByCodigoBarras(codigoOuCodigoBarras);
		
		tabelasItensSession.adicionarItem(uuid, produto, quantidade);

		vendaService.imprimirItemCupom(venda, uuid, produto, quantidade);
		
		mv.addObject("itens", tabelasItensSession.getItens(uuid));	
		mv.addObject("valorTotal", decimalFormat.format(tabelasItensSession.getValorTotal(uuid)));

		return mv;
	}
	
	@PutMapping("/item/{codigoProduto}")
	public ModelAndView alterarQuantidadeItem(@PathVariable Long codigoProduto, BigDecimal quantidade, String uuid) {
		ModelAndView mv = new ModelAndView(VIEW_ITENS_VENDA);
		
		Produto produto = produtos.findByCodigo(codigoProduto);
		tabelasItensSession.alterarQuantidadeItens(uuid, produto, quantidade);
		
		mv.addObject("itens", tabelasItensSession.getItens(uuid));
		mv.addObject("valorTotal", decimalFormat.format(tabelasItensSession.getValorTotal(uuid)));
		
		return mv;
	}
	
	@DeleteMapping("/item/{uuid}/{codigo}")
	public ModelAndView excluirItem(@PathVariable("codigo") Long codigo, @PathVariable("uuid") String uuid) {
		Produto produtoExcluir = produtos.findByCodigo(codigo);
		
		try {
			tabelasItensSession.excluirItem(uuid, produtoExcluir);
		} catch (Exception e) {
			System.out.println("O item já foi excluído!");
		}
		
		ModelAndView mv = new ModelAndView(VIEW_ITENS_VENDA);
		mv.addObject("itens", tabelasItensSession.getItens(uuid));
		mv.addObject("valorTotal", decimalFormat.format(tabelasItensSession.getValorTotal(uuid)));
		
		return mv;
	}
	
	@GetMapping("/novo")
	public ModelAndView novo(Venda venda, String uuid) {
		ModelAndView mv = new ModelAndView(VIEW_VENDA_NOVO);
		
		venda.setUuid(UUID.randomUUID().toString());
		
		mv.addObject("printer", genericPrinter.listarImpressoras());
		mv.addObject("status", StatusVenda.values());
		mv.addObject("itens", tabelasItensSession.getItens(uuid));
		mv.addObject("vendas", vendas.findAll());
		mv.addObject(venda);
		
		return mv;
	}

	@GetMapping("/iniciar")
	private ModelAndView iniciarVenda(Venda venda) {
		ModelAndView mv = new ModelAndView(VIEW_VENDA_NOVO);

		mv.addObject(venda);
		mv.addObject("status", StatusVenda.values());
		mv.addObject("itens", tabelasItensSession.getItens(venda.getUuid()));
		mv.addObject("vendas", vendas.findAll());
		
		vendaService.imprimirCabecalhoCupom(venda);
			
		return mv;
	}
	
	@PostMapping("/finalizar")
	public ModelAndView finalizarVenda(@AuthenticationPrincipal UsuarioSistema usuarioSistema, Venda venda, PdvDTO pdvDTO) {
		ModelAndView mv = new ModelAndView(VIEW_FINALIZAR_VENDA);

		pdvDTO.setValorVenda(tabelasItensSession.getValorTotal(venda.getUuid()));//redundante
		
		adicionarClienteAvulso(venda);

		mv.addObject("printer", genericPrinter.listarImpressoras());
		mv.addObject("status", StatusVenda.values());
		mv.addObject("itens", tabelasItensSession.getItens(venda.getUuid()));
		mv.addObject("vendas", vendas.findAll());
		mv.addObject(pdvDTO);//redundante
		mv.addObject(venda);

		return mv;
	}

	public void adicionarClienteAvulso(Venda venda) {
		if(venda.getCliente() == null) {
			Cliente cliente = new Cliente();
			cliente.setCodigo(1L);
			cliente.setNome("CLIENTE AVULSO");
			cliente.setLimiteCompra(new BigDecimal("1000000"));
			venda.setCliente(cliente);
		}
	}
	
	@PostMapping
	public ModelAndView cadastrar(Venda venda, String uuid, @AuthenticationPrincipal UsuarioSistema usuarioSistema, BindingResult result, Model model, RedirectAttributes attributes) {
		if(venda.isNovo()) {
			//venda.setDataCriacao(LocalDate.now());
			venda.setUsuario(usuarioSistema.getUsuario());
			venda.adicionarItens(tabelasItensSession.getItens(venda.getUuid()));
		}

		try {			
			vendaService.cadastrar(venda);
		} catch (GlobalException e) {
			//attributes.addFlashAttribute("aviso", e.getLocalizedMessage());
			//ResponseEntity.badRequest().body(e.getLocalizedMessage());
			//System.out.println(e.getLocalizedMessage());
			return novo(venda, uuid);		
		}

		return new ModelAndView(VIEW_VENDA_REDIRECT);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Venda venda, String uuid) {	
		ModelAndView mv = novo(venda, uuid);
		mv.addObject(venda);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Venda venda) {
		try {
			vendaService.excluir(venda);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();	
	}
	
	@GetMapping
	public ModelAndView pesquisar(VendaFilter vendaFilter, BindingResult result, @PageableDefault(size = 100) Pageable pageable) {
		ModelAndView mv = new ModelAndView(VIEW_PESQUISAR_VENDA);
		mv.addObject("clientes", clientes.findAll());
		mv.addObject("status", StatusVenda.values());
		mv.addObject("vendas", vendas.findAll());
		
		Page<Venda> pagina = vendas.filtrar(vendaFilter, pageable);
		mv.addObject("pagina", pagina);
		
		return mv;
	}
	
	@GetMapping("/limiteExcedido")
	public ModelAndView limiteExcedido(Venda venda) {
		ModelAndView mv = new ModelAndView(VIEW_LIMITE_CREDITO_ATINGIDO);
		
		mv.addObject(venda);
		
		return mv;
	}
	
	@GetMapping("/cancelar/{codigo}")
	public ModelAndView cancelar(@PathVariable("codigo") Venda venda) {		
		vendaService.cancelar(venda);
		
		return new ModelAndView("redirect:/vendas");
	}
	
	@GetMapping("/emitir/{codigo}")
	public ModelAndView emitir(@PathVariable("codigo") Venda venda) {		
		vendaService.emitir(venda);
		
		return new ModelAndView("redirect:/vendas");
	}
	
	@GetMapping("/orcamento/{codigo}")
	public ModelAndView cancelarVendaEmitida(@PathVariable("codigo") Venda venda) {	
		vendaService.orcamento(venda);
		
		return new ModelAndView("redirect:/vendas");
	}
	
	@GetMapping("/totalVendasDia")
	public @ResponseBody List<TotalVendasDia> totalVendasDia() {
		return vendas.totalVendasDia();
	}
	
	@GetMapping("/totalVendasMes")
	public @ResponseBody List<TotalVendasMes> totalVendasMes() {
		return vendas.totalVendasMes();
	}
	
	@GetMapping("/totalVendasAno")
	public @ResponseBody List<TotalVendasAno> totalVendasAno() {
		return vendas.totalVendasAno();
	}
	
	@GetMapping("/totalVendasDiaCrediario")
	public @ResponseBody List<TotalVendasDiaCrediario> totalVendasDiaCrediario() {
		return vendas.totalVendasDiaCrediario();
	}
	
	@GetMapping("/totalVendasMesCrediario")
	public @ResponseBody List<TotalVendasMesCrediario> totalVendasMesCrediario() {
		return vendas.totalVendasMesCrediario();
	}
	
	@GetMapping("/totalVendasAnoCrediario")
	public @ResponseBody List<TotalVendasAnoCrediario> totalVendasAnoCrediario() {
		return vendas.totalVendasAnoCrediario();
	}
	
	@GetMapping("/totalVendasDiaGeral")
	public @ResponseBody List<TotalVendasDiaGeral> totalVendasDiaGeral() {
		return vendas.totalVendasDiaGeral();
	}
	
	@GetMapping("/totalVendasMesGeral")
	public @ResponseBody List<TotalVendasMesGeral> totalVendasMesGeral() {
		return vendas.totalVendasMesGeral();
	}
	
	@GetMapping("/totalVendasAnoGeral")
	public @ResponseBody List<TotalVendasAnoGeral> totalVendasAnoGeral() {
		return vendas.totalVendasAnoGeral();
	}
}
