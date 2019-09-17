package br.com.jdrmservices.service;

import static br.com.jdrmservices.util.Constants.INFORMACOES_JA_CADASTRADAS;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.model.Categoria;
import br.com.jdrmservices.repository.Categorias;

@Service
public class CategoriaService {

	@Autowired
	private Categorias categorias;
	
	@Transactional
	public void cadastrar(Categoria categoria) {
		Optional<Categoria> optional = categorias.findByNomeIgnoreCase(categoria.getNome());
		
		if(categoria.isNovo() && optional.isPresent()) {
			throw new GlobalException(INFORMACOES_JA_CADASTRADAS);
		}
		
		categorias.saveAndFlush(categoria);
	}
	
	@Transactional
	public void excluir(Categoria categoria) {
		categorias.delete(categoria);
	}	
}