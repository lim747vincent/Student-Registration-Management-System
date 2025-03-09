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
 * @author yongf
 */

public class registerCourseDAO {

    private final String filePath = ".\\src\\file\\register_courses.txt"; // File path for storing register course data

    public registerCourseDAO() {
        try {
            new File(filePath).createNewFile();
        } catch (IOException e) {
            System.err.println("Failed to create register courses file: " + e.getMessage());
        }
    }

    public void saveRegisterCourse(RegisterCourse regCourse) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) { // true to append
            bw.write(registerCourseToString(regCourse) + ";");
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Failed to save register course: " + e.getMessage());
        }
    }

    public ListInterface<RegisterCourse> loadRegisterCourses() {
        ListInterface<RegisterCourse> registerCourses = new List<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                RegisterCourse regCourse = stringToRegisterCourse(line);
                if(regCourse != null) {
                    registerCourses.add(regCourse);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load register courses: " + e.getMessage());
        }
        return registerCourses;
    }
    
    public void updateRegisterCourseFile(ListInterface<RegisterCourse> registerCourseList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) { // false to overwrite
            for (RegisterCourse regCourse : registerCourseList) {
                bw.write(registerCourseToString(regCourse) + ";");
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to update register course file: " + e.getMessage());
        }
    }

    private String registerCourseToString(RegisterCourse regCourse) {
        String studentId = regCourse.getStudent().getId();
        String courseId = regCourse.getCourse().getCourseID();
        String courseDetailsId = regCourse.getCourseDetails() != null ? regCourse.getCourseDetails().getCourseId() : "none";
        return studentId + ";" + courseId + ";" + courseDetailsId; // Changed delimiter to semicolon
    }

    private RegisterCourse stringToRegisterCourse(String line) {
        String[] parts = line.split(";");
        if(parts.length < 2) return null; // Ensure there's at least ID for student and course

        StudentDAO studentsDAO = new StudentDAO();
        CourseDAO courseDAO = new CourseDAO();
        CourseDetailsDAO courseDetailsDAO = new CourseDetailsDAO();

        Student student = studentsDAO.findStudentById(parts[0]);
        Course course = courseDAO.findCourseById(parts[1]);
        CourseDetails courseDetails = parts.length > 2 && !parts[2].equals("none") ? courseDetailsDAO.findCourseDetailsByCourseId(parts[2]) : null;

        if (student == null || course == null) {
            // Handle the case where student or course is not found
            return null;
        }

        return new RegisterCourse(student, course, courseDetails);
    }
}


