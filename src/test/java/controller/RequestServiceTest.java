/**
 * Unit tests for the RequestService class.
 * These tests verify that submitting a course request invokes the
 * RequestDAO's insertRequest method, and that DAO exceptions are
 * correct.
 *
 * <p>Author: Jordan Earle</p>
 */

package controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import dao.interfaces.DAOException;
import dao.interfaces.RequestDAO;
import model.CourseRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.RequestService;
import service.ServiceException;

public class RequestServiceTest {

    private RequestDAO requestDAO;
    private RequestService requestService;

    /**
     * Sets up the test environment before each test case.
     * Initializes a mocked RequestDAO and a RequestService instance that uses it.
     */
    @BeforeEach
    void setUp() {
        requestDAO = mock(RequestDAO.class);
        requestService = new RequestService(requestDAO);
    }

    /**
     * Tests that a course request can be submitted successfully.
     * Ensures that the RequestDAO's insertRequest method is called exactly once.
     *
     * @throws DAOException if a database access error occurs
     * @throws ServiceException if a service-level error occurs
     */
    @Test
    void testSubmitRequestSuccessfully() throws DAOException, ServiceException {
        // Given
        CourseRequest courseRequest = new CourseRequest(101, 999);

        // When
        requestService.submitRequest(courseRequest);

        // Then
        verify(requestDAO, times(1)).insertRequest(courseRequest);
    }

    /**
     * Tests that if the DAO throws a DAOException, the RequestService
     * wraps it in a ServiceException. Verifies that the thrown ServiceException
     * message contains an appropriate error message.
     *
     * @throws DAOException if a database access error occurs
     */
    @Test
    void testSubmitRequestDAOException() throws DAOException {
        // Given
        CourseRequest courseRequest = new CourseRequest(101, 999);
        doThrow(new DAOException("DB Error")).when(requestDAO).insertRequest(courseRequest);

        // When & Then
        ServiceException thrown = assertThrows(ServiceException.class, () -> {
            requestService.submitRequest(courseRequest);
        });

        assertTrue(thrown.getMessage().contains("Error submitting course request"));
    }
}
