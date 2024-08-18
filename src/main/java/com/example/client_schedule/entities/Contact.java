package com.example.client_schedule.entities;

import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name="contacts")

public class Contact {
//region Entity Columns
    @Id
    @FXML
    @Column(name="Contact_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @FXML
    @Column(name="Contact_Name")
    private String name;

    @FXML
    @Column(name="Email")
    private String email;
//endregion

//region ORM relationship
    @OneToMany(mappedBy = "contact")
    private List<Appointment> appointments = new ArrayList<>();

//endregion

//region Constructors
    public Contact() {
    }

    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
//endregion

//region getters-setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//endregion
}
