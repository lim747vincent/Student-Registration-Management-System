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
import entity.Faculty;
import java.io.*;

public class FacultyDAO {
    private final String filePath = ".\\src\\file\\faculties.txt";
    private CourseDAO courseDAO; 

    public FacultyDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
        // Ensure the file exists
        try {
            new File(filePath).createNewFile();
        } catch (IOException e) {
            System.err.println("Failed to create the faculties file: " + e.getMessage());
        }
    }

    public void saveFaculty(Faculty faculty) {
        // Load existing faculties
        ListInterface<Faculty> faculties = loadFaculties();
        // Manually iterate to find and remove existing faculty
        int indexToRemove = -1;
        for (int i = 0; i < faculties.size(); i++) {
            if (faculties.get(i).getFacultyId().equals(faculty.getFacultyId())) {
                indexToRemove = i;
                break;
            }
        }
        if (indexToRemove != -1) {
            faculties.removeAt(indexToRemove);
        }
        // Add the new or updated faculty
        faculties.add(faculty);
        // Save the updated list
        saveAllFaculties(faculties);
    }

    private void saveAllFaculties(ListInterface<Faculty> faculties) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (Faculty faculty : faculties) {
                String facultyStr = facultyToString(faculty);
                writer.write(facultyStr + ";");
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Failed to save faculties: " + e.getMessage());
        }
    }

    public ListInterface<Faculty> loadFaculties() {
        ListInterface<Faculty> faculties = new List<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Faculty faculty = stringToFaculty(line);
                if (faculty != null) {
                    faculties.add(faculty);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load faculties: " + e.getMessage());
        }
        return faculties;
    }

    // Converters with semicolon delimiter
    private Faculty stringToFaculty(String str) {
        String[] parts = str.split(";");
        if (parts.length < 4) return null;
        Faculty faculty = new Faculty(parts[0], parts[1], parts[2], parts[3]);
        for (int i = 4; i < parts.length; i++) {
            faculty.addCourseId(parts[i]);
        }
        return faculty;
    }

    private String facultyToString(Faculty faculty) {
        StringBuilder sb = new StringBuilder();
        sb.append(faculty.getFacultyId()).append(";")
          .append(faculty.getName()).append(";")
          .append(faculty.getTitle()).append(";")
          .append(faculty.getDepartment());
        for (String courseId : faculty.getCoursesTaughtIds().toArray(new String[0])) {
            sb.append(";").append(courseId);
        }
        return sb.toString();
    }
    
    public int countFacultiesOfferingCourse(String courseId) {
        int count = 0;
        ListInterface<Faculty> faculties = loadFaculties();
        for (Faculty faculty : faculties) {
            if (faculty.getCoursesTaughtIds().contains(courseId)) {
                count++;
            }
        }
        return count;
    }
    
    public Faculty getFacultyById(String facultyId) {
        ListInterface<Faculty> faculties = loadFaculties();
        for (Faculty faculty : faculties) {
            if (faculty.getFacultyId().equals(facultyId)) {
                return faculty;
            }
        }
        return null; // Faculty not found
    }
    
    public void updateCourseIdInFaculties(String oldCourseId, String newCourseId) {
        ListInterface<Faculty> faculties = loadFaculties();
        for (Faculty faculty : faculties) {
            ListInterface<String> courseIds = faculty.getCoursesTaughtIds();
            if (courseIds.contains(oldCourseId)) {
                courseIds.remove(oldCourseId);
                courseIds.add(newCourseId);
            }
        }
        saveAllFaculties(faculties); // Save the updated faculty data
    }

}

