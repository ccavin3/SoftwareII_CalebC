package com.example.client_schedule.entities;

import jakarta.persistence.*;
import javafx.fxml.FXML;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="countries")

public class Country {
//region Entity Columns
    @Id
    @FXML
    @Column(name="Country_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @FXML
    @Column(name="Country")
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

//endregion

//region ORM
    @OneToMany(mappedBy = "country")
    private List<Division> divisions = new ArrayList<>();
//endregion

//region Constructors
    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Country(int id, String name, LocalDateTime created, String createdBy, ZonedDateTime updated, String updatedBy) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Division> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<Division> divisions) {
        this.divisions = divisions;
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
