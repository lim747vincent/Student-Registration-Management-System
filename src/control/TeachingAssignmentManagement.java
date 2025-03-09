package control;

import boundary.TeachingAssignmentManagementUI;
import entity.*;
import adt.*;
import dao.CourseDAO;
import dao.TutorDAO;
import dao.TutorialGroupDAO;
import java.io.*;
import util.*;

public class TeachingAssignmentManagement<T> {

    private final TeachingAssignmentManagementUI teachingAssignmentUI = new TeachingAssignmentManagementUI();

    CourseDAO courseDao = new CourseDAO();
    TutorialGroupDAO tutorialDao = new TutorialGroupDAO();
    TutorDAO tutorDao = new TutorDAO();

    public void runTeachingAssignmentMenuUI() {

        int choice;

        do {

            choice = 0; // Initialize choice to 0 to enter the loop

            // Display menu options
            choice = teachingAssignmentUI.menu();

            switch (choice) {
                case 0:
                    System.out.println("Thank You For This Subsystem. Exiting Now...");
                    return;
                case 1:
                    assignTutorToCourse();
                    break;
                case 2:
                    assignTutorialToTutor();
                    break;
                case 3:
                    assignTutorToTutorialToCourse();
                    break;
                case 4:
                    performSearchCoursesUnderTutor();
                    break;
                case 5:
                    performSearchTutorsForCourse();
                    break;
                case 6:
                    listTutorInTutorialForCourse();
                    break;
                case 7:
                    listTutorCourses();
                    break;
                case 8:
                    filterTutorCriteria();
                    break;
                case 9:
                    generateCourseTutorSummary();
                    break;
                case 10:
                    generateTutorCourseAssignedSummary();
                    break;
                default:
                    MessageUI.displayInvalidInputMessage();
                    break;
            }

        } while (choice != 0);
    }

    public void assignTutorToCourse() {
        String tutorID;
        String courseID;
        String classType;

        teachingAssignmentUI.displayTutors(tutorDao);
        // Loop until valid tutor ID is provided
        while (true) {

            tutorID = teachingAssignmentUI.inputTutorID();

            if (tutorID.equalsIgnoreCase("b")) {
                return;
            }

            // Check if the provided tutor ID is valid
            if (tutorDao.getTutorId(tutorID) != null) {
                break; // Exit the loop if valid tutor ID is provided
            } else {
                teachingAssignmentUI.displayTutorNotFound(tutorID);
            }
        }

        teachingAssignmentUI.displayCourses(courseDao);
        // Loop until valid course ID is provided
        while (true) {

            courseID = teachingAssignmentUI.inputCourseID();

            if (courseID.equalsIgnoreCase("b")) {
                return;
            }

            // Check if the provided course ID is valid
            if (courseDao.getCourseById(courseID) != null) {
                break; // Exit the loop if valid course ID is provided
            } else {
                teachingAssignmentUI.displayCourseNotFound(courseID);
            }
        }

        // Loop until valid class type is provided
        while (true) {
            classType = teachingAssignmentUI.inputClassType();

            if (classType.equalsIgnoreCase("b")) {
                return;
            }

            // Check if the class type is valid
            // Exit the loop if class type is provided
            break;
        }

        addTutorToCourse(courseID, tutorID, classType);

        teachingAssignmentUI.waitForEnter();
    }

