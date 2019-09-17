package br.com.jdrmservices.session;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import br.com.jdrmservices.model.Produto;
import br.com.jdrmservices.session.TabelaItensVenda;

public class TabelaItensVendaTest {

	private TabelasItensSession tabelasItensSession;
	private TabelaItensVenda tabelaItensVenda;
	private String uuid;
	
	@Before
	public void setup() {
		this.uuid = UUID.randomUUID().toString();
		this.tabelasItensSession = new TabelasItensSession();
		this.tabelaItensVenda = new TabelaItensVenda(uuid);
	}
	
	@Test
	public void testCalcularValorTotalSemItens() throws Exception {
		assertEquals(BigDecimal.ZERO, tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void testCalcularValorTotalComUmItem(String uuid) throws Exception {
		Produto produto = new Produto();
		
		BigDecimal valorUnitario = new BigDecimal("18.80");
		
		produto.setPrecoVenda(valorUnitario);
		
		tabelasItensSession.adicionarItem(uuid, produto, BigDecimal.ONE);
		
		assertEquals(valorUnitario, tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void testCalcularValorTotalComVariosItem(String uuid) throws Exception {
		Produto produto1 = new Produto();
		Produto produto2 = new Produto();
		Produto produto3 = new Produto();
		
		BigDecimal valorUnitario1 = new BigDecimal("1.00");
		BigDecimal valorUnitario2 = new BigDecimal("1.00");
		BigDecimal valorUnitario3 = new BigDecimal("1.00");
		
		produto1.setPrecoVenda(valorUnitario1);
		produto2.setPrecoVenda(valorUnitario2);
		produto3.setPrecoVenda(valorUnitario3);
		
		tabelasItensSession.adicionarItem(uuid, produto1, BigDecimal.ONE);
		tabelasItensSession.adicionarItem(uuid, produto2, BigDecimal.ONE);
		tabelasItensSession.adicionarItem(uuid, produto3, new BigDecimal("10"));
		
		assertEquals(new BigDecimal("12.00"), tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void testDeveManterTamanhoListaMesmosItens(String uuid) throws Exception {
		Produto produto1 = new Produto();
		
		produto1.setCodigo(1L);
		produto1.setPrecoVenda(new BigDecimal("15.00"));
		
		tabelasItensSession.adicionarItem(uuid, produto1, BigDecimal.ONE);
		tabelasItensSession.adicionarItem(uuid, produto1, BigDecimal.ONE);
		
		assertEquals(1, tabelaItensVenda.getTotal());
	}
	
	@Test
	public void testDeveAlterarQuantidadeDoItem(String uuid) throws Exception {
		Produto produto1 = new Produto();
		
		produto1.setCodigo(1L);
		produto1.setPrecoVenda(new BigDecimal("10.00"));
		
		tabelasItensSession.adicionarItem(uuid, produto1, BigDecimal.ONE);
		tabelasItensSession.alterarQuantidadeItens(uuid, produto1, new BigDecimal("3"));
		
		assertEquals(1, tabelaItensVenda.getTotal());
		assertEquals(new BigDecimal("30.00"), tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void testDeveExcluirItem(String uuid) throws Exception {
		Produto produto1 = new Produto();
		produto1.setCodigo(1L);
		produto1.setPrecoVenda(new BigDecimal("5.00"));

		Produto produto2 = new Produto();
		produto2.setCodigo(2L);
		produto2.setPrecoVenda(new BigDecimal("5.00"));

		Produto produto3 = new Produto();
		produto3.setCodigo(3L);
		produto3.setPrecoVenda(new BigDecimal("5.00"));		
		
		tabelasItensSession.adicionarItem(uuid, produto1, new BigDecimal("1"));
		tabelasItensSession.adicionarItem(uuid, produto2, new BigDecimal("1"));
		tabelasItensSession.adicionarItem(uuid, produto3, new BigDecimal("1"));
		
		tabelasItensSession.excluirItem(uuid, produto2);
		
		assertEquals(2, tabelaItensVenda.getTotal());
		assertEquals(new BigDecimal("10.00"), tabelaItensVenda.getValorTotal());
	}
}
