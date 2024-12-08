/**
 * Represents a notification sent to a specific user (recipient).
 * Each Notification includes the recipient's ID, a message, a timestamp, and a read status.
 * By default, a new notification is created with the current time and marked as unread.
 *
 * <p>Getters and setters allow for retrieving and updating the notification details,
 * and a dedicated method {@link #markAsRead()} allows marking the notification as read.</p>
 *
 * <p>This class can be used to display user notifications, track unread messages,
 * and maintain a history of events that have occurred within the application.</p>
 *
 * @author Jordan Earle
 */

package model;

import java.time.LocalDateTime;

public class Notification {

    private int id;
    private int recipientId;
    private String message;
    private LocalDateTime timestamp;
    private boolean isRead;

    /**
     * Constructs a new Notification with the specified recipient ID and message.
     * The notification's timestamp is set to the current time, and its initial state is unread.
     *
     * @param recipientId the ID of the user receiving the notification
     * @param message     the message content of the notification
     */
    public Notification(int recipientId, String message) {
        this.recipientId = recipientId;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
    }

    /**
     * Gets the unique ID of the notification.
     *
     * @return the notification ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID of the notification.
     *
     * @param id the new notification ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the ID of the user who will receive this notification.
     *
     * @return the recipient's user ID
     */
    public int getRecipientId() {
        return recipientId;
    }

    /**
     * Gets the message content of the notification.
     *
     * @return the notification message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the timestamp when the notification was created.
     *
     * @return the creation timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Checks if the notification has been read.
     *
     * @return true if the notification is read; false otherwise
     */
    public boolean isRead() {
        return isRead;
    }

    /**
     * Marks the notification as read.
     */
    public void markAsRead() {
        this.isRead = true;
    }
}
