package com.clone.instagram.authservice.web.rest.errors;


public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
