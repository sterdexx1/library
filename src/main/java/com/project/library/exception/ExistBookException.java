package com.project.library.exception;

public class ExistBookException extends RuntimeException{
    public ExistBookException(String message) {
        super(message);
    }
}
