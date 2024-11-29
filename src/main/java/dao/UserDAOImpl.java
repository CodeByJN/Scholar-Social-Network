package dao;

import model.User;
import model.AcademicProfessional;
import model.AcademicInstitution;

import java.sql.*;

public class UserDAOImpl extends UserDAO {

    @Override
    public void insertUser(User user) {
        String sql = "INSERT INTO Users (name, email, password, userType, currentInstitution, academicPosition, institutionName) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getUserType());

            if (user instanceof AcademicProfessional) {
                stmt.setString(5, ((AcademicProfessional) user).getCurrentInstitution());
                stmt.setString(6, ((AcademicProfessional) user).getAcademicPosition());
                stmt.setNull(7, Types.VARCHAR);
            } else if (user instanceof AcademicInstitution) {
                stmt.setNull(5, Types.VARCHAR);
                stmt.setNull(6, Types.VARCHAR);
                stmt.setString(7, ((AcademicInstitution) user).getInstitutionName());
            }

            int rows = stmt.executeUpdate();
            System.out.println("Insert successful, rows affected: " + rows);
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

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
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("institutionName")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
