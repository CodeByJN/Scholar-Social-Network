package util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Utility class for managing database connections using HikariCP.
 * This class provides methods to initialize the database, obtain connections,
 * and manage connection pooling.
 */
public class DatabaseConnection {

    private static HikariDataSource dataSource;

    // Static block for initializing the data source
    static {
        try {
            Properties props = new Properties();
            try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("database.properties")) {
                props.load(input);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error initializing database connection", e);
            }

            // Configure HikariCP for PostgreSQL
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));
            config.setDriverClassName("org.postgresql.Driver");

            // Additional connection pool settings
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(5);
            config.setIdleTimeout(30000);
            config.setMaxLifetime(2000000);
            config.setConnectionTimeout(30000);

            // Create data source
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing database connection", e);
        }
    }

    /**
     * Obtains a connection from the connection pool.
     *
     * @return a {@link Connection} object from the pool.
     * @throws SQLException if a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Closes the data source, releasing all resources held by the connection pool.
     * Should be called when the application shuts down.
     */
    public static void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    /**
     * Retrieves the initialized {@link DataSource}.
     *
     * @return the data source used for managing connections.
     */
    public static DataSource lookupDataSource() {
        return dataSource;
    }

    /**
     * Initializes the database schema by creating necessary tables if they do not exist.
     * This includes tables for Users, Courses, Requests, and Notifications.
     *
     * This method is idempotent and will not overwrite existing tables.
     */
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Create Users Table
            stmt.execute("CREATE TABLE IF NOT EXISTS Users (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "email VARCHAR(100) UNIQUE, " +
                    "password VARCHAR(255), " +
                    "user_type VARCHAR(50)" +
                    ")");

            // Create Courses Table
            stmt.execute("CREATE TABLE IF NOT EXISTS Courses (" +
                    "id SERIAL PRIMARY KEY, " +
                    "course_title VARCHAR(200), " +
                    "course_code VARCHAR(50), " +
                    "term VARCHAR(50), " +
                    "owner_id INT, " +
                    "FOREIGN KEY (owner_id) REFERENCES Users(id)" +
                    ")");

            // Create Requests Table
            stmt.execute("CREATE TABLE IF NOT EXISTS Requests (" +
                    "id SERIAL PRIMARY KEY, " +
                    "course_id INT, " +
                    "applicant_id INT, " +
                    "status VARCHAR(20), " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                    ")");

            // Create Notifications Table
            stmt.execute("CREATE TABLE IF NOT EXISTS Notifications (" +
                    "id SERIAL PRIMARY KEY, " +
                    "recipient_id INT, " +
                    "message TEXT, " +
                    "is_read BOOLEAN DEFAULT FALSE, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing database tables", e);
        }
    }
}
