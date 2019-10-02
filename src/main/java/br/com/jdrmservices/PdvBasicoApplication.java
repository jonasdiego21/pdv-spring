package br.com.jdrmservices;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@SpringBootApplication
@EnableAutoConfiguration
public class PdvBasicoApplication {

	public static void main(String[] args) {
		
		Logger logger = LoggerFactory.getLogger(PdvBasicoApplication.class);
		
		try {
			SpringApplication.run(PdvBasicoApplication.class, args);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			logger.info("Finalizado acesse: http://localhost:8083");
		}
	}
	
	@Bean
	public FixedLocaleResolver fixedLocalResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
}