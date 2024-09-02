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

public class tabsController implements Initializable {

    protected DBContext db;

    protected ResourceBundle _bundle;

    @FXML
    private TabPane tabPanel;

    public tabsController(DBContext db) {
        this.db = db;
    }

//    class AppointmentTabController extends tabController<Appointment> {};
//    class ContactTabController extends tabController<Contact> {}
//    class CountryTabController extends tabController<Country> {}
//    class CustomerTabController extends tabController<Customer> {}
//    class DivisionTabController extends tabController<Division> {}
//    class UserTabController extends tabController<User> {}

    private HashMap<String, tabController> controllers = new HashMap<>();


//    public tabsController() {

//        controllers.put("Appointment", new AppointmentTabController(this.db, this.userName));
//        controllers.put("Contact", new ContactTabController());
//        controllers.put("Country", new CountryTabController());
//        controllers.put("Customer", new CustomerTabController());
//        controllers.put("Division", new DivisionTabController());
//        controllers.put("User", new UserTabController());
//        for(tabController controller : controllers.values()) {
//            controller.setUserName(this.userName);
//            controller.setDb(this.db);
//            try {
//                controller.setTabTitle(_bundle.getString("tab."+controller.getGenericClass().getSimpleName().toLowerCase()+".title"));
//            } catch(Exception e) {
//
//            }
//        }
//    }

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
        customerTab.setContent(custRoot);
        tabPanel.getTabs().addAll(customerTab, appointmentTab);
    }

}
