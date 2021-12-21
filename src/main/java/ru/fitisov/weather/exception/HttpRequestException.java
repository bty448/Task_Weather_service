package ru.fitisov.weather.exception;

public class HttpRequestException extends Exception {
    private String message;

    public HttpRequestException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
