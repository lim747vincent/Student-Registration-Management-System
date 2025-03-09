/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.*;

/**
 *
 * @author Seah Meng Kwang Date: 10/4/2024 Course Entity
 */
public class Course implements Comparable<Course> {

    private String courseID;
    private String courseName;
    private int credits;
    private String classType;
    private int numberOfTutors;
    private int numberOfClassType;
    private ListInterface<String> semesterOffered;
    private MapInterface<String, Programme> associatedProgrammes;
    private MapInterface<String, Faculty> teachingFaculty;
    private MapInterface<String, Tutor> associatedTutors;

    public Course(String courseID, String courseName, int credits) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.credits = credits;
        this.semesterOffered = new List<>();
        this.associatedProgrammes = new Map<>();
        this.teachingFaculty = new Map<>();
        this.associatedTutors = new Map<>();

    }

    public Course(String courseID, String courseName, String classType) {

        this.courseID = courseID;
        this.courseName = courseName;
        this.classType = classType;
        this.associatedTutors = new Map<>();

    }

    public String getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public ListInterface<String> getSemesterOffered() {
        return semesterOffered;
    }

    public MapInterface<String, Programme> getAssociatedProgrammes() {
        return associatedProgrammes;
    }

    public MapInterface<String, Faculty> getTeachingFaculty() {
        return teachingFaculty;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    // Adding and removing semesters
    public void addSemesterOffered(String semester) {
        if (!semesterOffered.contains(semester)) {
            semesterOffered.add(semester);
        }
    }

    public boolean removeSemesterOffered(String semester) {
        return semesterOffered.remove(semester);
    }

    // Adding and removing programmes
    public void addProgramme(Programme programme) {
        associatedProgrammes.add(programme.getProgrammeId(), programme);
    }

    public boolean removeProgramme(String programmeId) {
        return associatedProgrammes.remove(programmeId) != null;
    }

    // Adding and removing faculty
    public void addFaculty(Faculty faculty) {
        teachingFaculty.add(faculty.getFacultyId(), faculty);
    }

    public boolean removeFaculty(String facultyId) {
        return teachingFaculty.remove(facultyId) != null;
    }

    public String getClassType() {
        return classType;
    }

    public void addTutor(Tutor tutor) {
        associatedTutors.add(tutor.getTutorID(), tutor);
    }

    // Get associated tutors
    public ListInterface<Tutor> getAssociatedTutors() {
        return associatedTutors.getValues();
    }

    public void incrementNumberOfTutors() {
        this.numberOfTutors++;
    }
    
    public void incrementNumberOfClassType() {
        this.numberOfClassType++;
    }

    public int getNumberOfClassType() {
        return numberOfClassType;
    }
    
    
    
    

    public int getNumberOfTutors() {
        return this.numberOfTutors;
    }

    public boolean hasTutor(String tutorId) {
        for (Tutor tutor : associatedTutors.getValues()) {
            if (tutor.getTutorID().equals(tutorId)) {
                return true; // Tutor found
            }
        }
        return false; // Tutor not found
    }

    @Override
    public int compareTo(Course other) {
        return this.courseName.compareTo(other.courseName);
    }

    @Override
    public String toString() {
        return String.format("Course ID: %s, Name: %s, Credits: %d",
                courseID, courseName, credits);
    }
    
    
}
