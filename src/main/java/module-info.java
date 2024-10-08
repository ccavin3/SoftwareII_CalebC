module com.example.client_schedule {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires org.apache.logging.log4j.slf4j.impl;
    requires java.desktop;
    requires jdk.compiler;

    opens com.example.client_schedule.entities to org.hibernate.orm.core, java.base, javafx.fxml;
    exports com.example.client_schedule.entities;

    opens com.example.client_schedule to javafx.fxml, java.base;
    exports com.example.client_schedule;

    exports com.example.client_schedule.controller;
    opens com.example.client_schedule.controller to javafx.fxml;

    exports com.example.client_schedule.helper;

    opens com.example.client_schedule.adapters to javafx.base;
    opens com.example.client_schedule.helper to javafx.fxml;
}