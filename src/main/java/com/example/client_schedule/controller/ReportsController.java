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
    private TableColumn<Customer, Integer> countryTabCustomerIdColumn;
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
    private TableColumn<Appointment, Integer> appointmentIdColumn;
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
    private TableColumn<Appointment, Integer> customerIdColumn;

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
        countryComboBox.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country c) {
                if (c == null) {
                    return "";
                } else {
                    return c.getName();
                }
            }

            @Override
            public Country fromString(String s) {
                return countryComboBox.getItems().stream().filter(ap -> ap.getName().equals(s)).findFirst().orElse(null);
            }
        });
        contactComboBox.setConverter(new StringConverter<Contact>() {
            @Override
            public String toString (Contact c){
                if (c == null) {
                    return "";
                } else {
                    return c.getName();
                }
            }

            @Override
            public Contact fromString (String s){
               return contactComboBox.getItems().stream().filter(ap -> ap.getName().equals(s)).findFirst().orElse(null);
            }
        });

        countryComboBox.setItems(db.countries);
        contactComboBox.setItems(db.contacts);
        flCustomerListByCountry = new FilteredList<>(db.customers, p -> p.getDivision().getCountryId() == countryComboBox.getItems().get(0).getId());
        flAppointmentsByContact = new FilteredList<>(db.appointments, p -> p.getContactId() == contactComboBox.getItems().get(0).getId());
        List<Appointment> flAppointmentsByTypeMonth =
                db.appointments
                        .stream()
                        .sorted(MTComparator)
                        .collect(Collectors.toList());
        //                        .filter(a -> a.getStart().toLocalDate().isEqual(LocalDate.now()) || a.getStart().toLocalDate().isAfter(LocalDate.now()))

        ObservableList<apptReportFXAdapter> reportList = FXCollections.observableArrayList(
                flAppointmentsByTypeMonth
                .stream()
                .collect(Collectors.groupingBy(a -> Arrays.asList(a.getStart().getMonthValue(), a.getType()), Collectors.counting()))
                .entrySet()
                .stream()
                .map(e -> new apptReportFXAdapter(
                        new apptReport(
                                Integer.parseInt(e.getKey().get(0).toString()),
                                e.getKey().get(1).toString(),
                                Integer.parseInt(e.getValue().toString())
                        )
                ))
                .sorted(Comparator.comparing(a -> a.getMonth()))
                .collect(Collectors.toList()));
        // assuming ApptReport(Month month, String type, Long count) constructor

        ObservableList<apptReportFXAdapter> mtappointmentList = FXCollections.observableList(reportList);


        // type_Month TV
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));

        //Schedule TV
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));


        //Country TV
        countryTabCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalColumn.setCellValueFactory(new PropertyValueFactory<>("zip"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        countryTV.setItems(flCustomerListByCountry);
        scheduleTV.setItems(flAppointmentsByContact);
        type_MonthTV.setItems(mtappointmentList);

        onHomeAction = e -> goHome();

        homeButton.setOnAction(onHomeAction);

        onCountrySelectionAction = e -> filterCountries();
        countryComboBox.setOnAction(onCountrySelectionAction);
        onContactSelectionAction = e -> filterContacts();
        contactComboBox.setOnAction(onContactSelectionAction);

    }


    public void goHome(){
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Filters appointments by selected contact.
     * @lambda filterContacts  line 219 This changes the predicate controlling the filter. If the resultant comparison evaluates to true, the row will be included in the subset
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