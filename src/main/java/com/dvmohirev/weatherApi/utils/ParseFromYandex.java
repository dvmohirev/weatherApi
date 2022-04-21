package com.dvmohirev.weatherApi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dvmohirev.weatherApi.entity.Weather;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ParseFromYandex {
    private final String weatherServiceName = "Yandex.Weather";
    private RestTemplate restTemplate = new RestTemplate();

    public ParseFromYandex(){

    }

    public List<String> doCityToCoordinate (String countryName, String cityNameAbsolute) throws JsonProcessingException {
        String URL = "https://api.openweathermap.org/data/2.5/weather?q=" +
                cityNameAbsolute +
                "&appid=4b25446d25e57adaf28a2b7816187c2e&units=metric";
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<String> responseEntity = null;
        responseEntity = restTemplate.exchange(URL, HttpMethod.GET, new HttpEntity(headers)
                , new ParameterizedTypeReference<String>() {
                });

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(responseEntity.getBody());
        Map weatherJsonObject = mapper.readValue(responseEntity.getBody(), Map.class);
        Map coordinate = (Map) weatherJsonObject.get("coord");
        String latitude = String.valueOf((Double) coordinate.get("lat"));
        String longtitude = String.valueOf((Double) coordinate.get("lon"));
        System.out.println(weatherServiceName + "coordinate for Yandex: " + "lon = " + longtitude + "; lat = " + latitude);

        List<String> listOfCoordinate = new ArrayList<>();
        listOfCoordinate.add(latitude);
        listOfCoordinate.add(longtitude);
        return listOfCoordinate;
    }


    public Weather goParse(String country, String city) throws JsonProcessingException {
        List<String> listOfCoordinate = doCityToCoordinate(country, city);
        String URL = "https://api.weather.yandex.ru/v2/forecast?lat=" +
                listOfCoordinate.get(0) + "&lon=" + listOfCoordinate.get(1);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Yandex-API-Key", "38a476dd-d429-47d9-8bb6-b7681f0cc5b7");

        ResponseEntity<String> responseEntity = null;
        responseEntity = restTemplate.exchange(URL, HttpMethod.GET, new HttpEntity(headers)
                , new ParameterizedTypeReference<String>() {
                });

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(responseEntity.getBody());
        Map weatherJsonObject = mapper.readValue(responseEntity.getBody(), Map.class);
        System.out.println(weatherServiceName + " URL: " + URL);

        Map factArray = (Map) weatherJsonObject.get("fact");
        Integer temperature = (Integer) factArray.get("temp");
        Integer time = (Integer) factArray.get("obs_time");
        Map geoObject = (Map) weatherJsonObject.get("geo_object");
        Map locality = (Map) geoObject.get("locality");
        String cityName = (String) locality.get("name");

        return new Weather(cityName, new Timestamp(time*1000L), temperature, weatherServiceName);
    }
}
