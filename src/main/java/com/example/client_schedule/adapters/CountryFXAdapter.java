package com.example.client_schedule.adapters;

import com.example.client_schedule.entities.Country;
import com.example.client_schedule.helper.DBContext;
import javafx.beans.property.*;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.logging.Filter;

public class CountryFXAdapter {
    public Country country;

    public FilteredList<Country> countryList;

    private DBContext db;
    public CountryFXAdapter(Country country, DBContext db) {
        this.country = country;
        this.db = db;
        this.countryList = new FilteredList<>(db.countries, p-> true);
    }

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();

    public IntegerProperty idProperty() {
        id.set(country.getId());
        id.addListener((obs, old, wen) -> country.setId((Integer) wen));
        return id;
    }

    public StringProperty nameProperty() {
        name.set(country.getName());
        name.addListener((obj, old, wen) -> country.setName(wen));
        return name;
    }

    public void setName(String s) {
        name.set(s);
    }

    public String getName() {
        return name.get();
    }

    public Integer getId() {
        return id.get();
    }
}
