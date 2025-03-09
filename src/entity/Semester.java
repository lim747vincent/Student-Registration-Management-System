/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.List;
import adt.ListInterface;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Seah Meng Kwang 
 */
public class Semester {
    
    private String semesterId;
    private String name; // e.g., "Semester 1, 2, 3"
    private Date startDate;
    private Date endDate;
    private ListInterface<Course> coursesOffered; 

    // Constructor
    public Semester(String semesterId, String name, Date startDate, Date endDate) {
        this.semesterId = semesterId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coursesOffered = new List<>(); // Initialize the custom List
    }

    // Getters
    public String getSemesterId() {
        return semesterId;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public ListInterface<Course> getCoursesOffered() {
        return coursesOffered;
    }

    // Setters
    public void setSemesterId(String semesterId) {
        this.semesterId = semesterId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    // Method to add a course to the semester
    public void addCourse(Course course) {
        if (!coursesOffered.contains(course)) {
            coursesOffered.add(course);
        }
    }

    // Method to remove a course from the semester
    public boolean removeCourse(Course course) {
        return coursesOffered.remove(course);
    }
    
    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();

        sb.append("Semester ID: ").append(semesterId).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Start Date: ").append(dateFormat.format(startDate)).append("\n");
        sb.append("End Date: ").append(dateFormat.format(endDate)).append("\n");
        sb.append("Courses Offered:\n");

        for (int i = 0; i < coursesOffered.size(); i++) {
            Course course = coursesOffered.get(i);
            sb.append(course.toString()).append("\n");
            // The Course class also needs to have a toString method implemented
        }

        return sb.toString();
    }
}
