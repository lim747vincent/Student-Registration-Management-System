/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author yongf
 */
public class RegisterCourse {

    private Student student;
    private Course course;
    private CourseDetails courseDetails; // Added field for CourseDetails

    // Constructors
    public RegisterCourse() {
    }

    public RegisterCourse(Student student, Course course, CourseDetails courseDetails) {
        this.student = student;
        this.course = course;
        this.courseDetails = courseDetails;
    }

    // Getter and setter for student
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    // Getter and setter for course
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    // Getter and setter for courseDetails (This is what you might be missing)
    public CourseDetails getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(CourseDetails courseDetails) {
        this.courseDetails = courseDetails;
    }

    @Override
    public String toString() {
        return "RegisterCourse{" + "student=" + student + ", course=" + course + ", courseDetails=" + courseDetails + '}';
    }

}
