package br.com.jdrmservices.impressora;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.Attribute;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.text.AttributeSet.FontAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.model.Empresa;
import br.com.jdrmservices.model.Produto;
import br.com.jdrmservices.model.Venda;
import br.com.jdrmservices.model.enumeration.FormaPagamento;
import br.com.jdrmservices.repository.Empresas;
import br.com.jdrmservices.repository.Vendas;

@Component
@ComponentScan(basePackageClasses = { GenericPrinter.class })
@Service
public class GenericPrinter implements GenericPrinterInterface {

	
	@Autowired
	private Empresas empresas;
	
	@Autowired
	private Vendas vendas;

	private PrintService impressora = null;
    private Date data;
    private DecimalFormat decimalFormat;
	private DecimalFormat quantidadeFormat;
	private DecimalFormat moedaFormat;
	private SimpleDateFormat simpleDateFormat;
	private int INT_COUNT = 1;

    private static final char ESC = 27; //escape
    private static final char AT = 64; //@
    private static final char LINE_FEED = 10; //line feed/new line
    private static final char PARENTHESIS_LEFT = 40;
    private static final char BACKSLASH = 92;
    private static final char CR = 13; //carriage return
    private static final char TAB = 9; //horizontal tab
    private static final char FF = 12; //form feed
    private static final char P = 80; //10cpi pitch
    private static final char M = 77; //12cpi pitch
    private static final char g = 103; //15cpi pitch
    private static final char p = 112; //used for choosing proportional mode or fixed-pitch
    private static final char t = 116; //used for character set assignment/selection
    private static final char l = 108; //used for setting left margin
    private static final char x = 120; //used for setting draft or letter quality (LQ) printing
    private static final char E = 69; //bold font on
    private static final char F = 70; //bold font off
    private static final char J = 74; //used for advancing paper vertically
    private static final char Q = 81; //used for setting right margin
    private static final char $ = 36; //used for absolute horizontal positioning
    public static final char ITALIC_ON = 52; //set font italic
    public static final char ITALIC_OFF = 53; //unset font italic
    public static final char CONDENSED_ON = 15;
    public static final char CONDENSED_OFF = 18;
	
