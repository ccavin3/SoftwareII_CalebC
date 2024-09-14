package com.example.client_schedule.adapters;

import com.example.client_schedule.entities.Country;
import com.example.client_schedule.entities.Customer;
import com.example.client_schedule.entities.Division;
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

public class CustomerFXAdapter {

    private DBContext db;
    public CountryFXAdapter FXCountry;
    public DivisionFXAdapter FXDivision;

    public Customer customer;

    public FilteredList<Customer> customerList;

    public CustomerFXAdapter(Customer customer, DBContext db) {
        this.customer = customer;
        this.db = db;
        customerList = new FilteredList<>(db.customers, p -> true);
        FXDivision = new DivisionFXAdapter(customer.getDivision(),db);
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

    public Integer getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getPhone() {
        return phone.get();
    }

    public String getZip() {
        return zip.get();
    }

    public Division getDivision() {
        return division.get();
    }

    public Country getCountry() {
        return division.get().getCountry();
    }

    public Integer getDivisionId() {
        return divisionId.get();
    }

    public IntegerProperty idProperty() {
        id.set(customer.getId());
        id.addListener((obs, old, wen) -> customer.setId((Integer) wen));
        return id;
    }

    public StringProperty nameProperty() {
        name.set(customer.getName());
        name.addListener((obs, old, wen) -> customer.setName(wen));
        return name;
    }

    public StringProperty addressProperty() {
        address.set(customer.getAddress());
        address.addListener((obs, old, wen) -> customer.setAddress(wen));
        return address;
    }

    public StringProperty phoneProperty() {
        phone.set(customer.getPhone());
        phone.addListener((obs, old, wen) -> customer.setPhone(wen));
        return phone;
    }

    public StringProperty zipProperty() {
        zip.set(customer.getZip());
        zip.addListener((obs, old, wen) -> customer.setZip(wen));
        return zip;
    }

    public ObjectProperty<Division> divisionProperty() {
        division.set(customer.getDivision());
        division.addListener((obs, old, wen) -> {
            if (wen != null) customer.setDivision(wen);
        });
        return division;
    }

    public ObjectProperty<Country> countryProperty() {
        country.set(customer.getDivision().getCountry());
        country.addListener((obs, old, wen) -> {
            FXDivision.divisionList.setPredicate(t -> t.getCountry().equals(wen));
        });
        return country;
    }

    public IntegerProperty divisionIdProperty() {
        divisionId.set(customer.getDivisionId());
        divisionId.addListener((obs, old, wen) -> {
            if (wen != null) customer.setDivisionId((Integer) wen);
        });
        return divisionId;
    }

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
