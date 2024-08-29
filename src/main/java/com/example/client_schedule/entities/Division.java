package com.example.client_schedule.entities;

import jakarta.persistence.*;
import javafx.fxml.FXML;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Division.
 */
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

    /**
     * The Created.
     */
    @FXML
    @Column(name="Create_Date")
    protected LocalDateTime created;
    /**
     * The Created by.
     */
    @FXML
    @Column(name="Created_By")
    protected String createdBy;
    /**
     * The Updated.
     */
    @FXML
    @Column(name="Last_Update")
    protected ZonedDateTime updated;
    /**
     * The Updated by.
     */
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

    /**
     * Instantiates a new Division.
     */
//region Constructors
    public Division() {
    }

    /**
     * Instantiates a new Division.
     *
     * @param name the name
     */
    public Division(String name) {
        this.name = name;
    }

    /**
     * Instantiates a new Division.
     *
     * @param name      the name
     * @param countryId the country id
     */
    public Division(String name, int countryId) {
        this.name = name;
        this.countryId = countryId;
    }

    /**
     * Instantiates a new Division.
     *
     * @param id   the id
     * @param name the name
     */
    public Division(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Instantiates a new Division.
     *
     * @param id        the id
     * @param countryId the country id
     */
    public Division(int id, int countryId) {
        this.id = id;
        this.countryId = countryId;
    }

    /**
     * Instantiates a new Division.
     *
     * @param id        the id
     * @param name      the name
     * @param countryId the country id
     */
    public Division(int id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }


    /**
     * Instantiates a new Division.
     *
     * @param id        the id
     * @param name      the name
     * @param created   the created
     * @param createdBy the created by
     * @param updated   the updated
     * @param updatedBy the updated by
     * @param countryId the country id
     */
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
     * @param division the division
     */
    public void setName(String division) {
        this.name = name;
    }

    /**
     * Gets country id.
     *
     * @return the country id
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets country id.
     *
     * @param countryId the country id
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Gets customers.
     *
     * @return the customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Sets customers.
     *
     * @param customers the customers
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Gets created.
     *
     * @return the created
     */
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * Sets created.
     *
     * @param created the created
     */
    public void setCreated(LocalDateTime created) {
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
     * Gets updated.
     *
     * @return the updated
     */
    public ZonedDateTime getUpdated() {
        return updated;
    }

    /**
     * Sets updated.
     *
     * @param updated the updated
     */
    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    /**
     * Gets updated by.
     *
     * @return the updated by
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Sets updated by.
     *
     * @param updatedBy the updated by
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

//endregion
}
