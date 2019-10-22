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
import javax.print.SimpleDoc;

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

	private PrintService impressora = null;
	
	byte[] comecoNegrito = {0x1B, 0x45};  
	byte[] fimNegrito = {0x1B, 0x46}; 
    
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
			imprimir("VENDA Nº: " + vendas.count() + "\n");
			imprimir("CLIENTE: " + venda.getCliente().getNome() + "\n");
			imprimir("OPERADOR: " + venda.getUsuario().getNome() + "\n");
			
			if(venda.getFormaPagamento().equals(FormaPagamento.CREDIARIO)) {
				System.out.print("------------------------------------------------\n");
				System.out.print("\n");
				System.out.print("   __________________________________________   \n");
				System.out.print("             ASSINATURA DO CLIENTE              \n");
				
				imprimir("------------------------------------------------\n");
				imprimir("\n");
				imprimir("   __________________________________________   \n");
				imprimir("             ASSINATURA DO CLIENTE              \n");
			}
			
			System.out.print("================================================\n");
			System.out.print("             OBRIGADO, VOLTE SEMPRE!            \n");
			System.out.print("\n\n\n\n\n\n");
			
			imprimir("================================================\n");
			imprimir("             OBRIGADO, VOLTE SEMPRE!            \n");
			imprimir("\n\n\n\n\n\n");
			imprimir("\u001b|100fP");
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
                //System.out.println("Impressoras: " + p.getName());
                
                if (p.getName().toUpperCase().contains("Generic".toUpperCase())) {
                        //System.out.println("Impressora Selecionada: " + p.getName());
                        impressora = p;
                    break;
                }
            }
        } catch (Exception e) {
        	return "Não encontrada.";
        }
        
        return impressora.getName();
    }
    
    public synchronized boolean imprimir(String texto) {
        if (impressora == null) {
            throw new GlobalException("Nenhuma impressora encontrada.");
        } else {
            try {
                //System.out.println("Impressora: " + impressora);
                DocPrintJob docPrintJob = impressora.createPrintJob();
                InputStream inputStream = new ByteArrayInputStream(texto.getBytes());
                DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                Doc doc = new SimpleDoc(inputStream, docFlavor, null);
                docPrintJob.print(doc, null);
                
                return true;
            } catch (PrintException e) {
                System.out.println(e);
            }
        }
        
        return false;
    }
}
