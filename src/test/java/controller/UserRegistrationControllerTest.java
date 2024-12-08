package controller;

import dao.UserDAO;
import model.AcademicInstitution;
import model.AcademicProfessional;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link UserRegistrationController}.
 * This class contains unit tests for the UserRegistrationController using Mockito to mock dependencies.
 */
class UserRegistrationControllerTest {

    private UserRegistrationController userRegistrationController;
    private UserDAO userDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    /**
     * Initializes the mocks and the controller before each test method is run.
     */
    @BeforeEach
    void setUp() {
        userDAO = Mockito.mock(UserDAO.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        dispatcher = Mockito.mock(RequestDispatcher.class);

        userRegistrationController = new UserRegistrationController(userDAO);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    /**
     * Tests the {@link UserRegistrationController#doPost(HttpServletRequest, HttpServletResponse)} method
     * when the provided email is already registered.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Test
    void testDoPost_EmailAlreadyRegistered() throws ServletException, IOException {
        // Arrange
        when(request.getParameter("userType")).thenReturn("AcademicProfessional");
        when(request.getParameter("email")).thenReturn("already@registered.com");
        when(request.getParameter("password")).thenReturn("Password1@");
        when(request.getParameter("name")).thenReturn("Test User");
        when(request.getParameter("currentInstitution")).thenReturn("Test Institution");
        when(request.getParameter("academicPosition")).thenReturn("Professor");

        User existingUser = new AcademicProfessional("Existing User", "already@registered.com", "Password1@", "Existing Institution", "Professor");
        when(userDAO.getUserByEmail("already@registered.com")).thenReturn(existingUser);

        // Act
        userRegistrationController.doPost(request, response);

        // Assert
        verify(userDAO, never()).insertUser(any());
        verify(request, times(1)).setAttribute(eq("errorMessage"), eq("Email is already registered. Please use a different email."));
        verify(dispatcher, times(1)).forward(request, response);
    }

    /**
     * Tests the {@link UserRegistrationController#doPost(HttpServletRequest, HttpServletResponse)} method
     * when the provided input fields are invalid or incomplete.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Test
    void testDoPost_InvalidInput() throws ServletException, IOException {
        // Arrange
        when(request.getParameter("userType")).thenReturn("AcademicProfessional");
        when(request.getParameter("email")).thenReturn("");
        when(request.getParameter("password")).thenReturn("Password1@");

        // Act
        userRegistrationController.doPost(request, response);

        // Assert
        verify(request, times(1)).setAttribute(eq("errorMessage"), eq("Invalid input or missing required fields."));
        verify(dispatcher, times(1)).forward(request, response);
    }

    /**
     * Tests the {@link UserRegistrationController#doPost(HttpServletRequest, HttpServletResponse)} method
     * for a successful registration scenario.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Test
    void testDoPost_RegistrationSuccessful() throws ServletException, IOException {
        // Arrange
        when(request.getParameter("userType")).thenReturn("AcademicProfessional");
        when(request.getParameter("email")).thenReturn("new@user.com");
        when(request.getParameter("password")).thenReturn("Password1@");
        when(request.getParameter("name")).thenReturn("New User");
        when(request.getParameter("currentInstitution")).thenReturn("New Institution");
        when(request.getParameter("academicPosition")).thenReturn("Lecturer");

        when(userDAO.getUserByEmail("new@user.com")).thenReturn(null);

        // Act
        userRegistrationController.doPost(request, response);

        // Assert
        verify(userDAO, times(1)).insertUser(any(AcademicProfessional.class));
        verify(request, times(1)).setAttribute(eq("successMessage"), eq("Registration successful! Redirecting to complete profile..."));
        verify(dispatcher, times(1)).forward(request, response);
    }

    /**
     * Tests the {@link UserRegistrationController#doPost(HttpServletRequest, HttpServletResponse)} method
     * when a server error occurs during registration.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Test
    void testDoPost_ServerError() throws ServletException, IOException {
        // Arrange
        when(request.getParameter("userType")).thenReturn("AcademicProfessional");
        when(request.getParameter("email")).thenReturn("test@servererror.com");
        when(request.getParameter("password")).thenReturn("Password1@");
        when(request.getParameter("name")).thenReturn("Test User");
        when(request.getParameter("currentInstitution")).thenReturn("Test Institution");
        when(request.getParameter("academicPosition")).thenReturn("Professor");

        when(userDAO.getUserByEmail(anyString())).thenThrow(new RuntimeException("Database connection failed"));

        // Act
        userRegistrationController.doPost(request, response);

        // Assert
        verify(request, times(1)).setAttribute(eq("errorMessage"), contains("A server error occurred:"));
        verify(dispatcher, times(1)).forward(request, response);
    }
}
