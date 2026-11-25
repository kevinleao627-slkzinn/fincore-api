package com.kevin.fincore.account.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
        super("Conta não encontrada.");
    }
}
