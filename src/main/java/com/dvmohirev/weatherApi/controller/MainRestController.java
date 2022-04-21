package com.dvmohirev.weatherApi.controller;

import com.dvmohirev.weatherApi.entity.Weather;
import com.dvmohirev.weatherApi.service.WeatherService;
import com.dvmohirev.weatherApi.utils.ParseFromYandex;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dvmohirev.weatherApi.utils.ParseFromOpenWeather;
import com.dvmohirev.weatherApi.utils.ParseFromWeatherbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

@RestController
public class MainRestController {
    //добавил переменные Широта и долгота для передачи в метод goParse в парсеры
    //не забыть, что для каждого парсера нужно обработать URL по-своему
    private final long currentTime = new GregorianCalendar().getTimeInMillis();
    private final String latitude = "55.159897"; // Челябинск - Широта
    private final String longitude = "61.402554";// Челябинск - Долгота
    @Autowired
    ParseFromYandex parseFromYandex;

    @Autowired
    ParseFromOpenWeather parseFromOpenWeather;

    @Autowired
    ParseFromWeatherbit parseFromWeatherbit;

    @Autowired
    WeatherService weatherService;

    @GetMapping("/weather")
    public String getWeather(
            //добавил два параметра для того, чтобы выводить данные по условию
            @RequestParam(value="city", required=true) String city,
            @RequestParam(value="date", required = false) Timestamp date) throws JsonProcessingException {
        GregorianCalendar currentTime = new GregorianCalendar();

        Weather weatherDB = parseWeather();
        Timestamp lastDate = weatherDB.getDate();

        if (date == null){
            Weather weatherNow = weatherService.getWeatherByCityAndDate(city, lastDate);
            System.out.println("RESULT-LASTTIME: " + weatherNow.toString());
            ObjectMapper mapper = new ObjectMapper();
            String w = "RESULT-LASTTIME: " + weatherNow.toString();
            return w;
        } else {
            Weather weatherChoose = weatherService.getWeatherByCityAndDate(city, date);
            System.out.println("RESULT-CHOOSE: " + weatherChoose.toString());
            ObjectMapper mapper = new ObjectMapper();
            String n = "RESULT-CHOOSE: " + weatherChoose.toString();
            return n;
        }
        //return "RESULT-LASTTIME: " + weatherDB.toString();


        /*Weather weatherDB = weatherService.getWeather(currentTimestamp);*/
        /*Weather weatherYandex = null;
        Weather weatherOpenW = null;
        Weather weatherWeatherbit = null;
        Weather joinWeather = null;*/
        //if (weatherDB == null) {
            /*для Яндекс
            Передаю в метод goParse широту и долготу для определения погоды по одному городу*//*
            weatherYandex = parseFromYandex.goParse(latitude, longitude);
            *//*Пока скрываем, т.к. в базу должен вноситься только один объект
            weatherYandex = weatherDB.toString();
            weatherService.saveWeather(weatherDB);*//*
            *//*для OpenW
            Передаю в метод goParse широту и долготу для определения погоды по одному городу*//*
            weatherOpenW = parseFromOpenWeather.goParse(latitude, longitude);
            *//*Пока скрываем, т.к. в базу должен вноситься только один объект
            weatherOpenW = weatherDB.toString();
            weatherService.saveWeather(weatherDB);*//*
            *//*для Weatherbit
            Передаю в метод goParse широту и долготу для определения погоды по одному городу*//*
            weatherWeatherbit = parseFromWeatherbit.goParse(latitude, longitude);
            *//*Пока скрываем, т.к. в базу должен вноситься только один объект
            weatherWeatherbit = weatherDB.toString();
            weatherService.saveWeather(weatherDB);*//*
            *//*Теперь нужно взять данные из 3-х сервисов по одному городу и усреднить.
            После этого записать из в БД*//*
            joinWeather = new Weather(
                    weatherYandex.getCity(),
                    weatherYandex.getDate(),
                    (weatherYandex.getTemperature() +
                            weatherOpenW.getTemperature() +
                            weatherWeatherbit.getTemperature())/3,
                    weatherYandex.getWeatherServiceName() +
                            "&" + weatherOpenW.getWeatherServiceName() +
                            "&" + weatherWeatherbit.getWeatherServiceName()
            );
            weatherService.saveWeather(joinWeather);*/
        //}

        //return weatherService.getWeather();
    }

    public Weather parseWeather() throws JsonProcessingException {
        Weather weatherYandex = null;
        Weather weatherOpenW = null;
        Weather weatherWeatherbit = null;
        Weather joinWeather = null;
        /*для Яндекс
            Передаю в метод goParse широту и долготу для определения погоды по одному городу*/
        weatherYandex = parseFromYandex.goParse(latitude, longitude);
            /*Пока скрываем, т.к. в базу должен вноситься только один объект
            weatherYandex = weatherDB.toString();
            weatherService.saveWeather(weatherDB);*/
            /*для OpenW
            Передаю в метод goParse широту и долготу для определения погоды по одному городу*/
        weatherOpenW = parseFromOpenWeather.goParse(latitude, longitude);
            /*Пока скрываем, т.к. в базу должен вноситься только один объект
            weatherOpenW = weatherDB.toString();
            weatherService.saveWeather(weatherDB);*/
            /*для Weatherbit
            Передаю в метод goParse широту и долготу для определения погоды по одному городу*/
        weatherWeatherbit = parseFromWeatherbit.goParse(latitude, longitude);
            /*Пока скрываем, т.к. в базу должен вноситься только один объект
            weatherWeatherbit = weatherDB.toString();
            weatherService.saveWeather(weatherDB);*/
            /*Теперь нужно взять данные из 3-х сервисов по одному городу и усреднить.
            После этого записать из в БД*/
        joinWeather = new Weather(
                weatherYandex.getCity(),
                weatherYandex.getDate(),
                (weatherYandex.getTemperature() +
                        weatherOpenW.getTemperature() +
                        weatherWeatherbit.getTemperature())/3,
                weatherYandex.getWeatherServiceName() +
                        "&" + weatherOpenW.getWeatherServiceName() +
                        "&" + weatherWeatherbit.getWeatherServiceName()
        );
        weatherService.saveWeather(joinWeather);
        return joinWeather;
    }
}