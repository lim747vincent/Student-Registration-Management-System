package entity;

import adt.*;

public class Tutor {

    private String tutorName;
    private String tutorID;
    private String qualification;
    private String position;
    private String faculty;
    private int numberOfCourse;
    private int numberOfTutorial;
    private MapInterface<String, Course> associatedCourse;
    private MapInterface<String, TutorialGroup> associatedTutorial;

    public Tutor(String tutorID, String tutorName) {
        this.tutorName = tutorName;
        this.tutorID = tutorID;

    }

    public Tutor(String tutorName, String tutorID, String qualification, String position, String faculty) {
        this.tutorName = tutorName;
        this.tutorID = tutorID;
        this.qualification = qualification;
        this.position = position;
        this.faculty = faculty;
        this.associatedCourse = new Map<>();
        this.associatedTutorial = new Map<>();
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getTutorID() {
        return tutorID;
    }

    public void setTutorID(String tutorID) {
        this.tutorID = tutorID;

    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public MapInterface<String, Course> getAssociatedCourse() {
        return associatedCourse;
    }

    public void setAssociatedCourse(Map<String, Course> associatedCourse) {
        this.associatedCourse = associatedCourse;
    }

    public MapInterface<String, TutorialGroup> getAssociatedTutorial() {
        return associatedTutorial;
    }

    public void setAssociatedTutorial(Map<String, TutorialGroup> associatedTutorial) {
        this.associatedTutorial = associatedTutorial;
    }

    // Add tutor to the course
    public void addCourse(Course course) {
        associatedCourse.add(course.getCourseID(), course);
    }

    public void addTutorial(TutorialGroup tutorialGroup) {
        associatedTutorial.add(tutorialGroup.getId(), tutorialGroup);
    }

    public void incrementNumberCourses() {
        this.numberOfCourse++;
    }

    public void incrementNumberTutorials() {
        this.numberOfTutorial++;
    }

    public int getNumberOfCourse() {
        return this.numberOfCourse;
    }

    public int getNumberOfTutorial() {
        return this.numberOfTutorial;
    }

    @Override
    public String toString() {
        return "Tutor{" + "tutorName=" + tutorName + ", tutorID=" + tutorID + ", qualification=" + qualification + ", position=" + position + ", faculty=" + faculty + ", numberOfCourse=" + numberOfCourse + ", numberOfTutorial=" + numberOfTutorial + ", associatedCourse=" + associatedCourse + ", associatedTutorial=" + associatedTutorial + '}';
    }

}
