package br.com.jdrmservices;

import java.util.Locale;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import br.com.jdrmservices.util.UtilitarioTrial;

@SpringBootApplication
@EnableAutoConfiguration
public class PdvBasicoApplication {

	public static void main(String[] args) {
		
		Logger logger = LoggerFactory.getLogger(PdvBasicoApplication.class);
		
		try {
			/*if(UtilitarioTrial.isExpired()) {
				JOptionPane.showMessageDialog(null, "Tempo de teste expirado!", "Atenção", JOptionPane.WARNING_MESSAGE);
			} else {				
				SpringApplication.run(PdvBasicoApplication.class, args);
			}*/
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