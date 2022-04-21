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
import java.util.Map;

@Component
public class ParseFromYandex {
    private final String weatherServiceName = "Yandex.Weather";
    private RestTemplate restTemplate = new RestTemplate();
    //private final String URL = "https://api.weather.yandex.ru/v2/forecast?lat=55.159897&lon=61.402554";


    public ParseFromYandex(){

    }
    //добавил параметры широта и долгота
    public Weather goParse(String latitude, String longitude) throws JsonProcessingException {
        String URL = "https://api.weather.yandex.ru/v2/forecast?lat=" +
                latitude + "&lon=" + longitude;
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Yandex-API-Key", "38a476dd-d429-47d9-8bb6-b7681f0cc5b7");

        ResponseEntity<String> responseEntity = null;
        responseEntity = restTemplate.exchange(URL, HttpMethod.GET, new HttpEntity(headers)
                , new ParameterizedTypeReference<String>() {
                });

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(responseEntity.getBody());
        Map weatherJsonObject = mapper.readValue(responseEntity.getBody(), Map.class);
        System.out.println(weatherServiceName + ": " + responseEntity.getBody());

        Map factArray = (Map) weatherJsonObject.get("fact");
        Integer temperature = (Integer) factArray.get("temp");
        Integer time = (Integer) factArray.get("obs_time");
        Map geoObject = (Map) weatherJsonObject.get("geo_object");
        Map locality = (Map) geoObject.get("locality");
        String city = (String) locality.get("name");

        return new Weather(city, new Timestamp(time*1000L), temperature, weatherServiceName);
    }
}
