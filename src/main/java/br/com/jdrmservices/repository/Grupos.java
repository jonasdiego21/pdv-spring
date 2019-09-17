package br.com.jdrmservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jdrmservices.model.Grupo;

@Repository
public interface Grupos extends JpaRepository<Grupo, Long> {

}