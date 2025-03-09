/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.*;
import entity.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author SEAH MENG KWANG
 */
public class CourseDAO {

    private final String filePath = ".\\src\\file\\courses.txt"; // File path for storing course data
    private ProgrammeDAO programmeDAO;

    public CourseDAO() {
        this.programmeDAO = programmeDAO;
        try {
            new File(filePath).createNewFile();
        } catch (IOException e) {
            System.err.println("Failed to create the courses file: " + e.getMessage());
        }
    }

    // Saves a course to the file, using semicolon as the delimiter
    public void saveCourse(Course course) {
        ListInterface<Course> courses = loadCourses(); // Load existing courses from the file
        int existingCourseIndex = -1;

        // Check if the course already exists and find its index
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseID().equals(course.getCourseID())) {
                existingCourseIndex = i;
                break;
            }
        }

        if (existingCourseIndex != -1) {
            courses.removeAt(existingCourseIndex); // Remove the existing course
        }
        courses.add(course); // Add new or updated course

        // Rewrite the updated course list back to the file with semicolons
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
            for (Course c : courses) {
                String courseStr = String.join(";", c.getCourseID(), c.getCourseName(), Integer.toString(c.getCredits()));
                bw.write(courseStr + ";");
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save the course: " + e.getMessage());
        }
    }

    // Loads courses from the file, now expecting semicolon as the delimiter
    public ListInterface<Course> loadCourses() {
        ListInterface<Course> courses = new List<>(); // Use your custom List ADT
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) { // Ensure that there are exactly three parts
                    String courseID = parts[0];
                    String courseName = parts[1];
                    int credits = Integer.parseInt(parts[2]);
                    courses.add(new Course(courseID, courseName, credits));
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load courses: " + e.getMessage());
        }
        return courses;
    }

    public Course getCourseById(String courseId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3 && parts[0].trim().equals(courseId)) {
                    return new Course(parts[0].trim(), parts[1].trim(), Integer.parseInt(parts[2].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the course file: " + e.getMessage());
        }
        return null;
    }

    public void amendCourseId(String oldCourseId, String newCourseId, ProgrammeDAO programmeDAO, FacultyDAO facultyDAO, SemesterDAO semesterDAO) {
        ListInterface<Course> courses = loadCourses();
        boolean found = false;

        for (Course course : courses) {
            if (course.getCourseID().equals(oldCourseId)) {
                course.setCourseID(newCourseId);
                found = true;
                break;
            }
        }

        if (found) {
            saveAllCourses(courses); // Save updated courses

            // Update course ID in programme associations if ProgrammeDAO is provided
            if (programmeDAO != null) {
                programmeDAO.updateCourseIdInAssociations(oldCourseId, newCourseId);
            } else {
                System.err.println("ProgrammeDAO is required but not provided.");
            }

            // Update course ID in faculty records if FacultyDAO is provided
            if (facultyDAO != null) {
                facultyDAO.updateCourseIdInFaculties(oldCourseId, newCourseId);
            } else {
                System.err.println("FacultyDAO is required but not provided.");
            }

            // Update course ID in semester records if SemesterDAO is provided
            if (semesterDAO != null) {
                semesterDAO.updateCourseIdInSemesters(oldCourseId, newCourseId);
            } else {
                System.err.println("SemesterDAO is required but not provided.");
            }

        } else {
            System.out.println("Course with ID " + oldCourseId + " not found.");
        }
    }

    private void saveAllCourses(ListInterface<Course> courses) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
            for (Course course : courses) {
                String courseStr = String.join(";", course.getCourseID(), course.getCourseName(), Integer.toString(course.getCredits()));
                bw.write(courseStr + ";");
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save the course: " + e.getMessage());
        }
    }

    public Course findCourseById(String courseId) {
        ListInterface<Course> courses = loadCourses();
        for (Course course : courses) {
            if (course.getCourseID().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    public static MapInterface<String, Course> searchCoursesUnderTutor(String tutorID) {
        MapInterface<String, Course> coursesUnderTutor = new Map<>();
        String tutorIDLower = tutorID.toLowerCase();

        try (BufferedReader reader = new BufferedReader(new FileReader(".\\src\\file\\TutorCourse.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 5 && parts[0].toLowerCase().contains(tutorIDLower)) {
                    String courseID = parts[2];
                    Course course = new Course(courseID, parts[3], parts[4]); // Assuming Course constructor takes course ID and name
                    coursesUnderTutor.add(courseID, course);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CourseTutors.txt: " + e.getMessage());
        }

        return coursesUnderTutor;
    }

    public static String getCourseName(String courseID) {
        String courseName = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(".\\src\\file\\TutorTutorialCourse.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 6 && parts[4].equals(courseID)) {
                    courseName = parts[5];
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courseName;
    }
}


