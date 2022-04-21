package com.dvmohirev.weatherApi.utils;

import com.dvmohirev.weatherApi.utils.dto.OpenWeatherDto;
import com.dvmohirev.weatherApi.utils.dto.YandexDto;
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
                cityNameAbsolute + "&appid=4b25446d25e57adaf28a2b7816187c2e&units=metric";
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<OpenWeatherDto> responseEntity = null;
        responseEntity = restTemplate.exchange(URL, HttpMethod.GET, new HttpEntity(headers), OpenWeatherDto.class);

        String latitude = String.valueOf(responseEntity.getBody().getOpenWeatherCoordDto().getLatitude());
        String longtitude = String.valueOf(responseEntity.getBody().getOpenWeatherCoordDto().getLontitude());
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

        ResponseEntity<YandexDto> responseEntity = null;
        responseEntity = restTemplate.exchange(URL, HttpMethod.GET, new HttpEntity(headers), YandexDto.class);
        System.out.println(weatherServiceName + " URL: " + URL);

        String cityName = responseEntity.getBody().getYandexGeoObjectDto().getYandexLocalityDto().getName();
        Integer temperature = responseEntity.getBody().getYandexFactDto().getTemperature();
        Long time = responseEntity.getBody().getYandexFactDto().getTime();

        return new Weather(cityName, new Timestamp(time*1000L), temperature, weatherServiceName);
    }
}
