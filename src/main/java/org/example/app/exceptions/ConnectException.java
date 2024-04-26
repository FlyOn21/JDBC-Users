package org.example.app.exceptions;

public class ConnectException extends RuntimeException {
    public ConnectException(String msg) {
            super(msg);
        }
}
