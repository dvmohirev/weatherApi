package com.dvmohirev.weatherApi.service.weatherSchedul;

import com.dvmohirev.weatherApi.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class WeatherScheduler {
    @Autowired
    WeatherService weatherService;

    @Value("${country.city.settings}")
    //private String[] countryCitySettings;
    private List<String> countryCitySettings;

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void testScheduling() throws JsonProcessingException {
        if(countryCitySettings.size() % 2 != 0) {
            System.out.println("Введите четное значение стран");
        } else {
            weatherService.parseWeather(countryCitySettings);
            System.out.println("Прогноз погоды получен от трех сервисов");
        }


    }
}
