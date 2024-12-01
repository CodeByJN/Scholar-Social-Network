package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing database connections to a PostgreSQL database.
 */
public class DatabaseConnection {

    // Static initializer block to load the PostgreSQL JDBC Driver
    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found. Ensure the dependency is added to the project.", e);
        }
    }

    /**
     * Establishes and returns a connection to the PostgreSQL database.
     *
     * @return a Connection object for the PostgreSQL database.
     * @throws SQLException if a database access error occurs or the connection URL is incorrect.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/AEP", "postgres", "3578");
    }
}
