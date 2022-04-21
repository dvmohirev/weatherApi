package com.dvmohirev.weatherApi.dao;

import com.dvmohirev.weatherApi.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Integer> {
    Weather findWeatherByDate(Timestamp date);
    List<Weather> findWeatherByCityAndDate(String city, Timestamp date);
    Weather findFirstWeatherByCityOrderByDateDesc(String city);
    Weather findWeatherByCity(String city);
}
