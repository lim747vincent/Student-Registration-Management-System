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
import java.io.*;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class SemesterDAO {
    private final String filePath = ".\\src\\file\\semesters.txt"; // File path for storing semester data
    private final CourseDAO courseDAO; // For loading courses by ID

    public SemesterDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
        // Ensure the file exists
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Failed to create the semesters file: " + e.getMessage());
        }
    }

    public void saveSemester(Semester semester) {
        ListInterface<Semester> semesters = loadSemesters(); // Load existing semesters
        int indexToRemove = -1;
        for (int i = 0; i < semesters.size(); i++) {
            if (semesters.get(i).getSemesterId().equals(semester.getSemesterId())) {
                indexToRemove = i;
                break;
            }
        }
        if (indexToRemove != -1) {
            semesters.removeAt(indexToRemove);
        }
        semesters.add(semester);
        saveAllSemesters(semesters);
    }

    public void saveAllSemesters(ListInterface<Semester> semesters) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (Semester semester : semesters) {
                String startDateStr = dateFormat.format(semester.getStartDate());
                String endDateStr = dateFormat.format(semester.getEndDate());
                bw.write(semester.getSemesterId() + ";" + semester.getName() + ";" + startDateStr + ";" + endDateStr + ";");
                for (Course course : semester.getCoursesOffered()) {
                    bw.write(";" + course.getCourseID()); // Append course IDs with semicolon
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save semesters: " + e.getMessage());
        }
    }

    public ListInterface<Semester> loadSemesters() {
        ListInterface<Semester> semesters = new List<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 4) {
                    try {
                        Date startDate = dateFormat.parse(parts[2]);
                        Date endDate = dateFormat.parse(parts[3]);
                        Semester semester = new Semester(parts[0].trim(), parts[1].trim(), startDate, endDate);
                        for (int i = 4; i < parts.length; i++) {
                            Course course = courseDAO.getCourseById(parts[i].trim());
                            if (course != null) {
                                semester.getCoursesOffered().add(course);
                            }
                        }
                        semesters.add(semester);
                    } catch (ParseException e) {
                        System.err.println("Error parsing dates for semester: " + parts[0] + ": " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load semesters: " + e.getMessage());
        }
        return semesters;
    }

    public Semester getSemesterById(String semesterId) {
        ListInterface<Semester> semesters = loadSemesters();
        for (Semester semester : semesters) {
            if (semester.getSemesterId().equals(semesterId)) {
                return semester;
            }
        }
        return null;
    }
    
    public void updateCourseIdInSemesters(String oldCourseId, String newCourseId) {
        ListInterface<Semester> semesters = loadSemesters();
        for (Semester semester : semesters) {
            ListInterface<Course> courses = semester.getCoursesOffered();
            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).getCourseID().equals(oldCourseId)) {
                    courses.get(i).setCourseID(newCourseId); // Directly update the course ID
                }
            }
        }
        saveAllSemesters(semesters); // Save updated semesters
    }

}
