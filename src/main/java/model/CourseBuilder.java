/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author baljo
 */

public class CourseBuilder {
    private String title;
    private String code;
    private String term;

    public CourseBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public CourseBuilder setCode(String code) {
        this.code = code;
        return this;
    }

    public CourseBuilder setTerm(String term) {
        this.term = term;
        return this;
    }




    public Course build() {
        return new Course(title, code, term);
    }
}

