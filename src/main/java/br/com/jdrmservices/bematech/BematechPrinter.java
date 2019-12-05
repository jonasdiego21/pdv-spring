/*package br.com.jdrmservices.bematech;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.jdrmservices.model.Empresa;
import br.com.jdrmservices.model.Produto;
import br.com.jdrmservices.model.Venda;
import br.com.jdrmservices.model.enumeration.FormaPagamento;
import br.com.jdrmservices.repository.Empresas;
import br.com.jdrmservices.repository.Vendas;

@Component
@ComponentScan(basePackageClasses = { BematechPrinter.class })
@Service
public class BematechPrinter implements BematechInterface {

	@Autowired
	private Empresas empresas;
	
	@Autowired
	private Vendas vendas;

    private Date data;
    private DecimalFormat decimalFormat;
	private DecimalFormat quantidadeFormat;
	private DecimalFormat moedaFormat;
	private SimpleDateFormat simpleDateFormat;
	private int INT_COUNT = 1;
	
	public BematechPrinter() {
		data = new Date();

		decimalFormat = new DecimalFormat();	
		quantidadeFormat = new DecimalFormat();
		moedaFormat = new DecimalFormat();
		simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		decimalFormat.applyPattern("000");
		quantidadeFormat.applyPattern("#,##0.000");
		moedaFormat.applyPattern("#,##0.00");
	}
	
	@Override
	public boolean imprimirCabacalho() {
		try {
			Optional<Empresa> empresa = empresas.findById(1L);	
			
			//String cnpjLine = String.format("%s", empresa.get().getCnpj() + "\n");
			String empresaLine = String.format("                       ".substring(0, 23 - empresa.get().getNome().length() / 2) + "%s" + "                        ".substring(0, 24 - empresa.get().getNome().length() / 2), empresa.get().getNome());			
			String enderecoLine = String.format("%s, %s, %s", empresa.get().getRua(), empresa.get().getNumero(), empresa.get().getBairro() + "\n");
			String localizacaoLine = String.format("%s - %s | %s", empresa.get().getCidade().getNome(), empresa.get().getEstado().getSigla(), empresa.get().getTelefone() + "\n");
			
			imprimir(empresaLine + "\n");
			imprimir(enderecoLine);
			imprimir(localizacaoLine);
			imprimir("           DOCUMENTO SEM VALOR FISCAL           \n");			
			imprimir("================================================\n");
			imprimir("# COD     DESC     UN    QTD     VL UN.    VL TL\n");
			imprimir("------------------------------------------------\n");
		} catch (Exception ex) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean imprimirItem(String uuid, Produto produto, BigDecimal quantidade) {
		try {
			String itemLinhaUm = String.format(decimalFormat.format(INT_COUNT) + "   %s" + "                                                              ".substring(0, 42 - (produto.getCodigoBarras().length() + produto.getNome().length())) + "%s\n", produto.getCodigoBarras(), produto.getNome());
            String itemLinhaDois = String.format("%s  x  %s" + "                                                               ".substring(0, 44 - (quantidadeFormat.format(quantidade).length() + moedaFormat.format(produto.getPrecoVenda()).length() + quantidadeFormat.format(quantidade.multiply(produto.getPrecoVenda())).length())) + "%s\n", quantidadeFormat.format(quantidade), moedaFormat.format(produto.getPrecoVenda()), moedaFormat.format(quantidade.multiply(produto.getPrecoVenda())));
            
            imprimir(itemLinhaUm);		
            imprimir(itemLinhaDois);
		} catch (Exception e) {
			return false;
		}
		
		INT_COUNT++;
		
		return true;
	}
	
	@Override
	public boolean imprimirFechamento(Venda venda) {	
		try {
			String linhaValorTotal = String.format("%s" + "                                         ".substring(0, 39 - moedaFormat.format(venda.getValorTotal()).length()) + "%s\n", "Val Total", moedaFormat.format(venda.getValorTotal()));
			String linhaDesconto = String.format("%s" + "                                        ".substring(0, 39 - moedaFormat.format(venda.getValorDesconto()).length()) + "-%s\n", "Desconto", moedaFormat.format(venda.getValorDesconto()));
			String linhaValorSubTotal = String.format("%s" + "                                         ".substring(0, 39 - moedaFormat.format(venda.getValorTotal().subtract(venda.getValorDesconto())).length()) + "%s\n", "Sub Total", moedaFormat.format(venda.getValorTotal().subtract(venda.getValorDesconto())));
			String linhaTotalPago = String.format("%s" + "                                         ".substring(0, 38 - moedaFormat.format(venda.getValorPago()).length()) + "%s\n", "Total Pago", moedaFormat.format(venda.getValorPago()));          
            String linhaTroco = String.format("%s" + "                                            ".substring(0, 43 - moedaFormat.format((venda.getTroco())).length()) + "%s\n", "Troco", moedaFormat.format((venda.getTroco())));

            imprimir("                                 ---------------\n");
			imprimir(linhaValorTotal);
			
			if(!venda.getFormaPagamento().equals(FormaPagamento.CREDIARIO)) {
				imprimir(linhaDesconto);
				imprimir(linhaValorSubTotal);
				imprimir("------------------------------------------------\n");
				imprimir(linhaTotalPago);
				imprimir(linhaTroco);
			}
			
			imprimir("------------------------------------------------\n");
			imprimir("Data/Hora                    " + simpleDateFormat.format(data) + "\n");
			imprimir("------------------------------------------------\n");
			imprimir("VENDA Nº: " + (vendas.count() + 1) + "\n");
			imprimir("CLIENTE: " + venda.getCliente().getNome() + "\n");
			imprimir("OPERADOR: " + venda.getUsuario().getNome() + "\n");
			imprimir("================================================\n");
			imprimir("             OBRIGADO, VOLTE SEMPRE!            \n");
			imprimir("\n\n\n\n\n\n");

			// guilhotina
			
			if(venda.getFormaPagamento().equals(FormaPagamento.CREDIARIO)) {
				Optional<Empresa> empresa = empresas.findById(1L);	
				
				String empresaLine = String.format("                       ".substring(0, 23 - empresa.get().getNome().length() / 2) + "%s" + "                        ".substring(0, 24 - empresa.get().getNome().length() / 2), empresa.get().getNome());			
				String enderecoLine = String.format("%s, %s, %s", empresa.get().getRua(), empresa.get().getNumero(), empresa.get().getBairro() + "\n");
				String localizacaoLine = String.format("%s - %s | %s", empresa.get().getCidade().getNome(), empresa.get().getEstado().getSigla(), empresa.get().getTelefone() + "\n");

				imprimir(empresaLine + "\n");
				imprimir(enderecoLine);
				imprimir(localizacaoLine);
				imprimir("   COMPROVANTE VALE DE CLIENTE - NAO E FISCAL   \n");			
				imprimir("================================================\n");
				imprimir(" ESTE DOCUMENTO COMPROVA QUE O REFERENTE CLIENTE\n");
				imprimir(" CITADO  ABAIXO  O  QUAL  POR MEIO DE ASSINATURA\n");
				imprimir(" FIRMA   CONTRATO  DE  COMPRA  NO  CREDIARIO  NA\n");
				imprimir(" REFERIDA  DATA  TAMBEM  CITADA  NESSE DOCUMENTO\n");
				imprimir(" FICA  CIENTE  QUE  O NAO PAGAMENTO RESULTARA EM\n");
				imprimir(" ACAO DE COBRANCA NAS NORMAS DA LEI.            \n");
				imprimir("------------------------------------------------\n");
				imprimir("CPF: " + venda.getCliente().getCpf() + "\n");
				imprimir("CLIENTE: " + venda.getCliente().getNome() + "\n");
				imprimir("ENDERECO: " + venda.getCliente().getRua() + ", " + venda.getCliente().getNumero() + ", " + venda.getCliente().getBairro() + "\n");
				imprimir("LOCALIDADE: " + venda.getCliente().getCidade().getNome() + " - " + venda.getCliente().getEstado().getSigla() + "\n");
				imprimir("------------------------------------------------\n");
				imprimir("VALOR TOTAL: " + venda.getValorTotal() + "\n");
				imprimir("VENDA Nº: " + (vendas.count() + 1) + "\n");
				imprimir("OPERADOR: " + venda.getUsuario().getNome() + "\n");
				imprimir("------------------------------------------------\n");
				imprimir("Data/Hora                    " + simpleDateFormat.format(data) + "\n");
				imprimir("------------------------------------------------\n");
				imprimir("        RECONHECO E PAGAREI A DIVIDA AQUI       \n");
				imprimir("\n");
				imprimir("   __________________________________________   \n");
				imprimir("             ASSINATURA DO CLIENTE              \n");
				imprimir("================================================\n");
				imprimir("             OBRIGADO, VOLTE SEMPRE!            \n");
				imprimir("\n\n\n\n\n\n");
				
				//guilhotina
			}
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	public void imprimir(String texto) {
		BematechNativeInterface bematech = BematechNativeInterface.Instance;
		
		bematech.BematechTX(texto);
	}
}*/
