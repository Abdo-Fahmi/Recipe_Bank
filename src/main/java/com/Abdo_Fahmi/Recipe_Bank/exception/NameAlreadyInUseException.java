package com.Abdo_Fahmi.Recipe_Bank.exception;

public class NameAlreadyInUseException extends RuntimeException{
    public NameAlreadyInUseException(String message) {
        super(message);
    }
}
