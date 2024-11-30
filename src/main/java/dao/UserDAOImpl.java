package dao;

import model.User;
import model.AcademicProfessional;
import model.AcademicInstitution;

import java.sql.*;

/**
 * Implementation of the UserDAO interface for accessing User data in a PostgreSQL database.
 * This class is a Singleton to ensure only one instance is created.
 */
public class UserDAOImpl implements UserDAO {
    private static UserDAOImpl instance;

    /**
     * Private constructor to prevent instantiation from other classes.
     * Use {@link #getInstance()} to get the single instance of this class.
     */
    private UserDAOImpl() {
    }

    /**
     * Provides the single instance of this class (Singleton pattern).
     *
     * @return the single instance of UserDAOImpl.
     */
    public static synchronized UserDAOImpl getInstance() {
        if (instance == null) {
            instance = new UserDAOImpl();
        }
        return instance;
    }

    /**
     * Inserts a user into the database.
     *
     * @param user the user to be inserted into the database.
     */
    @Override
    public void insertUser(User user) {
        String sql = "INSERT INTO Users (name, email, password, userType, currentInstitution, academicPosition, institutionName) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (user instanceof AcademicProfessional) {
                stmt.setString(1, ((AcademicProfessional) user).getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                stmt.setString(4, user.getUserType());
                stmt.setString(5, ((AcademicProfessional) user).getCurrentInstitution());
                stmt.setString(6, ((AcademicProfessional) user).getAcademicPosition());
                stmt.setNull(7, Types.VARCHAR); // InstitutionName is null for AcademicProfessional
            } else if (user instanceof AcademicInstitution) {
                stmt.setString(1, ""); // Assuming AcademicInstitution has no name attribute
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                stmt.setString(4, user.getUserType());
                stmt.setNull(5, Types.VARCHAR); // CurrentInstitution is null for AcademicInstitution
                stmt.setNull(6, Types.VARCHAR); // AcademicPosition is null for AcademicInstitution
                stmt.setString(7, ((AcademicInstitution) user).getInstitutionName());
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a user by their email address from the database.
     *
     * @param email the email address of the user to retrieve.
     * @return a User object if a user with the specified email exists, otherwise null.
     */
    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String userType = rs.getString("userType");
                if ("AcademicProfessional".equalsIgnoreCase(userType)) {
                    return new AcademicProfessional(
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("currentInstitution"),
                            rs.getString("academicPosition")
                    );
                } else if ("AcademicInstitution".equalsIgnoreCase(userType)) {
                    return new AcademicInstitution(
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("institutionName")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
