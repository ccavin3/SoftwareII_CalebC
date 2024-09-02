package com.example.client_schedule.entities;

import com.example.client_schedule.helper.JPAListener;
import jakarta.persistence.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Country.
 */
@Entity
@EntityListeners(JPAListener.class)
@Table(name="countries")

public class Country extends baseEntity {
//region Entity Columns
    @Id
    @FXML
    @Column(name="Country_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @FXML
    @Column(name="Country")
    private String name;

//    /**
//     * The Created.
//     */
//    @FXML
//    @Column(name="Create_Date")
//    private LocalDateTime created;
//    /**
//     * The Created by.
//     */
//    @FXML
//    @Column(name="Created_By")
//    private String createdBy;
//    /**
//     * The Updated.
//     */
//    @FXML
//    @Column(name="Last_Update")
//    private ZonedDateTime updated;
//    /**
//     * The Updated by.
//     */
//    @FXML
//    @Column(name="Last_Updated_By")
//    private String updatedBy;
//
//endregion

//region ORM
    @OneToMany(mappedBy = "country")
    private List<Division> divisions = new ArrayList<>();
//endregion

    /**
     * Instantiates a new Country.
     */
//region Constructors
    public Country() {
    }

    /**
     * Instantiates a new Country.
     *
     * @param name the name
     */
    public Country(String name) {
        this.name = name;
    }

    /**
     * Instantiates a new Country.
     *
     * @param id   the id
     * @param name the name
     */
    public Country(int id, String name) {
        this.setId(id);
        this.name = name;
    }

    /**
     * Instantiates a new Country.
     *
     * @param id        the id
     * @param name      the name
     * @param created   the created
     * @param createdBy the created by
     * @param updated   the updated
     * @param updatedBy the updated by
     */
    public Country(int id, String name, LocalDateTime created, String createdBy, ZonedDateTime updated, String updatedBy) {
        this.setId(id);
        this.name = name;
        this.created = created;
        this.createdBy = createdBy;
        this.updated = updated;
        this.updatedBy = updatedBy;
    }
//endregion

    /**
     * Gets id.
     *
     * @return the id
     */
//region getters-setters
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets divisions.
     *
     * @return the divisions
     */
    public List<Division> getDivisions() {
        return divisions;
    }

    /**
     * Sets divisions.
     *
     * @param divisions the divisions
     */
    public void setDivisions(List<Division> divisions) {
        this.divisions = divisions;
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

//endregion
}
