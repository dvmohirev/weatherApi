package com.dvmohirev.weatherApi.service.weatherSchedul;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class WeatherScheduler {
    @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.SECONDS)
    public void testScheduling() {
        //System.out.println("loh");
    }
}
