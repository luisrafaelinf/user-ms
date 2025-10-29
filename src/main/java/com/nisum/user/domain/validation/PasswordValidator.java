package com.nisum.user.domain.validation;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Value("${pass.regex}")
    private String regex;

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
