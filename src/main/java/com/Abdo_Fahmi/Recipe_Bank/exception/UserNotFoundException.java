package com.Abdo_Fahmi.Recipe_Bank.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
