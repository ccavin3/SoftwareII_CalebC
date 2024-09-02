package com.example.client_schedule.adapters;

import com.example.client_schedule.entities.Customer;
import com.example.client_schedule.entities.Division;
import com.example.client_schedule.entities.Customer;
import jakarta.persistence.criteria.CriteriaBuilder;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CustomerFXAdapter {
    public Customer customer;

    public CustomerFXAdapter(Customer customer) {
        this.customer = customer;
        id.addListener((obs, old, wen) -> customer.setId((Integer) wen));
        name.addListener((obs, old, wen) -> customer.setName(wen));
        address.addListener((obs, old, wen) -> customer.setAddress(wen));
        phone.addListener((obs, old, wen) -> customer.setPhone(wen));
        zip.addListener((obs, old, wen) -> customer.setZip(wen));
        division.addListener((obs, old, wen) -> {
            if (wen != null) customer.setDivision(wen);
        });
        divisionId.addListener((obs, old, wen) -> {
            if (wen != null) customer.setDivisionId((Integer) wen);
        });
    }

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final StringProperty phone = new SimpleStringProperty();
    private final StringProperty zip = new SimpleStringProperty();
    private final ObjectProperty<Division> division = new SimpleObjectProperty<>();
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

    public Integer getDivisionId() {
        return divisionId.get();
    }

    public IntegerProperty idProperty() {
        id.set(customer.getId());
        return id;
    }

    public StringProperty nameProperty() {
        name.set(customer.getName());
        return name;
    }

    public StringProperty addressProperty() {
        address.set(customer.getAddress());
        return address;
    }

    public StringProperty phoneProperty() {
        phone.set(customer.getPhone());
        return phone;
    }

    public StringProperty zipProperty() {
        zip.set(customer.getZip());
        return zip;
    }

    public ObjectProperty<Division> divisionProperty() {
        division.set(customer.getDivision());
        return division;
    }

    public IntegerProperty divisionIdProperty() {
        divisionId.set(customer.getDivisionId());
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
    }
}
