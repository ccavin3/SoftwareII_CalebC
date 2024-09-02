package com.example.client_schedule.entities;

import com.example.client_schedule.helper.JPAListener;
import jakarta.persistence.*;
import javafx.fxml.FXML;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type User.
 */
@Entity
@EntityListeners(JPAListener.class)
@Table(name="users")

public class User extends baseEntity {
//region Entity Columns
    @Id
    @FXML
    @Column(name="User_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @FXML
    @Column(name="User_Name")
    private String userName;

    @FXML
    @Column(name="Password")
    private String password;

//    /**
//     * The Created.
//     */
//    @FXML
//    @Column(name="Create_Date")
//    protected LocalDateTime created;
//    /**
//     * The Created by.
//     */
//    @FXML
//    @Column(name="Created_By")
//    protected String createdBy;
//    /**
//     * The Updated.
//     */
//    @FXML
//    @Column(name="Last_Update")
//    protected ZonedDateTime updated;
//    /**
//     * The Updated by.
//     */
//    @FXML
//    @Column(name="Last_Updated_By")
//    protected String updatedBy;
//
//endregion

//region ORM relationship
@OneToMany(mappedBy = "user")
private List<Appointment> appointments = new ArrayList<>();

//endregion

    /**
     * Instantiates a new User.
     */
//region Constructors
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param userName the user name
     */
    public User(String userName) {
        this.userName = userName;
    }

    /**
     * Instantiates a new User.
     *
     * @param userName the user name
     * @param password the password
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Instantiates a new User.
     *
     * @param id       the user id
     * @param userName the user name
     * @param password the password
     */
    public User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Instantiates a new User.
     *
     * @param id        the user id
     * @param userName  the user name
     * @param password  the password
     * @param created   the created
     * @param createdBy the created by
     * @param updated   the updated
     * @param updatedBy the updated by
     */
    public User(int id, String userName, String password, LocalDateTime created, String createdBy, ZonedDateTime updated, String updatedBy) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.created = created;
        this.createdBy = createdBy;
        this.updated = updated;
        this.updatedBy = updatedBy;
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
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets appointments.
     *
     * @return the appointments
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Sets appointments.
     *
     * @param appointments the appointments
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
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


//region
}
