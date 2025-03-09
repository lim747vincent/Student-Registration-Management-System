/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author SEAH MENG KWANG
 */
import entity.CourseDetails;

public class CourseDetailsInitializer {

    private CourseDetailsDAO courseDetailsDAO;

    public CourseDetailsInitializer(CourseDetailsDAO courseDetailsDAO) {
        this.courseDetailsDAO = courseDetailsDAO;
    }

    public void initialize() {
        
        CourseDetails[] detailsArray = new CourseDetails[]{
            new CourseDetails("BABS1000", "Main", 777.00),
            new CourseDetails("BABS1001", "Main ", 777.00),
            new CourseDetails("BABS1002", "Main", 777.00),
            new CourseDetails("BABS1004", "Main", 777.00),
            new CourseDetails("BABS1100", "Elective", 518.00),
            new CourseDetails("BABS1102", "Resit", 88.00),
            new CourseDetails("BABS1200", "Elective", 777.00),
            new CourseDetails("BACS1000", "Elective", 777.00),
            new CourseDetails("BACS1001", "Repeat", 518.00),
            new CourseDetails("BACS1002", "Resit", 88.00),
            new CourseDetails("BACS1003", "Main", 777.00),
            new CourseDetails("BACS1004", "Elective", 777.00),
            new CourseDetails("BACS1005", "Main", 777.00),
            new CourseDetails("BAEC1000", "Repeat", 518.00),
            new CourseDetails("BAPS1000", "Main", 518.00),
            new CourseDetails("BAPS1001", "Resit", 88.00),
            new CourseDetails("BAPS1002", "Resit", 88.00),
            new CourseDetails("BAPS1003", "Main", 518.00),
            new CourseDetails("BAPS1004", "Elective", 777.00),
            new CourseDetails("BAQS1000", "Main", 777.00),
            new CourseDetails("BAQS1001", "Repeat", 777.00),
            new CourseDetails("BAQS1002", "Resit", 88.00),
            new CourseDetails("BARS1001", "Elective", 518.00),
            new CourseDetails("BFFS1000", "Elective", 777.00),
            new CourseDetails("BFFS1001", "Main", 518.00),
            new CourseDetails("BFFS1002", "Elective", 777.00),
            new CourseDetails("BIAT1002", "Main", 518.00),
            new CourseDetails("MACA1000", "Repeat", 777.00),
            new CourseDetails("MACB1000", "Main", 777.00),
            new CourseDetails("MADM0004", "Main", 777.00),
        };

        // Save each CourseDetails object using CourseDetailsDAO
        for (CourseDetails details : detailsArray) {
            courseDetailsDAO.saveCourseDetails(details);
        }
    }
}

