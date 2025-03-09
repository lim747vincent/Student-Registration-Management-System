/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Course;

/**
 *
 * @author SEAH MENG KWANG
 */
public class CourseInitializer {
    private CourseDAO courseDao;

    public CourseInitializer(CourseDAO courseDao) {
        this.courseDao = courseDao;
    }

    public void initializeCourses() {
        // Define a set of courses to initialize
        Course[] courses = {
            new Course("MADM1000", "Advance Discrete Maths", 4),
            new Course("MACA1000", "Calculas I", 4),
            new Course("MACB1000", "Calculas II", 4),
            new Course("BACS1000", "Introduction to Computer Science", 3),
            new Course("BACS1001", "Software Engineering", 3),
            new Course("BACS1002", "Artificial Intelligence", 4),
            new Course("BACS1003", "Introduction to Information Security", 4),
            new Course("BABS1000", "Entrepreneurship", 3),
            new Course("BABS1001", "Introduction to Accounting", 3),
            new Course("BABS1002", "Introduction to Marketing", 3),
            new Course("BIAT1000", "Introduction to Art", 3),
            new Course("BIAT1001", "Basic Photography", 3),
            new Course("BIAT1002", "Graphic Design Project Management", 3),
            new Course("BFFS1000", "Introduction to Food Microbiology", 4),
            new Course("BFFS1001", "Introduction to Food Science", 4),
            new Course("BFFS1002", "Food Analysis", 4),
            new Course("BAPS1000", "Introduction to Psychology", 4),
            new Course("BAPS1001", "Introduction to Counselling", 4),
            new Course("BAPS1002", "Lifespan Development Psychology", 4),
            new Course("BAPS1003", "Research Method in Psychology", 4),
            new Course("BAQS1000", "Measurement of Substructure Works", 3),
            new Course("BAQS1001", "Measurement of Architecture Works", 3),
            new Course("BAQS1002", "Measurement of Civil Engineering Works", 3)
        };

        // Iterate through the array and save each course
        for (Course course : courses) {
            courseDao.saveCourse(course);
        }

        System.out.println("Courses initialized successfully.");
    }
}

