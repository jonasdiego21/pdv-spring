package br.com.jdrmservices.converter;

import java.sql.Timestamp;
import java.time.LocalTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalTimeAttributeConverter implements AttributeConverter<LocalTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalTime localTime) {
		return (localTime == null ? null : Timestamp.valueOf(localTime.toString()));
	}

	@Override
	public LocalTime convertToEntityAttribute(Timestamp timestamp) {
		return (timestamp == null ? null : new LocalTimeAttributeConverter().convertToEntityAttribute(timestamp));
	}
	
}
