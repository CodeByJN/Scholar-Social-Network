/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author baljo
 */
import DAO.*;
import models.Course;

import java.util.List;

/**
 * Service class for searching courses.
 */
public class SearchService {
    private final CourseDAO searchDAO;

    // Constructor Injection for flexibility
    public SearchService(CourseDAO searchDAO) {
        this.searchDAO = searchDAO;
    }

    // Default constructor using the concrete implementation
    public SearchService() {
        this(new CourseDAOimplement());
    }

    // Method to search courses by title or term
    public List<Course> searchCourses(String criteria) {
        return searchDAO.findCourses(criteria);
    }
}
