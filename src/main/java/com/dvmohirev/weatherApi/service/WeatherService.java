package com.dvmohirev.weatherApi.service;


import com.dvmohirev.weatherApi.entity.Weather;

import java.sql.Timestamp;
import java.util.List;

public interface WeatherService {

    void saveWeather(Weather weather);
    Weather getWeather(Timestamp date);
    List<Weather> getWeatherByCityAndDate(String city, Timestamp date);
    Weather findFirstWeatherByCityOrderByDateDesc(String city);
    Weather getWeatherByCity(String city);
}
