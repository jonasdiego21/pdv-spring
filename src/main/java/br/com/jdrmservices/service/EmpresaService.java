package br.com.jdrmservices.service;

import static br.com.jdrmservices.util.Constants.INFORMACOES_JA_CADASTRADAS;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.model.Empresa;
import br.com.jdrmservices.repository.Empresas;

@Service
public class EmpresaService {

	@Autowired
	private Empresas empresas;
	
	@Transactional
	public void cadastrar(Empresa empresa) {
		Optional<Empresa> optional = empresas.findByNomeIgnoreCase(empresa.getNome());
		
		if(empresa.isNovo() && optional.isPresent()) {
			throw new GlobalException(INFORMACOES_JA_CADASTRADAS);
		}
		
		empresas.saveAndFlush(empresa);
	}
	
	@Transactional
	public void excluir(Empresa empresa) {
		empresas.delete(empresa);
	}	
}