/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import model.Course;
import java.util.List;
/**
 *
 * @author baljo
 */

public interface CourseDAO {
    boolean insertCourse(Course course);
    Course fetchCourseByCode(String code);
    boolean updateCourse(Course course);
    List<Course> findCourses(String criteria);
}
