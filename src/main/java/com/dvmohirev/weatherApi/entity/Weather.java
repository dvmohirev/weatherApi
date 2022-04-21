package com.dvmohirev.weatherApi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "weather_history", schema = "public")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "weather_city")
    private String city;

    @Column(name = "weather_date")
    private Timestamp date;

    @Column(name = "weather_temp")
    private int temperature;

    @Column(name = "weather_service")
    private String weatherServiceName;

    public Weather(){

    }

    public Weather(String city, Timestamp date, int temperature, String weatherServiceName) {
        this.city = city;
        this.date = date;
        this.temperature = temperature;
        this.weatherServiceName = weatherServiceName;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", city=" + city +
                ", date=" + date +
                ", temperature=" + temperature +
                ", weatherServiceName=" + weatherServiceName +
                '}';
    }
}
