package ru.fitisov.weather.domain;


import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.fitisov.weather.exception.WeatherFetchException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Component
@RequestScope
public class YandexWeatherFetcher {
    public long getTodayWeatherTemperature() throws WeatherFetchException {
        try {
            URL yandexUrl = new URL("https://yandex.ru/pogoda/saint-petersburg");
            HttpURLConnection connection = (HttpURLConnection) yandexUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");
            connection.setInstanceFollowRedirects(true);
            int status = connection.getResponseCode();
            if (status != 200) {
                throw new WeatherFetchException("Response status code is not 200");
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
            StringBuilder temperatureString = new StringBuilder();
            for (int i = content.indexOf(tag) + tag.length(); content.charAt(i) != '<'; i++) {
                temperatureString.append(content.charAt(i));
            }
            if (!Character.isDigit(temperatureString.charAt(0))) {
                temperatureString.setCharAt(0, '-');
            }
            return Long.parseLong(temperatureString.toString());
        } catch (NumberFormatException e) {
            throw new WeatherFetchException("Temperature value couldn't be parsed to long");
        } catch (MalformedURLException e) {
            throw new WeatherFetchException("Bad URL formed while fetching weather");
        } catch (IOException e) {
            throw new WeatherFetchException("Error while reading yandex.ru response");
        }
    }
}
