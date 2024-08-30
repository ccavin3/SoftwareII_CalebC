package com.example.client_schedule.helper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


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

    public static void makeConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // reference Connection object
            connectionStatus = "Database Connection successful!";
        }
        catch(ClassNotFoundException e) {
            System.out.println("Error:" + e.getMessage());
        }
        catch(SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

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
