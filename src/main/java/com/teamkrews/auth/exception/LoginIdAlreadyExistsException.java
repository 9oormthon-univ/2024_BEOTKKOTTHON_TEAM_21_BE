package com.teamkrews.auth.exception;

public class LoginIdAlreadyExistsException extends RuntimeException{
    public LoginIdAlreadyExistsException(String message) {
        super(message);
    }
}
