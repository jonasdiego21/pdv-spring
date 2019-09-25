package br.com.jdrmservices.impressora;

import java.math.BigDecimal;

import br.com.jdrmservices.model.Produto;
import br.com.jdrmservices.model.Venda;

public interface GenericPrinterInterface {
	public boolean imprimirCabacalho();
	public boolean imprimirItem(String uuid, Produto produto, BigDecimal quantidade);
	public boolean imprimirFechamento(Venda venda);
}
