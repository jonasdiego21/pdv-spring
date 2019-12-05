/*package br.com.jdrmservices.epson;

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
import jpos.JposException;
import jpos.POSPrinter;
import jpos.POSPrinterConst;
import jpos.POSPrinterControl114;

@Component
@ComponentScan(basePackageClasses = { EpsonPrint.class })
@Service
public class EpsonPrint implements EpsonPrintInterface {

	@Autowired
	private Empresas empresas;
	
	@Autowired
	private Vendas vendas;

	private POSPrinterControl114 printer = (POSPrinterControl114) new POSPrinter();
	
	private static final String JPOS_SIZE0 = "\u001b|1C";
    private static final String JPOS_SIZE1 = "\u001b|2C";
    private static final String JPOS_BOLD = "\u001b|bC";
    private static final String JPOS_CUT = "\u001b|100fP";

    private Date data;

    private DecimalFormat decimalFormat;
	private DecimalFormat quantidadeFormat;
	private DecimalFormat moedaFormat;
	private SimpleDateFormat simpleDateFormat;
	
	private int INT_COUNT = 1;
	
	public EpsonPrint() {
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
	public boolean conectar() {
		try {
			printer.open("POSPrinter");
			printer.claim(1000);
			printer.setDeviceEnabled(true);
		} catch (JposException ex) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean desconectar() {
		try {
			printer.setDeviceEnabled(false);
			printer.release();
			printer.close();
		} catch (JposException ex) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean imprimirCabacalho() {
		try {
			conectar();

			Optional<Empresa> empresa = empresas.findById(1L);	
			
			//String cnpjLine = String.format("%s", empresa.get().getCnpj() + "\n");
			String empresaLine = String.format("           ".substring(0, 11 - empresa.get().getNome().length() / 2) + "%s" + "            ".substring(0, 12 - empresa.get().getNome().length() / 2), empresa.get().getNome());			
			String enderecoLine = String.format("%s, %s, %s", empresa.get().getRua(), empresa.get().getNumero(), empresa.get().getBairro() + "\n");
			String localizacaoLine = String.format("%s - %s | %s", empresa.get().getCidade().getNome(), empresa.get().getEstado().getSigla(), empresa.get().getTelefone() + "\n");

			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, JPOS_SIZE1 + JPOS_BOLD + empresaLine + "\n");
			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, JPOS_BOLD + enderecoLine);
			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, JPOS_BOLD + localizacaoLine);
			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "           DOCUMENTO SEM VALOR FISCAL           \n");			
			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "================================================\n");
			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "# COD     DESC     UN    QTD     VL UN.    VL TL\n");
			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "------------------------------------------------\n");
		} catch (JposException ex) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean imprimirItem(String uuid, Produto produto, BigDecimal quantidade) {
		try {
			String itemLinhaUm = String.format(decimalFormat.format(INT_COUNT) + "   %s" + "                                                              ".substring(0, 42 - (produto.getCodigoBarras().length() + produto.getNome().length())) + "%s\n", produto.getCodigoBarras(), produto.getNome());
            String itemLinhaDois = String.format("%s  x  %s" + "                                                               ".substring(0, 44 - (quantidadeFormat.format(quantidade).length() + moedaFormat.format(produto.getPrecoVenda()).length() + quantidadeFormat.format(quantidade.multiply(produto.getPrecoVenda())).length())) + "%s\n", quantidadeFormat.format(quantidade), moedaFormat.format(produto.getPrecoVenda()), moedaFormat.format(quantidade.multiply(produto.getPrecoVenda())));
			
            printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, itemLinhaUm);		
			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, itemLinhaDois);
		} catch (JposException e) {
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
			
			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "                                 ---------------\n");
			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, JPOS_SIZE0 + JPOS_BOLD + linhaValorTotal);
			
			if(!venda.getFormaPagamento().equals(FormaPagamento.CREDIARIO)) {
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, linhaDesconto);
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, linhaValorSubTotal);
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "------------------------------------------------\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, linhaTotalPago);
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, linhaTroco);
			}
			
			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "------------------------------------------------\n");
			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "Data/Hora                    " + simpleDateFormat.format(data) + "\n");
			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "------------------------------------------------\n");
			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "VENDA Nº: " + (vendas.count() + 1) + "\n");
			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "CLIENTE: " + venda.getCliente().getNome() + "\n");
			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "OPERADOR: " + venda.getUsuario().getNome() + "\n");
			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "================================================\n");
			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "             OBRIGADO, VOLTE SEMPRE!            \n");
			printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, JPOS_CUT);

			
			if(venda.getFormaPagamento().equals(FormaPagamento.CREDIARIO)) {
				Optional<Empresa> empresa = empresas.findById(1L);	
				
				String empresaLine = String.format("                       ".substring(0, 23 - empresa.get().getNome().length() / 2) + "%s" + "                        ".substring(0, 24 - empresa.get().getNome().length() / 2), empresa.get().getNome());			
				String enderecoLine = String.format("%s, %s, %s", empresa.get().getRua(), empresa.get().getNumero(), empresa.get().getBairro() + "\n");
				String localizacaoLine = String.format("%s - %s | %s", empresa.get().getCidade().getNome(), empresa.get().getEstado().getSigla(), empresa.get().getTelefone() + "\n");

				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, empresaLine + "\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, enderecoLine);
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, localizacaoLine);
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "   COMPROVANTE VALE DE CLIENTE - NAO E FISCAL   \n");			
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "================================================\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, " ESTE DOCUMENTO COMPROVA QUE O REFERENTE CLIENTE\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, " CITADO  ABAIXO  O  QUAL  POR MEIO DE ASSINATURA\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, " FIRMA   CONTRATO  DE  COMPRA  NO  CREDIARIO  NA\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, " REFERIDA  DATA  TAMBEM  CITADA  NESSE DOCUMENTO\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, " FICA  CIENTE  QUE  O NAO PAGAMENTO RESULTARA EM\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, " ACAO DE COBRANCA NAS NORMAS DA LEI.            \n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "------------------------------------------------\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "CPF: " + venda.getCliente().getCpf() + "\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "CLIENTE: " + venda.getCliente().getNome() + "\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "ENDERECO: " + venda.getCliente().getRua() + ", " + venda.getCliente().getNumero() + ", " + venda.getCliente().getBairro() + "\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "LOCALIDADE: " + venda.getCliente().getCidade().getNome() + " - " + venda.getCliente().getEstado().getSigla() + "\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "------------------------------------------------\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "VALOR TOTAL: " + venda.getValorTotal() + "\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "VENDA Nº: " + (vendas.count() + 1) + "\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "OPERADOR: " + venda.getUsuario().getNome() + "\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "------------------------------------------------\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "Data/Hora                    " + simpleDateFormat.format(data) + "\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "------------------------------------------------\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "        RECONHECO E PAGAREI A DIVIDA AQUI       \n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "   __________________________________________   \n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "             ASSINATURA DO CLIENTE              \n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "================================================\n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "             OBRIGADO, VOLTE SEMPRE!            \n");
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\n\n\n\n\n\n");				
				printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, JPOS_CUT);
			}
			
			desconectar();
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
}*/
