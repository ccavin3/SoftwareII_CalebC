package com.example.client_schedule.helper;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Properties;

public class AppConfig {

    public AppConfig() {
        try (InputStream in = AppConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load properties file", e);
        }
    }

    private static Properties properties = new Properties();

    public ZonedDates getZonedDateTime() {
        String shString = AppConfig.properties.getProperty("HoursOpen");
        String seString =  AppConfig.properties.getProperty("HoursClose");
        String zoneString = AppConfig.properties.getProperty("TimeZone");

        // Convert zoneString to ZoneId
        ZoneId zoneId = ZoneId.of(zoneString);

        // Convert hourString to LocalTime
        int sHour = Integer.parseInt(shString);
        int eHour = Integer.parseInt(seString);
        LocalTime localStartTime = LocalTime.of(sHour, 0);
        LocalTime localEndTime = LocalTime.of(eHour, 0);


        // Get the current date
        return new ZonedDates(ZonedDateTime.now(zoneId).with(localStartTime), ZonedDateTime.now(zoneId).with(localEndTime));
    }

    public static String getValue(String key) {
        return AppConfig.properties.getProperty(key);
    }
}