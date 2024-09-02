package com.example.client_schedule.controller;

import com.example.client_schedule.MainApplication;
import com.example.client_schedule.helper.DBContext;

public class ReportsController {
    DBContext db;

    public ReportsController(DBContext db) {
        this.db = db;
    }
}
