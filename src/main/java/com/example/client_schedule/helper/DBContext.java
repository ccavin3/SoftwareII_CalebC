package com.example.client_schedule.helper;

import com.example.client_schedule.entities.*;
import jakarta.persistence.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DBContext {

    @PersistenceContext(unitName="client_schedule")
    public EntityManager em;

    public DBContext(){
        em = CRUD.em;
        em.setFlushMode(FlushModeType.COMMIT);
        em.getTransaction().begin();

        try {
            users = userDB.rows;
            divisions = divisionDB.rows;
            customers = customerDB.rows;
            countries = countryDB.rows;
            contacts = contactDB.rows;
            appointments = appointmentDB.rows;
        }
        catch (Exception e) {
            if (e.getMessage() != null) {}
        }
    }

    public AppointmentDB appointmentDB = new AppointmentDB();
    public CustomerDB customerDB = new CustomerDB();
    public CountryDB countryDB = new CountryDB();
    public ContactDB contactDB = new ContactDB();
    public DivisionDB divisionDB = new DivisionDB();
    public UserDB userDB = new UserDB();

    public ObservableList<User> users;
    public ObservableList<Division> divisions;
    public ObservableList<Customer> customers;
    public ObservableList<Country> countries;
    public ObservableList<Contact> contacts;
    public ObservableList<Appointment> appointments;

}
