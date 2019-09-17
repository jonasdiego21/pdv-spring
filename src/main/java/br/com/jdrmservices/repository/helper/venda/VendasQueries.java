package br.com.jdrmservices.repository.helper.venda;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jdrmservices.dto.TotalVendasAno;
import br.com.jdrmservices.dto.TotalVendasDia;
import br.com.jdrmservices.dto.TotalVendasMes;
import br.com.jdrmservices.model.Venda;
import br.com.jdrmservices.repository.filter.VendaFilter;

public interface VendasQueries {
	public Page<Venda> filtrar(VendaFilter filtro, Pageable pageable);
	
	public BigDecimal valorTotalDia();
	public BigDecimal valorTotalMes();
	public BigDecimal valorTotalAno();
	
	public List<TotalVendasDia> totalVendasDia();
	public List<TotalVendasMes> totalVendasMes();
	public List<TotalVendasAno> totalVendasAno();
}