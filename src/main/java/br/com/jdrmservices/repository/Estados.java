package br.com.jdrmservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jdrmservices.model.Estado;

@Repository
public interface Estados extends JpaRepository<Estado, Long> {
	public List<Estado> findAllByOrderByNomeAsc();
}