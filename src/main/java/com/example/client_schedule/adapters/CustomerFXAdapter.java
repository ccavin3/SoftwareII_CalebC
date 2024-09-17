package com.example.client_schedule.adapters;

import com.example.client_schedule.entities.*;
import com.example.client_schedule.entities.Customer;
import com.example.client_schedule.helper.DBContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class CustomerFXAdapter {

    private DBContext db;
    public CountryFXAdapter FXCountry;
    public DivisionFXAdapter FXDivision;

    public Customer customer;

    public FilteredList<Customer> customerList;

    /**
     * Constructor to initialize the database context, customer and filter list
     *
     * @param customer Customer instance
     * @param db       dbContext instance
     */
    public CustomerFXAdapter(Customer customer, DBContext db) {
        this.customer = customer;
        this.db = db;
        customerList = new FilteredList<>(db.customers, p -> true);
        FXDivision = new DivisionFXAdapter(customer.getDivision(), db);
        FXCountry = new CountryFXAdapter(customer.getDivision().getCountry(), db);
    }

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final StringProperty phone = new SimpleStringProperty();
    private final StringProperty zip = new SimpleStringProperty();
    private final ObjectProperty<Division> division = new SimpleObjectProperty<>();
    private final ObjectProperty<Country> country = new SimpleObjectProperty<>();
    private final IntegerProperty divisionId = new SimpleIntegerProperty();

    /**
     * Gets appointments.
     *
     * @return the appointments
     */
    public List<Appointment> getAppointments() {
        return customer.getAppointments();
    }

    /**
     * Getter for customer's id.
     *
     * @return the customer's id.
     */
    public Integer getId() {
        return id.get();
    }

    /**
     * Getter for customer's name.
     *
     * @return the customer's name.
     */
    public String getName() {
        return name.get();
    }

    /**
     * Getter for customer's address.
     *
     * @return the customer's address.
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * Getter for customer's phone.
     *
     * @return the customer's phone.
     */
    public String getPhone() {
        return phone.get();
    }

    /**
     * Getter for customer's zip.
     *
     * @return the customer's zip.
     */
    public String getZip() {
        return zip.get();
    }

    /**
     * Getter for customer's division.
     *
     * @return the customer's division.
     */
    public Division getDivision() {
        return division.get();
    }

    /**
     * Getter for customer's country.
     *
     * @return the customer's country.
     */
    public Country getCountry() {
        return division.get().getCountry();
    }

    /**
     * Getter for customer's division id.
     *
     * @return the customer's division id.
     */
    public Integer getDivisionId() {
        return divisionId.get();
    }

    /**
     * Property method for customer's id.
     *
     * @return the customer's id property.
     */
    public IntegerProperty idProperty() {
        id.set(customer.getId());
        id.addListener((obs, old, wen) -> customer.setId((Integer) wen));
        return id;
    }

    /**
     * Property method for customer's name.
     *
     * @return the customer's name property.
     */
    public StringProperty nameProperty() {
        name.set(customer.getName());
        name.addListener((obs, old, wen) -> customer.setName(wen));
        return name;
    }

    /**
     * Property method for customer's address.
     *
     * @return the customer's address property.
     */
    public StringProperty addressProperty() {
        address.set(customer.getAddress());
        address.addListener((obs, old, wen) -> customer.setAddress(wen));
        return address;
    }

    /**
     * Property method for customer's phone.
     *
     * @return the customer's phone property.
     */
    public StringProperty phoneProperty() {
        phone.set(customer.getPhone());
        phone.addListener((obs, old, wen) -> customer.setPhone(wen));
        return phone;
    }

    /**
     * Property method for customer's zip.
     *
     * @return the customer's zip property.
     */
    public StringProperty zipProperty() {
        zip.set(customer.getZip());
        zip.addListener((obs, old, wen) -> customer.setZip(wen));
        return zip;
    }

    /**
     * Property method for customer's division.
     *
     * @return the customer's division property.
     */
    public ObjectProperty<Division> divisionProperty() {
        division.set(customer.getDivision());
        division.addListener((obs, old, wen) -> {
            if (wen != null) customer.setDivision(wen);
        });
        return division;
    }

    /**
     * Property method for customer's country.
     *
     * @return the customer's country property.
     */
    public ObjectProperty<Country> countryProperty() {
        country.set(customer.getDivision().getCountry());
        country.addListener((obs, old, wen) -> {
            FXDivision.divisionList.setPredicate(t -> t.getCountry().equals(wen));
        });
        return country;
    }

    /**
     * Property method for customer's divisionId.
     *
     * @return the customer's divisionId property.
     */
    public IntegerProperty divisionIdProperty() {
        divisionId.set(customer.getDivisionId());
        divisionId.addListener((obs, old, wen) -> {
            if (wen != null) customer.setDivisionId((Integer) wen);
        });
        return divisionId;
    }

    /**
     * This method enables updates of a customer's data.
     *
     * @param wen a customer's updated data.
     */
    public void update(Customer wen) {
        if (customer == null || customer.getId() != wen.getId()) {
            customer.setId(wen.getId());
        }
        if (customer == null || customer.getName() == null || !customer.getName().equals(wen.getName())) {
            customer.setName(wen.getName());
        }
        if (customer == null || customer.getAddress() == null || !customer.getAddress().equals(wen.getAddress())) {
            customer.setAddress(wen.getAddress());
        }
        if (customer == null || customer.getPhone() == null || !customer.getPhone().equals(wen.getPhone())) {
            customer.setPhone(wen.getPhone());
        }
        if (customer == null || customer.getZip() == null || !customer.getZip().equals(wen.getZip())) {
            customer.setZip(wen.getZip());
        }
        if (customer == null || customer.getDivisionId() != (wen.getDivisionId())) {
            customer.setDivisionId(wen.getDivisionId());
        }
        if (customer == null || customer.getDivision() == null || !customer.getDivision().equals(wen.getDivision())) {
            customer.setDivision(wen.getDivision());
        }
        if (customer == null || customer.getDivision().getCountry() == null || !customer.getDivision().getCountry().equals(wen.getDivision().getCountry())) {
            customer.getDivision().setCountry(wen.getDivision().getCountry());
        }
    }
}
