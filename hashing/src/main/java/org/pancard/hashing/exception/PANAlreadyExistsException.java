package org.pancard.hashing.exception;

public class PANAlreadyExistsException extends RuntimeException {

    public PANAlreadyExistsException(String message) {
        super(message);
    }
}
