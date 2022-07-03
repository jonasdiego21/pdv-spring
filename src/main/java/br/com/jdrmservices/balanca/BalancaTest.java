package br.com.jdrmservices.balanca;

public class BalancaTest {

	public static void main(String[] args) {
		Balanca balanca = new Balanca();
		balanca.conectar();
		
		synchronized (balanca) {
			while (true) {
				try {
					Thread.sleep(1000);
					
					System.out.println("=> " + balanca.getPesoBalanca() + " kg");				
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
