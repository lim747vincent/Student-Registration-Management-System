/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Seah Meng Kwang 
 */
public class CourseDetails {

    private String courseId; // Unique identifier for the course
    private String courseType; // Main, elective, repeat, resit
    private double courseFees; // Fees for the course

    // Constructor, getters, and setters
    public CourseDetails(String courseId, String courseType, double courseFees) {
        this.courseId = courseId;
        this.courseType = courseType;
        this.courseFees = courseFees;
    }

    // Getters and setters
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public double getCourseFees() {
        return courseFees;
    }

    public void setCourseFees(double courseFees) {
        this.courseFees = courseFees;
    }

    @Override
    public String toString() {
        return "CourseDetails{" + "courseId=" + courseId + ", courseType=" + courseType + ", courseFees=" + courseFees + '}';
    }

}
