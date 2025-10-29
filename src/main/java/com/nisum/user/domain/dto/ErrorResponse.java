package com.nisum.user.domain.dto;

import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    @JsonProperty("mensaje")
    private String errorMessage;

    public static ErrorResponse fromFieldError(FieldError error) {
        return new ErrorResponse( error.getDefaultMessage());
    }
    
    public static ErrorResponse fromConstraintViolation(ConstraintViolation error) {
        return new ErrorResponse( error.getMessage());
    }

}
