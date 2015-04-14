package edu.alvin.ninja.core.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Time;
import java.time.LocalTime;

@SuppressWarnings("UnusedDeclaration")
@Converter(autoApply = true)
public class LocalTimeConverter implements AttributeConverter<LocalTime, Time> {

    @Override
    public Time convertToDatabaseColumn(LocalTime localTime) {
        return Time.valueOf(localTime);
    }

    @Override
    public LocalTime convertToEntityAttribute(Time time) {
        return time.toLocalTime();
    }
}
