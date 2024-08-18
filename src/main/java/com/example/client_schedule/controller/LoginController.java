package com.example.client_schedule.controller;

import com.example.client_schedule.MainApplication;
import com.example.client_schedule.helper.DBContext;
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

public class LoginController implements Initializable {

    Logger logger = LogManager.getLogger(LoginController.class);

    private ResourceBundle _bundle;

    @FXML
    private Label countryLabel;
    @FXML
    private Label countryText;

    @FXML
    private Label usernameLabel;

    @FXML TextField textUser;

    @FXML
    Label pwdLabel;

    @FXML
    private TextField textPwd;

    @FXML
    private Button loginButton;

    @FXML
    private EventHandler<ActionEvent> onLoginAction;

    @FXML
    private Label loginMessage;

    private DBContext db = new DBContext();

    private String country;
    private String userName;

    public LoginController(String country) {
        this.country = country;
        db = new DBContext();
    }

    private String msg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _bundle = resourceBundle;
        countryText.setText(country);
        onLoginAction = e -> {
            String user = userName = textUser.getText();
            String pwd = textPwd.getText();
            if (db == null) {
                msg = _bundle.getString("login.db.error");
                loginMessage.setText(msg);
                logger.log(Level.forName("LOGIN", 350), loginMessage(msg));
//                return false;
            } else {
                msg = "";
                loginMessage.setText(msg);
                if (user == null || user.trim() == "" || pwd == null || pwd.trim() == "") {
                    msg = _bundle.getString("login.invalid.creds.response");
                    loginMessage.setText(msg);
                    logger.log(Level.forName("LOGIN", 350), loginMessage(msg));
//                    return false;
                } else {
                    try {
                        Query q = db.em.createQuery("from com.example.client_schedule.entities.User where userName = :n and password = :p");
                        q.setParameter("n", user);
                        q.setParameter("p", pwd);
                        if (q.getResultList().stream().count() > 0) {
                            msg = _bundle.getString("login.successful.text");
                            loginMessage.setText("");
                            logger.log(Level.forName("LOGIN", 350), loginMessage(msg));
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

    private String loginMessage(String msg) {
        return String.format("User: %s - %s", userName, msg);
    }

    private void launchTabForm() throws IOException {
        Stage thiswindow = (Stage)loginButton.getScene().getWindow();
        CustomerFormController controller = new CustomerFormController(db, userName);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("customerForm.fxml"), _bundle);
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1200, 945);
        Stage stage = new Stage();
        stage.setTitle("Schedule Data");
        stage.setScene(scene);
        stage.show();
        thiswindow.close();
    }
}