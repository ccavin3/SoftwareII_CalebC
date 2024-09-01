package com.example.client_schedule.controller;

import com.example.client_schedule.helper.DBContext;

public class ReportsController {
    DBContext db;
    String userName;

    public ReportsController(DBContext db, String userName) {
        this.db = db;
        this.userName = userName;
    }
}
