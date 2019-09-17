package br.com.jdrmservices.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalTime;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalTime localTime) {
		return null;
	}

	@Override
	public LocalTime convertToEntityAttribute(Timestamp timestamp) {
		return null;
	}

}
