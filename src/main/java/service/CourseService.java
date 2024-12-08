/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import dao.CourseDAO;
import dao.CourseDAOimplement;
import model.Course;
/**
 *
 * @author baljo
 */

public class CourseService {
    private final CourseDAO courseDAO = new CourseDAOimplement() {};

    // Create a new course
    public boolean insertCourse(Course course) {
        try {
            return courseDAO.insertCourse(course);
        } catch (Exception e) {
            return false;
        }
    }

    // Fetch course by code (for editing)
    public Course fetchCourseByCode(String code) {
        try {
            return courseDAO.fetchCourseByCode(code);
        } catch (Exception e) {
            return null;
        }
    }

    // Update an existing course
    public boolean updateCourse(Course course) {
        try {
            return courseDAO.updateCourse(course);
        } catch (Exception e) {
            return false;
        }
    }
}

