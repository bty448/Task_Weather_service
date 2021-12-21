package ru.fitisov.weather.service;

import org.springframework.stereotype.Service;
import ru.fitisov.weather.domain.Weather;
import ru.fitisov.weather.repository.WeatherRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class WeatherService {
    private final String dateFormat = "MM/dd/yy";
    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Weather getTodayWeather() {
        String date = new SimpleDateFormat(dateFormat).format(Calendar.getInstance().getTime());
        Weather weather = weatherRepository.findByDate(date);
        if (weather == null) {
            weather = new Weather();
            weather.setDate(date);
            weather.setWeather(getTodayWeatherFromYandex());
        }
        return weather;
    }

    private long getTodayWeatherFromYandex() {
        return 1337;
    }
}
