package com.dvmohirev.weatherApi.utils.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenWeatherDto {
    @JsonProperty("coord")
    private OpenWeatherCoordDto openWeatherCoordDto;

    public OpenWeatherCoordDto getOpenWeatherCoordDto() {
        return openWeatherCoordDto;
    }

    public void setOpenWeatherCoordDto(OpenWeatherCoordDto openWeatherCoordDto) {
        this.openWeatherCoordDto = openWeatherCoordDto;
    }

    public OpenWeatherMainDto getOpenWeatherMainDto() {
        return openWeatherMainDto;
    }

    public void setOpenWeatherMainDto(OpenWeatherMainDto openWeatherMainDto) {
        this.openWeatherMainDto = openWeatherMainDto;
    }

    @JsonProperty("main")
    private OpenWeatherMainDto openWeatherMainDto;

    @JsonProperty("dt")
    private Long dt;

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("name")
    private String name;

    public static class OpenWeatherCoordDto{
        @JsonProperty("lon")
        private Double lontitude;

        @JsonProperty("lat")
        private Double latitude;

        public Double getLontitude() {
            return lontitude;
        }

        public void setLontitude(Double lontitude) {
            this.lontitude = lontitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }
    }

    public static class OpenWeatherMainDto{
        @JsonProperty("temp")
        private Integer temperature;

        public Integer getTemperature() {
            return temperature;
        }

        public void setTemperature(Integer temperature) {
            this.temperature = temperature;
        }
    }
}
