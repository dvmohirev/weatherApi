package com.dvmohirev.weatherApi.service;

import com.dvmohirev.weatherApi.dao.WeatherRepository;
import com.dvmohirev.weatherApi.entity.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class WeatherServiceImpl implements WeatherService{

    @Autowired
    private WeatherRepository weatherRepository;

    @Override
    public void saveWeather(Weather weather) {
        weatherRepository.save(weather);
    }

    @Override
    public Weather getWeather(Timestamp date){
        Weather weather = weatherRepository.findWeatherByDate(date);
        return weather;
    }

    @Override
    public Weather getWeatherByCityAndDate(String city, Timestamp date) {
        Weather weather = weatherRepository.findWeatherByCityAndDate(city, date);
        return weather;
    }

    @Override
    public Weather getWeatherByCity(String city) {
        Weather weather = weatherRepository.findWeatherByCity(city);
        return weather;
    }
}
