package com.example.client_schedule.controller;

import com.example.client_schedule.entities.*;
import com.example.client_schedule.helper.DBContext;
import com.example.client_schedule.helper.TypeToken;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Tab;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public abstract class tabsController implements Initializable {

    protected DBContext db;

    protected String userName;

    protected ResourceBundle _bundle;

    @FXML
    protected TableView tableView;

    @FXML
    protected Button insertButton;

    @FXML
    protected Button deleteButton;

    @FXML
    protected Button revertButton;

    @FXML
    protected Button commitButton;

    @FXML
    protected HBox tableViewControlButtons;

    @FXML
    protected HBox addInsert;

    @FXML
    protected HBox commitRevert;

    @FXML
    protected VBox tabContent;

    public tabsController(DBContext db, String userName) {
        this.db = db;
        this.userName = userName;
    }

    class AppointmentTabController extends tabController<Appointment> {};
    class ContactTabController extends tabController<Contact> {}
    class CountryTabController extends tabController<Country> {}
    class CustomerTabController extends tabController<Customer> {}
    class DivisionTabController extends tabController<Division> {}
    class UserTabController extends tabController<User> {}

    private HashMap<String, tabController> controllers = new HashMap<>();

    public tabsController() {
        controllers.put("Appointment", new AppointmentTabController());
        controllers.put("Contact", new ContactTabController());
        controllers.put("Country", new CountryTabController());
        controllers.put("Customer", new CustomerTabController());
        controllers.put("Division", new DivisionTabController());
        controllers.put("User", new UserTabController());
        for(tabController controller : controllers.values()) {
            controller.setUserName(this.userName);
            controller.setDb(this.db);
            try {
                controller.setTabTitle(_bundle.getString("tab."+controller.getGenericClass().getSimpleName().toLowerCase()+".title"));
            } catch(Exception e) {

            }
        }
    }

    @Override
    public void initialize(URL Url, ResourceBundle bundle) {
        this._bundle = bundle;
        Tab userTab = new Tab();

    }

}
