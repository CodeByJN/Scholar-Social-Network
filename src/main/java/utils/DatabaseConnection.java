package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/AEP";
    private static final String USER = "postgres";
    private static final String PASSWORD = "7347";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found. Add the dependency to your project.", e);
        }

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Database connection established successfully!");
        return connection;
    }
}
