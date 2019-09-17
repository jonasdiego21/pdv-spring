package br.com.jdrmservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jdrmservices.model.Caixa;

@Repository
public interface Caixas extends JpaRepository<Caixa, Long> {

}
