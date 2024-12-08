/**
 * Provides services for managing course requests, including submitting new requests
 * and responding to existing ones by updating their status.
 *
 * <p>This class acts as an intermediary between the web layer and the data access layer,
 * performing necessary validations and error handling. It relies on a RequestDAO
 * implementation for database operations.</p>
 *
 * <p>Common usage scenarios include creating a RequestService with a concrete RequestDAO,
 * then using it to submit or respond to course requests on behalf of the application.</p>
 *
 * <p>All database errors or unexpected conditions are translated into a ServiceException
 * to allow higher-level code to handle errors uniformly.</p>
 *
 * @author Jordan Earle
 */

package service;

import dao.interfaces.DAOException;
import dao.interfaces.RequestDAO;
import model.CourseRequest;

public class RequestService {

    private RequestDAO requestDAO;

    /**
     * Constructs a RequestService with the specified RequestDAO for database operations.
     *
     * @param requestDAO the DAO used to interact with the course requests data source
     */
    public RequestService(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    /**
     * Submits a new course request, inserting it into the database.
     * Any validation logic (e.g., ensuring the request is complete) can be placed here.
     *
     * @param request the CourseRequest object containing details of the requested course
     * @throws ServiceException if an error occurs while submitting the request
     */
    public void submitRequest(CourseRequest request) throws ServiceException {
        try {
            requestDAO.insertRequest(request);
        } catch (Exception e) {
            throw new ServiceException("Error submitting course request", e);
        }
    }

    /**
     * Responds to an existing course request by updating its status (e.g., "ACCEPTED" or "REJECTED").
     *
     * @param requestId the ID of the request to update
     * @param status    the new status for the course request
     * @throws ServiceException if an error occurs while updating the request status
     */
    public void respondToRequest(int requestId, String status) throws ServiceException {
        try {
            requestDAO.updateRequestStatus(requestId, status);
        } catch (Exception e) {
            throw new ServiceException("Error responding to course request", e);
        }
    }
}
