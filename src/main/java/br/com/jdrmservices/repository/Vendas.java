package br.com.jdrmservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jdrmservices.model.Venda;
import br.com.jdrmservices.repository.helper.venda.VendasQueries;

@Repository
public interface Vendas extends JpaRepository<Venda, Long>, VendasQueries {

}