package com.dvmohirev.weatherApi.service;


import com.dvmohirev.weatherApi.entity.Weather;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.Timestamp;
import java.util.List;

public interface WeatherService {

    void saveWeather(Weather weather);
    List<Weather> getWeatherByCityAndDate(String city, Timestamp date);
    Weather findFirstWeatherByCityOrderByDateDesc(String city);
    Weather parseWeather(List<String> countryCitySettings) throws JsonProcessingException;
}
