package controller;

/**
 * course: CST8288
 * name: Ningyi Wang
 * Student ID: 041120798
 */
import dao.UserDAO;
import dao.UserDAOImpl;
import model.AcademicInstitution;
import model.AcademicProfessional;
import model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet controller for handling user registration.
 * Supports registration for both Academic Professionals and Academic Institutions.
 */
public class UserRegistrationController extends HttpServlet {
    private final UserDAO userDAO;

    /**
     * Default constructor.
     * Initializes UserDAO with a singleton instance.
     */
    public UserRegistrationController() {
        this.userDAO = UserDAOImpl.getInstance();
    }

    /**
     * Parameterized constructor for injecting UserDAO, used primarily for testing.
     *
     * @param userDAO the UserDAO instance to use.
     */
    public UserRegistrationController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Handles the HTTP POST request for user registration.
     * Validates user input, creates a user based on the type, and inserts the user into the database.
     *
     * @param req  the HttpServletRequest object that contains the request.
     * @param resp the HttpServletResponse object that contains the response.
     * @throws IOException if an I/O error occurs during forwarding the request.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html; charset=UTF-8");

        try {
            String userType = req.getParameter("userType");
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            // Validate input and respond accordingly
            if (!isValidInput(userType, email, password, req)) {
                req.setAttribute("errorMessage", "Invalid input or missing required fields.");
                forwardToRegistrationPage(req, resp);
                return;
            }

            // Check if the email is already registered
            if (userDAO.getUserByEmail(email) != null) {
                req.setAttribute("errorMessage", "Email is already registered. Please use a different email.");
                forwardToRegistrationPage(req, resp);
                return;
            }

            // Create the user and insert it into the database
            User user = createUser(userType, email, password, req);
            if (user != null) {
                userDAO.insertUser(user);

                // Save user type and user info into the session
                HttpSession session = req.getSession();
                session.setAttribute("userType", userType);
                session.setAttribute("user", user);

                req.setAttribute("successMessage", "Registration successful! Redirecting to complete profile...");
                req.getRequestDispatcher("jsp/CompleteProfile.jsp").forward(req, resp);
            } else {
                req.setAttribute("errorMessage", "Invalid user type.");
                forwardToRegistrationPage(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "A server error occurred: " + e.getMessage());
            forwardToRegistrationPage(req, resp);
        }
    }

    /**
     * Validates the input parameters for user registration.
     *
     * @param userType the type of user being registered ("AcademicProfessional" or "AcademicInstitution").
     * @param email    the email address provided by the user.
     * @param password the password provided by the user.
     * @param req      the HttpServletRequest object that contains additional parameters.
     * @return true if the input is valid; false otherwise.
     */
    private boolean isValidInput(String userType, String email, String password, HttpServletRequest req) {
        if ("AcademicProfessional".equalsIgnoreCase(userType)) {
            return userType != null && !userType.isEmpty()
                    && req.getParameter("name") != null && !req.getParameter("name").isEmpty()
                    && isValidEmail(email)
                    && isValidPassword(password)
                    && req.getParameter("currentInstitution") != null && !req.getParameter("currentInstitution").isEmpty()
                    && req.getParameter("academicPosition") != null && !req.getParameter("academicPosition").isEmpty();
        } else if ("AcademicInstitution".equalsIgnoreCase(userType)) {
            return userType != null && !userType.isEmpty()
                    && isValidEmail(email)
                    && isValidPassword(password)
                    && req.getParameter("institutionName") != null && !req.getParameter("institutionName").isEmpty();
        }
        return false;
    }

    /**
     * Validates the provided email.
     *
     * @param email the email address to validate.
     * @return true if the email is valid; false otherwise.
     */
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    /**
     * Validates the provided password.
     * The password must contain at least one digit, one lowercase letter, one uppercase letter, one special character,
     * and be at least 8 characters long.
     *
     * @param password the password to validate.
     * @return true if the password is valid; false otherwise.
     */
    private boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$");
    }

    /**
     * Creates a User object based on the provided user type.
     *
     * @param userType the type of user to create ("AcademicProfessional" or "AcademicInstitution").
     * @param email    the email address of the user.
     * @param password the password of the user.
     * @param req      the HttpServletRequest object containing additional parameters.
     * @return the created User object, or null if the user type is invalid.
     */
    private User createUser(String userType, String email, String password, HttpServletRequest req) {
        if ("AcademicProfessional".equalsIgnoreCase(userType)) {
            String name = req.getParameter("name");
            String currentInstitution = req.getParameter("currentInstitution");
            String academicPosition = req.getParameter("academicPosition");
            return new AcademicProfessional(name, email, password, currentInstitution, academicPosition);
        } else if ("AcademicInstitution".equalsIgnoreCase(userType)) {
            String institutionName = req.getParameter("institutionName");
            return new AcademicInstitution(email, password, institutionName);
        }
        return null;
    }

    /**
     * Forwards the request back to the registration page.
     *
     * @param req  the HttpServletRequest object.
     * @param resp the HttpServletResponse object.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException      if an I/O error occurs.
     */
    private void forwardToRegistrationPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/Registration.jsp");
        dispatcher.forward(req, resp);
    }
}
