package com.gk.portfolio.exceptions;

public class ExternalServiceUnavailableException extends RuntimeException {
    public ExternalServiceUnavailableException(String message) {
        super(message);
    }
}
