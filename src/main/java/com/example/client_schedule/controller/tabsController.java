package com.example.client_schedule.controller;

import com.example.client_schedule.MainApplication;
import com.example.client_schedule.entities.*;
import com.example.client_schedule.helper.DBContext;
import com.example.client_schedule.helper.TypeToken;
import com.sun.tools.javac.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Tab;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * The tabsController is responsible for the control flow of tab view.
 */
public class tabsController implements Initializable {

    protected DBContext db;

    protected ResourceBundle _bundle;

    @FXML
    private TabPane tabPanel;

    /**
     * Constructs a tabsController with given DBContext
     *
     * @param db DBContext object
     */
    public tabsController(DBContext db) {
        this.db = db;
    }

    private HashMap<String, tabController> controllers = new HashMap<>();

    /**
     * This is an overridden method from Initializable interface,
     * which is responsible for logic that needs to be executed after all FXML
     * components are loaded into the sense.
     *
     * @param Url    URL used for the ResourceBundle provided as a parameter
     * @param bundle ResourceBundle of the Application
     */
    @Override
    public void initialize(URL Url, ResourceBundle bundle) {
        this._bundle = bundle;
        AppointmentFormController appointmentFormController = new AppointmentFormController(this.db);
        CustomerFormController customerFormController = new CustomerFormController(this.db);
        FXMLLoader apptFxmlLoader = new FXMLLoader(MainApplication.class.getResource("appointmentForm.fxml"), _bundle);
        FXMLLoader custFxmlLoader = new FXMLLoader(MainApplication.class.getResource("customerForm.fxml"), _bundle);
        apptFxmlLoader.setController(appointmentFormController);
        custFxmlLoader.setController(customerFormController);
        Parent apptRoot = null;
        Parent custRoot = null;
        try {
            apptRoot = apptFxmlLoader.load();
            custRoot = custFxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Tab appointmentTab = new Tab(_bundle.getString("label.appointment.appointment.text"));
        Tab customerTab = new Tab(_bundle.getString("label.appointment.customer.text"));
        appointmentTab.setContent(apptRoot);
        appointmentTab.setClosable(false);
        customerTab.setContent(custRoot);
        customerTab.setClosable(false);
        tabPanel.getTabs().addAll(customerTab, appointmentTab);
    }
}
