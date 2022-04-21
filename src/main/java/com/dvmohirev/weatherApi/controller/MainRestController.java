package com.dvmohirev.weatherApi.controller;

import com.dvmohirev.weatherApi.entity.Weather;
import com.dvmohirev.weatherApi.service.WeatherService;
import com.dvmohirev.weatherApi.utils.ParseFromYandex;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.dvmohirev.weatherApi.utils.ParseFromOpenWeather;
import com.dvmohirev.weatherApi.utils.ParseFromWeatherbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@RestController
public class MainRestController {

    @Autowired
    WeatherService weatherService;

    @GetMapping("/weather")
    public List<Weather> getWeather(
            @RequestParam(value="city") String city,
            @RequestParam(value="date", required = false) String date) throws JsonProcessingException {

        Timestamp timestamp = null;
        if (!date.isEmpty()){
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = dateFormat.parse(date);
                timestamp = new Timestamp(parsedDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
                throw new RuntimeException("Введена не корректная дата");
            }
        }

        if (timestamp == null){
            Weather weatherNow = weatherService.findFirstWeatherByCityOrderByDateDesc(city);
            List<Weather> weatherList = new ArrayList<>();
            weatherList.add(weatherNow);
            return weatherList;
        } else {
            List<Weather> weatherChoose = weatherService.getWeatherByCityAndDate(city, timestamp);
            return weatherChoose;
        }
    }
}