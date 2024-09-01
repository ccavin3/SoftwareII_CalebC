package com.example.client_schedule.entities;

import jakarta.persistence.*;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    /**
     * The Id.
     */
//region Entity Columns
    @Id
    @FXML
    @Column(name="Customer_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer id;

    @Transient
    private IntegerProperty idProperty = new SimpleIntegerProperty();

    /**
     * The Name.
     */
    @FXML
    @Column(name="Customer_Name")
    public String name;

    @Transient
    private StringProperty nameProperty = new SimpleStringProperty();

    /**
     * The Address.
     */
    @FXML
    @Column(name="Address")
    public String address;

    @Transient
    private StringProperty addressProperty = new SimpleStringProperty();

    /**
     * The Zip.
     */
    @FXML
    @Column(name="Postal_Code")
    public String zip;

    @Transient
    private StringProperty zipProperty = new SimpleStringProperty();

    /**
     * The Phone.
     */
    @FXML
    @Column(name="Phone")
    public String phone;

    @Transient
    private StringProperty phoneProperty = new SimpleStringProperty();

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

    /**
     * The Division id.
     */
    @FXML
    @Column(name="Division_ID")
    public Integer divisionId;

    @Transient
    private IntegerProperty divisionIdProperty = new SimpleIntegerProperty();

//endregion

    private void setListeners(Division division) {
        this.getDivisionIdProperty().bindBidirectional(division.getIdProperty());
    }

    private void unSetListeners(Division division) {
        this.getDivisionIdProperty().bindBidirectional(division.getIdProperty());
    }

    /**
     * The Division.
     */
//region ORM
    @ManyToOne
    @JoinColumn(name="Division_ID", insertable = false, updatable = false)
    public Division division;

    @Transient
    private ObjectProperty<Division> divisionProperty = new SimpleObjectProperty<>();

    /**
     * The Appointments.
     */
    @OneToMany(mappedBy = "customer")
    public List<Appointment> appointments = new ArrayList<>();

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
        this.setName(name);
    }

    /**
     * Instantiates a new Customer.
     *
     * @param id   the id
     * @param name the name
     */
    public Customer(int id, String name) {
        this.setId(id);
        this.setName(name);
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
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setZip(zip);
        this.setPhone(phone);
        this.setDivisionId(divisionId);
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
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setZip(zip);
        this.setPhone(phone);
        this.setDivisionId(divisionId);
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
        this.idProperty.set(id);
    }

    /**
     * Gets id property.
     *
     * @return the id property
     */
    public IntegerProperty getIdProperty() {
        return this.idProperty;
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
     * Gets name property.
     *
     * @return the name property
     */
    public StringProperty getNameProperty() {
        return nameProperty;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
        this.nameProperty.set(name);
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
     * Gets address property.
     *
     * @return the address property
     */
    public StringProperty getAddressProperty() {
        return this.addressProperty;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
        this.addressProperty.set(address);
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
     * Gets zip property.
     *
     * @return the zip property
     */
    public StringProperty getZipProperty() {
        return this.zipProperty;
    }

    /**
     * Sets zip.
     *
     * @param zip the zip
     */
    public void setZip(String zip) {
        this.zip = zip;
        this.zipProperty.set(zip);
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
     * Gets phone property.
     *
     * @return the phone property
     */
    public StringProperty getPhoneProperty() {
        return this.phoneProperty;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
        this.phoneProperty.set(phone);
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
     * Gets division property.
     *
     * @return the division property
     */
    public ObjectProperty<Division> getDivisionProperty() {
        return this.divisionProperty;
    }

    /**
     * Sets division.
     *
     * @param division the division
     */
    public void setDivision(Division division) {
        unSetListeners(this.division);
        setListeners(division);
        this.division = division;
        this.divisionProperty.set(division);
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
     * Gets division id property.
     *
     * @return the division id property
     */
    public IntegerProperty getDivisionIdProperty() {
        return this.divisionIdProperty;
    }

    /**
     * Sets division id.
     *
     * @param divisionId the division id
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
        this.divisionIdProperty.set(divisionId);
    }


    //endregion
}