    private void addTutorToCourse(String courseId, String tutorId, String classType) {
        Tutor tutor = tutorDao.getTutorId(tutorId);

        if (tutor == null) {
            teachingAssignmentUI.displayTutorNotFound(tutorId);
            return;
        }

        Course course = courseDao.getCourseById(courseId);

        if (course == null) {
            teachingAssignmentUI.displayCourseNotFound(courseId);
            return;
        }

        String courseName = course.getCourseName();
        String tutorName = tutor.getTutorName();

        ListInterface<String[]> tutorCourses = TutorDAO.checkTutorCourse(tutorId);
        for (String[] courseInfo : tutorCourses) {
            if (courseInfo[0].equals(courseId) && courseInfo[1].equals(classType)) {
                // Tutor is already assigned to the course with the same class type
                teachingAssignmentUI.displayCourseAlreadyAssociateTutor(courseId);
                return;
            }
        }

        // Add tutor to course
        course.addTutor(tutor);
        tutor.addCourse(course);

        // Save tutor-course mapping
        tutorDao.saveTutorCourse(tutorId, tutorName, courseId, courseName, classType);

        // Save updated tutor and course
        teachingAssignmentUI.displaySuccessAssignTutorToCourse(tutorId, courseId);
    }

    public void assignTutorialToTutor() {
        String tutorID;
        String tutorialID;
        String programmeID;

        teachingAssignmentUI.displayTutorials(tutorialDao);
        while (true) {

            programmeID = teachingAssignmentUI.inputProgrammeID();

            // Check if the user input is "b" or "B", and return if true
            if (programmeID.equalsIgnoreCase("b")) {
                return;
            }

            tutorialID = teachingAssignmentUI.inputTutorialGroupID();

            // Check if the user input is "b" or "B", and return if true
            if (tutorialID.equalsIgnoreCase("b")) {
                return;
            }

            if (tutorialDao.getTutorialId(programmeID, tutorialID) != null) {
                break; // Exit the loop if valid tutorial ID is provided
            } else {
                teachingAssignmentUI.displayTutorialNotFound(programmeID);
            }
        }

        teachingAssignmentUI.displayTutors(tutorDao);
        while (true) {
            tutorID = teachingAssignmentUI.inputTutorID();

            // Check if the user input is "b" or "B", and return if true
            if (tutorID.equalsIgnoreCase("b")) {
                return;
            }

            if (tutorDao.getTutorId(tutorID) != null) {
                break; // Exit the loop if valid tutor ID is provided
            } else {
                teachingAssignmentUI.displayTutorNotFound(tutorID);
            }

        }

        addTutorialToTutor(programmeID, tutorialID, tutorID);
        teachingAssignmentUI.waitForEnter();
    }

    public void addTutorialToTutor(String programmeId, String tutorialId, String tutorId) {
        TutorialGroup tgrp = tutorialDao.getTutorialId(programmeId, tutorialId);
        Tutor tutor = tutorDao.getTutorId(tutorId);

        if (tgrp == null) {
            teachingAssignmentUI.displayTutorialNotFound(programmeId);
            return;
        }
        if (tutor == null) {
            teachingAssignmentUI.displayTutorNotFound(tutorId);
            return;
        }

        // Get the courses associated with the tutorial group
        ListInterface<String> relatedCourses = TutorDAO.getRelatedCoursesForTutorial(programmeId);

        if (relatedCourses.isEmpty()) {
            teachingAssignmentUI.displayNoRelatedCourseFound(programmeId, tutorialId, tutorId);
            return;
        }

        // Get the courses assigned to the tutor
        ListInterface<String> tutorCourses = TutorDAO.getTutorCourses(tutorId);

// Check if the tutor has any course related to the tutorial group
        boolean hasRelatedCourse = false;
        for (String course : tutorCourses) {
            if (relatedCourses.contains(course)) {
                hasRelatedCourse = true;
                break;
            }
        }

// If the tutor has no related courses, display an error message
        if (!hasRelatedCourse) {
            teachingAssignmentUI.displayTutorialUnableAssign(tutorId);
            return;
        }

        // Get the courses assigned to the tutor
        ListInterface<String> tutorTutorials = TutorDAO.getTutorTutorial(tutorId, programmeId, tutorialId);

        if (tutorTutorials.contains(tutorialId)) {
            teachingAssignmentUI.displayTutorAlreadyAssociateTutorial(programmeId, tutorialId);
            return;
        }

        // Add tutor to tutorial
        tgrp.addTutorToTutorial(tutor);
        tutor.addTutorial(tgrp);

        // Save tutor-tutorial mapping
        tutorialDao.saveTutorialToTutor(programmeId, tgrp.getId(), tutorId, tutor.getTutorName());

        // Save updated tutor and tutorial
        teachingAssignmentUI.displayTutorialSuccessAssign(tutorId);
    }

