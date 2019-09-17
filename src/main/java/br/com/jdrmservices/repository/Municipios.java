package br.com.jdrmservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jdrmservices.model.Municipio;

@Repository
public interface Municipios extends JpaRepository<Municipio, Long> {
	List<Municipio> findByEstadoCodigo(Long codigoEstado);
}