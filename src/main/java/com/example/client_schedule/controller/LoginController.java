package com.example.client_schedule.controller;

import com.example.client_schedule.MainApplication;
import com.example.client_schedule.helper.DBContext;
import jakarta.persistence.Query;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * The Login page controller.
 */
public class LoginController implements Initializable {

    /**
     * The Logger.
     */
    Logger logger = LogManager.getLogger(LoginController.class);

    private ResourceBundle _bundle;

    @FXML
    private Label countryLabel;
    @FXML
    private Label countryText;

    @FXML
    private Label usernameLabel;

    /**
     * The Text user.
     */
    @FXML
    TextField textUser;

    /**
     * The Pwd label.
     */
    @FXML
    Label pwdLabel;

    @FXML
    private PasswordField textPwd;

    @FXML
    private Button loginButton;

    @FXML
    private EventHandler<ActionEvent> onLoginAction;

    @FXML
    private Label loginMessage;

    private DBContext db;

    private String country;

    /**
     * Instantiates a new Login controller.
     *
     * @param country the country
     */
    public LoginController(DBContext db, String country) {
        this.db = db;
        this.country = country;
    }

    private String msg;

    /**
     * Initialization method for the controller.
     *
     * @param url            the url to use for initialization
     * @param resourceBundle the resources to use for initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _bundle = resourceBundle;
        countryText.setText(country);


        onLoginAction = e -> {
            // implementation here...
        };
        loginButton.setOnAction(onLoginAction);
    }

    /**
     * Returns the login message for a given message.
     *
     * @param msg the input message.
     * @return the login message.
     */
    private String loginMessage(String msg) {
        return String.format("User: %s - %s", MainApplication.curUser, msg);
    }

    /**
     * Launches the tab form for this controller.
     *
     * @throws IOException if an error occurs during the launching of the tab form.
     */
    private void launchTabForm() throws IOException {
        Stage thiswindow = (Stage) loginButton.getScene().getWindow();
        tabsController controller = new tabsController(db);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("tabs.fxml"), _bundle);
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 900, 600);
        Stage stage = new Stage();
        stage.setTitle("Schedule Data");
        stage.setScene(scene);
        stage.show();
        thiswindow.close();
    }
}