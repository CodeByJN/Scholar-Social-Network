/**
 * course: CST8288
 * name: Ningyi Wang
 * Student ID: 041120798
 */
package factory;

import model.User;
import model.AcademicProfessional;
import model.AcademicInstitution;

/**
 * Factory class for creating User objects based on user type.
 * This class provides a method to create different types of users, such as AcademicProfessional or AcademicInstitution.
 */
public class UserFactory {

    /**
     * Creates a User object based on the provided userType.
     *
     * @param userType  The type of user to create (e.g., "AcademicProfessional" or "AcademicInstitution").
     * @param email     The email address of the user.
     * @param password  The password for the user.
     * @param extraInfo Additional information required for user creation.
     *                  - For "AcademicProfessional", this represents the user's name.
     *                  - For "AcademicInstitution", this represents the institution name.
     * @return A User object of the specified type, or null if the userType is invalid.
     */
    public User createUser(String userType, String email, String password, String extraInfo) {
        if (userType.equalsIgnoreCase("AcademicProfessional")) {
            return new AcademicProfessional(extraInfo, email, password, "Unknown Institution", "Unknown Position");
        } else if (userType.equalsIgnoreCase("AcademicInstitution")) {
            return new AcademicInstitution(email, password, extraInfo);
        }
        return null;
    }
}
