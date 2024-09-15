package com.example.client_schedule.controller;

import com.example.client_schedule.helper.TypeToken;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.util.ResourceBundle;
import com.example.client_schedule.MainApplication;
import com.example.client_schedule.entities.User;
import com.example.client_schedule.helper.DBContext;
import com.example.client_schedule.helper.JDBC;
import jakarta.persistence.Query;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.function.Predicate;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class tabController<T> extends TypeToken<T> implements Initializable {

    protected DBContext db;
    protected ObservableList<T> rows;
    protected ResourceBundle _bundle;

    @FXML
    protected String tabTitle;

    /**
     * Gets the tab title.
     *
     * @return the tab title
     */
    public String getTabTitle() {
        return tabTitle;
    }

    /**
     * Sets the tab title.
     *
     * @param tabTitle tab title to be set
     */
    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

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

    /**
     * Default Constructor.
     */
    public tabController() {
    }

    /**
     * Constructor which initiates the DBContext.
     *
     * @param db DBContext
     */
    public tabController(DBContext db) {
        this.db = db;
    }

    /**
     * Initializes the controller.
     *
     * @param Url    URL used for the ResourceBundle
     * @param bundle ResourceBundle of the Application
     */
    @Override
    public void initialize(URL Url, ResourceBundle bundle) {
        this._bundle = bundle;
    }

    /**
     * Get the current DBContext.
     *
     * @return the DBContext of the application
     */
    public DBContext getDb() {
        return db;
    }

    /**
     * Set a new DBContext.
     *
     * @param db new DBContext to be set
     */
    public void setDb(DBContext db) {
        this.db = db;
    }
}
