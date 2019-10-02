package br.com.jdrmservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jdrmservices.model.ItemVenda;

@Repository
public interface ItensVendas extends JpaRepository<ItemVenda, Long> {

}
