package com.example.client_schedule.entities;

import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

/**
 * The type Contact.
 */
@Entity
@Table(name="contacts")

public class Contact {
//region Entity Columns
    @Id
    @FXML
    @Column(name="Contact_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected int id;

    @FXML
    @Column(name="Contact_Name")
    protected String name;

    @FXML
    @Column(name="Email")
    protected String email;
//endregion

//region ORM relationship
    @OneToMany(mappedBy = "contact")
    protected List<Appointment> appointments = new ArrayList<>();

//endregion

    /**
     * Instantiates a new Contact.
     */
//region Constructors
    public Contact() {
    }

    /**
     * Instantiates a new Contact.
     *
     * @param id    the id
     * @param name  the name
     * @param email the email
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
//endregion

//region getters-setters

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

//endregion
}
