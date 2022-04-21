package com.dvmohirev.weatherApi.utils;

import com.dvmohirev.weatherApi.entity.Weather;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Map;

@Component
public class ParseFromOpenWeather {
    private final String weatherServiceName = "OpenWeather";
    private RestTemplate restTemplate = new RestTemplate();
    //private final String URL = "https://api.openweathermap.org/data/2.5/weather?lat=55.75396&lon=37.620393&appid=4b25446d25e57adaf28a2b7816187c2e&units=metric";

    public ParseFromOpenWeather(){

    }
    //добавил параметры широта и долгота
    public Weather goParse(String country, String city) throws JsonProcessingException {
        String URL = "https://api.openweathermap.org/data/2.5/weather?q=" +
                city +
                "&appid=4b25446d25e57adaf28a2b7816187c2e&units=metric";
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<String> responseEntity = null;
        responseEntity = restTemplate.exchange(URL, HttpMethod.GET, new HttpEntity(headers)
                , new ParameterizedTypeReference<String>() {
                });

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(responseEntity.getBody());
        Map weatherJsonObject = mapper.readValue(responseEntity.getBody(), Map.class);
        System.out.println(weatherServiceName + " URL: " + URL);

        String cityName = (String) weatherJsonObject.get("name");
        Integer time = (Integer) weatherJsonObject.get("dt");
        Map mainInfo = (Map) weatherJsonObject.get("main");
        Integer temperature = ((Double) mainInfo.get("temp")).intValue();

        return new Weather(cityName, new Timestamp(time * 1000L), temperature, weatherServiceName);
    }
}
