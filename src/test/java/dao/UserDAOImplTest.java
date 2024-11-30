package dao;

import model.AcademicInstitution;
import model.AcademicProfessional;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Test class for testing UserDAOImpl.
 * This class tests methods for inserting and retrieving users, including academic professionals and institutions.
 */
public class UserDAOImplTest {
    private UserDAO userDAO;

    /**
     * Sets up the test environment by initializing UserDAO instance before each test.
     */
    @BeforeEach
    void setUp() {
        userDAO = UserDAOImpl.getInstance(); // Initialize UserDAO instance
    }

    /**
     * Tears down the test environment by cleaning up the database after each test.
     * Deletes any users inserted during tests.
     */
    @AfterEach
    void tearDown() {
        String sql = "DELETE FROM Users WHERE email LIKE 'test%'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests inserting an AcademicProfessional user into the database.
     * Ensures that the user can be inserted and retrieved correctly.
     */
    @Test
    void testInsertUser_AcademicProfessional() {
        User user = new AcademicProfessional(
                "Professor John", // Name
                "test.prof@example.com",
                "password123",
                "Test University",
                "Professor"
        );

        userDAO.insertUser(user);
        User fetchedUser = userDAO.getUserByEmail("test.prof@example.com");

        assertNotNull(fetchedUser); // Verify the user is inserted
        assertEquals("test.prof@example.com", fetchedUser.getEmail()); // Verify email
        assertTrue(fetchedUser instanceof AcademicProfessional); // Verify user type

        AcademicProfessional professional = (AcademicProfessional) fetchedUser;
        assertEquals("Test University", professional.getCurrentInstitution()); // Verify currentInstitution
        assertEquals("Professor", professional.getAcademicPosition()); // Verify academicPosition
    }

    /**
     * Tests inserting an AcademicInstitution user into the database.
     * Ensures that the user can be inserted and retrieved correctly.
     */
    @Test
    void testInsertUser_AcademicInstitution() {
        User user = new AcademicInstitution(
                "test.inst@example.com",
                "password456",
                "Test Institution Name"
        );

        userDAO.insertUser(user);
        User fetchedUser = userDAO.getUserByEmail("test.inst@example.com");

        assertNotNull(fetchedUser); // Verify the user is inserted
        assertEquals("test.inst@example.com", fetchedUser.getEmail()); // Verify email
        assertTrue(fetchedUser instanceof AcademicInstitution); // Verify user type

        AcademicInstitution institution = (AcademicInstitution) fetchedUser;
        assertEquals("Test Institution Name", institution.getInstitutionName()); // Verify institutionName
    }

    /**
     * Tests retrieving a user that does not exist in the database.
     * Ensures that a null value is returned if the user is not found.
     */
    @Test
    void testGetUserByEmail_UserNotFound() {
        User user = userDAO.getUserByEmail("nonexistent@example.com");
        assertNull(user); // Verify the user is not found
    }
}
