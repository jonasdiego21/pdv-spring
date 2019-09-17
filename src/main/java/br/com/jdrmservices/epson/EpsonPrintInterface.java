package br.com.jdrmservices.epson;

import java.math.BigDecimal;

import br.com.jdrmservices.model.Produto;
import br.com.jdrmservices.model.Venda;

public interface EpsonPrintInterface {
	public boolean conectar();
	public boolean desconectar();
	public boolean imprimirCabacalho();
	public boolean imprimirItem(String uuid, Produto produto, BigDecimal quantidade);
	public boolean imprimirFechamento(Venda venda);
}
