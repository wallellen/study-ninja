package edu.alvin.ninja.core.validators;

import com.google.common.base.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class TelephoneValidator implements ConstraintValidator<Telephone, String> {
    private static final Pattern pattern = Pattern.compile("^\\(?0\\d{2}[\\s)-]?\\d{8}$|" +
            "^\\(?0\\d{3}[\\s)-]?\\d{7}$|^0?1\\d{10}$");

    @Override
    public void initialize(Telephone telephone) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Strings.isNullOrEmpty(s) || pattern.matcher(s).matches();
    }
}
