package ru.fitisov.weather.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.fitisov.weather.domain.Weather;
import ru.fitisov.weather.exception.HttpRequestException;
import ru.fitisov.weather.repository.WeatherRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
            try {
                long temperature = getTodayWeatherTemperatureFromYandex();
                weather = new Weather();
                weather.setDate(date);
                weather.setTemperature(temperature);
                //weatherRepository.save(weather);
            } catch (IOException e) {
                //todo: handle
            } catch (HttpRequestException e) {
                //todo: handle
            } catch (NumberFormatException e) {
                //todo: handle
            }
        }
        return weather;
    }

    private long getTodayWeatherTemperatureFromYandex() throws IOException, HttpRequestException, NumberFormatException {
        URL yandexUrl = new URL("https://yandex.ru/pogoda/saint-petersburg");
        HttpURLConnection connection = (HttpURLConnection) yandexUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");
        connection.setInstanceFollowRedirects(true);
        int status = connection.getResponseCode();
        if (status != 200) {
            throw new HttpRequestException("Response status code is not 200");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = reader.readLine();
        StringBuilder content = new StringBuilder();
        while (line != null) {
            content.append(line);
            line = reader.readLine();
        }
        reader.close();
        String tag = "<span class=\"temp__value temp__value_with-unit\">";
        content.indexOf(tag);
        StringBuilder temperatureString = new StringBuilder();
        for (int i = content.indexOf(tag) + tag.length(); content.charAt(i) != '<'; i++) {
            temperatureString.append(content.charAt(i));
        }
        return Long.parseLong(temperatureString.toString());
    }
}
