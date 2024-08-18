package com.example.client_schedule.entities;

import jakarta.persistence.*;
import javafx.fxml.FXML;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="customers")

public class Customer {
//region Entity Columns
    @Id
    @FXML
    @Column(name="Customer_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @FXML
    @Column(name="Customer_Name")
    private String name;

    @FXML
    @Column(name="Address")
    private String address;

    @FXML
    @Column(name="Postal_Code")
    private String zip;

    @FXML
    @Column(name="Phone")
    private String phone;

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
    @Column(name="Division_ID", insertable=false, updatable=false)
    private int divisionId;

//endregion

//region ORM
    @ManyToOne
    @JoinColumn(name="Division_ID")
    private Division division;

    @OneToMany(mappedBy = "customer")
    private List<Appointment> appointments = new ArrayList<>();

//endregion

//region Constructors
    public Customer() {
    }

    public Customer(String name) {
        this.name = name;
    }

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Customer(int id, String name, String address, String zip, String phone, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    public Customer(int id, String name, String address, String zip, String phone, LocalDateTime created, String createdBy, ZonedDateTime updated, String updatedBy, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.created = created;
        this.createdBy = createdBy;
        this.updated = updated;
        this.updatedBy = updatedBy;
        this.divisionId = divisionId;
    }

    //endregion

//region getters-setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    public String getupdatedBy() {
        return updatedBy;
    }

    public void setupdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    //endregion
}
