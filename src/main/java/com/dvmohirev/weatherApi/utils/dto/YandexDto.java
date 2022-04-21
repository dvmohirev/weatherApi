package com.dvmohirev.weatherApi.utils.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YandexDto {
    @JsonProperty("fact")
    private YandexFactDto yandexFactDto;
    @JsonProperty("geo_object")
    private YandexGeoObjectDto yandexGeoObjectDto;

    public YandexFactDto getYandexFactDto() {
        return yandexFactDto;
    }

    public void setYandexFactDto(YandexFactDto yandexFactDto) {
        this.yandexFactDto = yandexFactDto;
    }

    public YandexGeoObjectDto getYandexGeoObjectDto() {
        return yandexGeoObjectDto;
    }

    public void setYandexGeoObjectDto(YandexGeoObjectDto yandexGeoObjectDto) {
        this.yandexGeoObjectDto = yandexGeoObjectDto;
    }

    public static class YandexFactDto{
        @JsonProperty("temp")
        private Integer temperature;

        @JsonProperty("obs_time")
        private Long time;

        public Integer getTemperature() {
            return temperature;
        }

        public void setTemperature(Integer temperature) {
            this.temperature = temperature;
        }

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }
    }

    public static class YandexGeoObjectDto{
        @JsonProperty("locality")
        private YandexLocalityDto yandexLocalityDto;

        public YandexLocalityDto getYandexLocalityDto() {
            return yandexLocalityDto;
        }

        public void setYandexLocalityDto(YandexLocalityDto yandexLocalityDto) {
            this.yandexLocalityDto = yandexLocalityDto;
        }

        public static class YandexLocalityDto{
            @JsonProperty("name")
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
