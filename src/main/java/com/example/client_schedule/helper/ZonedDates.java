package com.example.client_schedule.helper;

import java.time.ZonedDateTime;

public class ZonedDates {
    public ZonedDates(ZonedDateTime start, ZonedDateTime end) {
        this.start = start;
        this.end = end;
    }

    public ZonedDateTime start;
    public ZonedDateTime end;
}
