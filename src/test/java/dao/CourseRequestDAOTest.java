package dao;

import model.CourseRequest;
import org.junit.jupiter.api.*;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Unit tests for the `CourseRequestDAO` class.
 * These tests verify the CRUD operations performed on the `courserequest` table.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourseRequestDAOTest {

    // Database connection used for testing
    private Connection connection;

    // DAO instance for interacting with the database
    private CourseRequestDAO dao;

    /**
     * Sets up the database connection and initializes the DAO before any tests run.
     *
     * @throws Exception if the database connection fails
     */
    @BeforeAll
    void setUp() throws Exception {
        connection = DatabaseConnection.getConnection();
        dao = new CourseRequestDAO(connection);
    }

    /**
     * Prepares test data before each test.
     * Resets the `courserequest` table and inserts predefined rows.
     *
     * @throws SQLException if an error occurs while resetting or populating the table
     */
    @BeforeEach
    void setUpTestData() throws SQLException {
        // Clear the table and reset the ID sequence
        connection.createStatement().execute("TRUNCATE courserequest RESTART IDENTITY CASCADE");

        // Insert test data into the table
        connection.createStatement().execute(
                "INSERT INTO courserequest (course_id, professional_id, status, created_at) VALUES " +
                        "(1, 1, 'PENDING', NOW()), " +
                        "(2, 2, 'PENDING', NOW());"
        );
    }

    /**
     * Cleans up the test data after each test.
     * Truncates the `courserequest` table to ensure a clean state.
     *
     * @throws SQLException if an error occurs while truncating the table
     */
    @AfterEach
    void cleanupTestData() throws SQLException {
        connection.createStatement().execute("TRUNCATE courserequest RESTART IDENTITY CASCADE");
    }

    /**
     * Closes the database connection after all tests are complete.
     *
     * @throws Exception if the connection closure fails
     */
    @AfterAll
    void tearDown() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * Tests the `createRequest` method to ensure a new request is successfully created.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testCreateRequest() throws Exception {
        // Create a new request and verify the generated ID
        int requestId = dao.createRequest(3, 3, "PENDING");
        Assertions.assertTrue(requestId > 0, "Request ID should be generated");
    }

    /**
     * Tests the `getRequestById` method to ensure a request can be retrieved by its ID.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testGetRequestById() throws Exception {
        // Retrieve a request by ID and verify its attributes
        CourseRequest request = dao.getRequestById(1);
        Assertions.assertNotNull(request, "Request should not be null");
        Assertions.assertEquals("PENDING", request.getStatus(), "Status should match");
    }

    /**
     * Tests the `updateRequestStatus` method to ensure a request's status is successfully updated.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testUpdateRequestStatus() throws Exception {
        // Update the status of a request and verify the operation
        boolean updated = dao.updateRequestStatus(1, "ACCEPTED");
        Assertions.assertTrue(updated, "Update should return true");

        // Retrieve the updated request and verify the new status
        CourseRequest request = dao.getRequestById(1);
        Assertions.assertEquals("ACCEPTED", request.getStatus(), "Status should be updated");
    }

    /**
     * Tests the `deleteRequest` method to ensure a request is successfully deleted.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testDeleteRequest() throws Exception {
        // Delete a request and verify the operation
        boolean deleted = dao.deleteRequest(1);
        Assertions.assertTrue(deleted, "Delete should return true");

        // Attempt to retrieve the deleted request and verify it no longer exists
        CourseRequest request = dao.getRequestById(1);
        Assertions.assertNull(request, "Request should no longer exist");
    }
}
