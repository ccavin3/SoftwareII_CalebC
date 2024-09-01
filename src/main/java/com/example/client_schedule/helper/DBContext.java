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
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("client_schedule");
            CRUD.em = em = emf.createEntityManager();
            em.setFlushMode(FlushModeType.COMMIT);
            em.getTransaction().begin();
            users = userDB.rows();
            divisions = FXCollections.observableList(divisionDB.rows().stream().peek(i -> {
                i.setId(i.getId());
            }).collect(Collectors.toList()));
            customers = FXCollections.observableList(customerDB.rows().stream().peek(i -> {
                i.setPhone(i.getPhone());
                i.setZip(i.getZip());
                i.setName(i.getName());
                i.setAddress(i.getAddress());
                i.setDivisionId(i.getDivisionId());
                i.setDivision(i.getDivision());
                i.setId(i.getId());
            }).collect(Collectors.toList()));
            countries = FXCollections.observableList(countryDB.rows().stream().peek(i -> {
                i.setId(i.getId());
            }).collect(Collectors.toList()));
            contacts = FXCollections.observableList(contactDB.rows().stream().peek(i -> {
            }).collect(Collectors.toList()));
            appointments = FXCollections.observableList(appointmentDB.rows().stream().peek(i -> {
                i.setContact(i.getContact());
                i.setId(i.getId());
                i.setContact(i.getContact());
                i.setDescription(i.getDescription());
                i.setLocation(i.getLocation());
                i.setContactId(i.getContactId());
                i.setCustomerId(i.getCustomerId());
                i.setUserId(i.getContactId());
                i.setContact(i.getContact());
                i.setCustomer(i.getCustomer());
                i.setUser(i.getUser());
                i.setStart(i.getStart());
                i.setEnd(i.getEnd());
                i.setStartTime(i.getStart().toLocalTime());
                i.setStartDate(i.getStart().toLocalDate());
                i.setEndDate(i.getEnd().toLocalDate());
                i.setEndTime(i.getEnd().toLocalTime());
                i.setTitle(i.getTitle());
                i.setType(i.getType());
            }).collect(Collectors.toList()));
        }
        catch (Exception e) {
            if (e.getMessage() != null) {}
        }
    }

    class UserDB extends CRUD<User> {}
    class DivisionDB extends CRUD<Division> {}
    class ContactDB extends CRUD<Contact> {}
    class CountryDB extends CRUD<Country> {}
    class CustomerDB extends CRUD<Customer> {}
    class AppointmentDB extends CRUD<Appointment> {}

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
