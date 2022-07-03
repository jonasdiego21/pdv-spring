package br.com.jdrmservices.balanca;

public interface BalancaInterface {
	public boolean conectar();
	public boolean desconectar();
	public String getPesoBalanca();
}