package boundary;

import adt.*;
import dao.*;
import entity.*;

import java.util.Scanner;

public class TeachingAssignmentManagementUI {

    Scanner scan = new Scanner(System.in);

    public int menu() {

        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println("|                                                |");
        System.out.println("|       Teaching Assignment Management Menu      |");
        System.out.println("|                                                |");
        System.out.println("--------------------------------------------------");
        System.out.println("1. Assign tutor to courses");
        System.out.println("2. Assign tutorial groups to a tutor");
        System.out.println("3. Add tutors to a tutorial group for a course ");
        System.out.println("4. Search courses under a tutor");
        System.out.println("5. Search tutors for a course (T, P, L)");
        System.out.println("6. List tutors and tutorial groups for a course ");
        System.out.println("7. List courses for each tutor");
        System.out.println("8. Filter tutors based on criteria");
        System.out.println("9. Generate Tutor Course Summary Report");
        System.out.println("10. Generate Tutorial Tutor Summary Report");
        System.out.println("0. Exit");
        System.out.println();
        System.out.print("Enter choice : ");

        int choice = scan.nextInt();

        scan.nextLine();

        System.out.println();

        return choice;

    }

    public String inputTutorID() {
        System.out.print("Enter Tutor ID or [b] to back : ");
        String tutorID = scan.nextLine().trim(); // Trim to remove any leading or trailing whitespace

        return tutorID; // Return the entered tutor ID otherwise
    }

    public String inputTutorName() {

        System.out.print("Enter Tutor Name or [b] to back : ");
        String tutorName = scan.nextLine();
        return tutorName;

    }

    public String inputCourseName() {

        System.out.print("Enter Course Name or [b] to back : ");
        String courseName = scan.nextLine();
        return courseName;

    }

    public String inputClassType() {
        String classType;
        do {
            System.out.print("Enter Class Type (T, L, P) or [b] to go back: ");
            classType = scan.nextLine().trim(); // Trim to remove any leading or trailing whitespace

            // Check if the input is valid
            if (!classType.equalsIgnoreCase("b") && !classType.matches("[TLP]")) {
                System.out.println("Invalid input. Please enter 'L' for Lecture, 'T' for Tutorial, or 'P' for Practical.");
            }
        } while (!classType.equalsIgnoreCase("b") && !classType.matches("[TLP]")); // Repeat until valid input or "b" is entered

        return classType;
    }

    public String inputTutorialGroupID() {

        System.out.print("Enter Tutorial Group ID or [b] to back : ");
        String TgroupID = scan.nextLine();
        return TgroupID;

    }

    public String inputProgrammeID() {

        System.out.print("Enter Programme or [b] to back : ");
        String programme = scan.nextLine();
        return programme;

    }

    public String inputCourseID() {

        System.out.print("Enter Course ID or [b] to back : ");
        String courseID = scan.nextLine();

        if (courseID.equalsIgnoreCase("b")) {
            return "b";
        }
        return courseID;

    }

    public Tutor inputTutor() {
        String tutorID = inputTutorID();
        String tutorName = inputTutorName();

        return new Tutor(tutorName, tutorID);
    }

    public Tutor inputTutorToCourse() {

        String tutorID = inputTutorID();
        String tutorName = inputTutorName();

        return new Tutor(tutorID, tutorName);

    }

