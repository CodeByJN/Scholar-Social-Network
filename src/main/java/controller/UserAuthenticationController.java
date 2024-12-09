/**
 * course: CST8288
 * name: Ningyi Wang
 * Student ID: 041120798
 */
package controller;

import dao.UserDAO;
import dao.UserDAOImpl;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet controller for handling user authentication.
 * Supports user login and logout functionality.
 */
public class UserAuthenticationController extends HttpServlet {
    private final UserDAO userDAO;

    /**
     * Default constructor, used in production to initialize UserDAO.
     */
    public UserAuthenticationController() {
        this.userDAO = UserDAOImpl.getInstance();
    }

    /**
     * Parameterized constructor, used for injecting UserDAO during testing.
     *
     * @param userDAO the UserDAO instance to use.
     */
    public UserAuthenticationController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Handles the HTTP POST request for user login.
     * Authenticates user credentials and establishes a session if successful.
     *
     * @param req  the HttpServletRequest object that contains the request.
     * @param resp the HttpServletResponse object that contains the response.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException      if an I/O error occurs during forwarding the request.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = userDAO.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userEmail", user.getEmail());
            resp.sendRedirect(req.getContextPath() + "/jsp/Dashboard.jsp");
        } else {
            req.setAttribute("message", "Invalid email or password.");
            req.setAttribute("messageColor", "red");
            req.getRequestDispatcher("/jsp/Login.jsp").forward(req, resp);
        }

    }



    /**
     * Handles the HTTP GET request for user logout.
     * If the user is logged in, invalidates the session and redirects to the login page with a message.
     *
     * @param req  the HttpServletRequest object that contains the request.
     * @param resp the HttpServletResponse object that contains the response.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException      if an I/O error occurs during forwarding the request.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("logout".equalsIgnoreCase(action)) {
            HttpSession session = req.getSession(false);

            // Check if there is an active session
            if (session == null || session.getAttribute("user") == null) {
                // If user is not logged in, set error message and forward to login page
                req.setAttribute("message", "You are not logged in.");
                req.setAttribute("messageColor", "red");
            } else {
                // If user is logged in, perform logout
                session.invalidate(); // Invalidate the session
                req.setAttribute("message", "Logged out successfully");
                req.setAttribute("messageColor", "green");
            }

            // Forward the request to the login page
            req.getRequestDispatcher("jsp/Login.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }
}
