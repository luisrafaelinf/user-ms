package com.nisum.user.domain.validation;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private final String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (Objects.isNull(value)) {
			return false;
		}

        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(value);
        
		return m.matches();
    }


}
