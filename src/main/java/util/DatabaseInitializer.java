package util;

import util.DatabaseConnection;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * A listener to handle database setup when the application starts or stops.
 */
@WebListener
public class DatabaseInitializer implements ServletContextListener {

    /**
     * Called when the application starts.
     * Initializes database tables.
     *
     * @param sce the event with the application context.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            DatabaseConnection.initializeDatabase();
            System.out.println("Database initialized successfully");
        } catch (Exception e) {
            System.err.println("Failed to initialize database");
            e.printStackTrace();
        }
    }

    /**
     * Called when the application stops.
     * Closes the database connection pool.
     *
     * @param sce the event with the application context.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DatabaseConnection.closeDataSource();
    }
}
