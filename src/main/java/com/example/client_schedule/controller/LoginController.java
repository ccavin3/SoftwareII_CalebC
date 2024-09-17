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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
//      determine the user’s location (i.e., ZoneId) and displays it in a label on the log-in form       •  determine the user’s location (i.e., ZoneId) and displays it in a label on the log-in form        // Get the system's default ZoneId        ZoneId zoneId = ZoneId.systemDefault();        // Get the current date and time in the system's ZoneId        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);        // Format the date and time in a readable format        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");        // Display the Zone ID, country, and the formatted current date-time in the label        countryText.setText(String.format("%s (Zone: %s, Time: %s)",                country,                zoneId,                zonedDateTime.format(formatter)        ));
        ZoneId zoneId = ZoneId.systemDefault();
        // Get the current date and time in the system's ZoneID
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        // Format the date and time in a readable format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        // Display the Zone ID, country, and the formatted current date-time in the label
        countryText.setText(String.format("%s (Zone: %s, Time: %s)",
                country,
                zoneId,
                zonedDateTime.format(formatter)));

        onLoginAction = e -> {
            String user = textUser.getText();
            String pwd = textPwd.getText();
            if (db == null) {
                msg = _bundle.getString("login.db.error");
                loginMessage.setText(msg);
                logger.log(Level.forName("LOGIN", 350), loginMessage(msg));
//                return false;
            } else {
                msg = "";
                loginMessage.setText(msg);
                if (user == null || user.trim().isEmpty() || pwd == null || pwd.trim().isEmpty()) {
                    msg = _bundle.getString("login.invalid.creds.response");
                    loginMessage.setText(msg);
                    logger.log(Level.forName("LOGIN", 350), loginMessage(msg));
//                    return false;
                } else {
                    try {
                        Query q = db.em.createQuery("from com.example.client_schedule.entities.User where userName = :n and password = :p");
                        q.setParameter("n", user);
                        q.setParameter("p", pwd);
                        if ((long) q.getResultList().size() > 0) {
                            msg = _bundle.getString("login.successful.text");
                            loginMessage.setText("");
                            logger.log(Level.forName("LOGIN", 350), loginMessage(msg));
                            MainApplication.curUser = user;
                            launchTabForm();
                            // valid login
//                        return true;
                        } else {
                            msg = _bundle.getString("login.invalid.creds.response");
                            loginMessage.setText(msg);
                            logger.log(Level.forName("LOGIN", 350), loginMessage(msg));
//                        return false;
                        }
                    } catch(Exception ex) {
                        msg = _bundle.getString("login.db.error");
                        loginMessage.setText(msg);
                        logger.log(Level.forName("LOGIN", 350), loginMessage(msg));
                    }
                }
            }
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