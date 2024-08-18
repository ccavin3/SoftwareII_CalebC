package com.example.client_schedule.entities;

import javafx.fxml.FXML;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import jakarta.persistence.*;

@Entity
@Table(name="appointments")

public class Appointment {
//region Entity Columns
    @Id
    @FXML
    @Column(name="Appointment_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @FXML
    @Column(name="Title")
    private String title;

    @FXML
    @Column(name="Description")
    private String description;

    @FXML
    @Column(name="Location")
    private String location;

    @FXML
    @Column(name="Type")
    private String type;

    @FXML
    @Column(name="Start")
    private LocalDateTime start;

    @FXML
    @Column(name="End")
    private LocalDateTime end;

    @FXML
    @Column(name="Create_Date")
    protected LocalDateTime created;
    @FXML
    @Column(name="Created_By")
    protected String createdBy;
    @FXML
    @Column(name="Last_Update")
    protected ZonedDateTime updated;
    @FXML
    @Column(name="Last_Updated_By")
    protected String updatedBy;

    @FXML
    @Column(name="Customer_ID", insertable=false, updatable=false)
    private int customerId;

    @FXML
    @Column(name="User_ID", insertable=false, updatable=false)
    private int userId;

    @FXML
    @Column(name="Contact_ID", insertable=false, updatable=false)
    private int contactId;


//endregion

//region ORM
    @ManyToOne()
    @JoinColumn(name="Customer_ID")
    private Customer customer;

    @ManyToOne()
    @JoinColumn(name="User_ID")
    private User user;

    @ManyToOne()
    @JoinColumn(name="Contact_ID")
    private Contact contact;

//endregion

//region Constructors
    public Appointment() {
    }

    public Appointment(String title) {
        this.title = title;
    }
    public Appointment(String title, String description) {

        this.title = title;
        this.description = description;
    }

    public Appointment(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Appointment(String title, String description, String location) {
        this.title = title;
        this.description = description;
        this.location = location;
    }

    public Appointment(int id, String title, String description, String location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
    }

    public Appointment(String title, String description, String location, String type) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
    }

    public Appointment(int id, String title, String description, String location, String type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
    }

    public Appointment(String title, String description, String loction, String type, LocalDateTime start) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = start;
    }

    public Appointment(String title, String description, String loction, String type, LocalDateTime start, LocalDateTime end) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
    }

    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime created, String createdBy, ZonedDateTime updated, String updatedBy, int customerId, int userId, int contactId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.created = created;
        this.createdBy = createdBy;
        this.updated = updated;
        this.updatedBy = updatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    //endregion

//region getters-setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public LocalDateTime getcreated() {
        return created;
    }

    public void setcreated(LocalDateTime created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getupdated() {
        return updated;
    }

    public void setupdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public String getupdatedBy() {
        return updatedBy;
    }

    public void setupdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

//endregion
}
