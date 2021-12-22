package ru.fitisov.weather.controller.exception_handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.fitisov.weather.exception.WeatherFetchException;

@RestControllerAdvice
public class RestControllerExceptionHandler {
    @ExceptionHandler(WeatherFetchException.class)
    public ResponseEntity<String> onWeatherFetchException(WeatherFetchException e) {
        String errorMessage = e.getMessage();
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
