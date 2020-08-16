package br.com.jdrmservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jdrmservices.dto.ClientesAniversariantes;
import br.com.jdrmservices.dto.ProdutosBaixoEstoque;
import br.com.jdrmservices.repository.Clientes;
import br.com.jdrmservices.repository.Produtos;

@RestController
@RequestMapping("/notifications")
public class ApplicationController {
	
	@Autowired
	private Clientes clientes;
	
	@Autowired
	private Produtos produtos;

	@GetMapping("/produtosBaixoEstoque")
	public List<ProdutosBaixoEstoque> produtosBaixoEstoque() {
		List<ProdutosBaixoEstoque> produtosBaixoEstoque = produtos.findByProdutosQuantidadeMenorQueCinco();
		return produtosBaixoEstoque;
	}
	
	@GetMapping("/clientesAniversariantes")
	public List<ClientesAniversariantes> clientesAniversariantes() {
		List<ClientesAniversariantes> clientesAniversariantes = clientes.findByClientesAniversariantes();
		return clientesAniversariantes;
	}
}
