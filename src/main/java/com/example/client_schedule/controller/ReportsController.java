package com.example.client_schedule.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import com.example.client_schedule.helper.DBContext;
import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Reports controller.
 */
public class ReportsController implements Initializable {

    private DBContext db;

    @FXML
    private Button homeButton;

    @FXML
    private ComboBox comboBoxContact;

    @FXML
    private ComboBox comboBoxCountry;

    @FXML
    private EventHandler<ActionEvent> selectContact;

    @FXML
    private EventHandler<ActionEvent> selectCountry;

    @FXML
    private EventHandler<ActionEvent> returnHome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeButton.setOnAction(returnHome);
        comboBoxContact.setItems(db.contacts);
        comboBoxCountry.setItems(db.countries);

    }


}
