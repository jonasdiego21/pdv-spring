/*
package br.com.jdrmservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.jdrmservices.epson.EpsonPrint;

@RestController
@RequestMapping("/")
public class EpsonController {
	
	@Autowired
	private EpsonPrint epsonPrint;
	
	@GetMapping("error-imprimir")
	public ModelAndView imprimir() {
		ModelAndView mv = new ModelAndView("epson/impressora");	
		
		try {
			if(epsonPrint.conectar()) {
				System.out.println("IMPRESSORA CONECTADA COM SUESSO!");
			}
		} catch (Exception e) {
			System.out.println("ERRO AO CONECTAR A IMPRESSORA - " + e.getMessage());
		}
		
		return mv;
	}
}
*/
