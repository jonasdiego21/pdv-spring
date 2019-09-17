package br.com.jdrmservices.service.event.venda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.jdrmservices.epson.EpsonPrint;

@Component
public class FechamentoCupomListener {

	@Autowired
	private EpsonPrint epsonPrint;
	
	@EventListener
	public void imprimeFechamentoCupom(FechamentoCupomEvent fechamentoCupomEvent) {	
		epsonPrint.imprimirFechamento(fechamentoCupomEvent.getVenda());
	}
}
