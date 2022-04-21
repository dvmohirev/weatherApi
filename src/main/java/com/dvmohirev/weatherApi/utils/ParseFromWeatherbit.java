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
import java.util.Arrays;
import java.util.Map;

@Component
public class ParseFromWeatherbit {
    private final String weatherServiceName = "Weatherbit";
    private RestTemplate restTemplate = new RestTemplate();
    //private final String URL = "https://api.weatherbit.io/v2.0/current?lat=51.50628&lon=0.13025&key=fe2446c083fc4cfc8c487f6a8b267ced";


    public ParseFromWeatherbit(){

    }
    //добавил параметры широта и долгота
    public Weather goParse(String latitude, String longitude) throws JsonProcessingException {
        String URL = "https://api.weatherbit.io/v2.0/current?lat=" +
                latitude + "&lon=" + longitude +
                "&key=fe2446c083fc4cfc8c487f6a8b267ced";
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<String> responseEntity = null;
        responseEntity = restTemplate.exchange(URL, HttpMethod.GET, new HttpEntity(headers)
                , new ParameterizedTypeReference<String>() {
                });

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(responseEntity.getBody());
        System.out.println(weatherServiceName + ": " + responseEntity.getBody());
        Map weatherJsonObject = mapper.readValue(responseEntity.getBody(), Map.class); //получаем DATA и массив
        String sTemp = weatherJsonObject.get("data").toString();
        String str = sTemp.substring(sTemp.indexOf("{") + 1, sTemp.lastIndexOf("}"));
        String[] sMass = str.split(",");
        String strCity = Arrays.stream(sMass)
                .filter(s -> s.contains("city_name"))
                .findAny().get();
        String strTime = Arrays.stream(sMass)
                .filter(s -> s.contains("ts"))
                .findAny().get();
        String strTemperature = Arrays.stream(sMass)
                .filter(s -> s.contains("temp"))
                .findFirst().get();
        String city = strCity.substring(strCity.indexOf("=")+1);
        Integer time = Integer.parseInt(strTime.substring(strTime.indexOf("=")+1));
        Double d = Double.parseDouble(strTemperature.substring(strTemperature.indexOf("=")+1));
        Integer temperature = d.intValue();

        return new Weather(city, new Timestamp(time * 1000L), temperature, weatherServiceName);
    }
}
