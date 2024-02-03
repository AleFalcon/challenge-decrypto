package com.challenge.decrypto.application.exception;

public class ElementExisteException extends RuntimeException{
    public ElementExisteException(String message){
        super(message);
    }
}