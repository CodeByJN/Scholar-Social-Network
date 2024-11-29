package controller;

import dao.UserDAO;
import dao.UserDAOImpl;
import model.AcademicInstitution;
import model.AcademicProfessional;
import model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserRegistrationController extends HttpServlet {
    private final UserDAO userDAO;

    public UserRegistrationController() {
        this.userDAO = new UserDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");

        try {
            String userType = req.getParameter("userType");
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            if (!isValidInput(userType, name, email, password)) {
                sendResponse(resp, "Invalid input or missing required fields. Redirecting to Registration...", "red", "/Scholar_Social_Network_war/jsp/Registration.jsp");
                return;
            }

            if (userDAO.getUserByEmail(email) != null) {
                sendResponse(resp, "Email is already registered. Redirecting to Registration...", "red", "/Scholar_Social_Network_war/jsp/Registration.jsp");
                return;
            }

            User user = createUser(userType, name, email, password, req);
            if (user != null) {
                userDAO.insertUser(user);
                sendResponse(resp, "Registration successful! Redirecting to Login...", "green", "/Scholar_Social_Network_war/jsp/Login.jsp");
            } else {
                sendResponse(resp, "Invalid user type or missing additional fields. Redirecting to Registration...", "red", "/Scholar_Social_Network_war/jsp/Registration.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(resp, "A server error occurred: " + e.getMessage() + ". Redirecting to Registration...", "red", "/Scholar_Social_Network_war/jsp/Registration.jsp");
        }
    }

    private boolean isValidInput(String userType, String name, String email, String password) {
        return userType != null && !userType.isEmpty()
                && name != null && !name.isEmpty()
                && isValidEmail(email)
                && isValidPassword(password);
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$");
    }

    private User createUser(String userType, String name, String email, String password, HttpServletRequest req) {
        if ("AcademicProfessional".equals(userType)) {
            String currentInstitution = req.getParameter("currentInstitution");
            String academicPosition = req.getParameter("academicPosition");
            return new AcademicProfessional(name, email, password, currentInstitution, academicPosition);
        } else if ("AcademicInstitution".equals(userType)) {
            String institutionName = req.getParameter("institutionName");
            return new AcademicInstitution(name, email, password, institutionName);
        }
        return null;
    }

    private void sendResponse(HttpServletResponse resp, String message, String color, String redirectUrl) throws IOException {
        resp.getWriter().write(String.format(
                "<div style='margin:50px auto; width:400px; text-align:center;'>"
                        + "<h3 style='color:%s;'>%s</h3>"
                        + "<a href='%s'>If not redirected, click here</a>"
                        + "</div>"
                        + "<script>setTimeout(function(){ window.location.href='%s'; }, 3000);</script>",
                color, message, redirectUrl, redirectUrl
        ));
    }
}
