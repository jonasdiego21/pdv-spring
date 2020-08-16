package br.com.jdrmservices.service;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
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

	public byte[] gerarRelatorioVendas(LocalDate dataInicio, LocalDate dataFim) throws Exception {	
		Map<String, Object> parametros = new HashMap<>();

		if(dataInicio != null && dataFim != null) {
			Date dataInicioConvertido = Date.from(LocalDateTime.of(dataInicio, LocalTime.of(0, 0, 0))
					.atZone(ZoneId.systemDefault()).toInstant());
			
			Date dataFimConvertido = Date.from(LocalDateTime.of(dataFim, LocalTime.of(23, 59, 59))
					.atZone(ZoneId.systemDefault()).toInstant());
			
			parametros.put("data_inicio", dataInicioConvertido);
			parametros.put("data_fim", dataFimConvertido);
		}
		
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
	
	public byte[] gerarRelatorioComissaoFuncionarios(String nomeFuncionario, LocalDate dataInicioFuncionario, LocalDate dataFimFuncionario) throws Exception {	
		Map<String, Object> parametros = new HashMap<>();
		
		Date dataInicioFuncionarioConvertido = Date.from(LocalDateTime.of(dataInicioFuncionario, LocalTime.of(0, 0, 0))
				.atZone(ZoneId.systemDefault()).toInstant());
		
		Date dataFimFuncionarioConvertido = Date.from(LocalDateTime.of(dataFimFuncionario, LocalTime.of(23, 59, 59))
				.atZone(ZoneId.systemDefault()).toInstant());

		parametros.put("funcionario", nomeFuncionario);
		parametros.put("data_inicio", dataInicioFuncionarioConvertido);
		parametros.put("data_fim", dataFimFuncionarioConvertido);
		
		return exportandoPdf(parametros, "/relatorios/relatorio_funcionario_comissao.jasper");
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
