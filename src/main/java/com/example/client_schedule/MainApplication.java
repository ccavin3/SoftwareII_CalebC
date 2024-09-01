package com.example.client_schedule;

import com.example.client_schedule.controller.LoginController;
import com.example.client_schedule.entities.User;
import com.example.client_schedule.helper.DBContext;
import com.example.client_schedule.helper.JDBC;
import jakarta.persistence.EntityManagerFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The Main application class. Sets the stage, bundles resources, sets locale
 */
public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Locale locale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("com.example.client_schedule.i18n", locale);
        LoginController controller = new LoginController(new DBContext(), locale.getDisplayCountry());
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"), bundle);
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The entry point of application. Makes connection with the Database
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        JDBC.makeConnection();
        launch();
        JDBC.closeConnection();
    }
}