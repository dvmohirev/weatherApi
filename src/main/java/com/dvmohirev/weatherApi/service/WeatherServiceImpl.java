package com.dvmohirev.weatherApi.service;

import com.dvmohirev.weatherApi.dao.WeatherRepository;
import com.dvmohirev.weatherApi.entity.Weather;
import com.dvmohirev.weatherApi.utils.ParseFromOpenWeather;
import com.dvmohirev.weatherApi.utils.ParseFromWeatherbit;
import com.dvmohirev.weatherApi.utils.ParseFromYandex;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService{

    @Autowired
    ParseFromYandex parseFromYandex;

    @Autowired
    ParseFromOpenWeather parseFromOpenWeather;

    @Autowired
    ParseFromWeatherbit parseFromWeatherbit;

    @Autowired
    private WeatherRepository weatherRepository;

    @Override
    public void saveWeather(Weather weather) {
        weatherRepository.save(weather);
    }

    @Override
    public Weather findFirstWeatherByCityOrderByDateDesc(String city) {
        Weather weather = weatherRepository.findFirstWeatherByCityOrderByDateDesc(city);
        return weather;
    }

    @Override
    public List<Weather> getWeatherByCityAndDate(String city, Timestamp date) {
        List<Weather> weather = weatherRepository.findWeatherByCityAndDate(city, date);
        return weather;
    }

    @Override
    public Weather parseWeather(List<String> countryCitySettings) throws JsonProcessingException {
        List<String> listCountryCity = countryCitySettings;
        Weather weatherYandex = null;
        Weather weatherOpenW = null;
        Weather weatherWeatherbit = null;
        Weather joinWeather = null;
        for (int i = 1; i < listCountryCity.size(); i=i+2) {
            weatherYandex = parseFromYandex.goParse(listCountryCity.get(i-1), listCountryCity.get(i));
            weatherOpenW = parseFromOpenWeather.goParse(listCountryCity.get(i-1), listCountryCity.get(i));
            weatherWeatherbit = parseFromWeatherbit.goParse(listCountryCity.get(i-1), listCountryCity.get(i));
            joinWeather = new Weather(
                    weatherOpenW.getCity(),
                    weatherYandex.getDate(),
                    (weatherYandex.getTemperature() +
                            weatherOpenW.getTemperature() +
                            weatherWeatherbit.getTemperature())/3,
                    weatherYandex.getWeatherServiceName() +
                            "&" + weatherOpenW.getWeatherServiceName() +
                            "&" + weatherWeatherbit.getWeatherServiceName()
            );
            saveWeather(joinWeather);
        }
        return joinWeather;
    }
}
