package com.nisum.user.configuration;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nisum.user.domain.dto.ErrorResponse;
import com.nisum.user.domain.exception.UserException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler  {

    private final MessageSource messageSource;

    public ApiExceptionHandler() {

        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("message/message");
        source.setDefaultEncoding("UTF-8");

        this.messageSource = source;
    }

    public static String getSimpleName(Throwable ex) {

        String name = ex.getClass().getName();
        int lastDotIndex = name.lastIndexOf('.');

        String n = name.substring(lastDotIndex + 1);

        return n;
    }

    private ErrorResponse getErrorResponse(String className, Object[] args) {

        final String message = messageSource.getMessage("%s.message".formatted(className), args, Locale.ENGLISH);

        return new ErrorResponse(message);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> UserExceptionHandler(UserException ex) {

        Object[] args = switch (ex) {
            default -> new Object[]{};
        };

        ErrorResponse errorResponse = getErrorResponse(getSimpleName(ex), args);

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<ErrorResponse> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse::fromFieldError)
                .collect(Collectors.toCollection(LinkedList::new));
        
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}

