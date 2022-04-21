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
public class ParseFromAccuWeather {
    //написал модуль, а на форумах пишут, что АПИ перестал работать неделю назад
    private final String weatherServiceName = "AccuWeather";
    private RestTemplate restTemplate = new RestTemplate();
    private final String urlForFindLocation = "http://api.accuweather.com/locations/v1/geoposition/search?q=51.50628,0.13025&apikey=ad3vl8JyhRzKwrxSQrzKrRqjCsF49Npu";

    public ParseFromAccuWeather(){

    }

    /*public Map<String, Object> findLocationFromAccuWeather() throws JsonProcessingException{
        Map<String, Object> mapAccuWeather = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();


        ResponseEntity<String> responseEntity = null;
        responseEntity = restTemplate.exchange(urlForFindLocation, HttpMethod.GET, new HttpEntity(headers)
                , new ParameterizedTypeReference<String>() {
                });

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(weatherServiceName + ": " + responseEntity.getBody());
        Map weatherJsonObject = mapper.readValue(responseEntity.getBody(), Map.class);
        mapAccuWeather.put("Key", weatherJsonObject.get("Key"));
        mapAccuWeather.put("Time", weatherJsonObject.get("EpochTime"));
        mapAccuWeather.put("City", weatherJsonObject.get("LocalizedName"));


        return mapAccuWeather;
    }*/

    public Weather goParse() throws JsonProcessingException {
        //Map<String, Object> tempMap = findLocationFromAccuWeather();
        /*String urlForWeather = "http://api.accuweather.com/locations/v1/" +
                //(String) tempMap.get("Key") +
                2532627 +
                ".json?apikey=ad3vl8JyhRzKwrxSQrzKrRqjCsF49Npu";*/
        String urlForWeather = "http://apidev.accuweather.com/currentconditions/v1/2532627.json?apikey=ad3vl8JyhRzKwrxSQrzKrRqjCsF49Npu&details=false";
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<String> responseEntity = null;
        responseEntity = restTemplate.exchange(urlForWeather, HttpMethod.GET, new HttpEntity(headers)
                , new ParameterizedTypeReference<String>() {
                });

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(responseEntity.getBody());
        System.out.println(weatherServiceName + ": " + responseEntity.getBody());
        Map weatherJsonObject = mapper.readValue(responseEntity.getBody(), Map.class);


        /*String city = (String) tempMap.get("City");
        Integer time = Integer.parseInt((String) tempMap.get("Time"));*/
        String city = "London";
        Integer time = 1650470564;
        Map temperatureInfo = (Map) weatherJsonObject.get("Temperature");
        Map metricTemperatureInfo = (Map) temperatureInfo.get("Metric");
        Integer temperature = ((Double) metricTemperatureInfo.get("Value")).intValue();

        return new Weather(city, new Timestamp(time * 1000L), temperature, weatherServiceName);
    }
}
