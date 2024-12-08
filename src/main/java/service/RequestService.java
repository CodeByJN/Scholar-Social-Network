package service;

import dao.interfaces.DAOException;
import dao.interfaces.RequestDAO;
import model.CourseRequest;

public class RequestService {

    private RequestDAO requestDAO;


    public RequestService(RequestDAO requestDAO){
        this.requestDAO = requestDAO;

    }

    public void submitRequest(CourseRequest request) throws ServiceException {

        // Validation should be implemented here.
        try {
            // Inserting request
            requestDAO.insertRequest(request);
        }
        catch(Exception e){
            throw new ServiceException("Error submiting course request", e);
        }

    }

    public void respondToRequest(int requestId, String status) throws ServiceException{

        try{

            requestDAO.updateRequestStatus(requestId, status);

        }
        catch(Exception e){
            throw new ServiceException("Error responding to course Request", e);
        }

    }
}
