package dao;

import model.UserProfile;

import java.util.Map;

public interface ProfileDAO {

    /**
     * Updates the profile information of the specified user.
     *
     * @param email       the email of the user whose profile needs to be updated
     * @param profileData a map of new profile data
     * @param userType    the type of user, either AcademicProfessional or AcademicInstitution
     */
    void updateProfile(String email, Map<String, String> profileData, String userType);

    /**
     * Retrieves the profile by the user's email and user type.
     *
     * @param email    the email of the user whose profile needs to be retrieved
     * @param userType the type of user, either AcademicProfessional or AcademicInstitution
     * @return a UserProfile object containing the profile information
     */
    UserProfile getProfileByEmail(String email, String userType);
}
