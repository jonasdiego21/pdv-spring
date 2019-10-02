package br.com.jdrmservices.controller.threads;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.model.Produto;
import br.com.jdrmservices.model.Venda;
import br.com.jdrmservices.service.VendaService;

public class ImprimeItemCupomRunnable implements Runnable {
	
	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private Venda venda;
	
	@Autowired
	private Produto produto;
	
	private String uuid;
	
	private BigDecimal quantidade;

	public ImprimeItemCupomRunnable(VendaService vendaService, Venda venda, 
			String uuid, Produto produto, BigDecimal quantidade) {
		
		this.vendaService = vendaService;
		this.venda = venda;
		this.uuid = uuid;
		this.produto = produto;
		this.quantidade = quantidade;
	}

	@Override
	public void run() {
		try {
			vendaService.imprimirItemCupom(venda, uuid, produto, quantidade);
		} catch (GlobalException e) {
			System.out.println(e);
		}
	}
}
