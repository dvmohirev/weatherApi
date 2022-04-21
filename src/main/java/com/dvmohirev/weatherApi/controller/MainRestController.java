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
import org.yaml.snakeyaml.scanner.ScannerImpl;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
        //нужно где-то поставить парсинг погоды по сервисам
        Weather weatherDB = parseWeather();

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

    public Weather parseWeather() throws JsonProcessingException {
        Weather weatherYandex = null;
        Weather weatherOpenW = null;
        Weather weatherWeatherbit = null;
        Weather joinWeather = null;
        //для Яндекс
        weatherYandex = parseFromYandex.goParse(latitude, longitude);
        //для OpenW
        weatherOpenW = parseFromOpenWeather.goParse(latitude, longitude);
        //для Weatherbit
        weatherWeatherbit = parseFromWeatherbit.goParse(latitude, longitude);
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