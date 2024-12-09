/**
 * course: CST8288
 * name: Ningyi Wang
 * Student ID: 041120798
 */
package controller;

import service.ProfileService;
import dao.ProfileDAOImpl;
import model.User;
import model.UserProfile;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class for handling user profile related actions.
 * Handles requests to view or update user profiles.
 */
public class ProfileController extends HttpServlet {
    private ProfileService profileService;
    private static final Logger logger = Logger.getLogger(ProfileController.class.getName());

    /**
     * Default constructor (no arguments).
     * Initializes ProfileService with an instance of ProfileDAOImpl.
     */
    public ProfileController() {
        this.profileService = new ProfileService(ProfileDAOImpl.getInstance());
    }

    /**
     * Parameterized constructor for injecting ProfileService (used for testing or dependency injection).
     *
     * @param profileService The ProfileService instance to use.
     */
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * Handles the HTTP GET request for viewing user profiles.
     * Validates the user session, retrieves the profile, and forwards to the Profile JSP page.
     *
     * @param req  The HttpServletRequest object containing the client request.
     * @param resp The HttpServletResponse object containing the response to the client.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();

        // Default action handling if path is null or empty
        if (action == null || action.isEmpty()) {
            action = "/profile";
        }

        // Validate user session
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/jsp/Login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        if ("/profile".equals(action) || "/complete".equals(action)) {
            // Get user profile and set it as request attribute
            UserProfile profile = profileService.viewProfile(user);
            if (profile == null) {
                req.setAttribute("message", "Profile not found.");
            } else {
                req.setAttribute("profile", profile);
            }
            req.setAttribute("userType", user.getUserType());

            // Forward to Profile.jsp to display or complete user information
            req.getRequestDispatcher("/jsp/Profile.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid action.");
        }
    }

    /**
     * Handles the HTTP POST request for updating user profiles.
     * Validates the user session, processes profile update data, and redirects to avoid form resubmission.
     *
     * @param req  The HttpServletRequest object containing the client request.
     * @param resp The HttpServletResponse object containing the response to the client.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();

        // Default action handling if path is null or empty
        if (action == null || action.isEmpty()) {
            action = "/update";
        }

        // Validate user session
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/jsp/Login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        String userType = user.getUserType();

        Map<String, String> profileData = new HashMap<>();

        // Add specific fields based on user type
        if ("AcademicProfessional".equals(userType)) {
            profileData.put("educationBackground", getRequestParameter(req, "educationBackground"));
            profileData.put("areaOfExpertise", getRequestParameter(req, "areaOfExpertise"));
            profileData.put("currentInstitution", getRequestParameter(req, "currentInstitution"));
            profileData.put("academicPosition", getRequestParameter(req, "academicPosition"));
        } else if ("AcademicInstitution".equals(userType)) {
            profileData.put("institutionName", getRequestParameter(req, "institutionName"));
            profileData.put("address", getRequestParameter(req, "address"));
            profileData.put("coursesOfferedByTerm", getRequestParameter(req, "coursesOfferedByTerm"));
        }

        // Log request data
        logger.log(Level.INFO, "Updating profile for user: " + user.getEmail() + " with data: " + profileData);

        // Handle the update or complete action
        boolean updateSuccess = false;
        if ("/update".equals(action) || "/complete".equals(action)) {
            updateSuccess = profileService.updateProfile(user, profileData, userType);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid action.");
            return;
        }

        // Set message based on success or failure
        if (updateSuccess) {
            logger.log(Level.INFO, "Profile updated successfully for user: " + user.getEmail());
            session.setAttribute("message", "Profile updated successfully.");
        } else {
            logger.log(Level.SEVERE, "Failed to update profile for user: " + user.getEmail());
            session.setAttribute("message", "Failed to update profile. Please try again.");
        }

        // Redirect to avoid form resubmission
        resp.sendRedirect(req.getContextPath() + "/profile/profile");
    }

    /**
     * Helper method to safely retrieve request parameters, avoiding null values.
     *
     * @param req   The HttpServletRequest object.
     * @param param The parameter name to retrieve.
     * @return The parameter value or an empty string if null.
     */
    protected String getRequestParameter(HttpServletRequest req, String param) {
        String value = req.getParameter(param);
        return value != null ? value.trim() : "";
    }
}
