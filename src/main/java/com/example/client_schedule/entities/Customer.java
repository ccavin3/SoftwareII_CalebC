package com.example.client_schedule.entities;

import jakarta.persistence.*;
import javafx.fxml.FXML;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Customer.
 */
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

    /**
     * Instantiates a new Customer.
     */
//region Constructors
    public Customer() {
    }

    /**
     * Instantiates a new Customer.
     *
     * @param name the name
     */
    public Customer(String name) {
        this.name = name;
    }

    /**
     * Instantiates a new Customer.
     *
     * @param id   the id
     * @param name the name
     */
    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Instantiates a new Customer.
     *
     * @param id         the id
     * @param name       the name
     * @param address    the address
     * @param zip        the zip
     * @param phone      the phone
     * @param divisionId the division id
     */
    public Customer(int id, String name, String address, String zip, String phone, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    /**
     * Instantiates a new Customer.
     *
     * @param id         the id
     * @param name       the name
     * @param address    the address
     * @param zip        the zip
     * @param phone      the phone
     * @param created    the created
     * @param createdBy  the created by
     * @param updated    the updated
     * @param updatedBy  the updated by
     * @param divisionId the division id
     */
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
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets zip.
     *
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets zip.
     *
     * @param zip the zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets division.
     *
     * @return the division
     */
    public Division getDivision() {
        return division;
    }

    /**
     * Sets division.
     *
     * @param division the division
     */
    public void setDivision(Division division) {
        this.division = division;
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

    /**
     * Gets division id.
     *
     * @return the division id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets division id.
     *
     * @param divisionId the division id
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    //endregion
}
