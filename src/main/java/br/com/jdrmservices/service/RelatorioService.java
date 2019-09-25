package br.com.jdrmservices.service;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class RelatorioService {

	@Autowired
	private DataSource dataSource;

	public byte[] gerarRelatorioVendas(String dataInicio, String dataFim) throws Exception {	
		Map<String, Object> parametros = new HashMap<>();
		
		parametros.put("data_inicio", dataInicio);
		parametros.put("data_fim", dataFim);
		
		return exportandoPdf(parametros, "/relatorios/relatorio_vendas.jasper");
	}
	
	public byte[] gerarRelatorioClientes() throws Exception {	
		Map<String, Object> parametros = new HashMap<>();		
		return exportandoPdf(parametros, "/relatorios/relatorio_clientes.jasper");
	}
	
	public byte[] gerarRelatorioProdutos() throws Exception {	
		Map<String, Object> parametros = new HashMap<>();		
		return exportandoPdf(parametros, "/relatorios/relatorio_produtos.jasper");
	}
	
	public byte[] gerarRelatorioFuncionarios() throws Exception {	
		Map<String, Object> parametros = new HashMap<>();		
		return exportandoPdf(parametros, "/relatorios/relatorio_funcionarios.jasper");
	}
	
	private byte[] exportandoPdf(Map<String, Object> parametros, String caminhoRelatorio) throws JRException, SQLException {
		parametros.put("format", "pdf");
		
		InputStream inputStream = this.getClass().getResourceAsStream(caminhoRelatorio);		
		Connection connection = this.dataSource.getConnection();
		
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, connection);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {
			connection.close();
		}	
	}	
}
