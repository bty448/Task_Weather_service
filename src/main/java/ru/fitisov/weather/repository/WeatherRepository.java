package ru.fitisov.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fitisov.weather.domain.Weather;

public interface WeatherRepository extends JpaRepository<Weather, String> {
    Weather findByDate(String date);
}
