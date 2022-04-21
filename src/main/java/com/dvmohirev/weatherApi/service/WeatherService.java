package com.dvmohirev.weatherApi.service;


import com.dvmohirev.weatherApi.entity.Weather;

import java.sql.Timestamp;

public interface WeatherService {

    public void saveWeather(Weather weather);
    public Weather getWeather(Timestamp date);
    public Weather getWeatherByCityAndDate(String city, Timestamp date);
    public Weather getWeatherByCity(String city);
}
