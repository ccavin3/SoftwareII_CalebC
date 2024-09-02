package com.example.client_schedule.adapters;

import com.example.client_schedule.entities.Country;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CountryFXAdapter {
    public Country country;

    public CountryFXAdapter(Country country) {
        this.country = country;

        this.id.set(country.getId());
        this.id.addListener((obs, old, wen) -> country.setId((Integer) wen));

        this.name.set(country.getName());
        this.name.addListener((obs, old, wen) -> country.setName(wen));

    }

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }
}
