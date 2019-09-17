package br.com.jdrmservices.controller;

import static br.com.jdrmservices.util.Constants.VIEW_DASHBOARD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.jdrmservices.repository.Clientes;
import br.com.jdrmservices.repository.Fornecedores;
import br.com.jdrmservices.repository.Funcionarios;
import br.com.jdrmservices.repository.Produtos;
import br.com.jdrmservices.repository.Usuarios;
import br.com.jdrmservices.repository.Vendas;
import br.com.jdrmservices.security.UsuarioSistema;

@Controller
@RequestMapping("/")
public class DashboardController {
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private Funcionarios funcionarios;
	
	@Autowired
	private Clientes clientes;
	
	@Autowired
	private Fornecedores fornecedores;
	
	@Autowired
	private Produtos produtos;
	
	@Autowired
	private Vendas vendas;
	/*
	@Autowired
	private ContasPagar contasPagar;
	
	@Autowired
	private ContasReceber contasReceber;
	*/
	@GetMapping
	public ModelAndView index(@AuthenticationPrincipal UsuarioSistema usuarioSistema) {	
		return getDashBoard();
	}
	
	@GetMapping("admin")
	public ModelAndView dashboard(@AuthenticationPrincipal UsuarioSistema usuarioSistema) {	
		return getDashBoard();
	}
	
	public ModelAndView getDashBoard() {
		ModelAndView mv = new ModelAndView(VIEW_DASHBOARD);
		
		mv.addObject("usuarios", usuarios.count());
		mv.addObject("funcionarios", funcionarios.count());
		mv.addObject("clientes", clientes.count());
		
		mv.addObject("fornecedores", fornecedores.count());
		mv.addObject("produtos", produtos.count());
		mv.addObject("vendas", vendas.count());
		
		mv.addObject("valorVendasDia", vendas.valorTotalDia());
		mv.addObject("valorVendasMes", vendas.valorTotalMes());
		mv.addObject("valorVendasAno", vendas.valorTotalAno());
		
		return mv;
	}
}