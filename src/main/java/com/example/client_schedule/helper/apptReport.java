package com.example.client_schedule.helper;

import java.time.LocalDateTime;
import java.time.Month;

public class apptReport {
    public apptReport(String type, Integer month, Integer count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }
    public apptReport(Integer month, String type, Integer count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

    public Integer month;
    public String type;
    public Integer count;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
