package dao;

import model.CourseRequest;
import java.sql.*;

/**
 * DAO for managing `CourseRequest` objects in the database.
 * Provides CRUD (Create, Read, Update, Delete) operations for the `courserequest` table.
 */
public class CourseRequestDAO {

    // Database connection instance
    private Connection connection;

    /**
     * Constructs a `CourseRequestDAO` with the specified database connection.
     *
     * @param connection the database connection to be used by the DAO
     */
    public CourseRequestDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Default constructor for `CourseRequestDAO`.
     * It is recommended to initialize the connection manually before using DAO methods.
     */
    public CourseRequestDAO() {
        // Empty constructor; connection must be set manually
    }

    /**
     * Creates a new course request in the database.
     *
     * @param courseId       the ID of the course for which the request is made
     * @param professionalId the ID of the professional making the request
     * @param status         the initial status of the request
     * @return the ID of the newly created course request, or -1 if creation fails
     * @throws SQLException if a database error occurs
     */
    public int createRequest(int courseId, int professionalId, String status) throws SQLException {
        String query = "INSERT INTO courserequest (course_id, professional_id, status) VALUES (?, ?, ?) RETURNING id";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set query parameters
            statement.setInt(1, courseId);
            statement.setInt(2, professionalId);
            statement.setString(3, status);

            // Execute the query and retrieve the generated ID
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Return the generated ID
            }
            return -1; // Indicate failure
        }
    }

    /**
     * Retrieves a course request by its unique ID.
     *
     * @param id the unique ID of the course request
     * @return a `CourseRequest` object if found, otherwise `null`
     * @throws SQLException if a database error occurs
     */
    public CourseRequest getRequestById(int id) throws SQLException {
        String query = "SELECT * FROM courserequest WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set query parameter
            statement.setInt(1, id);

            // Execute the query and map the result to a CourseRequest object
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new CourseRequest(
                        rs.getInt("id"),
                        rs.getInt("course_id"),
                        rs.getInt("professional_id"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                );
            }
            return null; // Return null if no matching record is found
        }
    }

    /**
     * Updates the status of an existing course request.
     *
     * @param id     the ID of the course request to update
     * @param status the new status to be set
     * @return `true` if the update was successful, otherwise `false`
     * @throws SQLException if a database error occurs
     */
    public boolean updateRequestStatus(int id, String status) throws SQLException {
        String query = "UPDATE courserequest SET status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set query parameters
            statement.setString(1, status);
            statement.setInt(2, id);

            // Execute the update and check if any rows were affected
            return statement.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a course request by its unique ID.
     *
     * @param id the ID of the course request to delete
     * @return `true` if the deletion was successful, otherwise `false`
     * @throws SQLException if a database error occurs
     */
    public boolean deleteRequest(int id) throws SQLException {
        String query = "DELETE FROM courserequest WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set query parameter
            statement.setInt(1, id);

            // Execute the deletion and check if any rows were affected
            return statement.executeUpdate() > 0;
        }
    }
}
