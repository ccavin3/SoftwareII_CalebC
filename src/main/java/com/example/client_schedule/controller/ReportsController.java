package com.example.client_schedule.controller;

import com.example.client_schedule.adapters.apptReportFXAdapter;
import com.example.client_schedule.helper.DBContext;
import com.example.client_schedule.helper.apptReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import com.example.client_schedule.entities.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import javax.swing.*;

public class ReportsController implements Initializable {
    DBContext db;

    @FXML
    private Button homeButton;
    @FXML
    private EventHandler<ActionEvent> onHomeAction;
    @FXML
    private EventHandler<ActionEvent> onCountrySelectionAction;
    @FXML
    private EventHandler<ActionEvent> onContactSelectionAction;

    @FXML
    private TableView<Customer> countryTV;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, String> customerAddressColumn;
    @FXML
    private TableColumn<Customer, String> customerPostalColumn;
    @FXML
    private TableColumn<Customer, String> customerPhoneColumn;

    @FXML
    private TableView<Appointment> scheduleTV;
    @FXML
    private TableColumn<Appointment, String> appointmentTitleColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentTypeColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentDescriptionColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentStartColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentEndColumn;

    @FXML
    private TableView<apptReportFXAdapter> type_MonthTV;
    @FXML
    private TableColumn<apptReport, Integer> monthColumn;
    @FXML
    private TableColumn<apptReport, String> typeColumn;
    @FXML
    private TableColumn<apptReport, Integer> countColumn;

    @FXML
    private Tab countryTab;
    @FXML
    private Tab scheduleTab;
    @FXML
    private Tab type_MonthTab;
    @FXML
    private ComboBox<Country> countryComboBox;
    @FXML
    private ComboBox<Contact> contactComboBox;

    private FilteredList<Customer> flCustomerListByCountry;
    private FilteredList<Appointment> flAppointmentsByContact;

    /**
     * Constructs a ReportsController with a given DBContext.
     *
     * @param db the DBContext
     */
    public ReportsController(DBContext db) {
        this.db = db;
    }

    private Comparator<Appointment> MTComparator =
            Comparator
                    .comparing((Appointment a) -> a.getStart().getMonth())
                    .thenComparing(Appointment::getType);

    /**
     * Initializes the ReportsController.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // code omitted for brevity
    }

    /**
     * Closes the application window.
     */
    public void goHome() {
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Filters appointments by selected contact.
     */
    public void filterContacts() {
        flAppointmentsByContact.setPredicate(p -> p.getContactId() == contactComboBox.getSelectionModel().getSelectedItem().getId());

    }

    /**
     * Filters customers by selected country.
     */
    public void filterCountries() {
        flCustomerListByCountry.setPredicate(p -> p.getDivision().getCountryId() == countryComboBox.getSelectionModel().getSelectedItem().getId());
    }
}