    public void waitForEnter() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
        System.out.println();
    }

    public void listAllTutorAndGroup() {

        System.out.println("\nList of all tutors and tutorial groups for a course :\n");
    }

    public void listAllTutorCourses() {

        System.out.println("\nList of all courses for each tutor :\n");
    }

    public int filterTutorCriteria() {
        System.out.println("1. Tutor ID ");
        System.out.println("2. Tutor Name ");
        System.out.println("3. Qualification  ");
        System.out.println("4. Position  ");
        System.out.println("5. Faculty  ");
        System.out.println();
        System.out.print("Enter filter choice : ");
        int option = scan.nextInt();
        return option;
    }

    public void displayCourses(CourseDAO courseDAO) {
        ListInterface<Course> courses = courseDAO.loadCourses();

        System.out.println("All Courses:");
        System.out.println("---------------------------------------------------------");
        System.out.println("| Course ID   |                     Course Name         |");
        System.out.println("---------------------------------------------------------");

        for (Course course : courses) {
            System.out.printf("| %-12s| %-40s|\n", course.getCourseID(), course.getCourseName());
        }

        System.out.println("---------------------------------------------------------");
    }

    public void displayTutorials(TutorialGroupDAO tutorialDao) {
        ListInterface<TutorialGroup> tutorial = tutorialDao.loadTutorialGroupList();

        System.out.println("Tutorial Groups:");
        System.out.println("--------------------------------------");
        System.out.println("| Programme   |   Tutorial ID        |");
        System.out.println("--------------------------------------");

        for (TutorialGroup tutorialGroup : tutorial) {
            System.out.printf("| %-12s| %-21s|\n", tutorialGroup.getProgrammeId(), tutorialGroup.getId());
        }
    }

    public void displayTutors(TutorDAO tutorDao) {
        ListInterface<Tutor> tutors = tutorDao.loadTutors();

        System.out.println();
        System.out.println("Tutors:");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.println("| Tutor ID       |   Tutor Name          |   Qualification       |   Position            |   Faculty          |");
        System.out.println("---------------------------------------------------------------------------------------------------------------");

        for (Tutor tutor : tutors) {
            System.out.printf(" %-16s| %-22s| %-22s| %-22s| %-20s\n",
                    tutor.getTutorID(),
                    tutor.getTutorName(),
                    tutor.getQualification(),
                    tutor.getPosition(),
                    tutor.getFaculty());
        }

        System.out.println("---------------------------------------------------------------------------------------------------------------");
    }

    public void displayTutorNotFound(String tutorId) {
        System.out.println("\nTutor with ID " + tutorId + " not found.");
    }

    public void displayCourseNotFound(String courseId) {
        System.out.println("\nCourse with ID " + courseId + " not found.");
    }

    public void displayProgrammeNotFound(String programmeId) {
        System.out.println("\nProgramme ID " + programmeId + " not found.");
    }
    
    public void displayTutorialNotFound(String tutorialId) {
        System.out.println("\nTutorial with ID " + tutorialId + " not found.");
    }

    public void displaySuccessAssignTutorToCourse(String tutorId, String courseId) {
        System.out.println("\nTutor with ID " + tutorId + " successfully assigned to Course with ID " + courseId + ".");
    }

    public void displayCourseAlreadyAssociateTutor(String courseId) {
        System.out.println("\nError : Course ID: " + courseId + " is already associated with the Tutor.");
    }

    public void displayTutorAlreadyAssociateTutorial(String programmeId, String tutorialId) {
        System.out.println("\nUnable to assign " + programmeId + " " + tutorialId + " because it is already assigned to the tutor.");
    }

    public void displayNoRelatedCourseFound(String tutorialId, String programmeId, String tutorId) {
        System.out.println("\n" + tutorId + " does not have Course that is related to " + programmeId + " " + tutorialId);
    }

    public void displayTutorialUnableAssign(String tutorId) {
        System.out.println("\nError : Tutorial Group is unable to be assign to " + tutorId + " because the tutor does not take any course related.");
    }

    public void displayTutorialSuccessAssign(String tutorId) {
        System.out.println("\nTutor with ID " + tutorId + " successfully assigned to Tutorial with ID " + tutorId + ".");
    }

    public void displayTutorUnableToAssignTutorial(String tutorId, String programmeId, String tutorialId) {
        System.out.println("\nTutor with ID " + tutorId + " is not able to be assigned to Tutorial Group " + programmeId + " " + tutorialId);
        System.out.println("Because tutor already exist in the Tutorial Group");
        System.out.println("");
    }

    public void displayTutorSuccessAssignToTutorialCourse(String tutorId, String programmeId, String tutorialId, String courseId) {
        System.out.println("\nTutor with ID " + tutorId + " successfully assigned to Tutorial Group " + programmeId + " " + tutorialId
                + " for Course ID " + courseId + ".");
    }

    public void displayNoCoursesUnderTutor(String tutorId) {
        System.out.println("No courses found under the tutor " + tutorId);
    }

    public void displayNoTutorUnderCourse(String courseId) {
        System.out.println("No courses found under the tutor " + courseId);
    }

    public void displayCoursesUnderTutor(String tutorId, String tutorName) {
        System.out.println("Courses under " + tutorId + " " + tutorName);
        System.out.println("-----------------------------------------");
    }

    public void displayNoCourseFoundAny() {
        System.out.println("No courses found for any tutors.");
    }

    public void displayNoCourseFound() {
        System.out.println("No courses found.");
    }

    public void printCoursesUnderTutor(String tutorID, MapInterface<String, Course> coursesUnderTutor) {
        System.out.println("Courses under the tutor: " + tutorID);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("|     Course ID                     |            Course Name                        |  Class Type  |");
        System.out.println("----------------------------------------------------------------------------------------------------");
        for (Course course : coursesUnderTutor.getValues()) {
            System.out.format("| %-33s | %-45s | %-12s |\n", course.getCourseName(), course.getCourseID(), course.getClassType());
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    public void printTutorsForCourse(String courseID, String courseName, MapInterface<String, Tutor> tutorsForCourse, String classType) {
        System.out.println();
        System.out.println("Tutors for Course ID " + courseID + " " + courseName);
        System.out.println("--------------------------------------------------");
        System.out.println("| Tutor ID     | Tutor Name     |  ClassType     |");
        System.out.println("--------------------------------------------------");
        for (Tutor tutor : tutorsForCourse.getValues()) {
            System.out.format("| %-12s | %-14s | %-14s |\n", tutor.getTutorID(), tutor.getTutorName(), classType);
        }
        System.out.println("--------------------------------------------------");
    }

    public void printTutorTutorial(String courseID, String courseName) {
        System.out.println("\nTutors and TutorialGroups --> " + courseID + " " + courseName);
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Tutor ID       | TutorName        | TutorialGroup   ");
        System.out.println("--------------------------------------------------------------------");
    }

    public void printTutorTutorialHeader(String courseID, String courseName) {
        System.out.println("\nTutors and TutorialGroups --> " + courseID + " " + courseName);
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Tutor ID       | TutorName        | Programme     | Tutorial ID    |");
        System.out.println("--------------------------------------------------------------------");
    }
}
