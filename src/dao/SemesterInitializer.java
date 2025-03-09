/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author SEAH MENG KWANG
 */
import adt.List;
import adt.ListInterface;
import entity.Course;
import entity.Semester;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SemesterInitializer {
    private SemesterDAO semesterDao;
    private CourseDAO courseDao;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public SemesterInitializer(SemesterDAO semesterDao, CourseDAO courseDao) {
        this.semesterDao = semesterDao;
        this.courseDao = courseDao;
    }

    public void initializeSemesters() throws ParseException {
        ListInterface<Semester> semesters = new List<>(); 

        // Here we create our semesters
        Semester semester1 = new Semester("SEM001", "Semester 1", dateFormat.parse("2024-01-10"), dateFormat.parse("2024-05-15"));
        Semester semester2 = new Semester("SEM002", "Semester 2", dateFormat.parse("2024-06-10"), dateFormat.parse("2024-10-15"));
        Semester semester3 = new Semester("SEM003", "Semester 3", dateFormat.parse("2024-12-10"), dateFormat.parse("2025-02-15"));

        addCoursesToSemesterFromDAO(semester1, new String[]{"BABS1000", "BABS1001", "BABS1002", "BABS1004", "BABS1100", "BABS1102", "BABS1200", "BACS1000", "BACS1001", "BACS1002", "BACS1003"});
        addCoursesToSemesterFromDAO(semester2, new String[]{"BACS1004", "BACS1005", "BAEC1000", "BAPS1000", "BAPS1001", "BAPS1002", "BAPS1003", "BAPS1004", "BAQS1000", "BAQS1001"});
        addCoursesToSemesterFromDAO(semester3, new String[]{"BAQS1002", "BARS1001", "BFFS1000", "BFFS1001", "BFFS1002", "BIAT1002", "MACA1000", "MACB1000", "MADM0004", "MADM1000"});

        // Add semesters to the list
        semesters.add(semester1);
        semesters.add(semester2);
        semesters.add(semester3);

        // Save all semesters at once to avoid duplication
        semesterDao.saveAllSemesters(semesters);
    }

    private void addCoursesToSemesterFromDAO(Semester semester, String[] courseIds) {
        for (String courseId : courseIds) {
            Course course = courseDao.getCourseById(courseId);
            if (course != null) {
                semester.getCoursesOffered().add(course);
            } else {
                System.err.println("Course with ID " + courseId + " not found.");
            }
        }
    }
}