	public GenericPrinter() {
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
			listarImpressoras();
			Optional<Empresa> empresa = empresas.findById(1L);	
			
			//String cnpjLine = String.format("%s", empresa.get().getCnpj() + "\n");
			String empresaLine = String.format("                       ".substring(0, 23 - empresa.get().getNome().length() / 2) + "%s" + "                        ".substring(0, 24 - empresa.get().getNome().length() / 2), empresa.get().getNome());			
			String enderecoLine = String.format("%s, %s, %s", empresa.get().getRua(), empresa.get().getNumero(), empresa.get().getBairro() + "\n");
			String localizacaoLine = String.format("%s - %s | %s", empresa.get().getCidade().getNome(), empresa.get().getEstado().getSigla(), empresa.get().getTelefone() + "\n");

			System.out.print("\n\n\n\n");
			System.out.print(empresaLine + "\n");
			System.out.print(enderecoLine);
			System.out.print(localizacaoLine);
			System.out.print("           DOCUMENTO SEM VALOR FISCAL           \n");
			System.out.print("================================================\n");
			System.out.print("# COD     DESC     UN    QTD     VL UN.    VL TL\n");
			System.out.print("------------------------------------------------\n");
			
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
			listarImpressoras();
			
			String itemLinhaUm = String.format(decimalFormat.format(INT_COUNT) + "   %s" + "                                                              ".substring(0, 42 - (produto.getCodigoBarras().length() + produto.getNome().length())) + "%s\n", produto.getCodigoBarras(), produto.getNome());
            String itemLinhaDois = String.format("%s  x  %s" + "                                                               ".substring(0, 44 - (quantidadeFormat.format(quantidade).length() + moedaFormat.format(produto.getPrecoVenda()).length() + quantidadeFormat.format(quantidade.multiply(produto.getPrecoVenda())).length())) + "%s\n", quantidadeFormat.format(quantidade), moedaFormat.format(produto.getPrecoVenda()), moedaFormat.format(quantidade.multiply(produto.getPrecoVenda())));
			
            System.out.print(itemLinhaUm);
            System.out.print(itemLinhaDois);
            
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
			listarImpressoras();
			
			String linhaValorTotal = String.format("%s" + "                                         ".substring(0, 39 - moedaFormat.format(venda.getValorTotal()).length()) + "%s\n", "Val Total", moedaFormat.format(venda.getValorTotal()));
			String linhaDesconto = String.format("%s" + "                                        ".substring(0, 39 - moedaFormat.format(venda.getValorDesconto()).length()) + "-%s\n", "Desconto", moedaFormat.format(venda.getValorDesconto()));
			String linhaValorSubTotal = String.format("%s" + "                                         ".substring(0, 39 - moedaFormat.format(venda.getValorTotal().subtract(venda.getValorDesconto())).length()) + "%s\n", "Sub Total", moedaFormat.format(venda.getValorTotal().subtract(venda.getValorDesconto())));
			String linhaTotalPago = String.format("%s" + "                                         ".substring(0, 38 - moedaFormat.format(venda.getValorPago()).length()) + "%s\n", "Total Pago", moedaFormat.format(venda.getValorPago()));          
            String linhaTroco = String.format("%s" + "                                            ".substring(0, 43 - moedaFormat.format((venda.getTroco())).length()) + "%s\n", "Troco", moedaFormat.format((venda.getTroco())));
			
            System.out.print("                                 ---------------\n");
            System.out.print(linhaValorTotal);

            imprimir("                                 ---------------\n");
			imprimir(linhaValorTotal);
			
			if(!venda.getFormaPagamento().equals(FormaPagamento.CREDIARIO)) {
				System.out.print(linhaDesconto);
				System.out.print(linhaTotalPago);
				System.out.print("------------------------------------------------\n");
				System.out.print(linhaValorSubTotal);
				System.out.print(linhaTroco);
				
				imprimir(linhaDesconto);
				imprimir(linhaValorSubTotal);
				imprimir("------------------------------------------------\n");
				imprimir(linhaTotalPago);
				imprimir(linhaTroco);
			}
			
			System.out.print("------------------------------------------------\n");
			System.out.print("Data/Hora                    " + simpleDateFormat.format(data) + "\n");
			System.out.print("------------------------------------------------\n");
			System.out.print("VENDA Nº: " + vendas.count() + "\n");
			System.out.print("CLIENTE: " + venda.getCliente().getNome() + "\n");
			System.out.print("OPERADOR: " + venda.getUsuario().getNome() + "\n");
			
			imprimir("------------------------------------------------\n");
			imprimir("Data/Hora                    " + simpleDateFormat.format(data) + "\n");
			imprimir("------------------------------------------------\n");
			imprimir("VENDA Nº: " + (vendas.count() + 1) + "\n");
			imprimir("CLIENTE: " + venda.getCliente().getNome() + "\n");
			imprimir("OPERADOR: " + venda.getUsuario().getNome() + "\n");
			
			/*
			if(venda.getFormaPagamento().equals(FormaPagamento.CREDIARIO)) {
				System.out.print("------------------------------------------------\n");
				System.out.print("\n");
				System.out.print("   __________________________________________   \n");
				System.out.print("             ASSINATURA DO CLIENTE              \n");
				
				imprimir("------------------------------------------------\n");
				imprimir("\n");
				imprimir("   __________________________________________   \n");
				imprimir("             ASSINATURA DO CLIENTE              \n");
			}*/
			
			System.out.print("================================================\n");
			System.out.print("             OBRIGADO, VOLTE SEMPRE!            \n");
			System.out.print("\n\n\n\n\n\n");
			
			imprimir("================================================\n");
			imprimir("             OBRIGADO, VOLTE SEMPRE!            \n");
			imprimir("\n\n\n");

			this.acionarGuilhotina();
			
			if(venda.getFormaPagamento().equals(FormaPagamento.CREDIARIO)) {
				listarImpressoras();
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
				imprimir("\n\n\n");
				
				this.acionarGuilhotina();
				
				System.out.print(empresaLine + "\n");
				System.out.print(enderecoLine);
				System.out.print(localizacaoLine);
				System.out.print("   COMPROVANTE VALE DE CLIENTE - NAO E FISCAL   \n");			
				System.out.print("================================================\n");
				System.out.print(" ESTE DOCUMENTO COMPROVA QUE O REFERENTE CLIENTE\n");
				System.out.print(" CITADO  ABAIXO  O  QUAL  POR MEIO DE ASSINATURA\n");
				System.out.print(" FIRMA   CONTRATO  DE  COMPRA  NO  CREDIARIO  NA\n");
				System.out.print(" REFERIDA  DATA  TAMBEM  CITADA  NESSE DOCUMENTO\n");
				System.out.print(" FICA  CIENTE  QUE  O NAO PAGAMENTO RESULTARA EM\n");
				System.out.print(" ACAO DE COBRANCA NAS NORMAS DA LEI.            \n");
				System.out.print("------------------------------------------------\n");
				System.out.print("CPF: " + venda.getCliente().getCpf() + "\n");
				System.out.print("CLIENTE: " + venda.getCliente().getNome() + "\n");
				System.out.print("ENDERECO: " + venda.getCliente().getRua() + ", " + venda.getCliente().getNumero() + ", " + venda.getCliente().getBairro() + "\n");
				System.out.print("LOCALIDADE: " + venda.getCliente().getCidade().getNome() + " - " + venda.getCliente().getEstado().getSigla() + "\n");
				System.out.print("------------------------------------------------\n");
				System.out.print("VALOR TOTAL: " + venda.getValorTotal() + "\n");
				System.out.print("VENDA Nº: " + (vendas.count() + 1) + "\n");
				System.out.print("OPERADOR: " + venda.getUsuario().getNome() + "\n");
				System.out.print("------------------------------------------------\n");
				System.out.print("Data/Hora                    " + simpleDateFormat.format(data) + "\n");
				System.out.print("------------------------------------------------\n");
				System.out.print("        RECONHECO E PAGAREI A DIVIDA AQUI       \n");
				System.out.print("\n");
				System.out.print("   __________________________________________   \n");
				System.out.print("             ASSINATURA DO CLIENTE              \n");
				System.out.print("================================================\n");
				System.out.print("             OBRIGADO, VOLTE SEMPRE!            \n");
				System.out.print("\n");
			}
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	public String listarImpressoras() {
        try {
            DocFlavor docFlavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(docFlavor, null);
           
            for (PrintService p : printServices) {
                if (p.getName().toUpperCase().contains("Generic".toUpperCase())) {
                        impressora = p;
                    break;
                }
            }
        } catch (Exception e) {
        	return "Não encontrada.";
        }
        
        return impressora != null ? impressora.getName() : "Impressora não encontrada";
    }
    
    public synchronized boolean imprimir(String texto) {
        if (impressora == null) {
            throw new GlobalException("Nenhuma impressora encontrada.");
        } else {
            try {
            	DocPrintJob docPrintJob = impressora.createPrintJob();
                InputStream inputStream = new ByteArrayInputStream(texto.getBytes("Latin1"));
                DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                
                Doc doc = new SimpleDoc(inputStream, docFlavor, null);
                
                docPrintJob.print(doc, null);
                
                return true;                
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        return false;
    }
    
    public void acionarGuilhotina() {
        this.imprimir("" + (char) 27 + (char) 109);
    }
}
