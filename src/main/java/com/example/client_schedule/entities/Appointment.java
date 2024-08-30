package com.example.client_schedule.entities;

import javafx.fxml.FXML;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;

import jakarta.persistence.*;

/**
 * The type Appointment.
 */
@Entity
@Table(name="appointments")

public class Appointment {
//region Entity Columns
    @Id
    @FXML
    @Column(name="Appointment_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int id;

    @FXML
    @Column(name="Title")
    public String title;

    @FXML
    @Column(name="Description")
    public String description;

    @FXML
    @Column(name="Location")
    public String location;

    @FXML
    @Column(name="Type")
    public String type;

    @FXML
    @Column(name="Start")
    public LocalDateTime start;

    @FXML
    @Column(name="End")
    public LocalDateTime end;

    /**
     * The Created.
     */
    @FXML
    @Column(name="Create_Date")
    public LocalDateTime created;
    /**
     * The Created by.
     */
    @FXML
    @Column(name="Created_By")
    public String createdBy;
    /**
     * The Updated.
     */
    @FXML
    @Column(name="Last_Update")
    public ZonedDateTime updated;
    /**
     * The Updated by.
     */
    @FXML
    @Column(name="Last_Updated_By")
    public String updatedBy;

    @FXML
    @Column(name="Customer_ID", insertable=false, updatable=false)
    public int customerId;

    @FXML
    @Column(name="User_ID", insertable=false, updatable=false)
    public int userId;

    @FXML
    @Column(name="Contact_ID", insertable=false, updatable=false)
    public int contactId;

    @FXML
    @Transient
    private LocalTime startTime;

    @FXML
    @Transient
    private LocalDate startDate;

    @FXML
    @Transient
    private LocalTime endTime;

    @FXML
    @Transient
    private LocalDate endDate;
//endregion

//region ORM
    @ManyToOne()
    @JoinColumn(name="Customer_ID")
    public Customer customer;

    @ManyToOne()
    @JoinColumn(name="User_ID")
    public User user;

    @ManyToOne()
    @JoinColumn(name="Contact_ID")
    public Contact contact;

//endregion

    /**
     * Instantiates a new Appointment.
     */
//region Constructors
    public Appointment() {
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title the title
     */
    public Appointment(String title) {
        this.title = title;
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title       the title
     * @param description the description
     */
    public Appointment(String title, String description) {

        this.title = title;
        this.description = description;
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param id    the id
     * @param title the title
     */
    public Appointment(int id, String title) {
        this.id = id;
        this.title = title;
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title       the title
     * @param description the description
     * @param location    the location
     */
    public Appointment(String title, String description, String location) {
        this.title = title;
        this.description = description;
        this.location = location;
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param id          the id
     * @param title       the title
     * @param description the description
     * @param location    the location
     */
    public Appointment(int id, String title, String description, String location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title       the title
     * @param description the description
     * @param location    the location
     * @param type        the type
     */
    public Appointment(String title, String description, String location, String type) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param id          the id
     * @param title       the title
     * @param description the description
     * @param location    the location
     * @param type        the type
     */
    public Appointment(int id, String title, String description, String location, String type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title       the title
     * @param description the description
     * @param loction     the loction
     * @param type        the type
     * @param start       the start
     */
    public Appointment(String title, String description, String location, String type, LocalDateTime start) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.startDate = start.toLocalDate();
        this.startTime = start.toLocalTime();
        this.end = start;
        this.endDate = start.toLocalDate();
        this.endTime = start.toLocalTime();
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title       the title
     * @param description the description
     * @param loction     the loction
     * @param type        the type
     * @param start       the start
     * @param end         the end
     */
    public Appointment(String title, String description, String loction, String type, LocalDateTime start, LocalDateTime end) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.startDate = start.toLocalDate();
        this.startTime = start.toLocalTime();
        this.end = end;
        this.endDate = end.toLocalDate();
        this.endTime = end.toLocalTime();
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param id          the id
     * @param title       the title
     * @param description the description
     * @param location    the location
     * @param type        the type
     * @param start       the start
     * @param end         the end
     * @param created     the created
     * @param createdBy   the created by
     * @param updated     the updated
     * @param updatedBy   the updated by
     * @param customerId  the customer id
     * @param userId      the user id
     * @param contactId   the contact id
     */
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime created, String createdBy, ZonedDateTime updated, String updatedBy, int customerId, int userId, int contactId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.startDate = start.toLocalDate();
        this.startTime = start.toLocalTime();
        this.end = end;
        this.endDate = end.toLocalDate();
        this.endTime = end.toLocalTime();
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

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets start.
     *
     * @return the start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Gets end.
     *
     * @return the end
     */
    public LocalDateTime getEnd() {
        return end;
    }

     /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets customer id.
     *
     * @param customerId the customer id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets contact id.
     *
     * @param contactId the contact id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Gets customer.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets customer.
     *
     * @param customer the customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets contact.
     *
     * @return the contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Sets contact.
     *
     * @param contact the contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * Gets .
     *
     * @return the
     */
    public LocalDateTime getcreated() {
        return created;
    }

    /**
     * Sets .
     *
     * @param created the created
     */
    public void setcreated(LocalDateTime created) {
        this.created = created;
    }

    /**
     * Gets created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets created by.
     *
     * @param createdBy the created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets .
     *
     * @return the
     */
    public ZonedDateTime getupdated() {
        return updated;
    }

    /**
     * Sets .
     *
     * @param updated the updated
     */
    public void setupdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    /**
     * Gets by.
     *
     * @return the by
     */
    public String getupdatedBy() {
        return updatedBy;
    }

    /**
     * Sets by.
     *
     * @param updatedBy the updated by
     */
    public void setupdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalTime getStartTime() {
        return this.start.toLocalTime();
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getStartDate() {
        return start.toLocalDate();
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalTime getEndTime() {
        return end.toLocalTime();
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getEndDate() {
        return end.toLocalDate();
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Sets start.
     *
     * @param start the start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
//        this.startDate = start.toLocalDate();
//        this.startTime = start.toLocalTime();
    }

    /**
     * Sets end.
     *
     * @param end the end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
//        this.endDate = end.toLocalDate();
//        this.endTime = end.toLocalTime();
    }


    //endregion
}
