package br.com.jdrmservices.controller;

import static br.com.jdrmservices.util.Constants.VIEW_RELATORIOS;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.jdrmservices.repository.Funcionarios;
import br.com.jdrmservices.service.RelatorioService;

@RestController
@RequestMapping("/relatorios")
public class RelatoriosController {
	
	@Autowired
	private RelatorioService relatorioService;

	@Autowired
	private Funcionarios funcionarios;
	
	@GetMapping
	public ModelAndView emitirRelatorioVendas() {
		ModelAndView mv = new ModelAndView(VIEW_RELATORIOS);
		mv.addObject("funcionarios", funcionarios.findAllByOrderByNomeAsc());
		return mv;
	}
	
	@GetMapping("/vendas")
	public ResponseEntity<byte[]> gerarRelatorioVendas(@RequestParam LocalDate dataInicio, @RequestParam LocalDate dataFim) throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioVendas(dataInicio, dataFim);
		
		return relatorioEmPdf(relatorio);
	}
	
	@GetMapping("/clientes")
	public ResponseEntity<byte[]> gerarRelatorioClientes() throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioClientes();
		
		return relatorioEmPdf(relatorio);
	}
	
	@GetMapping("/produtos")
	public ResponseEntity<byte[]> gerarRelatorioProdutos() throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioProdutos();
		
		return relatorioEmPdf(relatorio);
	}
	
	@GetMapping("/funcionarios")
	public ResponseEntity<byte[]> gerarRelatorioFuncionarios() throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioFuncionarios();
		
		return relatorioEmPdf(relatorio);
	}
	
	@GetMapping("/comissaoFuncionarios")
	public ResponseEntity<byte[]> gerarRelatorioComissaoFuncionarios(@RequestParam String nomeFuncionario, @RequestParam LocalDate dataInicioFuncionario, @RequestParam LocalDate dataFimFuncionario) throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioComissaoFuncionarios(nomeFuncionario, dataInicioFuncionario, dataFimFuncionario);
		
		return relatorioEmPdf(relatorio);
	}
	
	private ResponseEntity<byte[]> relatorioEmPdf(byte[] relatorio) {
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);
	}
}
