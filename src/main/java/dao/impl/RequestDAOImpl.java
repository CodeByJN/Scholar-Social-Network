/**
 * An implementation of the RequestDAO interface providing methods to insert
 * and update CourseRequest records in the database.
 *
 * <p>This class uses a JDBC DataSource to manage database connections and
 * performs operations such as inserting new course requests and updating their status.</p>
 *
 * <p>Any database-related issues throw a DAOException to indicate that the
 * operation could not be completed.</p>
 *
 * @author Jordan Earle
 */

package dao.impl;

import dao.interfaces.DAOException;
import dao.interfaces.RequestDAO;
import model.CourseRequest;

import javax.sql.DataSource;
import java.sql.*;

public class RequestDAOImpl implements RequestDAO {
    private DataSource datasource;

    /**
     * Constructs a RequestDAOImpl with the specified DataSource.
     *
     * @param datasource the DataSource used for obtaining database connections
     */
    public RequestDAOImpl(DataSource datasource) {
        this.datasource = datasource;
    }

    /**
     * Inserts a new CourseRequest record into the database.
     * The request's ID is updated with the generated primary key upon success.
     *
     * @param request the CourseRequest object to be inserted
     * @throws DAOException if a database access error occurs or the operation fails
     */
    @Override
    public void insertRequest(CourseRequest request) throws DAOException {
        String sql = "INSERT INTO Requests (course_id, applicant_id, status, created_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = datasource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, request.getCourseId());
            pstmt.setInt(2, request.getApplicantId());
            pstmt.setString(3, request.getStatus());
            pstmt.setTimestamp(4, Timestamp.valueOf(request.getCreatedAt()));

            pstmt.executeUpdate();

            // Retrieve the generated ID
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    request.setId(generatedKeys.getInt(1));
                }
            } catch (SQLException e) {
                throw new DAOException("Error inserting course request", e);
            }

        } catch (SQLException e) {
            throw new DAOException("Error inserting course request", e);
        }
    }

    /**
     * Updates the status of an existing course request record.
     *
     * @param requestId the ID of the request to update
     * @param status    the new status for the request
     * @throws DAOException if a database access error occurs or the operation fails
     */
    @Override
    public void updateRequestStatus(int requestId, String status) throws DAOException {
        String sql = "UPDATE Request SET status = ? WHERE id = ?";

        try (Connection conn = datasource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, requestId);

            pstmt.execute();

        } catch (SQLException e) {
            throw new DAOException("Error updating request status", e);
        }
    }
}
