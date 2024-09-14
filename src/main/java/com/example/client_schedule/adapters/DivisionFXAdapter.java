package com.example.client_schedule.adapters;

import com.example.client_schedule.entities.Country;
import com.example.client_schedule.entities.Division;
import com.example.client_schedule.helper.DBContext;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class DivisionFXAdapter {
    public Division division;

    public FilteredList<Division> divisionList;
    public ObservableList<Country> countryList;

    private DBContext db;
    public DivisionFXAdapter(Division division, DBContext db) {
        this.division = division;
        this.db = db;
        countryList = FXCollections.observableList(db.countries);
        divisionList = new FilteredList<>(db.divisions, p-> true);
    }

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty countryId = new SimpleIntegerProperty();
    private final ObjectProperty<Country> country = new SimpleObjectProperty<>();

    public IntegerProperty idProperty() {
        id.set(division.getId());
        id.addListener((obs, old, wen) -> id.set((Integer)wen));
        return id;
    }

    public StringProperty nameProperty() {
        name.set(division.getName());
        name.addListener((obs, old, wen) -> name.set(wen));
        return name;
    }

    public IntegerProperty countryIdProperty() {
        countryId.set(division.getCountryId());
        countryId.addListener((obs, old, wen) -> countryId.set((Integer)wen));
        return countryId;
    }

    public ObjectProperty<Country> countryProperty() {
        country.set(division.getCountry());
        this.country.addListener((obs, old, wen) -> {
            division.setCountry(wen);
            if (wen == null) {
                // do nothing
            } else if (old == null || old.getId() != wen.getId()) {
                countryId.set(wen.getId());
            }
        });
        return country;
    }
}
