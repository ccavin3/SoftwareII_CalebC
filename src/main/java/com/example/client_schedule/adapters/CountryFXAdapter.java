package com.example.client_schedule.adapters;

import com.example.client_schedule.entities.Country;
import com.example.client_schedule.helper.DBContext;
import javafx.beans.property.*;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.logging.Filter;

/**
 * Adapter class for Country entities to integrate with JavaFX properties
 */
public class CountryFXAdapter {
    public Country country;

    public FilteredList<Country> countryList;

    private DBContext db;

    /**
     * Constructor for CountryFXAdapter.
     *
     * @param country country entity object
     * @param db      database context object
     */
    public CountryFXAdapter(Country country, DBContext db) {
        this.country = country;
        this.db = db;
        this.countryList = new FilteredList<>(db.countries, p -> true);
    }

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();

    /**
     * Represents id as IntegerProperty for Country entity.
     *
     * @return id property object
     */
    public IntegerProperty idProperty() {
        id.set(country.getId());
        id.addListener((obs, old, wen) -> country.setId((Integer) wen));
        return id;
    }

    /**
     * Represents name as StringProperty for Country entity.
     *
     * @return name property object
     */
    public StringProperty nameProperty() {
        name.set(country.getName());
        name.addListener((obj, old, wen) -> country.setName(wen));
        return name;
    }

    /**
     * Sets name for this adapter and underlying entity.
     *
     * @param s new name
     */
    public void setName(String s) {
        name.set(s);
    }

    /**
     * Gets the name for this adapter and underlying entity.
     *
     * @return name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Gets the id for this adapter and underlying entity.
     *
     * @return id
     */
    public Integer getId() {
        return id.get();
    }
}
