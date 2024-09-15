package com.example.client_schedule.helper;

import com.example.client_schedule.entities.*;
import jakarta.persistence.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Context for Database operations.
 */
public class DBContext {

    /**
     * Entity manager for managing entities.
     */
    @PersistenceContext(unitName = "client_schedule")
    public EntityManager em;

    /**
     * AppointmentDB instance for appointment related operations.
     */
    public AppointmentDB appointmentDB = new AppointmentDB();

    /**
     * CustomerDB instance for customer related operations.
     */
    public CustomerDB customerDB = new CustomerDB();

    /**
     * CountryDB instance for country related operations.
     */
    public CountryDB countryDB = new CountryDB();

    /**
     * ContactDB instance for contact related operations.
     */
    public ContactDB contactDB = new ContactDB();

    /**
     * DivisionDB instance for division related operations.
     */
    public DivisionDB divisionDB = new DivisionDB();

    /**
     * UserDB instance for user related operations.
     */
    public UserDB userDB = new UserDB();

    /**
     * ObservableList for storing users.
     */
    public ObservableList<User> users;

    /**
     * ObservableList for storing divisions.
     */
    public ObservableList<Division> divisions;

    /**
     * ObservableList for storing customers.
     */
    public ObservableList<Customer> customers;

    /**
     * ObservableList for storing countries.
     */
    public ObservableList<Country> countries;

    /**
     * ObservableList for storing contacts.
     */
    public ObservableList<Contact> contacts;

    /**
     * ObservableList for storing appointments.
     */
    public ObservableList<Appointment> appointments;

    /**
     * Constructor for DBContext. Initialises entity manager and populates the ObservableLists
     * by fetching data from DB.
     */
    public DBContext() {
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
        } catch (Exception e) {
            if (e.getMessage() != null) {
            }
        }
    }
}
