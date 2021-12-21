package ru.fitisov.weather.domain;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "date"))
public class Weather {
    @Id
    private String date;

    private long temperature;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTemperature() {
        return temperature;
    }

    public void setTemperature(long weather) {
        this.temperature = weather;
    }
}