    public void assignTutorToTutorialToCourse() {
        String courseID;
        String tutorID;
        String tutorialID;
        String programmeID;

        teachingAssignmentUI.displayCourses(courseDao);

        while (true) {
            // Step 1: Input the Course ID
            courseID = teachingAssignmentUI.inputCourseID();

            // Check if the user input is "b" or "B", and return if true
            if (courseID.equalsIgnoreCase("b")) {
                return;
            }

            // Step 2: Display the list of tutors assigned to the chosen course
            ListInterface<String> tutorsAssignedToCourse = TutorDAO.getTutorsAssignedToCourse(courseID);
            if (tutorsAssignedToCourse.isEmpty()) {
                teachingAssignmentUI.displayCourseNotFound(courseID);
                continue;
            }
            ListInterface<String> seenTutors = new List<>();

            for (String tutor : tutorsAssignedToCourse) {
                if (!seenTutors.contains(tutor)) {
                    System.out.println(tutor);
                    seenTutors.add(tutor);
                }
            }

            // Step 3: Choose a tutor from the list
            System.out.println("");
            tutorID = teachingAssignmentUI.inputTutorID();

            // Check if the user input is "b" or "B", and return if true
            if (tutorID.equalsIgnoreCase("b")) {
                return;
            }

            // Step 4: Input the Programme ID and Tutorial Group ID
            teachingAssignmentUI.displayTutorials(tutorialDao);
            while (true) {
                programmeID = teachingAssignmentUI.inputProgrammeID();
                tutorialID = teachingAssignmentUI.inputTutorialGroupID();

                // Check if the user input is "b" or "B", and return if true
                if (programmeID.equalsIgnoreCase("b") || tutorialID.equalsIgnoreCase("b")) {
                    return;
                }

                // Check if the provided tutor ID is valid
                if (tutorialDao.getTutorialId(programmeID, tutorialID) != null) {
                    break; // Exit the loop if valid tutor ID is provided
                } else {
                    teachingAssignmentUI.displayTutorNotFound(tutorID);
                }
                break;
            }

            // If all inputs are valid, break the outer loop and proceed
            break;
        }

        addTutorsToTutorialGroupForCourse(tutorID, programmeID, tutorialID, courseID);
        teachingAssignmentUI.waitForEnter();
    }

