package dao;

import model.UserProfile;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Implementation of the ProfileDAO interface for managing user profiles in the database.
 * This implementation uses a singleton pattern.
 */
public class ProfileDAOImpl implements ProfileDAO {

    private static ProfileDAOImpl instance;

    // Private constructor to prevent direct instantiation.
    private ProfileDAOImpl() {
    }

    /**
     * Returns the singleton instance of the ProfileDAOImpl.
     *
     * @return the instance of ProfileDAOImpl.
     */
    public static ProfileDAOImpl getInstance() {
        if (instance == null) {
            instance = new ProfileDAOImpl();
        }
        return instance;
    }

    /**
     * Updates the profile information of the specified user.
     *
     * @param email       the email of the user whose profile needs to be updated.
     * @param profileData a map of new profile data, where keys are field names and values are the new values for those fields.
     * @param userType    the type of user, either "AcademicProfessional" or "AcademicInstitution".
     */
    @Override
    public void updateProfile(String email, Map<String, String> profileData, String userType) {
        StringBuilder sql = new StringBuilder("UPDATE Users SET ");
        boolean firstField = true;

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Prepare SQL update based on user type
            if ("AcademicProfessional".equals(userType)) {
                if (profileData.containsKey("name") && !profileData.get("name").isEmpty()) {
                    sql.append("name = ?");
                    firstField = false;
                }
                if (profileData.containsKey("educationBackground") && !profileData.get("educationBackground").isEmpty()) {
                    if (!firstField) sql.append(", ");
                    sql.append("educationBackground = ?");
                    firstField = false;
                }
                if (profileData.containsKey("areaOfExpertise") && !profileData.get("areaOfExpertise").isEmpty()) {
                    if (!firstField) sql.append(", ");
                    sql.append("areaOfExpertise = ?");
                    firstField = false;
                }
                if (profileData.containsKey("currentInstitution") && !profileData.get("currentInstitution").isEmpty()) {
                    if (!firstField) sql.append(", ");
                    sql.append("currentInstitution = ?");
                    firstField = false;
                }
                if (profileData.containsKey("academicPosition") && !profileData.get("academicPosition").isEmpty()) {
                    if (!firstField) sql.append(", ");
                    sql.append("academicPosition = ?");
                }
            } else if ("AcademicInstitution".equals(userType)) {
                if (profileData.containsKey("institutionName") && !profileData.get("institutionName").isEmpty()) {
                    sql.append("institutionName = ?");
                    firstField = false;
                }
                if (profileData.containsKey("address") && !profileData.get("address").isEmpty()) {
                    if (!firstField) sql.append(", ");
                    sql.append("address = ?");
                    firstField = false;
                }
                if (profileData.containsKey("coursesOfferedByTerm") && !profileData.get("coursesOfferedByTerm").isEmpty()) {
                    if (!firstField) sql.append(", ");
                    sql.append("coursesOfferedByTerm = ?");
                }
            } else {
                throw new IllegalArgumentException("Unknown user type: " + userType);
            }

            sql.append(" WHERE email = ?");

            try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
                int paramIndex = 1;

                if ("AcademicProfessional".equals(userType)) {
                    if (profileData.containsKey("name") && !profileData.get("name").isEmpty()) {
                        stmt.setString(paramIndex++, profileData.get("name"));
                    }
                    if (profileData.containsKey("educationBackground") && !profileData.get("educationBackground").isEmpty()) {
                        stmt.setString(paramIndex++, profileData.get("educationBackground"));
                    }
                    if (profileData.containsKey("areaOfExpertise") && !profileData.get("areaOfExpertise").isEmpty()) {
                        stmt.setString(paramIndex++, profileData.get("areaOfExpertise"));
                    }
                    if (profileData.containsKey("currentInstitution") && !profileData.get("currentInstitution").isEmpty()) {
                        stmt.setString(paramIndex++, profileData.get("currentInstitution"));
                    }
                    if (profileData.containsKey("academicPosition") && !profileData.get("academicPosition").isEmpty()) {
                        stmt.setString(paramIndex++, profileData.get("academicPosition"));
                    }
                } else if ("AcademicInstitution".equals(userType)) {
                    if (profileData.containsKey("institutionName") && !profileData.get("institutionName").isEmpty()) {
                        stmt.setString(paramIndex++, profileData.get("institutionName"));
                    }
                    if (profileData.containsKey("address") && !profileData.get("address").isEmpty()) {
                        stmt.setString(paramIndex++, profileData.get("address"));
                    }
                    if (profileData.containsKey("coursesOfferedByTerm") && !profileData.get("coursesOfferedByTerm").isEmpty()) {
                        stmt.setString(paramIndex++, profileData.get("coursesOfferedByTerm"));
                    }
                }

                stmt.setString(paramIndex, email);

                // Debugging logs for SQL execution
                System.out.println("Executing SQL: " + sql);
                System.out.println("With parameters: " + profileData);
                System.out.println("Updating profile for email: " + email);

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the profile of the specified user by email and user type.
     *
     * @param email    the email of the user whose profile needs to be retrieved.
     * @param userType the type of user, either "AcademicProfessional" or "AcademicInstitution".
     * @return a {@link UserProfile} object containing the user's profile information, or {@code null} if not found.
     */
    @Override
    public UserProfile getProfileByEmail(String email, String userType) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if ("AcademicProfessional".equals(userType)) {
                    String name = rs.getString("name");
                    String educationBackground = rs.getString("educationBackground");
                    String areaOfExpertise = rs.getString("areaOfExpertise");
                    String currentInstitution = rs.getString("currentInstitution");
                    String academicPosition = rs.getString("academicPosition");

                    return new UserProfile(
                            email,
                            name != null ? name : "",
                            educationBackground != null ? educationBackground : "",
                            areaOfExpertise != null ? areaOfExpertise : "",
                            null,
                            null,
                            currentInstitution != null ? currentInstitution : "",
                            academicPosition != null ? academicPosition : "",
                            null
                    );
                } else if ("AcademicInstitution".equals(userType)) {
                    String institutionName = rs.getString("institutionName");
                    String address = rs.getString("address");
                    String coursesOfferedByTerm = rs.getString("coursesOfferedByTerm");

                    return new UserProfile(
                            email,
                            null,
                            null,
                            null,
                            address != null ? address : "",
                            coursesOfferedByTerm != null ? coursesOfferedByTerm : "",
                            null,
                            null,
                            institutionName != null ? institutionName : ""
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
