/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.*;
import entity.Student;

public class StudentInitializer {

    private StudentDAO studentDAO;

    public StudentInitializer(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public void initialize() {
        // Check if students need to be initialized or load existing ones
        ListInterface<Student> studentsList = studentDAO.loadStudents();

        if (studentsList.isEmpty()) {
            System.out.println("Student list is empty.");
        }

    }
}
