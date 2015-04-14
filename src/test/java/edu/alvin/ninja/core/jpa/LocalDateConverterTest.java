package edu.alvin.ninja.core.jpa;

import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LocalDateConverterTest {

    @Test
    public void testConvertToDatabaseColumn() throws Exception {
        final LocalDate expected = LocalDate.of(2015, 1, 1);
        LocalDateConverter converter = new LocalDateConverter();
        Date actual = converter.convertToDatabaseColumn(expected);
        assertThat(actual, is(Date.valueOf(expected)));
    }

    @Test
    public void testConvertToEntityAttribute() throws Exception {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.JANUARY, 1);
        final Date expected = new Date(calendar.getTimeInMillis());
        LocalDateConverter converter = new LocalDateConverter();
        LocalDate actual = converter.convertToEntityAttribute(expected);
        assertThat(actual, is(expected.toLocalDate()));
    }
}