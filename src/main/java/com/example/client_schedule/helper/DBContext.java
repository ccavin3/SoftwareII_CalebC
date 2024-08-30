package com.example.client_schedule.helper;

import com.example.client_schedule.entities.*;
import jakarta.persistence.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DBContext {

    @PersistenceContext(unitName="client_schedule")
    public EntityManager em;

    public DBContext(){
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("client_schedule");
            CRUD.em = em = emf.createEntityManager();
            users = (new userdb()).rows();
            divisions = (new divisiondb()).rows();
            customers = (new customerdb()).rows();
            countries = (new countrydb()).rows();
            contacts = (new contactdb()).rows();
            appointments = (new appointmentdb()).rows();
        }
        catch (Exception e) {
            if (e.getMessage() != null) {}
        }
    }

    class userdb extends CRUD<User> {}
    class divisiondb extends CRUD<Division> {}
    class contactdb extends CRUD<Contact> {}
    class countrydb extends CRUD<Country> {}
    class customerdb extends CRUD<Customer> {}
    class appointmentdb extends CRUD<Appointment> {
        @Override
        public ObservableList<Appointment> rows() {
            return FXCollections.observableList(super.rows().stream().peek(r -> {
                r.setStartDate(r.getStart().toLocalDate());
                r.setStartTime(r.getStart().toLocalTime());
                r.setEndDate(r.getEnd().toLocalDate());
                r.setEndTime(r.getEnd().toLocalTime());
            }).collect(Collectors.toList()));
        }
    }

    public ObservableList<User> users;
    public ObservableList<Division> divisions;
    public ObservableList<Customer> customers;
    public ObservableList<Country> countries;
    public ObservableList<Contact> contacts;
    public ObservableList<Appointment> appointments;

}
