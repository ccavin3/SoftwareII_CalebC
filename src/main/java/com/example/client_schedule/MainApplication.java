package com.example.client_schedule;

import com.example.client_schedule.controller.LoginController;
import com.example.client_schedule.entities.Appointment;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Main application class
 */
public class MainApplication extends Application {

    /**
     * Current user identifier
     */
    public static String curUser;


    public static boolean betweenDates(LocalDateTime needle, LocalDateTime start, LocalDateTime end) {
        return (needle.isAfter(start) && needle.isBefore(end))
                || needle.isEqual(start)
                || needle.isEqual(end);
    }

    public static boolean overlapping(LocalDateTime d1, LocalDateTime d2, LocalDateTime d3, LocalDateTime d4) {
        return betweenDates(d1, d3, d4)
                || betweenDates(d2, d3, d4)
                || betweenDates(d3, d1, d1)
                || betweenDates(d4, d1, d2);
    }

    public static boolean betweenHours(LocalTime needle, LocalTime start, LocalTime end) {
        return (needle.isAfter(start) && needle.isBefore(end))
                || needle.equals(start)
                || needle.equals(end);
    }

    /**
     * Starts the JavaFX application
     *
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set
     * @throws IOException if loading the FXML document fails
     */
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
     * The main entry point for all JavaFX applications
     * It establishes a database connection, then launches the javafx application and finally close the database connection
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JDBC.makeConnection();
        launch();
        JDBC.closeConnection();
    }
}