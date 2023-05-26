package com.example.example.exception;

public class EntityNotReadyException extends RuntimeException{
    public EntityNotReadyException(String message) {
        super(message);
    }
}
