/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author SEAH MENG KWANG
 */

import entity.Faculty;

public class FacultyInitializer {
    private FacultyDAO facultyDAO;

    public FacultyInitializer(FacultyDAO facultyDAO) {
        this.facultyDAO = facultyDAO;
    }

    public void initialize() {
        // Example faculties
        Faculty faculty1 = new Faculty("FAC001", "Faculty of Science", "Professor", "Science");
        faculty1.addCourseId("MADM0004");
        faculty1.addCourseId("MADM1000");
        faculty1.addCourseId("MACA1000");
        faculty1.addCourseId("MACB1000");
        faculty1.addCourseId("BFFS1000");
        faculty1.addCourseId("BFFS1001");
        faculty1.addCourseId("BACS1002");
        faculty1.addCourseId("BAEC1000");
        faculty1.addCourseId("BFFS1002");
        faculty1.addCourseId("BAPS1000");
        faculty1.addCourseId("BAPS1001");
        faculty1.addCourseId("BAPS1002");
        faculty1.addCourseId("BAPS1003");
        faculty1.addCourseId("BAPS1004");

        Faculty faculty2 = new Faculty("FAC002", "Faculty of Engineering", "Associate Professor", "Engineering");
        faculty2.addCourseId("MADM0004");
        faculty2.addCourseId("MADM1000");
        faculty2.addCourseId("MACA1000");
        faculty2.addCourseId("MACB1000");
        faculty2.addCourseId("BACS1002");
        faculty2.addCourseId("BACS1001");
        
        Faculty faculty3 = new Faculty("FAC003", "Faculty of Arts", "Associate Professor", "Arts");
        faculty3.addCourseId("MADM0004");
        faculty3.addCourseId("MADM1000");
        faculty3.addCourseId("BAQS1000");
        faculty3.addCourseId("BAQS1001");
        faculty3.addCourseId("BAQS1002");
        
        Faculty faculty4 = new Faculty("FAC004", "Faculty of Business", "Associate Professor", "Business");
        faculty4.addCourseId("MADM0004");
        faculty4.addCourseId("MADM1000");
        faculty4.addCourseId("MACA1000");
        faculty4.addCourseId("MACB1000");
        faculty4.addCourseId("BABS1100");
        faculty4.addCourseId("BABS1000");
        faculty4.addCourseId("BABS1001");
        faculty4.addCourseId("BABS1102");
        faculty4.addCourseId("BABS1001");
        
        Faculty faculty5 = new Faculty("FAC005", "Faculty of Computing", "Associate Professor", "Computing");
        faculty5.addCourseId("MADM0004");
        faculty5.addCourseId("MADM1000");
        faculty5.addCourseId("MACA1000");
        faculty5.addCourseId("MACB1000");
        faculty5.addCourseId("BACS1000");
        faculty5.addCourseId("BACS1001");
        faculty5.addCourseId("BACS1002");
        faculty5.addCourseId("BACS1003");
        faculty5.addCourseId("BACS1004");
        faculty5.addCourseId("BACS1005");

        // Save faculties
        facultyDAO.saveFaculty(faculty1);
        facultyDAO.saveFaculty(faculty2);
        facultyDAO.saveFaculty(faculty3);
        facultyDAO.saveFaculty(faculty4);
        facultyDAO.saveFaculty(faculty5);

    }
}

