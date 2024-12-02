package model;

import java.sql.Timestamp;

/**
 * Represents a course request made by a professional to teach a course
 * at an academic institution.
 */
public class CourseRequest {

    // Unique identifier for the course request
    private int id;

    // Identifier of the course for which the request is made
    private int courseId;

    // Identifier of the professional making the request
    private int professionalId;

    // Current status of the request (e.g., "Pending", "Accepted", "Rejected")
    private String status;

    // Timestamp when the request was created
    private Timestamp requestDate;

    /**
     * Constructs a new CourseRequest object with the given details.
     *
     * @param id             the unique identifier for the course request
     * @param courseId       the identifier of the course
     * @param professionalId the identifier of the professional making the request
     * @param status         the current status of the request
     * @param requestDate    the timestamp when the request was created
     */
    public CourseRequest(int id, int courseId, int professionalId, String status, Timestamp requestDate) {
        this.id = id;
        this.courseId = courseId;
        this.professionalId = professionalId;
        this.status = status;
        this.requestDate = requestDate;
    }

    /**
     * Gets the unique identifier of the course request.
     *
     * @return the course request ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the identifier of the course for which the request is made.
     *
     * @return the course ID
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Gets the identifier of the professional who made the request.
     *
     * @return the professional ID
     */
    public int getProfessionalId() {
        return professionalId;
    }

    /**
     * Gets the current status of the request.
     *
     * @return the status of the course request
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets the timestamp of when the request was created.
     *
     * @return the creation date and time of the request
     */
    public Timestamp getRequestDate() {
        return requestDate;
    }
}
