package ru.fitisov.weather.exception;

public class WeatherFetchException extends Exception {
    private String message;

    public WeatherFetchException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
