package com.dvmohirev.weatherApi.utils.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WeatherBitDto {
    private List<WeatherBitDataDto> data;

    public List<WeatherBitDataDto> getData() {
        return data;
    }

    public void setData(List<WeatherBitDataDto> data) {
        this.data = data;
    }

    public static class WeatherBitDataDto {
        @JsonProperty("city_name")
        private String cityName;
        @JsonProperty("ts")
        private Long ts;
        @JsonProperty("temp")
        private Integer temp;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public Long getTs() {
            return ts;
        }

        public void setTs(Long ts) {
            this.ts = ts;
        }

        public Integer getTemp() {
            return temp;
        }

        public void setTemp(Integer temp) {
            this.temp = temp;
        }
    }
}
