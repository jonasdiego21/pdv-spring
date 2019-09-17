package br.com.jdrmservices.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jdrmservices.model.Caixa;
import br.com.jdrmservices.repository.Caixas;

@Service
public class CaixaService {

	@Autowired
	private Caixas caixas;
	
	@Transactional
	public void abrirCaixa(Caixa caixa) {
		//regras de negocio
		caixas.saveAndFlush(caixa);
	}
	
	@Transactional
	public void fecharCaixa(Caixa caixa) {
		// regras de negocio
		caixas.saveAndFlush(caixa);
	}
}
