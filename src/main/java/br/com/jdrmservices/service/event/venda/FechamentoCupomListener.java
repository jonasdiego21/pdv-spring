package br.com.jdrmservices.service.event.venda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.jdrmservices.epson.EpsonPrint;
import br.com.jdrmservices.impressora.GenericPrinter;
import br.com.jdrmservices.repository.Vendas;

@Component
public class FechamentoCupomListener {

	@Autowired
	private EpsonPrint epsonPrint;
	
	@Autowired
	private GenericPrinter genericPrinter;
	
	@EventListener
	public void imprimeFechamentoCupom(FechamentoCupomEvent fechamentoCupomEvent) {	
		//epsonPrint.imprimirFechamento(fechamentoCupomEvent.getVenda());
		genericPrinter.imprimirFechamento(fechamentoCupomEvent.getVenda());
	}
}
