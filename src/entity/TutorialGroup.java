package entity;

import adt.Map;
import adt.MapInterface;

/**
 *
 * @author LIM CHEE LEONG
 */
public class TutorialGroup {

    private String id;
    private int maxCapacity;
    private int numStudents;
    private String programmeId;
    private MapInterface<String, Student> students;
    private MapInterface<String, Tutor> associatedTutors;


    public TutorialGroup() {
    }

    public TutorialGroup(String id, int maxCapacity, int numStudents, String programmeId) {
        this.id = id;
        this.maxCapacity = maxCapacity;
        this.students = new Map<>();
        this.numStudents = numStudents;
        this.programmeId = programmeId;
        this.associatedTutors = new Map<>();
    }

    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }

    public int getNumStudents() {
        return numStudents;
    }

    public void setNumStudents(int numStudents) {
        this.numStudents = numStudents;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public MapInterface<String, Student> getStudents() {
        return students;
    }

    public void setStudents(MapInterface<String, Student> students) {
        this.students = students;
    }

    public void addStudent(Student newStudent) {
        students.add(newStudent.getId(), newStudent);
        numStudents++;
    }

    public Student removeStudent(String studentId) {
        numStudents--;
        return students.remove(studentId);
    }

    public boolean isFull() {
        return numStudents >= maxCapacity;
    }

    public MapInterface<String, Tutor> getAssociatedTutors() {
        return associatedTutors;
    }
    
    public void addTutorToTutorial(Tutor tutor) {
        associatedTutors.add(tutor.getTutorID(), tutor);
    }

    @Override
    public String toString() {
        return "TutorialGroup{" + "id=" + id + ", maxCapacity=" + maxCapacity + ", numStudents=" + numStudents + ", programmeId=" + programmeId + ", students=" + students + '}';
    }

}
