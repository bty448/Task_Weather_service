package ru.fitisov.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fitisov.weather.domain.Weather;
import ru.fitisov.weather.exception.WeatherFetchException;
import ru.fitisov.weather.service.WeatherService;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public Weather getWeather() throws WeatherFetchException {
        return weatherService.getTodayWeather();
    }
}
