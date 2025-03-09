/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.List;
import adt.ListInterface;

/**
 *
 * @author Seah Meng Kwang 
 */
public class Faculty {

    private String facultyId;
    private String name;
    private String title;
    private String department;
    private ListInterface<String> coursesTaughtIds;

    public Faculty(String facultyId, String name, String title, String department) {
        this.facultyId = facultyId;
        this.name = name;
        this.title = title;
        this.department = department;
        this.coursesTaughtIds = new List<>();
    }

    // Getters
    public String getFacultyId() {
        return facultyId;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDepartment() {
        return department;
    }

    public ListInterface<String> getCoursesTaughtIds() {
        return coursesTaughtIds;
    }

    // Setters
    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // Method to add a course ID to the list of courses taught by the faculty
    public void addCourseId(String courseId) {
        if (!coursesTaughtIds.contains(courseId)) {
            coursesTaughtIds.add(courseId);
        }
    }

    // Method to remove a course ID from the list of courses taught by the faculty
    public boolean removeCourseId(String courseId) {
        return coursesTaughtIds.remove(courseId);
    }

    @Override
    public String toString() {
        return "Faculty{" + "facultyId=" + facultyId + ", name=" + name + ", title=" + title + ", department=" + department + ", coursesTaughtIds=" + coursesTaughtIds + '}';
    }

}
