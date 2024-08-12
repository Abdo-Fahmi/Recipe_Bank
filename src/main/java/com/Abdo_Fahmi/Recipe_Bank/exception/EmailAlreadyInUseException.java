package com.Abdo_Fahmi.Recipe_Bank.exception;

public class EmailAlreadyInUseException extends RuntimeException{
    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}
