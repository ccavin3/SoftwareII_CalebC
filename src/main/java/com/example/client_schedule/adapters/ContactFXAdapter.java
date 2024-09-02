package com.example.client_schedule.adapters;

import com.example.client_schedule.entities.Contact;
import javafx.beans.property.*;

public class ContactFXAdapter {
    public Contact contact;

    public ContactFXAdapter(Contact contact) {
        this.contact = contact;

        this.id.set(contact.getId());
        this.id.addListener((obs, old, wen) -> contact.setId((Integer) wen));

        this.name.set(contact.getName());
        this.name.addListener((obs, old, wen) -> contact.setName(wen));

        this.email.set(contact.getEmail());
        this.email.addListener((obs, old, wen) -> contact.setEmail(wen));
    }

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty emailProperty() {
        return email;
    }
}