    public void addTutorsToTutorialGroupForCourse(String tutorId, String programmeId, String tutorialId, String courseId) {
        Course course = courseDao.getCourseById(courseId);
        TutorialGroup tutorialGroup = tutorialDao.getTutorialId(programmeId, tutorialId);
        Tutor tutor = tutorDao.getTutorId(tutorId);

        if (course == null) {
            teachingAssignmentUI.displayCourseNotFound(courseId);
            return;
        }

        if (tutorialGroup == null) {
            teachingAssignmentUI.displayTutorialNotFound(programmeId);
            return;
        }

        // Check if the tutorial group is related to the course
        ListInterface<String> relatedCourses = TutorDAO.getRelatedCoursesForTutorial(programmeId);
        if (!relatedCourses.contains(courseId)) {
            teachingAssignmentUI.displayNoRelatedCourseFound(programmeId, tutorialId, tutorId);
            return;
        }

        // Check if tutor exists
        if (tutor == null) {
            teachingAssignmentUI.displayTutorNotFound(tutorId);
            return; // Skip this tutor and proceed with the next one
        }

        // Retrieve tutors assigned to the course
        ListInterface<Tutor> tutorsAssignedToCourse = course.getAssociatedTutors();

        // Display tutors assigned to the course
        for (Tutor assignedTutor : tutorsAssignedToCourse) {
            System.out.println(assignedTutor.getTutorID());
        }

        // Get tutors assigned to the tutorial group
        ListInterface<String> relatedTutorialTutor = TutorDAO.getTutorialAssignedToTutor(tutorId, courseId, programmeId, tutorialId);

        // Check if the selected tutor is already associated with the tutorial group
        if (relatedTutorialTutor.contains(tutorialId)) {
            teachingAssignmentUI.displayTutorUnableToAssignTutorial(tutorId, programmeId, tutorialId);
            return;
        }

        // Add tutor to course
        course.addTutor(tutor);
        tutor.addCourse(course);

        // Add tutor to tutorial group
        tutorialGroup.addTutorToTutorial(tutor);
        tutor.addTutorial(tutorialGroup);

        // Save tutor-course and tutor-tutorialGroup mappings
        tutorialDao.saveTutorialToTutor(programmeId, tutorialGroup.getId(), tutorId, tutor.getTutorName());
        tutorDao.saveTutorTutorialCourse(tutorId, tutor.getTutorName(), programmeId, tutorialId, courseId, course.getCourseName());

        teachingAssignmentUI.displayTutorSuccessAssignToTutorialCourse(tutorId, programmeId, tutorialId, courseId);
    }

    public void performSearchCoursesUnderTutor() {
        String tutorID;

        teachingAssignmentUI.displayTutors(tutorDao);
        while (true) {

            tutorID = teachingAssignmentUI.inputTutorID();

            if (tutorID.equalsIgnoreCase("b")) {
                return;
            }

            // Check if the provided tutor ID is valid
            if (tutorDao.getTutorId(tutorID) != null) {
                break; // Exit the loop if valid tutor ID is provided
            } else {
                teachingAssignmentUI.displayTutorNotFound(tutorID);
            }
        }

        MapInterface<String, Course> searchCoursesUnderTutor = CourseDAO.searchCoursesUnderTutor(tutorID);

        if (searchCoursesUnderTutor.isEmpty()) {
            teachingAssignmentUI.displayNoCoursesUnderTutor(tutorID);
        } else {
            teachingAssignmentUI.printCoursesUnderTutor(tutorID, searchCoursesUnderTutor);
        }
        teachingAssignmentUI.waitForEnter();
    }

    public void performSearchTutorsForCourse() {
        String courseID;
        String classType;
        String courseName = ""; // Initialize courseName here

        String filePath = ".\\src\\file\\TutorCourse.txt";
        MapInterface<String, String> courseMap = TutorDAO.readTutorCourses2(filePath);

        // Display the courses in a table format
        System.out.println("Course ID       |   Course Name");
        System.out.println("----------------------------------------------------------");
        for (String courseIDs : courseMap.getKeys()) {
            String currentCourseName = courseMap.get(courseIDs);
            System.out.printf("%-15s |   %-30s\n", courseIDs, currentCourseName);
        }

        // Get user input for course ID
        while (true) {
            System.out.println("");
            courseID = teachingAssignmentUI.inputCourseID();

            if (courseID.equalsIgnoreCase("b")) {
                return;
            }

            // Check if the provided course ID is valid
            if (courseMap.containsKey(courseID)) {
                courseName = courseMap.get(courseID); // Extract courseName for the provided courseID
                break; // Exit the loop if valid course ID is provided
            } else {
                teachingAssignmentUI.displayCourseNotFound(courseID);
            }
        }

        // Get user input for class type
        classType = teachingAssignmentUI.inputClassType();

        // Search for tutors for the specified course ID and class type
        MapInterface<String, Tutor> tutorsMap = TutorDAO.searchTutorsForCourseByClassType(courseID, classType);

        // Display search results
        if (tutorsMap.isEmpty()) {
            teachingAssignmentUI.displayNoTutorUnderCourse(courseID);
        } else {
            teachingAssignmentUI.printTutorsForCourse(courseID, courseName, tutorsMap, classType); // Pass courseName here
        }
        teachingAssignmentUI.waitForEnter();
    }

