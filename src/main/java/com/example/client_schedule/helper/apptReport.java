package com.example.client_schedule.helper;

import java.time.LocalDateTime;
import java.time.Month;

public class apptReport {
    /**
     * Constructor for apptReport class setting type, month and count
     *
     * @param type  String, type of the report
     * @param month Integer, month of the report
     * @param count Integer, count of the report
     */
    public apptReport(String type, Integer month, Integer count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

    /**
     * Constructor for apptReport class setting month, type and count
     *
     * @param month Integer, month of the report
     * @param type  String, type of the report
     * @param count Integer, count of the report
     */
    public apptReport(Integer month, String type, Integer count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

    public Integer month;
    public String type;
    public Integer count;

    /**
     * Getter method for month
     *
     * @return Integer, month of the report
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * Setter method for month
     *
     * @param month Integer, month of the report
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * Getter method for type
     *
     * @return String, type of the report
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method for type
     *
     * @param type String, type of the report
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter method for count
     *
     * @return Integer, count of the report
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Setter method for count
     *
     * @param count Integer, count of the report
     */
    public void setCount(Integer count) {
        this.count = count;
    }
}
