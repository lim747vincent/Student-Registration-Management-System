package entity;

import adt.Map;
import adt.List;
import adt.ListInterface;
import adt.MapInterface;

/**
 *
 * @author LIM CHEE LEONG, SEAH MENG KWANG
 */
public class Programme {

    private String programmeId;
    private String programmeName;
    private String degreeLevel;
    private MapInterface<String, TutorialGroup> tutorialGroups;
    private MapInterface<String, Course> courses;

    public Programme() {

    }

    // for course subsystem
    public Programme(String programmeId, String programmeName) {
        this.programmeId = programmeId;
        this.programmeName = programmeName;
    }

    public Programme(String programmeId, String programmeName, String degreeLevel) {
        this.programmeId = programmeId;
        this.programmeName = programmeName;
        this.degreeLevel = degreeLevel;
        this.tutorialGroups = new Map<>();
        this.courses = new Map<>();
    }

    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }

    public String getProgrammeName() {
        return programmeName;
    }

    public void setProgrammeName(String programmeName) {
        this.programmeName = programmeName;
    }

    public String getDegreeLevel() {
        return degreeLevel;
    }

    public void setDegreeLevel(String degreeLevel) {
        this.degreeLevel = degreeLevel;
    }

    public MapInterface<String, TutorialGroup> getTutorialGroups() {
        return tutorialGroups;
    }

    public void setTutorialGroups(MapInterface<String, TutorialGroup> tutorialGroups) {
        this.tutorialGroups = tutorialGroups;
    }

    public void addTutorialGroup(TutorialGroup newTutorialGroup) {
        tutorialGroups.add(newTutorialGroup.getId(), newTutorialGroup);
    }

    public void removeTutorialGroup(TutorialGroup tutorialGroup) {
        tutorialGroups.remove(tutorialGroup.getId());
    }

    public MapInterface<String, Course> getCourses() {
        return courses;
    }

    // Method to add a course to the programme
    public void addCourse(Course course) {
        this.courses.add(course.getCourseID(), course);
    }

    // Method to remove a course from the programme
    public boolean removeCourse(String courseId) {
        return this.courses.remove(courseId) != null;
    }

    // Method to get a specific course by ID
    public Course getCourse(String courseId) {
        return this.courses.get(courseId);
    }

    public boolean isCourseAssociated(String courseId) {
        return courses.containsKey(courseId);
    }

    public boolean offersCourse(String courseId) {
        return this.courses.containsKey(courseId);
    }

    // Method to get all courses associated with the programme as a List of Course objects
    public ListInterface<Course> getAssociatedCourses() {
        ListInterface<Course> associatedCourses = new List<>();
        for (Course course : this.courses.getValues()) {
            associatedCourses.add(course);
        }
        return associatedCourses;
    }

    // Method to list all course names in the programme
    public ListInterface<String> listCourseNames() {
        ListInterface<String> courseNames = new List<>();
        for (int i = 0; i < this.courses.getValues().size(); i++) {
            Course course = this.courses.getValues().get(i);
            courseNames.add(course.getCourseName());
        }
        return courseNames;
    }

    @Override
    public String toString() {
        return "Programme{" + "programmeId=" + programmeId + ", programmeName=" + programmeName + ", degreeLevel=" + degreeLevel + ", tutorialGroups=" + tutorialGroups + ", courses=" + courses + '}';
    }

}