    public void listTutorCourses() {
        // Assuming filePath contains the path to the TutorCourse.txt file
        String filePath = ".\\src\\file\\TutorCourse.txt";

        // Call the readTutorCourses method and store the returned map
        MapInterface<String, ListInterface<String>> tutorCoursesMap = TutorDAO.readTutorCourses(filePath);

        // Retrieve tutor names map
        MapInterface<String, String> tutorNamesMap = TutorDAO.getTutorNamesMap(filePath); // Assuming this method exists

        // Now you can use the tutorCoursesMap as needed
        if (!tutorCoursesMap.isEmpty()) {
            ListInterface<String> tutorIDs = tutorCoursesMap.getKeys();
            for (String tutorID : tutorIDs) {
                String tutorName = tutorNamesMap.get(tutorID); // Retrieve tutor name using tutor ID
                System.out.println();
                teachingAssignmentUI.displayCoursesUnderTutor(tutorID, tutorName);
                ListInterface<String> courses = tutorCoursesMap.get(tutorID);
                for (String course : courses) {
                    System.out.println(course);
                }
                System.out.println();
            }
        } else {
            teachingAssignmentUI.displayNoCourseFoundAny();
        }

        teachingAssignmentUI.waitForEnter();
    }

    public void listTutorInTutorialForCourse() {
        String filePath = ".\\src\\file\\TutorTutorialCourse.txt";

        // Call the readTutorTutorialCourse method from TutorTutorialCourseReader class
        MapInterface<String, ListInterface<String>> courseTutorMap = TutorDAO.readTutorTutorialCourse(filePath);

        // Now you can use the courseTutorMap as needed
        if (!courseTutorMap.isEmpty()) {
            // Iterate over the map to print the tutor details for each course
            for (String courseID : courseTutorMap.getKeys()) {
                // Retrieve course name directly from the map
                String courseName = CourseDAO.getCourseName(courseID);
                teachingAssignmentUI.printTutorTutorialHeader(courseID, courseName);
                ListInterface<String> tutorDetails = courseTutorMap.get(courseID);

                for (String tutorDetail : tutorDetails) {
                    System.out.println(tutorDetail);
                }
                System.out.println();
            }
        } else {
            System.out.println("No courses found in the file.");
        }

        teachingAssignmentUI.waitForEnter();
    }

