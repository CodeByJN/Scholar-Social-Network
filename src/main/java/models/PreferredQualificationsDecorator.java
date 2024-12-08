/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author baljo
 */
 
public class PreferredQualificationsDecorator extends CourseDecorator {
    private String preferredQualifications;

    public PreferredQualificationsDecorator(Course decoratedCourse, String preferredQualifications) {
        super(decoratedCourse);
        this.preferredQualifications = preferredQualifications;
    }

    public String getPreferredQualifications() {
        return preferredQualifications;
    }

    public void setPreferredQualifications(String preferredQualifications) {
        this.preferredQualifications = preferredQualifications;
    }

    @Override
    public String toString() {
        return super.toString() + ", Preferred Qualifications: " + preferredQualifications;
    }
}
