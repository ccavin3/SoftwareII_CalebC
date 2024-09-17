package com.example.client_schedule.adapters;

import com.example.client_schedule.entities.Contact;
import javafx.beans.property.*;

public class ContactFXAdapter {
    public Contact contact;

    /**
     * Constructor for ContactFXAdapter.
     *
     * @param contact the contact
     */
    public ContactFXAdapter(Contact contact) {
        this.contact = contact;
    }

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id.get();
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email.get();
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id.set(id);
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email.set(email);
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Gets id property.
     *
     * @return the id property
     */
    public IntegerProperty idProperty() {
        id.set(contact.getId());
        id.addListener((obs, old, wen) -> contact.setId((Integer) wen));
        return id;
    }

    /**
     * Gets name property.
     *
     * @return the name property
     */
    public StringProperty nameProperty() {
        name.set(contact.getName());
        name.addListener((obs, old, wen) -> contact.setName(wen));
        return name;
    }

    /**
     * Gets email property.
     *
     * @return the email property
     */
    public StringProperty emailProperty() {
        email.set(contact.getEmail());
        email.addListener((obs, old, wen) -> contact.setEmail(wen));
        return email;
    }
}
