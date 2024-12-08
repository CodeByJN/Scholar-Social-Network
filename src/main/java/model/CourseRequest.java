/**
 * Represents a request made by an applicant to teach a particular course.
 * Each CourseRequest includes information about the course ID, the applicant's ID,
 * the request status, and the timestamp when the request was created.
 *
 * <p>The default status for a new CourseRequest is "PENDING" and the creation time
 * is set to the current time.</p>
 *
 * <p>Getters and setters are provided for all fields, allowing easy access and
 * updates to the request details.</p>
 *
 * @author Jordan Earle
 */

package model;

import java.time.LocalDateTime;

public class CourseRequest {

    private int id;
    private int courseId;
    private int applicantId;
    private String status;
    private LocalDateTime createdAt;

    /**
     * Constructs a CourseRequest for a given course and applicant.
     * The status is initially set to "PENDING" and the creation time is the current time.
     *
     * @param courseId    the ID of the course requested
     * @param applicantId the ID of the applicant making the request
     */
    public CourseRequest(int courseId, int applicantId) {
        this.courseId = courseId;
        this.applicantId = applicantId;
        this.status = "PENDING";
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Gets the unique ID of the request.
     *
     * @return the request ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID of the request.
     *
     * @param id the request ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the ID of the requested course.
     *
     * @return the course ID
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Gets the ID of the applicant requesting to teach the course.
     *
     * @return the applicant ID
     */
    public int getApplicantId() {
        return applicantId;
    }

    /**
     * Gets the current status of the request.
     * Possible values may include "PENDING", "ACCEPTED", or "REJECTED".
     *
     * @return the request status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the request.
     *
     * @param status the new status for the request (e.g., "ACCEPTED", "REJECTED")
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the timestamp when the request was created.
     *
     * @return the creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
