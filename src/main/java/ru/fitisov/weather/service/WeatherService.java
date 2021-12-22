package ru.fitisov.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fitisov.weather.domain.Weather;
import ru.fitisov.weather.domain.YandexWeatherFetcher;
import ru.fitisov.weather.exception.WeatherFetchException;
import ru.fitisov.weather.repository.WeatherRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class WeatherService {
    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private YandexWeatherFetcher yandexWeatherFetcher;

    public Weather getTodayWeather() throws WeatherFetchException {
        String date = new SimpleDateFormat("MM/dd/yy").format(Calendar.getInstance().getTime());
        Weather weather = weatherRepository.findByDate(date);
        if (weather == null) {
            long temperature = yandexWeatherFetcher.getTodayWeatherTemperature();
            weather = new Weather();
            weather.setDate(date);
            weather.setTemperature(temperature);
            weatherRepository.save(weather);
        }
        return weather;
    }
}
