package com.dvmohirev.weatherApi.utils;

import com.dvmohirev.weatherApi.entity.Weather;
import com.dvmohirev.weatherApi.utils.dto.OpenWeatherDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

@Component
public class ParseFromOpenWeather {
    private final String weatherServiceName = "OpenWeather";
    private RestTemplate restTemplate = new RestTemplate();

    public ParseFromOpenWeather(){

    }
    public Weather goParse(String country, String city) throws JsonProcessingException {
        String URL = "https://api.openweathermap.org/data/2.5/weather?q=" +
                city + "&appid=4b25446d25e57adaf28a2b7816187c2e&units=metric";
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<OpenWeatherDto> responseEntity = null;
        responseEntity = restTemplate.exchange(URL, HttpMethod.GET, new HttpEntity(headers)
                , OpenWeatherDto.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(responseEntity.getBody());
        System.out.println(weatherServiceName + " URL: " + URL);

        String cityName = responseEntity.getBody().getName();
        Long time = responseEntity.getBody().getDt();
        Integer temperature = responseEntity.getBody().getOpenWeatherMainDto().getTemperature();

        return new Weather(cityName, new Timestamp(time * 1000L), temperature, weatherServiceName);
    }
}
