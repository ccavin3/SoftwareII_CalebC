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

/**
 * The type Tab controller.
 *
 * @param <T> the type parameter
 */
public abstract class tabController<T> extends TypeToken<T> implements Initializable {

    /**
     * The Db.
     */
    protected DBContext db;

    /**
     * The User name.
     */
    protected String userName;

    /**
     * The Rows.
     */
    protected ObservableList<T> rows;

    /**
     * The Bundle.
     */
    protected ResourceBundle _bundle;

    /**
     * The Tab title.
     */
    @FXML
    protected String tabTitle;

    /**
     * Gets tab title.
     *
     * @return the tab title
     */
    public String getTabTitle() {
        return tabTitle;
    }

    /**
     * Sets tab title.
     *
     * @param tabTitle the tab title
     */
    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    /**
     * The Table view.
     */
    @FXML
    protected TableView tableView;

    /**
     * The Insert button.
     */
    @FXML
    protected Button insertButton;

    /**
     * The Delete button.
     */
    @FXML
    protected Button deleteButton;

    /**
     * The Revert button.
     */
    @FXML
    protected Button revertButton;

    /**
     * The Commit button.
     */
    @FXML
    protected Button commitButton;

    /**
     * The Table view control buttons.
     */
    @FXML
    protected HBox tableViewControlButtons;

    /**
     * The Add insert.
     */
    @FXML
    protected HBox addInsert;

    /**
     * The Commit revert.
     */
    @FXML
    protected HBox commitRevert;

    /**
     * The Tab content.
     */
    @FXML
    protected VBox tabContent;

    /**
     * Instantiates a new Tab controller.
     */
    public tabController() {}

    /**
     * Instantiates a new Tab controller.
     *
     * @param db       the db
     * @param userName the user name
     */
    public tabController(DBContext db, String userName) {
        this.db = db;
        this.userName = userName;
    }

    @Override
    public void initialize(URL Url, ResourceBundle bundle) {
        this._bundle = bundle;
    }

    /**
     * Gets db.
     *
     * @return the db
     */
    public DBContext getDb() {
        return db;
    }

    /**
     * Sets db.
     *
     * @param db the db
     */
    public void setDb(DBContext db) {
        this.db = db;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
