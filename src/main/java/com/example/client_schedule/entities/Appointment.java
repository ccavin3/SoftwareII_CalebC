package com.example.client_schedule.entities;

import com.example.client_schedule.helper.JPAListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.SortedMap;

import jakarta.persistence.*;

/**
 * The type Appointment.
 */
@Entity
@EntityListeners(JPAListener.class)
@Table(name="appointments")

public class Appointment extends baseEntity {
//region Entity Columns
    @Id
    @FXML
    @Column(name="Appointment_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


//    /**
//     * The Created.
//     */
//    @FXML
//    @Column(name="Create_Date")
//    public LocalDateTime created;
//
//    /**
//     * The Created by.
//     */
//    @FXML
//    @Column(name="Created_By")
//    public String createdBy;
//
//    /**
//     * The Updated.
//     */
//    @FXML
//    @Column(name="Last_Update")
//    public ZonedDateTime updated;
//
//    /**
//     * The Updated by.
//     */
//    @FXML
//    @Column(name="Last_Updated_By")
//    public String updatedBy;
//
    @FXML
    @Column(name="Customer_ID", insertable = false, updatable = false)
    private int customerId;
    
    @FXML
    @Column(name="User_ID", insertable = false, updatable = false)
    private int userId;

    @FXML
    @Column(name="Contact_ID", insertable = false, updatable = false)
    private int contactId;

    @Transient
    private LocalTime startTime;

    @Transient
    private LocalDate startDate;

    @Transient
    private LocalTime endTime;

    @Transient
    private LocalDate endDate;

    @Transient
    private DateTimeFormatter dformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    @Transient
    private DateTimeFormatter tformatter = DateTimeFormatter.ofPattern("hh:mm[:ss] a");
    @Transient
    private DateTimeFormatter dtformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm[:ss] a");

//endregion

//region ORM
    @ManyToOne()
    @JoinColumn(name="Customer_ID", nullable = false)
    private Customer customer;

    @ManyToOne()
    @JoinColumn(name="User_ID", nullable = false)
    private User user;

    @ManyToOne()
    @JoinColumn(name="Contact_ID", nullable = false)
    private Contact contact;

//endregion


//region Constructors
    /**
     * Instantiates a new Appointment.
     */
    public Appointment() {
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title the title
     */
    public Appointment(String title) {
        this.setTitle(title);
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title       the title
     * @param description the description
     */
    public Appointment(String title, String description) {
        this.setTitle(title);
        this.setDescription(description);
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param id    the id
     * @param title the title
     */
    public Appointment(int id, String title) {
        this.setId(id);
        this.setTitle(title);
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title       the title
     * @param description the description
     * @param location    the location
     */
    public Appointment(String title, String description, String location) {
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
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
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
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
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
        this.setType(type);
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
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
        this.setType(type);
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title       the title
     * @param description the description
     * @param location     the location
     * @param type        the type
     * @param start       the start
     */
    public Appointment(String title, String description, String location, String type, LocalDateTime start) {
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
        this.setType(type);
        this.setStart(start);
        this.setStartDate(start.toLocalDate());
        this.setStartTime(start.toLocalTime());
        this.setEnd(start);
        this.setEndDate(start.toLocalDate());
        this.setEndTime(start.toLocalTime());
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param title       the title
     * @param description the description
     * @param location     the location
     * @param type        the type
     * @param start       the start
     * @param end         the end
     */
    public Appointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end) {
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
        this.setType(type);
        this.setStart(start);
        this.setStartDate(start.toLocalDate());
        this.setStartTime(start.toLocalTime());
        this.setEnd(end);
        this.setEndDate(end.toLocalDate());
        this.setEndTime(end.toLocalTime());
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
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
        this.setType(type);
        this.setStart(start);
        this.setStartDate(start.toLocalDate());
        this.setStartTime(start.toLocalTime());
        this.setEnd(end);
        this.setEndDate(end.toLocalDate());
        this.setEndTime(end.toLocalTime());
        this.created = created;
        this.createdBy = createdBy;
        this.updated = updated;
        this.updatedBy = updatedBy;
        this.setCustomerId(customerId);
        this.setUserId(userId);
        this.setContactId(contactId);
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
        if (this.getStartDate() != null) {
            start = getStartDate().atTime(startTime);
        }
    }

    public LocalDate getStartDate() {
        return this.start.toLocalDate();
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        if (getStartTime() != null) {
            start = startDate.atTime(getStartTime());
        }
    }

    public LocalTime getEndTime() {
        return this.end.toLocalTime();
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
        if (getEndDate() != null) {
            end = getEndDate().atTime(endTime);
        }
    }

    public LocalDate getEndDate() {
        return this.getEnd().toLocalDate();
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        if (getEndTime() != null) {
            end = endDate.atTime(getEndTime());
        }
    }

    /**
     * Sets start.
     *
     * @param start the start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Sets end.
     *
     * @param end the end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }


    //endregion
}
