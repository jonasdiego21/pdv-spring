package br.com.jdrmservices.controller.threads;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.model.Venda;
import br.com.jdrmservices.service.VendaService;

public class ImprimeCabecalhoCupomRunnable implements Runnable {
	
	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private Venda venda;

	public ImprimeCabecalhoCupomRunnable(VendaService vendaService, Venda venda) {
		this.vendaService = vendaService;
		this.venda = venda;
	}

	@Override
	public void run() {
		try {
			vendaService.imprimirCabecalhoCupom(venda);
		} catch (GlobalException e) {
			System.out.println(e);
		}
	}

}
