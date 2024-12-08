/**
 * Unit tests for the NotificationService class.
 * These tests ensure that sending a notification results in
 * the expected DAO interaction.
 *
 * <p>Author: Jordan Earle</p>
 */

package controller;

import static org.mockito.Mockito.*;

import dao.interfaces.NotificationDAO;
import dao.interfaces.DAOException;
import model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.NotificationService;
import service.ServiceException;

public class NotificationServiceTest {

    private NotificationDAO notificationDAO;
    private NotificationService notificationService;

    /**
     * Sets up the test environment before each test.
     * Mocks the NotificationDAO and initializes the NotificationService
     * with the mock object.
     */
    @BeforeEach
    void setUp() {
        notificationDAO = mock(NotificationDAO.class);
        notificationService = new NotificationService(notificationDAO);
    }

    /**
     * Tests that calling sendNotification on the NotificationService
     * results in the NotificationDAO's insertNotification method being invoked.
     *
     * @throws DAOException      if a database access error occurs
     * @throws ServiceException  if a service-level error occurs
     */
    @Test
    void testSendNotificationSuccessfully() throws DAOException, ServiceException {
        // Given
        int userId = 42;
        String message = "Test notification message";

        // When
        notificationService.sendNotification(userId, message);

        // Then
        verify(notificationDAO, times(1)).insertNotification(any(Notification.class));
    }
}
