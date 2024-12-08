/**
 * A servlet that manages the display and handling of user notifications.
 * It retrieves both unread and read notifications for a user, displays them,
 * and provides functionality to mark all notifications as read.
 *
 * <p>Currently, the user ID is hardcoded for demonstration purposes. In a real application,
 * the user ID would be obtained from the session after user authentication.</p>
 *
 * @author Jordan Earle
 */

package controller;

import dao.impl.NotificationDAOImpl;
import dao.interfaces.NotificationDAO;
import model.Notification;
import service.NotificationService;
import util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/notifications")
public class NotificationsServlet extends HttpServlet {
    private NotificationService notificationService;

    /**
     * Initializes the servlet by setting up the required NotificationService 
     * to handle notification-related operations.
     *
     * @throws ServletException if initialization fails
     */
    @Override
    public void init() throws ServletException {
        DataSource dataSource = DatabaseConnection.lookupDataSource();
        NotificationDAO notificationDAO = new NotificationDAOImpl(dataSource);
        notificationService = new NotificationService(notificationDAO);
    }

    /**
     * Handles the HTTP GET request by retrieving unread and a limited set of read 
     * notifications for the user, then forwarding them to the Notifications JSP for display.
     *
     * @param request  the HttpServletRequest object that contains the request the client made
     * @param response the HttpServletResponse object that contains the response the servlet returns
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error is detected
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // For demonstration, using userId = 1
            List<Notification> unreadNotifications = notificationService.getNotificationsForUser(1, true);

            List<Notification> readNotifications = notificationService.getNotificationsForUser(1, false)
                    .stream()
                    .limit(20)
                    .collect(Collectors.toList());

            request.setAttribute("unreadNotifications", unreadNotifications);
            request.setAttribute("readNotifications", readNotifications);

            request.getRequestDispatcher("/WEB-INF/views/Notifications.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", URLEncoder.encode(e.getMessage(), "UTF-8"));
            request.getRequestDispatcher("/WEB-INF/views/Notifications.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP POST request to mark all notifications as read for the user.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error is detected
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userId = 1; // In a real scenario, derive from the session's current user
            notificationService.markAllNotificationsAsRead(userId);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
