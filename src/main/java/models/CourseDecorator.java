/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author baljo
 */

public abstract class CourseDecorator extends Course {
    protected Course decoratedCourse;

    public CourseDecorator(Course decoratedCourse) {
        super(decoratedCourse.getTitle(), decoratedCourse.getCode(), decoratedCourse.getTerm());
        this.decoratedCourse = decoratedCourse;
    }

    @Override
    public String toString() {
        return decoratedCourse.toString();
    }
}

