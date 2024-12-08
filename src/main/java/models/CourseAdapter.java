/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author baljo
 */
public class CourseAdapter {
    private final Course course;

    public CourseAdapter(Course course) {
        this.course = course;
    }

    public String getFormattedCourseDetails() {
        return "Course Title: " + course.getTitle() +
                ", Code: " + course.getCode() +
                ", Term: " + course.getTerm();
    }
}
