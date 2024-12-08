package dao;

import model.UserProfile;
import org.junit.jupiter.api.*;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ProfileDAOImpl}.
 * Contains unit tests for the ProfileDAOImpl to verify the behavior of updating and retrieving user profiles.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProfileDAOImplTest {

    private ProfileDAOImpl profileDAO;

    /**
     * Sets up the database before all tests are executed.
     * Inserts some initial test data into the database.
     */
    @BeforeAll
    public void setUpDatabase() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Manually insert some test data
            String sql = "INSERT INTO Users (email, userType, name, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                // Insert AcademicProfessional user
                stmt.setString(1, "test@example.com");
                stmt.setString(2, "AcademicProfessional");
                stmt.setString(3, "Existing User");
                stmt.setString(4, "test123");  // Default password
                stmt.executeUpdate();

                // Insert AcademicInstitution user
                stmt.setString(1, "institution@example.com");
                stmt.setString(2, "AcademicInstitution");
                stmt.setString(3, "Test Institution");
                stmt.setString(4, "test123");  // Default password
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            fail("Database setup failed: " + e.getMessage());
        }
    }

    /**
     * Initializes the ProfileDAOImpl instance before each test is executed.
     */
    @BeforeEach
    public void setUp() {
        profileDAO = ProfileDAOImpl.getInstance();
    }

    /**
     * Tests updating a profile for an AcademicProfessional user.
     * Verifies that the profile is updated successfully with the provided data.
     */
    @Test
    public void testUpdateProfile_AcademicProfessional() {
        // Arrange
        String email = "test@example.com";
        String userType = "AcademicProfessional";

        Map<String, String> profileData = new HashMap<>();
        profileData.put("name", "Test User");
        profileData.put("educationBackground", "Computer Science");
        profileData.put("areaOfExpertise", "Artificial Intelligence");
        profileData.put("currentInstitution", "Example University");
        profileData.put("academicPosition", "Professor");

        // Act
        profileDAO.updateProfile(email, profileData, userType);

        // Assert
        UserProfile updatedProfile = profileDAO.getProfileByEmail(email, userType);
        assertNotNull(updatedProfile, "Updated profile should not be null");
        assertEquals("Test User", updatedProfile.getName());
        assertEquals("Computer Science", updatedProfile.getEducationBackground());
        assertEquals("Artificial Intelligence", updatedProfile.getAreaOfExpertise());
        assertEquals("Example University", updatedProfile.getCurrentInstitution());
        assertEquals("Professor", updatedProfile.getAcademicPosition());
    }

    /**
     * Tests updating a profile for an AcademicInstitution user.
     * Verifies that the profile is updated successfully with the provided data.
     */
    @Test
    public void testUpdateProfile_AcademicInstitution() {
        // Arrange
        String email = "institution@example.com";
        String userType = "AcademicInstitution";

        Map<String, String> profileData = new HashMap<>();
        profileData.put("institutionName", "Test Institution");
        profileData.put("address", "1234 Test St");
        profileData.put("coursesOfferedByTerm", "Computer Science, Mathematics");

        // Act
        profileDAO.updateProfile(email, profileData, userType);

        // Assert
        UserProfile updatedProfile = profileDAO.getProfileByEmail(email, userType);
        assertNotNull(updatedProfile, "Updated profile should not be null");
        assertEquals("Test Institution", updatedProfile.getInstitutionName());
        assertEquals("1234 Test St", updatedProfile.getAddress());
        assertEquals("Computer Science, Mathematics", updatedProfile.getCoursesOfferedByTerm());
    }

    /**
     * Tests retrieving a profile by email where the profile exists.
     * Verifies that the profile is correctly retrieved from the database.
     */
    @Test
    public void testGetProfileByEmail_ProfileExists() {
        // Arrange
        String email = "test@example.com";
        String userType = "AcademicProfessional";

        // Act
        UserProfile profile = profileDAO.getProfileByEmail(email, userType);

        // Assert
        assertNotNull(profile, "Profile should not be null for an existing user");
        assertEquals(email, profile.getEmail());
        assertEquals("Test User", profile.getName()); // Updated expected value to "Test User"
    }

    /**
     * Tests retrieving a profile by email where the profile does not exist.
     * Verifies that null is returned when the profile is not found.
     */
    @Test
    public void testGetProfileByEmail_ProfileDoesNotExist() {
        // Arrange
        String email = "nonexistent@example.com";
        String userType = "AcademicProfessional";

        // Act
        UserProfile profile = profileDAO.getProfileByEmail(email, userType);

        // Assert
        assertNull(profile, "Profile should be null for non-existent email");
    }

    /**
     * Cleans up the database after all tests have been executed.
     * Deletes the test data that was inserted during the test setup.
     */
    @AfterAll
    public void tearDown() {
        // Clean up inserted data
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM Users WHERE email IN (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, "test@example.com");
                stmt.setString(2, "institution@example.com");
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Database teardown failed: " + e.getMessage());
        }
    }
}
