package com.nisum.user.domain.exception;

import lombok.Getter;

@Getter
public sealed class UserException extends RuntimeException{

    private UserException(String message) {
        super(message);
    }

    public static final class NotFound extends UserException {
        
        public NotFound() {
            super("");
        }

    }

    public static final class EmailAlreadyExists extends UserException {
        
        public EmailAlreadyExists() {
            super("");
        }

    }
}