    public void filterTutorCriteria() {
        try (BufferedReader reader = new BufferedReader(new FileReader(".\\src\\file\\Tutor.txt"))) {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.println("Enter filter criteria:");
                System.out.println("1. Tutor ID");
                System.out.println("2. Tutor Name");
                System.out.println("3. Tutor Qualification");
                System.out.println("4. Tutor Position");
                System.out.println("5. Tutor Faculty");
                System.out.print("\nEnter your Choice (b or B to back) : ");
                String choice = inputReader.readLine();

                if (choice.equalsIgnoreCase("b")) {
                    return;
                }

                int filter;
                try {
                    filter = Integer.parseInt(choice);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid choice. Please choose a valid option.");
                    System.out.println("");
                    continue;
                }

                if (filter < 1 || filter > 5) {
                    System.out.println("Invalid choice. Please choose a valid option.");
                    System.out.println("");
                    continue;
                }

                String value;
                switch (filter) {
                    case 1:
                        System.out.print("\nEnter Tutor ID (S101, S102, ...): ");
                        break;
                    case 2:
                        System.out.print("\nEnter Tutor Name (Full Name): ");
                        break;
                    case 3:
                        System.out.print("\nExample:");
                        System.out.print("\n[BSc] for Bachelor's Degree");
                        System.out.print("\n[Phd] for Doctor Of Philosophy");
                        System.out.print("\n[Msc] for Master's Degree");
                        System.out.print("\nEnter Tutor Qualification : ");
                        break;
                    case 4:
                        System.out.print("\nExample:");
                        System.out.print("\nLecturer");
                        System.out.print("\nAssociate Professor");
                        System.out.print("\nSenior Lecturer");
                        System.out.print("\nEnter Tutor Position: ");
                        break;
                    case 5:
                        System.out.print("\nExample:");
                        System.out.print("\nFOCS");
                        System.out.print("\nFAFB");
                        System.out.print("\nFSSH");
                        System.out.print("\nEnter Tutor Faculty: ");
                        break;
                }
                value = inputReader.readLine();

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (parts.length >= 5) {
                        String tutorID = parts[0];
                        String tutorName = parts[1];
                        String tutorQualification = parts[2];
                        String tutorPosition = parts[3];
                        String tutorFaculty = parts[4];

                        boolean matchesFilter = false;
                        switch (filter) {
                            case 1:
                                matchesFilter = tutorID.equals(value);
                                break;
                            case 2:
                                matchesFilter = tutorName.equalsIgnoreCase(value);
                                break;
                            case 3:
                                matchesFilter = tutorQualification.equalsIgnoreCase(value);
                                break;
                            case 4:
                                matchesFilter = tutorPosition.equalsIgnoreCase(value);
                                break;
                            case 5:
                                matchesFilter = tutorFaculty.equalsIgnoreCase(value);
                                break;
                        }

                        if (matchesFilter) {
                            System.out.println();
                            System.out.println("Tutor ID: " + tutorID);
                            System.out.println("Tutor Name: " + tutorName);
                            System.out.println("Tutor Qualification: " + tutorQualification);
                            System.out.println("Tutor Position: " + tutorPosition);
                            System.out.println("Tutor Faculty: " + tutorFaculty);
                            System.out.println();
                        }
                    }
                }
                teachingAssignmentUI.waitForEnter();
                break;
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void generateCourseTutorSummary() {
        MapInterface<String, Course> courseMap = new Map<>();

        String fileName = ".\\src\\file\\TutorCourse.txt"; // File name to read from
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) { // Assuming the file structure is tutorID, tutorName, courseID, courseName, classType
                    String courseID = parts[2].trim();
                    String courseName = parts[3].trim();
                    String classType = parts[4].trim();
                    Course course = courseMap.get(courseID);
                    if (course == null) {
                        course = new Course(courseID, courseName, classType);
                        courseMap.add(courseID, course); // Add the course to the map
                    }

                    course.incrementNumberOfTutors(); // Increment the number of tutors for this course
                    course.incrementNumberOfClassType();
                }
            }

