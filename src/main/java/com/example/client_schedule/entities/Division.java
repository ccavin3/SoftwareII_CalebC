package com.example.client_schedule.entities;

import jakarta.persistence.*;
import javafx.fxml.FXML;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="first_level_divisions")

public class Division {
//region Entity Columns
    @Id
    @FXML
    @Column(name="Division_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @FXML
    @Column(name="Division")
    private String name;

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
    @Column(name="Country_ID", insertable=false, updatable=false)
    private int countryId;

//endregion

//region ORM
    @ManyToOne()
    @JoinColumn(name="Country_ID")
    private Country country;

    @OneToMany(mappedBy = "division")
    private List<Customer> customers = new ArrayList<>();
//endregion

//region Constructors
    public Division() {
    }

    public Division(String name) {
        this.name = name;
    }

    public Division(String name, int countryId) {
        this.name = name;
        this.countryId = countryId;
    }

    public Division(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Division(int id, int countryId) {
        this.id = id;
        this.countryId = countryId;
    }

    public Division(int id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }


    public Division(int id, String name, LocalDateTime created, String createdBy, ZonedDateTime updated, String updatedBy, int countryId) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.createdBy = createdBy;
        this.updated = updated;
        this.updatedBy = updatedBy;
        this.countryId = countryId;
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

    public void setName(String division) {
        this.name = name;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

//endregion
}
