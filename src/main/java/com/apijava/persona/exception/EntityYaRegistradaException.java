package com.apijava.persona.exception;

public class EntityYaRegistradaException extends RuntimeException {

    public EntityYaRegistradaException() {
    }

    public EntityYaRegistradaException(String message) {
        super(message);
    }

    public EntityYaRegistradaException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityYaRegistradaException(Throwable cause) {
        super(cause);
    }

    public EntityYaRegistradaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
