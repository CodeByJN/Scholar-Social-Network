package controller;

import dao.CourseRequestDAO;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.sql.SQLException;

@WebServlet("/sendRequest")  // Changed to match the form action
public class SendRequestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CourseRequestDAO courseRequestDAO;

    public void init() throws ServletException {
        courseRequestDAO = new CourseRequestDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get parameters from the form
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            int professionalId = Integer.parseInt(request.getParameter("professionalId"));

            // Create the request in the database
            int requestId = courseRequestDAO.createRequest(courseId, professionalId, "PENDING");

            if (requestId > 0) {
                // Request created successfully
                response.sendRedirect("success.jsp");  // Create this JSP
            } else {
                // Failed to create request
                request.setAttribute("error", "Failed to create request");
                request.getRequestDispatcher("error.jsp").forward(request, response);  // Create this JSP
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid input format");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect GET requests to the form page
        response.sendRedirect("sendRequestForm.jsp");  // Rename your HTML to this
    }
}