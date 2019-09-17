package br.com.jdrmservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jdrmservices.model.Municipio;
import br.com.jdrmservices.repository.Municipios;
	
@RestController
@RequestMapping("/cidades")
public class MunicipiosController {
	
	@Autowired
	private Municipios municipios;
	
	@GetMapping
	public List<Municipio> municipiosPorEstado(@RequestParam(name = "codigo", defaultValue = "-1") Long codigoEstado) {
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		
		return municipios.findByEstadoCodigo(codigoEstado);
	}
	
}
