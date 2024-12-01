package service;

import dao.ProfileDAO;
import model.AcademicProfessional;
import model.User;
import model.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * JUnit test class for testing {@link ProfileService}.
 * This class tests methods for viewing, updating, and completing user profiles.
 */
public class ProfileServiceTest {

    private ProfileDAO profileDAO;
    private ProfileService profileService;
    private User user;

    /**
     * Sets up the test environment by initializing the ProfileDAO, ProfileService, and User instance.
     * The ProfileDAO is mocked to simulate database interactions.
     */
    @BeforeEach
    public void setUp() {
        // Mocking the ProfileDAO
        profileDAO = Mockito.mock(ProfileDAO.class);
        // Creating ProfileService with mocked ProfileDAO
        profileService = new ProfileService(profileDAO);

        // Setting up a sample user using concrete class AcademicProfessional
        user = new AcademicProfessional("Test User", "test@example.com", "password", "Example University", "Professor");
    }

    /**
     * Tests the {@link ProfileService#viewProfile(User)} method for a valid user.
     * Ensures that the profile returned matches the expected profile.
     */
    @Test
    public void testViewProfile_ValidUser() {
        // Arrange: Create a mocked UserProfile object
        UserProfile mockProfile = new UserProfile(
                user.getEmail(),               // Email
                "Test User",                   // Name
                "Education Background",        // Education Background
                "Area of Expertise",           // Area of Expertise
                "Address",                     // Address
                "Courses Offered by Term",     // Courses Offered by Term
                "Current Institution",         // Current Institution
                "Academic Position",           // Academic Position
                "Institution Name"             // Institution Name
        );
        when(profileDAO.getProfileByEmail(user.getEmail(), user.getUserType())).thenReturn(mockProfile);

        // Act: Call the viewProfile method
        UserProfile result = profileService.viewProfile(user);

        // Assert: Verify that the profile returned is as expected
        assertNotNull(result, "Profile should not be null for a valid user");
        assertEquals(user.getEmail(), result.getEmail(), "Email should match the mocked profile");
        verify(profileDAO, times(1)).getProfileByEmail(user.getEmail(), user.getUserType());
    }

    /**
     * Tests the {@link ProfileService#updateProfile(User, Map, String)} method for valid profile data.
     * Ensures that the update is successful.
     */
    @Test
    public void testUpdateProfile_ValidData() {
        // Arrange: Set up new profile data
        Map<String, String> newInfo = new HashMap<>();
        newInfo.put("name", "Updated Name");

        doNothing().when(profileDAO).updateProfile(user.getEmail(), newInfo, user.getUserType());

        // Act: Call the updateProfile method
        boolean result = profileService.updateProfile(user, newInfo, user.getUserType());

        // Assert: Verify that the update was successful
        assertTrue(result, "Profile update should be successful");
        verify(profileDAO, times(1)).updateProfile(user.getEmail(), newInfo, user.getUserType());
    }

    /**
     * Tests the {@link ProfileService#updateProfile(User, Map, String)} method with invalid data.
     * Expects an {@link IllegalArgumentException} to be thrown.
     */
    @Test
    public void testUpdateProfile_InvalidData_ThrowsException() {
        // Act & Assert: Expect IllegalArgumentException for null input
        assertThrows(IllegalArgumentException.class, () -> profileService.updateProfile(null, new HashMap<>(), null));
    }

    /**
     * Tests the {@link ProfileService#completeProfile(User, Map, String)} method for valid profile data.
     * Ensures that the profile completion is successful.
     */
    @Test
    public void testCompleteProfile_ValidData() {
        // Arrange: Set up new profile data
        Map<String, String> profileData = new HashMap<>();
        profileData.put("currentInstitution", "Example University");

        doNothing().when(profileDAO).updateProfile(user.getEmail(), profileData, user.getUserType());

        // Act: Call the completeProfile method
        boolean result = profileService.completeProfile(user, profileData, user.getUserType());

        // Assert: Verify that the profile completion was successful
        assertTrue(result, "Profile completion should be successful");
        verify(profileDAO, times(1)).updateProfile(user.getEmail(), profileData, user.getUserType());
    }

    /**
     * Tests the {@link ProfileService#completeProfile(User, Map, String)} method with invalid data.
     * Expects an {@link IllegalArgumentException} to be thrown.
     */
    @Test
    public void testCompleteProfile_InvalidData_ThrowsException() {
        // Act & Assert: Expect IllegalArgumentException for null input
        assertThrows(IllegalArgumentException.class, () -> profileService.completeProfile(null, null, null));
    }

    /**
     * Tests the {@link ProfileService#updateProfile(User, Map, String)} method when a database error occurs.
     * Ensures that the update fails gracefully and returns false.
     */
    @Test
    public void testUpdateProfile_DatabaseFailure() {
        // Arrange: Set up new profile data and simulate a database error
        Map<String, String> newInfo = new HashMap<>();
        newInfo.put("name", "Updated Name");

        doThrow(new RuntimeException("Database error")).when(profileDAO).updateProfile(anyString(), anyMap(), anyString());

        // Act: Call the updateProfile method
        boolean result = profileService.updateProfile(user, newInfo, user.getUserType());

        // Assert: Verify that the update failed
        assertFalse(result, "Profile update should fail when a database error occurs");
        verify(profileDAO, times(1)).updateProfile(user.getEmail(), newInfo, user.getUserType());
    }
}
