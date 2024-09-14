package com.example.client_schedule.adapters;

import com.example.client_schedule.entities.Contact;
import javafx.beans.property.*;

public class ContactFXAdapter {
    public Contact contact;

    public ContactFXAdapter(Contact contact) {
        this.contact = contact;
    }

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();

    public Integer getId() {
        return id.get();
    }
    public String getName() {
        return name.get();
    }

    public String getEmail() {
        return email.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public IntegerProperty idProperty() {
        id.set(contact.getId());
        id.addListener((obs, old, wen) -> contact.setId((Integer) wen));
        return id;
    }

    public StringProperty nameProperty() {
        name.set(contact.getName());
        name.addListener((obs, old, wen) -> contact.setName(wen));
        return name;
    }

    public StringProperty emailProperty() {
        email.set(contact.getEmail());
        email.addListener((obs, old, wen) -> contact.setEmail(wen));
        return email;
    }
}
