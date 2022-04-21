package com.dvmohirev.weatherApi.dao;


import com.dvmohirev.weatherApi.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {
    public Weather findWeatherByDate(Timestamp date);
    public Weather findWeatherByCityAndDate(String city, Timestamp date);
    public Weather findWeatherByCity(String city);
}
