package com.fredfmelo.notificationservice.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class TechException extends RuntimeException {

    @NonNull
    private final HttpStatus status;

    public TechException(String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public TechException(String message, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public TechException(String message, @NonNull HttpStatus status) {
        super(message);
        this.status = status;
    }

    public TechException(String message, Throwable cause, @NonNull HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    @NonNull
    public HttpStatus getStatus() {
        return status;
    }
}
