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

    /**
     * Constructor for UserFXAdapter.
     *
     * @param user The User object to be adapted
     * @param db   The DBContext to be used for database operations
     */
    public UserFXAdapter(User user, DBContext db) {
        this.user = user;
        this.db = db;
    }

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty userName = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();

    /**
     * Creates a JavaFX Property for the User's ID.
     *
     * @return The ID property of the User
     */
    public IntegerProperty idProperty() {
        id.set(user.getId());
        this.id.addListener((obs, old, wen) -> user.setId((Integer) wen));
        return id;
    }

    /**
     * Creates a JavaFX Property for the User's name.
     *
     * @return The name property of the User
     */
    public StringProperty userNameProperty() {
        userName.set(user.getUserName());
        this.userName.addListener((obs, old, wen) -> user.setUserName(wen));
        return userName;
    }

    /**
     * Creates a JavaFX Property for the User's password.
     *
     * @return The password property of the User
     */
    public StringProperty passwordProperty() {
        password.set(user.getPassword());
        this.password.addListener((obs, old, wen) -> user.setPassword(wen));
        return password;
    }
}
