package com.example.client_schedule.adapters;

import com.example.client_schedule.entities.Country;
import com.example.client_schedule.entities.User;
import com.example.client_schedule.entities.User;
import com.example.client_schedule.helper.DBContext;
import javafx.beans.property.*;

import java.util.SplittableRandom;

public class UserFXAdapter {
    public User user;
    private DBContext db;

    public UserFXAdapter(User user, DBContext db) {
        this.user = user;
        this.db = db;
    }

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty userName = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();

    public IntegerProperty idProperty() {
        id.set(user.getId());
        this.id.addListener((obs, old, wen) -> user.setId((Integer) wen));
        return id;
    }

    public StringProperty userNameProperty() {
        userName.set(user.getUserName());
        this.userName.addListener((obs, old, wen) -> user.setUserName(wen));
        return userName;
    }

    public StringProperty passwordProperty() {
        password.set(user.getPassword());
        this.password.addListener((obs, old, wen) -> user.setPassword(wen));
        return password;
    }
}
