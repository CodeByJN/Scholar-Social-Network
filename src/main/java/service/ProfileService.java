package service;

import dao.ProfileDAO;
import model.User;
import model.UserProfile;

import java.util.Map;

/**
 * Service class for managing user profiles.
 * Provides methods to view, update, and complete profile information of users.
 */
public class ProfileService {
    private final ProfileDAO profileDAO;

    /**
     * Constructs a {@code ProfileService} object with the specified {@code ProfileDAO} instance.
     *
     * @param profileDAO the data access object for managing user profiles.
     */
    public ProfileService(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }

    /**
     * Retrieves the profile information for the given user.
     *
     * @param user the user whose profile is being retrieved.
     * @return the user profile information, or {@code null} if not found.
     * @throws IllegalArgumentException if the provided user information is invalid.
     */
    public UserProfile viewProfile(User user) {
        if (user == null || user.getEmail() == null || user.getUserType() == null) {
            throw new IllegalArgumentException("Invalid user information provided.");
        }
        // Retrieve and return profile information for the given user
        return profileDAO.getProfileByEmail(user.getEmail(), user.getUserType());
    }

    /**
     * Updates the profile information of an existing user.
     *
     * @param user     the user whose profile needs to be updated.
     * @param newInfo  a map containing the new profile data.
     * @param userType the type of user (AcademicProfessional or AcademicInstitution).
     * @return {@code true} if the update is successful, {@code false} otherwise.
     * @throws IllegalArgumentException if the provided input for updating profile is invalid.
     */
    public boolean updateProfile(User user, Map<String, String> newInfo, String userType) {
        if (user == null || user.getEmail() == null || newInfo == null || userType == null) {
            throw new IllegalArgumentException("Invalid input provided for updating profile.");
        }

        try {
            // Update profile information in the database based on the user type
            profileDAO.updateProfile(user.getEmail(), newInfo, userType);
            return true;
        } catch (Exception e) {
            // Logging the exception can be added here to diagnose potential issues
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Completes the profile of a newly registered user.
     *
     * @param user        the user whose profile is being completed.
     * @param profileData a map containing profile data for completion.
     * @param userType    the type of user (AcademicProfessional or AcademicInstitution).
     * @return {@code true} if the profile completion is successful, {@code false} otherwise.
     * @throws IllegalArgumentException if the provided input for completing the profile is invalid.
     */
    public boolean completeProfile(User user, Map<String, String> profileData, String userType) {
        if (user == null || user.getEmail() == null || profileData == null || userType == null) {
            throw new IllegalArgumentException("Invalid input provided for completing profile.");
        }

        try {
            // Complete profile information based on user type
            profileDAO.updateProfile(user.getEmail(), profileData, userType);
            return true;
        } catch (Exception e) {
            // Logging the exception can be added here to diagnose potential issues
            e.printStackTrace();
            return false;
        }
    }
}
