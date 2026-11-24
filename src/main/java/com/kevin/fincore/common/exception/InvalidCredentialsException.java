package com.kevin.fincore.common.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Email ou senha inválidos.");
    }
    
}
