/**
 * Provides functionality for managing notifications within the application.
 * This service abstracts the underlying data access operations performed by the
 * NotificationDAO implementation.
 *
 * <p>Key responsibilities include sending new notifications, retrieving notifications
 * for a particular user (both unread and read), and marking notifications as read.</p>
 *
 * <p>All public methods may throw a ServiceException or DAOException if underlying
 * operations fail, ensuring that database or logic errors are not silently ignored.</p>
 *
 * <p>Typical usage involves creating a NotificationService with a concrete DAO, then
 * calling its methods to handle notifications for application users.</p>
 *
 * @author Jordan Earle
 */

package service;

import dao.interfaces.DAOException;
import dao.interfaces.NotificationDAO;
import model.Notification;

import java.util.List;

public class NotificationService {

    private NotificationDAO notificationDAO;

    /**
     * Constructs a NotificationService with a specific NotificationDAO for data access.
     *
     * @param notificationDAO the DAO used to interact with the notifications data source
     */
    public NotificationService(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    /**
     * Sends a new notification to a specified user.
     *
     * @param userId  the ID of the user to receive the notification
     * @param message the message content of the notification
     * @throws ServiceException if an error occurs while sending the notification
     */
    public void sendNotification(int userId, String message) throws ServiceException {
        try {
            Notification notification = new Notification(userId, message);
            notificationDAO.insertNotification(notification);
        } catch (Exception e) {
            throw new ServiceException("Error Sending notification", e);
        }
    }

    /**
     * Adds a pre-constructed notification to the data store.
     *
     * @param notification the Notification object to add
     * @throws ServiceException if an error occurs while adding the notification
     * @throws DAOException      if a data access error occurs
     */
    public void addNotification(Notification notification) throws ServiceException, DAOException {
        try {
            notificationDAO.insertNotification(notification);
        } catch (Exception e) {
            throw new ServiceException("Error adding Notification", e);
        }
    }

    /**
     * Retrieves notifications for a specific user. If unreadOnly is true,
     * only unread notifications are returned. Otherwise, only read notifications are returned.
     *
     * @param userId     the ID of the user for whom to retrieve notifications
     * @param unreadOnly true to retrieve only unread notifications; false for read notifications
     * @return a List of Notification objects that match the specified criteria
     * @throws DAOException if a data access error occurs
     */
    public List<Notification> getNotificationsForUser(int userId, boolean unreadOnly) throws DAOException {
        try {
            return notificationDAO.getNotificationsByUserId(userId, unreadOnly);
        } catch (Exception e) {
            throw new DAOException("Failed to get user notifications", e);
        }
    }

    /**
     * Marks all unread notifications for a specified user as read.
     *
     * @param userId the ID of the user whose unread notifications should be marked as read
     * @throws DAOException if a data access error occurs
     */
    public void markAllNotificationsAsRead(int userId) throws DAOException {
        try {
            List<Notification> unreadNotifications = notificationDAO.getNotificationsByUserId(userId, true);
            for (Notification notification : unreadNotifications) {
                notificationDAO.markNotificationAsRead(notification.getId());
            }
        } catch (Exception e) {
            throw new DAOException("Couldn't mark all notifications as read for some reason", e);
        }
    }
}
