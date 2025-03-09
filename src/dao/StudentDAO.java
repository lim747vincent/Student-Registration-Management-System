/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.Map;
import adt.List;
import adt.ListInterface;
import adt.MapInterface;
import entity.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class StudentDAO {

    private final String filePath = ".\\src\\file\\student.txt"; // File path for storing student data

    public StudentDAO() {
        // Check if file exists, if not, create it
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Failed to create students file: " + e.getMessage());
            }
        }
    }

    public void saveStudent(Student student) {
        try (FileWriter fw = new FileWriter(filePath, true); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(studentToString(student) + ";");
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.err.println("Failed to save student: " + e.getMessage());
        }
    }

    public ListInterface<Student> loadStudents() {
        ListInterface<Student> students = new List<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                students.add(stringToStudent(line));
            }
        } catch (IOException e) {
            System.err.println("Failed to load students: " + e.getMessage());
        }
        return students;
    }

    public void updateStudentFile(ListInterface<Student> studentsList) {
        try (FileWriter fw = new FileWriter(filePath); BufferedWriter bw = new BufferedWriter(fw)) {
            for (int i = 0; i < studentsList.size(); i++) {
                Student student = studentsList.get(i);
                bw.write(studentToString(student) + ";");
                bw.newLine();
            }

            bw.close();
            fw.close();
        } catch (IOException e) {
            System.err.println("Failed to update student file: " + e.getMessage());
        }
    }

    private String studentToString(Student student) {
        String programmeID = student.getProgrammeId();
        String groupID = student.getTutorialGroupId();

        if (student.getTutorialGroupId().equals("N/A")) {
            groupID = "N/A";
        }

        return String.join(";", student.getId(), student.getName(), String.valueOf(student.getAge()), student.getGender(), programmeID, groupID);
    }

    private Student stringToStudent(String line) {
        String[] parts = line.split(";");

        if (parts.length == 6) {
            String id = parts[0];
            String name = parts[1];
            int age = Integer.parseInt(parts[2]);
            String gender = parts[3];
            String programmeID = parts[4];
            String groupID = parts[5];
            return new Student(id, name, age, gender, programmeID, groupID);
        } else {
            String programmeID = "N/A";
            String groupID = "N/A";

            String id = parts[0];
            String name = parts[1];
            int age = Integer.parseInt(parts[2]);
            String gender = parts[3];
            return new Student(id, name, age, gender, programmeID, groupID);
        }
    }

    public Student findStudentById(String studentId) {
        ListInterface<Student> students = loadStudents();
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.getId().equals(studentId)) {
                return student;
            }
        }
        return null; // If student not found
    }

    public MapInterface<String, Student> loadStudentsHashMap() {
        MapInterface<String, Student> students = new Map<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Student student = stringToStudent(line);
                students.add(student.getId(), student);
            }
        } catch (IOException e) {
            System.err.println("Failed to load students: " + e.getMessage());
        }
        return students;
    }

    public void writeStudentsToFileList(ListInterface<Student> students) {
        File myObj = new File(filePath);
        try {
            myObj.createNewFile();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        try (FileWriter myWriter = new FileWriter(filePath)) {
            for (int i = 0; i < students.size(); i++) {

                Student student = students.get(i);

                String groupID = student.getTutorialGroupId();

                if (student.getTutorialGroupId().equals("N/A")) {
                    groupID = "N/A";
                }

                try {

                    myWriter.write(student.getId() + ";");
                    myWriter.write(student.getName() + ";");
                    myWriter.write(student.getAge() + ";");
                    myWriter.write(student.getGender() + ";");
                    myWriter.write(student.getProgrammeId() + ";");
                    myWriter.write(groupID);
                    myWriter.write(";\n");

                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }

            myWriter.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
