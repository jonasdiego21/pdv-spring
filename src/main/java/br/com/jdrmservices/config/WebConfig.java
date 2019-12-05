package br.com.jdrmservices.config;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static br.com.jdrmservices.util.Constants.FORMAT_DATE;
import static br.com.jdrmservices.util.Constants.FORMAT_TIME;
import static br.com.jdrmservices.util.Constants.FORMAT_CURRENCY;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {	
		NumberStyleFormatter bigDecimalFormatter = new NumberStyleFormatter(FORMAT_CURRENCY);
		registry.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);
		
		DateTimeFormatterRegistrar dateTimeFormatterRegistrar = new DateTimeFormatterRegistrar();
		dateTimeFormatterRegistrar.setDateFormatter(DateTimeFormatter.ofPattern(FORMAT_DATE));
		dateTimeFormatterRegistrar.setTimeFormatter(DateTimeFormatter.ofPattern(FORMAT_TIME));
		dateTimeFormatterRegistrar.registerFormatters(registry);
		
		DateFormatterRegistrar dateFormatterRegistrar = new DateFormatterRegistrar();
		dateFormatterRegistrar.setFormatter(new DateFormatter(FORMAT_DATE));
		dateFormatterRegistrar.registerFormatters(registry);
	}
}