            System.out.println("============================================================================================================");
            System.out.println("                    TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
            System.out.println("                                    TEACHING ASSIGNMENT SUBSYSTEM");
            System.out.println();
            System.out.println("                                        COURSE SUMMARY REPORT");
            System.out.println("                                --------------------------------------");
            System.out.println();
            System.out.println();

            // Display header
            System.out.println("Course ID\t\tCourse Name\t\t\t\tNumber Of Tutors");
            System.out.println("------------\t\t------------\t\t\t\t---------------");

            // Display each course and its corresponding number of tutors
            ListInterface<String> keys = courseMap.getKeys();
            int highestTutorCount = Integer.MIN_VALUE;
            int lowestTutorCount = Integer.MAX_VALUE;
            String highestTutorCourseID = "";
            String lowestTutorCourseID = "";

            for (int i = 0; i < keys.size(); i++) {
                String courseID = keys.get(i);
                Course course = courseMap.get(courseID);
                int numTutors = course.getNumberOfTutors(); // Get the number of tutors associated with this course
                System.out.printf("%-20s\t%-45s\t%-15d\n", course.getCourseID(), course.getCourseName(), numTutors);

                // Update highest and lowest tutor count and their respective course IDs
                if (numTutors > highestTutorCount) {
                    highestTutorCount = numTutors;
                    highestTutorCourseID = courseID;
                }
                if (numTutors < lowestTutorCount) {
                    lowestTutorCount = numTutors;
                    lowestTutorCourseID = courseID;
                }
            }

            // Create variables to store the counts of practical and tutorial classes
            int mostPracticalClassesCount = Integer.MIN_VALUE;
            int mostTutorialClassesCount = Integer.MIN_VALUE;
            String mostPracticalCourseID = "";
            String mostTutorialCourseID = "";

// Iterate through the keys to calculate the counts of practical and tutorial classes for each course
            for (int i = 0; i < keys.size(); i++) {
                String courseID = keys.get(i);
                Course course = courseMap.get(courseID);

                // Initialize counts inside the loop
                int practicalClassesCount = 0;
                int tutorialClassesCount = 0;

                // Count the occurrences of practical and tutorial classes for the current course
                if (course.getClassType().equalsIgnoreCase("P")) {
                    practicalClassesCount++;
                } else if (course.getClassType().equalsIgnoreCase("T")) {
                    tutorialClassesCount++;
                }

                // Update the most practical and tutorial courses if necessary
                if (practicalClassesCount > mostPracticalClassesCount) {
                    mostPracticalClassesCount = practicalClassesCount;
                    mostPracticalCourseID = courseID;
                }
                if (tutorialClassesCount > mostTutorialClassesCount) {
                    mostTutorialClassesCount = tutorialClassesCount;
                    mostTutorialCourseID = courseID;
                }
            }
            // Display total number of courses
            int totalCourses = courseMap.getValues().size();
            System.out.println("\nTotal " + totalCourses + " course has been assigned with tutors");

            System.out.println();
            System.out.println("----------------------------------------------------------------------------------------------");

            System.out.println("\nHighest Number of Tutor in a Course");
            System.out.println("--> [" + highestTutorCount + " Tutors] " + highestTutorCourseID + " " + courseMap.get(highestTutorCourseID).getCourseName());

            System.out.println();
            System.out.println("\nLowest Number of Tutor in a Course");
            System.out.println("--> [" + lowestTutorCount + " Tutors] " + lowestTutorCourseID + " " + courseMap.get(lowestTutorCourseID).getCourseName());

            System.out.println();
            System.out.println("[Note: 0 TUTOR ASSIGNED COURSE IS NOT COUNTED]");
            System.out.println();
            System.out.println();

            // Display the courses with the most practical and tutorial classes
            System.out.println("\nCourse with the Most Practical Classes:");
            System.out.println("--> ["+ "Practical Classes] " + mostPracticalCourseID + " " + courseMap.get(mostPracticalCourseID).getCourseName());

            System.out.println();
            System.out.println("\nCourse with the Most Tutorial Classes:");
            System.out.println("--> ["+ "Tutorial Classes] " + mostTutorialCourseID + " " + courseMap.get(mostTutorialCourseID).getCourseName());

            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("                           END OF THE TUTOR COURSE SUMMARY REPORT");
            System.out.println("============================================================================================================");

            teachingAssignmentUI.waitForEnter();

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }


