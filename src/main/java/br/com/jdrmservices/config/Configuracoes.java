package br.com.jdrmservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import br.com.jdrmservices.balanca.Balanca;
import br.com.jdrmservices.balanca.BalancaInterface;
//import br.com.jdrmservices.bematech.BematechInterface;
//import br.com.jdrmservices.bematech.BematechPrinter;
//import br.com.jdrmservices.epson.EpsonPrint;
//import br.com.jdrmservices.epson.EpsonPrintInterface;
import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
public class Configuracoes {
	
	@Bean
	public LayoutDialect layoutDialect() {
	    return new LayoutDialect();
	}
	
	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}
	
	//@Bean
	//public EpsonPrintInterface epsonPrintInterface() {
		//return new EpsonPrint();
	//}
	
	//@Bean
	//public BematechInterface bematechInterface() {
		//return new BematechPrinter();
	//}

	@Bean
	public BalancaInterface balancaInterface() {
		return new Balanca();
	}
}