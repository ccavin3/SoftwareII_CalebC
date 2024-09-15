package com.example.client_schedule.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The JDBC class provides methods to establish
 * and close a connection to the database.
 */
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sql" +
            "'" +
            "k/gcd" +
            "User";
    private static String password = "Passw0rd!";
    public static Connection connection;
    private static PreparedStatement preparedStatement;

    public static String connectionStatus;

    /**
     * The method establishes a connection to the database.
     * It uses Class.forName(driver) to locate the Driver and DriverManager.getConnection(jdbcUrl, userName, password) to reference Connection object.
     * If a connection is successful, "Database Connection successful!" will be set as connection status
     * If a connection is not successful, prints the error message
     */
    public static void makeConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // reference Connection object
            connectionStatus = "Database Connection successful!";
        } catch (ClassNotFoundException e) {
            System.out.println("Error:" + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * The method closes the connection to the database.
     * If a connection is not null, it will be closed.
     * Once the connection is closed, "Database Connection closed!" will be set as connection status
     * If attempts to close a connection is not successful, prints the error message
     */
    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
            connectionStatus = "Database Connection closed!";
            System.out.println(connectionStatus);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
