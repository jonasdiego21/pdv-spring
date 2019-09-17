package br.com.jdrmservices.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class EpsonController {
	
	@GetMapping("error-imprimir")
	public ModelAndView imprimir() {
		ModelAndView mv = new ModelAndView("epson/impressora");		
		
		return mv;
	}
}
