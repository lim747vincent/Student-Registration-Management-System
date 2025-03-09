package entity;

import adt.List;
import adt.ListInterface;

/**
 *
 * @author LIM CHEE LEONG
 */
public class Student {

    private String id;
    private String name;
    private int age;
    private String gender;
    private String programmeId;
    private String tutorialGroupId;
    private ListInterface<Course> courses;

    public Student(String id, String name, int age, String gender, String programmeId, String tutorialGroupId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.programmeId = programmeId;
        this.tutorialGroupId = tutorialGroupId;
        this.courses = new List<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }

    public String getTutorialGroupId() {
        return tutorialGroupId;
    }

    public void setTutorialGroupId(String tutorialGroupId) {
        this.tutorialGroupId = tutorialGroupId;
    }

    public ListInterface<Course> getCourses() {
        return courses;
    }

    public void setCourses(ListInterface<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", programmeId=" + programmeId + ", tutorialGroupId=" + tutorialGroupId + ", courses=" + courses + '}';
    }

}
