package dao;

import model.AcademicInstitution;
import model.AcademicProfessional;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Test class for testing {@link UserDAOImpl}.
 * This class tests methods for inserting and retrieving users, including academic professionals and institutions.
 */
public class UserDAOImplTest {
    private UserDAO userDAO;

    /**
     * Sets up the test environment by initializing the UserDAO instance before each test.
     * Ensures that a fresh UserDAO is used for every test case.
     */
    @BeforeEach
    void setUp() {
        userDAO = UserDAOImpl.getInstance(); // Initialize UserDAO instance
    }

    /**
     * Tears down the test environment by cleaning up the database after each test.
     * Deletes any users inserted during the tests to ensure isolation between test cases.
     */
    @AfterEach
    void tearDown() {
        String sql = "DELETE FROM Users WHERE email LIKE 'test%'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate(); // Clean up test data
        } catch (SQLException e) {
            e.printStackTrace(); // Print stack trace in case of SQL errors during cleanup
        }
    }

    /**
     * Tests inserting an {@link AcademicProfessional} user into the database.
     * Verifies that the user can be correctly inserted and retrieved.
     */
    @Test
    void testInsertUser_AcademicProfessional() {
        // Arrange: Create an AcademicProfessional user
        User user = new AcademicProfessional(
                "Professor John",                // Name
                "test.prof@example.com",         // Email
                "password123",                   // Password
                "Test University",               // Current Institution
                "Professor"                      // Academic Position
        );

        // Act: Insert the user and retrieve it from the database
        userDAO.insertUser(user);
        User fetchedUser = userDAO.getUserByEmail("test.prof@example.com");

        // Assert: Verify that the user was successfully inserted and retrieved
        assertNotNull(fetchedUser); // Verify the user is inserted
        assertEquals("test.prof@example.com", fetchedUser.getEmail()); // Verify email
        assertTrue(fetchedUser instanceof AcademicProfessional); // Verify user type is AcademicProfessional

        // Further verification of specific AcademicProfessional attributes
        AcademicProfessional professional = (AcademicProfessional) fetchedUser;
        assertEquals("Test University", professional.getCurrentInstitution()); // Verify current institution
        assertEquals("Professor", professional.getAcademicPosition()); // Verify academic position
    }

    /**
     * Tests inserting an {@link AcademicInstitution} user into the database.
     * Verifies that the user can be correctly inserted and retrieved.
     */
    @Test
    void testInsertUser_AcademicInstitution() {
        // Arrange: Create an AcademicInstitution user
        User user = new AcademicInstitution(
                "test.inst@example.com",         // Email
                "password456",                   // Password
                "Test Institution Name"          // Institution Name
        );

        // Act: Insert the user and retrieve it from the database
        userDAO.insertUser(user);
        User fetchedUser = userDAO.getUserByEmail("test.inst@example.com");

        // Assert: Verify that the user was successfully inserted and retrieved
        assertNotNull(fetchedUser); // Verify the user is inserted
        assertEquals("test.inst@example.com", fetchedUser.getEmail()); // Verify email
        assertTrue(fetchedUser instanceof AcademicInstitution); // Verify user type is AcademicInstitution

        // Further verification of specific AcademicInstitution attributes
        AcademicInstitution institution = (AcademicInstitution) fetchedUser;
        assertEquals("Test Institution Name", institution.getName()); // Verify institution name
    }

    /**
     * Tests retrieving a user that does not exist in the database.
     * Ensures that a null value is returned if the user is not found.
     */
    @Test
    void testGetUserByEmail_UserNotFound() {
        // Act: Attempt to retrieve a user that does not exist
        User user = userDAO.getUserByEmail("nonexistent@example.com");

        // Assert: Verify that null is returned for a non-existent user
        assertNull(user, "User should be null if not found in the database");
    }
}
