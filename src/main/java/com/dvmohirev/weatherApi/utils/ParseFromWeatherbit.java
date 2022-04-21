package com.dvmohirev.weatherApi.utils;

import com.dvmohirev.weatherApi.entity.Weather;
import com.dvmohirev.weatherApi.utils.dto.WeatherBitDto;
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
public class ParseFromWeatherbit {
    private final String weatherServiceName = "Weatherbit";
    private RestTemplate restTemplate = new RestTemplate();

    public ParseFromWeatherbit(){

    }
    public Weather goParse(String country, String city) throws JsonProcessingException {
        String URL = "https://api.weatherbit.io/v2.0/current?city=" +
                city + "&key=fe2446c083fc4cfc8c487f6a8b267ced";
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<WeatherBitDto> responseEntity = null;
        responseEntity = restTemplate.exchange(URL, HttpMethod.GET, new HttpEntity(headers), WeatherBitDto.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(responseEntity.getBody());
        System.out.println(weatherServiceName + " URL: " + URL);
        String cityName = responseEntity.getBody().getData().get(0).getCityName();
        Long time = responseEntity.getBody().getData().get(0).getTs();
        Integer temperature = responseEntity.getBody().getData().get(0).getTemp();

        return new Weather(cityName, new Timestamp(time * 1000L), temperature, weatherServiceName);
    }
}
