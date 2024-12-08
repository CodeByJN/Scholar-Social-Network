package controller;

import model.AcademicInstitution;
import model.AcademicProfessional;
import model.User;
import model.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.ProfileService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link ProfileController}.
 * This class contains unit tests for the ProfileController class using Mockito to mock dependencies.
 */
public class ProfileControllerTest {

    @InjectMocks
    private ProfileController profileController;

    @Mock
    private ProfileService profileService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    /**
     * Initializes mocks before each test method is run.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the {@link ProfileController#doGet(HttpServletRequest, HttpServletResponse)} method for a successful view of an AcademicProfessional profile.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Test
    void testDoGet_ProfileView_Success_AcademicProfessional() throws ServletException, IOException {
        // Arrange
        User user = new AcademicProfessional("John Doe", "johndoe@example.com", "password123", "Sample University", "Professor");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getRequestDispatcher("/jsp/Profile.jsp")).thenReturn(dispatcher);

        UserProfile userProfile = new UserProfile(
                "johndoe@example.com",
                "John Doe",
                "PhD in Computer Science",
                "Artificial Intelligence",
                null,
                null,
                "Sample University",
                "Professor",
                null
        );
        when(profileService.viewProfile(user)).thenReturn(userProfile);

        // Act
        profileController.doGet(request, response);

        // Assert
        verify(request).setAttribute("profile", userProfile);
        verify(request).setAttribute("userType", user.getUserType());
        verify(dispatcher).forward(request, response);
    }

    /**
     * Tests the {@link ProfileController#doGet(HttpServletRequest, HttpServletResponse)} method for a successful view of an AcademicInstitution profile.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Test
    void testDoGet_ProfileView_Success_AcademicInstitution() throws ServletException, IOException {
        // Arrange
        User user = new AcademicInstitution("university@example.com", "password123", "Sample University");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getRequestDispatcher("/jsp/Profile.jsp")).thenReturn(dispatcher);

        UserProfile userProfile = new UserProfile(
                "university@example.com",
                null,
                null,
                null,
                "123 Main St",
                "Courses A, Courses B",
                null,
                null,
                "Sample University"
        );
        when(profileService.viewProfile(user)).thenReturn(userProfile);

        // Act
        profileController.doGet(request, response);

        // Assert
        verify(request).setAttribute("profile", userProfile);
        verify(request).setAttribute("userType", user.getUserType());
        verify(dispatcher).forward(request, response);
    }

    /**
     * Tests the {@link ProfileController#doPost(HttpServletRequest, HttpServletResponse)} method for a successful profile update.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Test
    void testDoPost_UpdateProfile_Success() throws ServletException, IOException {
        // Arrange
        User user = new AcademicProfessional("John Doe", "johndoe@example.com", "password123", "Sample University", "Professor");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getContextPath()).thenReturn("");

        when(request.getParameter("educationBackground")).thenReturn("PhD in Computer Science");
        when(request.getParameter("areaOfExpertise")).thenReturn("Artificial Intelligence");
        when(request.getParameter("currentInstitution")).thenReturn("Sample University");
        when(request.getParameter("academicPosition")).thenReturn("Professor");

        when(profileService.updateProfile(any(User.class), anyMap(), any())).thenReturn(true);

        // Act
        profileController.doPost(request, response);

        // Assert
        verify(session).setAttribute("message", "Profile updated successfully.");
        verify(response).sendRedirect("/profile/profile");
    }

    /**
     * Tests the {@link ProfileController#doPost(HttpServletRequest, HttpServletResponse)} method for a failed profile update.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Test
    void testDoPost_UpdateProfile_Failure() throws ServletException, IOException {
        // Arrange
        User user = new AcademicProfessional("John Doe", "johndoe@example.com", "password123", "Sample University", "Professor");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getContextPath()).thenReturn("");

        when(request.getParameter("educationBackground")).thenReturn("PhD in Computer Science");
        when(request.getParameter("areaOfExpertise")).thenReturn("Artificial Intelligence");
        when(request.getParameter("currentInstitution")).thenReturn("Sample University");
        when(request.getParameter("academicPosition")).thenReturn("Professor");

        when(profileService.updateProfile(any(User.class), anyMap(), any())).thenReturn(false);

        // Act
        profileController.doPost(request, response);

        // Assert
        verify(session).setAttribute("message", "Failed to update profile. Please try again.");
        verify(response).sendRedirect("/profile/profile");
    }
}
