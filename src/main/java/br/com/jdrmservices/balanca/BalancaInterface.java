package br.com.jdrmservices.balanca;

import java.math.BigDecimal;
import java.util.Optional;

public interface BalancaInterface {
	public boolean conectar();
	public boolean desconectar();
	public void configurar();
	public Optional<BigDecimal> getPesoBalanca();
}