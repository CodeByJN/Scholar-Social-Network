/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author baljo
 */

public class Course {
    private String title;
    private String code;
    private String term;

    public Course(String title, String code, String term) {
        this.title = title;
        this.code = code;
        this.term = term;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }

    @Override
    public String toString() {
        return "Course{" + "title='" + title + "', code='" + code + "', term='" + term + "'}";
    }
}

