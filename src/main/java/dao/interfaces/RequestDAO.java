package dao.interfaces;

import model.CourseRequest;

public interface RequestDAO {

    void insertRequest(CourseRequest request) throws DAOException;
    void updateRequestStatus(int requestId, String status) throws DAOException;


}
