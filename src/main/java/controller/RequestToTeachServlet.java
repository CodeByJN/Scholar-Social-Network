/**
 * A servlet that handles the submission of course teaching requests.
 * It retrieves available courses, allows the user to request to teach a chosen course, 
 * and sends a notification once the request is submitted.
 *
 * @author Jordan Earle
 */

package controller;

import dao.impl.NotificationDAOImpl;
import dao.impl.RequestDAOImpl;
import dao.interfaces.NotificationDAO;
import dao.interfaces.RequestDAO;
import model.Course;
import model.CourseRequest;
import model.User;
import service.NotificationService;
import service.RequestService;
import util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet("/SubmitCourseRequestServlet")
public class RequestToTeachServlet extends HttpServlet {
    private RequestService requestService;
    private NotificationService notificationService;

    /**
     * Initializes the servlet by setting up the required services for handling 
     * course requests and notifications.
     *
     * @throws ServletException if an initialization error occurs
     */
    @Override
    public void init() throws ServletException {
        DataSource dataSource = DatabaseConnection.lookupDataSource();
        RequestDAO requestDAO = new RequestDAOImpl(dataSource);
        requestService = new RequestService(requestDAO);

        NotificationDAO notificationDAO = new NotificationDAOImpl(dataSource);
        notificationService = new NotificationService(notificationDAO);
    }

    /**
     * Handles the HTTP GET request. It fetches a list of available courses and forwards 
     * the request to the JSP page for displaying and selecting a course to teach.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error is detected
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Course> availableCourses = List.of(
                new Course("Introduction to Computer Science", "CS101", "Fall 2024"),
                new Course("Data Structures and Algorithms", "CS202", "Spring 2024"),
                new Course("Web Development Fundamentals", "WEB301", "Summer 2024"),
                new Course("Database Management Systems", "CS305", "Fall 2024"),
                new Course("Software Engineering", "CS401", "Spring 2025")
        );

        request.setAttribute("availableCourses", availableCourses);
        request.getRequestDispatcher("/WEB-INF/views/RequestToTeach.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST request. It processes the submitted course request form, 
     * creates a course request record, and sends a notification of the request submission.
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
            // For demonstration, using a fixed userId. Replace with currentUser session logic.
            int userId = 1;

            CourseRequest courseRequest = new CourseRequest(
                    Integer.parseInt(request.getParameter("courseId")),
                    userId
            );

            requestService.submitRequest(courseRequest);
            notificationService.sendNotification(
                    userId,
                    "A request to teach course with id " + courseRequest.getCourseId() +
                            " by applicant with user ID " + courseRequest.getApplicantId() + " has been submitted."
            );

            response.sendRedirect("notifications");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Failed to submit request: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/RequestToTeach.jsp").forward(request, response);
        }
    }
}
