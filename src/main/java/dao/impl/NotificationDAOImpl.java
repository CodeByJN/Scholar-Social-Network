/**
 * Implementation of the NotificationDAO interface that provides methods for
 * inserting and retrieving notifications from a data source.
 *
 * <p>This class uses a JDBC DataSource for database operations and
 * interacts with a Notifications table that includes fields for recipient_id,
 * message, created_at, and is_read.</p>
 *
 * <p>All database operations throw a DAOException on failure.</p>
 *
 * @author Jordan Earle
 */

package dao.impl;

import dao.interfaces.DAOException;
import dao.interfaces.NotificationDAO;
import model.Notification;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAOImpl implements NotificationDAO {

    private DataSource dataSource;

    /**
     * Constructs a NotificationDAOImpl with the specified DataSource.
     *
     * @param dataSource the DataSource used for obtaining database connections
     */
    public NotificationDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Inserts a new notification record into the database.
     * Upon successful insertion, the notification's ID is updated with the generated key.
     *
     * @param notification the Notification object to be inserted
     * @throws DAOException if a database access error occurs or the operation fails
     */
    @Override
    public void insertNotification(Notification notification) throws DAOException {
        String sql = "INSERT INTO Notifications (recipient_id, message, created_at, is_read) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, notification.getRecipientId());
            pstmt.setString(2, notification.getMessage());
            pstmt.setTimestamp(3, Timestamp.valueOf(notification.getTimestamp()));
            pstmt.setBoolean(4, notification.isRead());

            pstmt.executeUpdate();

            // Retrieve the generated ID
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    notification.setId(generatedKeys.getInt(1));
                }
            } catch (SQLException e) {
                throw new DAOException("Error adding notification", e);
            }

        } catch (SQLException e) {
            throw new DAOException("Error adding notification", e);
        }
    }

    /**
     * Retrieves notifications for a specific user. If unreadOnly is true,
     * only unread notifications are returned. Otherwise, only read notifications are returned.
     *
     * @param userId     the ID of the user for whom to retrieve notifications
     * @param unreadOnly if true, retrieve only unread notifications; otherwise, retrieve only read notifications
     * @return a list of Notification objects for the specified user
     * @throws DAOException if a database access error occurs or the operation fails
     */
    @Override
    public List<Notification> getNotificationsByUserId(int userId, boolean unreadOnly) throws DAOException {
        List<Notification> notifications = new ArrayList<>();
        String sql = unreadOnly
                ? "SELECT * FROM notifications WHERE recipient_id = ? AND is_read = false ORDER BY created_at DESC"
                : "SELECT * FROM notifications WHERE recipient_id = ? AND is_read = true ORDER BY created_at DESC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Notification notification = new Notification(
                            rs.getInt("recipient_id"),
                            rs.getString("message")
                    );
                    notification.setId(rs.getInt("id"));
                    if (!rs.getBoolean("is_read")) {
                        notification.markAsRead();
                    }

                    notifications.add(notification);
                }
            } catch (SQLException e) {
                throw new DAOException("Failed to get notifications", e);
            }
        } catch (SQLException e) {
            throw new DAOException("Error Retrieving notifications", e);
        }
        return notifications;
    }

    /**
     * Marks a specific notification as read in the database.
     *
     * @param notificationId the ID of the notification to mark as read
     * @throws DAOException if a database access error occurs or the operation fails
     */
    @Override
    public void markNotificationAsRead(int notificationId) throws DAOException {
        String sql = "UPDATE Notifications SET is_read = true WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, notificationId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error marking notification as read", e);
        }
    }
}
