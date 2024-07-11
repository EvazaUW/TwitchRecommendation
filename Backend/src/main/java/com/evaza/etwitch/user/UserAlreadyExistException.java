package com.evaza.etwitch.user;

public class UserAlreadyExistException extends Exception {
    UserAlreadyExistException(String message) {
        super(message);
    }
}
