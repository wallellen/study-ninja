package edu.alvin.ninja.core.freemarker.wrappers;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleDate;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ExtendedObjectWrapper extends DefaultObjectWrapper {

    public ExtendedObjectWrapper() {
        super(Configuration.VERSION_2_3_21);
    }

    @Override
    public TemplateModel wrap(Object o) throws TemplateModelException {
        if (o instanceof LocalDate) {
            return new SimpleDate(Date.valueOf((LocalDate) o));
        } else if (o instanceof LocalTime) {
            return new SimpleDate(Time.valueOf((LocalTime) o));
        } else if (o instanceof LocalDateTime) {
            return new SimpleDate(Timestamp.valueOf((LocalDateTime) o));
        } else {
            return super.wrap(o);
        }
    }
}
