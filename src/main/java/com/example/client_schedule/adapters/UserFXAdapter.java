package com.example.client_schedule.adapters;

import com.example.client_schedule.entities.Country;
import com.example.client_schedule.entities.User;
import com.example.client_schedule.entities.User;
import javafx.beans.property.*;

import java.util.SplittableRandom;

public class UserFXAdapter {
    public User user;

    public UserFXAdapter(User user) {
        this.user = user;

        this.id.set(user.getId());
        this.id.addListener((obs, old, wen) -> user.setId((Integer) wen));

        this.userName.set(user.getUserName());
        this.userName.addListener((obs, old, wen) -> user.setUserName(wen));

        this.password.set(user.getPassword());
        this.password.addListener((obs, old, wen) -> user.setPassword(wen));
    }

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty userName = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public StringProperty countryIdProperty() {
        return password;
    }
}
