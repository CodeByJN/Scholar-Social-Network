package controller;

import dao.UserDAO;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * Test class for {@link UserAuthenticationController}.
 * This class contains unit tests for the UserAuthenticationController class using Mockito to mock dependencies.
 */
public class UserAuthenticationControllerTest {

    private UserAuthenticationController userAuthenticationController;
    private UserDAO userDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    /**
     * Initializes mocks and the controller before each test method is run.
     */
    @BeforeEach
    public void setUp() {
        userDAO = mock(UserDAO.class);
        userAuthenticationController = new UserAuthenticationController(userDAO);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
    }

    /**
     * Tests the {@link UserAuthenticationController#doPost(HttpServletRequest, HttpServletResponse)} method
     * for a successful login scenario.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Test
    public void testDoPost_LoginSuccessful() throws ServletException, IOException {
        // Arrange
        String email = "test@example.com";
        String password = "password123";

        User user = mock(User.class);
        when(user.getEmail()).thenReturn(email);
        when(user.getPassword()).thenReturn(password);

        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(userDAO.getUserByEmail(email)).thenReturn(user);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("jsp/Login.jsp")).thenReturn(dispatcher);

        // Act
        userAuthenticationController.doPost(request, response);

        // Assert
        verify(session).setAttribute("user", user);
        verify(request).setAttribute("message", "Login successful!");
        verify(request).setAttribute("messageColor", "green");
        verify(dispatcher).forward(request, response);
    }

    /**
     * Tests the {@link UserAuthenticationController#doPost(HttpServletRequest, HttpServletResponse)} method
     * for an invalid credentials scenario where the provided password is incorrect.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Test
    public void testDoPost_InvalidCredentials() throws ServletException, IOException {
        // Arrange
        String email = "test@example.com";
        String password = "wrongpassword";

        User user = mock(User.class);
        when(user.getEmail()).thenReturn(email);
        when(user.getPassword()).thenReturn("correctpassword");

        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(userDAO.getUserByEmail(email)).thenReturn(user);
        when(request.getRequestDispatcher("jsp/Login.jsp")).thenReturn(dispatcher);

        // Act
        userAuthenticationController.doPost(request, response);

        // Assert
        verify(request).setAttribute("message", "Authentication failed.");
        verify(request).setAttribute("messageColor", "red");
        verify(dispatcher).forward(request, response);
    }

    /**
     * Tests the {@link UserAuthenticationController#doPost(HttpServletRequest, HttpServletResponse)} method
     * for an empty input fields scenario.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Test
    public void testDoPost_EmptyFields() throws ServletException, IOException {
        // Arrange
        when(request.getParameter("email")).thenReturn("");
        when(request.getParameter("password")).thenReturn("");
        when(request.getRequestDispatcher("jsp/Login.jsp")).thenReturn(dispatcher);

        // Act
        userAuthenticationController.doPost(request, response);

        // Assert
        verify(request).setAttribute("message", "Invalid email or password.");
        verify(request).setAttribute("messageColor", "red");
        verify(dispatcher).forward(request, response);
    }

    /**
     * Tests the {@link UserAuthenticationController#doGet(HttpServletRequest, HttpServletResponse)} method
     * for a successful logout scenario.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Test
    public void testDoGet_LogoutSuccessful() throws ServletException, IOException {
        // Arrange
        when(request.getParameter("action")).thenReturn("logout");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(mock(User.class));
        when(request.getRequestDispatcher("jsp/Login.jsp")).thenReturn(dispatcher);

        // Act
        userAuthenticationController.doGet(request, response);

        // Assert
        verify(session).invalidate();
        verify(request).setAttribute("message", "Logged out successfully");
        verify(request).setAttribute("messageColor", "green");
        verify(dispatcher).forward(request, response);
    }

    /**
     * Tests the {@link UserAuthenticationController#doGet(HttpServletRequest, HttpServletResponse)} method
     * for a logout attempt when the user is not logged in.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Test
    public void testDoGet_LogoutNotLoggedIn() throws ServletException, IOException {
        // Arrange
        when(request.getParameter("action")).thenReturn("logout");
        when(request.getSession(false)).thenReturn(null);
        when(request.getRequestDispatcher("jsp/Login.jsp")).thenReturn(dispatcher);

        // Act
        userAuthenticationController.doGet(request, response);

        // Assert
        verify(request).setAttribute("message", "You are not logged in.");
        verify(request).setAttribute("messageColor", "red");
        verify(dispatcher).forward(request, response);
    }

    /**
     * Tests the {@link UserAuthenticationController#doGet(HttpServletRequest, HttpServletResponse)} method
     * for an invalid action scenario.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Test
    public void testDoGet_InvalidAction() throws ServletException, IOException {
        // Arrange
        when(request.getParameter("action")).thenReturn("invalid");

        // Act
        userAuthenticationController.doGet(request, response);

        // Assert
        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
    }
}
