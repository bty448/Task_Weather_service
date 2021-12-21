package ru.fitisov.weather.domain;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "date"))
public class Weather {
    @Id
    private String date;

    private long weather;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getWeather() {
        return weather;
    }

    public void setWeather(long weather) {
        this.weather = weather;
    }
}