    public void generateTutorCourseAssignedSummary() {
        MapInterface<String, Tutor> tutorMap = new Map<>();

        String fileName = ".\\src\\file\\TutorCourse.txt"; // File name to read from
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) { // Assuming the file structure is tutorID, tutorName, courseID, courseName, classType
                    String tutorID = parts[0].trim();
                    String tutorName = parts[1].trim();
                    Tutor tutor = tutorMap.get(tutorID);
                    if (tutor == null) {
                        tutor = new Tutor(tutorID, tutorName);
                        tutorMap.add(tutorID, tutor); // Add the tutor to the map
                    }
                    tutor.incrementNumberCourses(); // Increment the number of courses for this tutor
                }
            }

            System.out.println("============================================================================================================");
            System.out.println("                    TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
            System.out.println("                                    TEACHING ASSIGNMENT SUBSYSTEM");
            System.out.println();
            System.out.println("                                        TUTOR SUMMARY REPORT");
            System.out.println("                                --------------------------------------");
            System.out.println();
            System.out.println();

            // Display header
            System.out.println("Tutor ID\t\t\tTutor Name\t\t\tNumber Of Courses\t\tCourse Load Description");
            System.out.println("------------\t\t------------\t\t\t-------------------\t\t------------------------");

            // Display each tutor and the number of courses they are assigned to
            ListInterface<String> keys = tutorMap.getKeys();
            int highestCourseCount = Integer.MIN_VALUE;
            int lowestCourseCount = Integer.MAX_VALUE;
            String highestCourseTutorID = "";
            String lowestCourseTutorID = "";
            int highWorkloadTutors = 0;
            int moderateWorkloadTutors = 0;
            int lowWorkloadTutors = 0;

            for (int i = 0; i < keys.size(); i++) {
                String tutorID = keys.get(i);
                Tutor tutor = tutorMap.get(tutorID);
                int numCourses = tutor.getNumberOfCourse(); // Get the number of courses associated with this tutor
                String courseLoadDescription;
                if (numCourses >= 4) {
                    courseLoadDescription = "High";
                    highWorkloadTutors++;
                } else if (numCourses >= 2) {
                    courseLoadDescription = "Moderate";
                    moderateWorkloadTutors++;
                } else {
                    courseLoadDescription = "Low";
                    lowWorkloadTutors++;
                }
                System.out.printf("%-12s\t\t%-35s\t%-12d\t\t%-15s\n", tutor.getTutorID(), tutor.getTutorName(), numCourses, courseLoadDescription);

                // Update highest and lowest course count and their respective tutor IDs
                if (numCourses > highestCourseCount) {
                    highestCourseCount = numCourses;
                    highestCourseTutorID = tutorID;
                }
                if (numCourses < lowestCourseCount) {
                    lowestCourseCount = numCourses;
                    lowestCourseTutorID = tutorID;
                }
            }

            // Display total number of tutors
            int totalTutors = tutorMap.getValues().size();
            System.out.println("\nTotal " + totalTutors + " tutors");

            System.out.println();
            System.out.println("----------------------------------------------------------------------------------------------");

            System.out.println("\nTutor with the Highest Number of Courses Assigned");
            System.out.println("--> [" + highestCourseCount + " Courses] " + highestCourseTutorID + " " + tutorMap.get(highestCourseTutorID).getTutorName());

            System.out.println();
            System.out.println("\nTutor with the Lowest Number of Courses Assigned");
            System.out.println("--> [" + lowestCourseCount + " Courses] " + lowestCourseTutorID + " " + tutorMap.get(lowestCourseTutorID).getTutorName());

            System.out.println();
            System.out.println("[Note: TUTORS WITH MORE COURSES ASSIGNED TEND TO HAVE HIGHER WORKLOAD]");
            System.out.println();
            System.out.println();
            System.out.println("**************************************************************");

            System.out.println();
            System.out.println("Number Of Tutor with High Workload:");
            System.out.println("--> [" + highWorkloadTutors + " Tutors] has High Workload ");

            System.out.println();
            System.out.println();
            System.out.println("Number Of Tutor with Moderate Workload:");
            System.out.println("--> [" + moderateWorkloadTutors + " Tutors] has Moderate Workload ");

            System.out.println();
            System.out.println();
            System.out.println("Number Of Tutor with Low Workload:");
            System.out.println("--> [" + lowWorkloadTutors + " Tutors] has Low Workload ");
            System.out.println();
            System.out.println("[Note: TUTORS WITH 0 COURSES ASSIGNED ARE NOT INCLUDED.]");

            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("                           END OF THE COURSE TUTOR SUMMARY REPORT");
            System.out.println("============================================================================================================");

            teachingAssignmentUI.waitForEnter();

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

}
