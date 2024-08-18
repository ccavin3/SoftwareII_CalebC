package com.example.client_schedule.entities;

import jakarta.persistence.*;
import javafx.fxml.FXML;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")

public class User {
//region Entity Columns
    @Id
    @FXML
    @Column(name="User_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @FXML
    @Column(name="User_Name")
    private String userName;

    @FXML
    @Column(name="Password")
    private String password;

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

//endregion

//region ORM relationship
@OneToMany(mappedBy = "user")
private List<Appointment> appointments = new ArrayList<>();

//endregion

//region Constructors
    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
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


//region
}
