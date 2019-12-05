package br.com.jdrmservices.repository.helper.venda;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jdrmservices.dto.TotalVendasAno;
import br.com.jdrmservices.dto.TotalVendasAnoCrediario;
import br.com.jdrmservices.dto.TotalVendasAnoGeral;
import br.com.jdrmservices.dto.TotalVendasDia;
import br.com.jdrmservices.dto.TotalVendasDiaCrediario;
import br.com.jdrmservices.dto.TotalVendasDiaGeral;
import br.com.jdrmservices.dto.TotalVendasMes;
import br.com.jdrmservices.dto.TotalVendasMesCrediario;
import br.com.jdrmservices.dto.TotalVendasMesGeral;
import br.com.jdrmservices.model.Venda;
import br.com.jdrmservices.repository.filter.VendaFilter;

public interface VendasQueries {
	public Page<Venda> filtrar(VendaFilter filtro, Pageable pageable);
	
	public BigDecimal valorTotalCaixa();
	public BigDecimal valorTotalDespesas();
	public BigDecimal valorTotalSaldo();
	
	public BigDecimal valorTotalDia();
	public BigDecimal valorTotalMes();
	public BigDecimal valorTotalAno();
	
	public BigDecimal valorTotalDiaCrediario();
	public BigDecimal valorTotalMesCrediario();
	public BigDecimal valorTotalAnoCrediario();
	
	public BigDecimal valorTotalDiaCredito();
	public BigDecimal valorTotalMesCredito();
	public BigDecimal valorTotalAnoCredito();
	
	public BigDecimal valorTotalDiaDebito();
	public BigDecimal valorTotalMesDebito();
	public BigDecimal valorTotalAnoDebito();
	
	public BigDecimal valorTotalDiaGeral();
	public BigDecimal valorTotalMesGeral();
	public BigDecimal valorTotalAnoGeral();
	
	public List<TotalVendasDia> totalVendasDia();
	public List<TotalVendasMes> totalVendasMes();
	public List<TotalVendasAno> totalVendasAno();
	
	public List<TotalVendasDiaCrediario> totalVendasDiaCrediario();
	public List<TotalVendasMesCrediario> totalVendasMesCrediario();
	public List<TotalVendasAnoCrediario> totalVendasAnoCrediario();
	
	public List<TotalVendasDiaGeral> totalVendasDiaGeral();
	public List<TotalVendasMesGeral> totalVendasMesGeral();
	public List<TotalVendasAnoGeral> totalVendasAnoGeral();
}