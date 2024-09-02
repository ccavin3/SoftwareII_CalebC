package com.example.client_schedule.adapters;

import com.example.client_schedule.entities.Country;
import com.example.client_schedule.entities.Division;
import com.example.client_schedule.entities.Division;
import javafx.beans.property.*;

public class DivisionFXAdapter {
    public Division division;

    public DivisionFXAdapter(Division division) {
        this.division = division;

        this.id.set(division.getId());
        this.id.addListener((obs, old, wen) -> division.setId((Integer) wen));

        this.name.set(division.getName());
        this.name.addListener((obs, old, wen) -> division.setName(wen));

        this.country.set(division.getCountry());
        this.country.addListener((obs, old, wen) -> {
            division.setCountry(wen);
            if (wen == null) {
                // do nothing
            } else if (old == null || old.getId() != wen.getId()) {
                countryId.set(wen.getId());
            }
        });
        this.countryId.set(division.getCountryId());
        this.countryId.addListener((obs, old, wen) -> division.setCountryId((Integer) wen));
    }

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty countryId = new SimpleIntegerProperty();
    private final ObjectProperty<Country> country = new SimpleObjectProperty<>();

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public IntegerProperty countryIdProperty() {
        return countryId;
    }

    public ObjectProperty<Country> countryProperty() {
        return country;
    }
}
