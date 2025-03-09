/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.List;
import adt.ListInterface;
import adt.Map;
import adt.MapInterface;
import entity.Course;
import entity.CourseDetails;
import entity.Programme;
import entity.Semester;
import entity.Faculty;
import dao.CourseDAO;
import dao.CourseDetailsDAO;
import dao.ProgrammeDAO;
import dao.SemesterDAO;
import dao.FacultyDAO;
import dao.TutorDAO;
import boundary.CourseManagementUI;
import java.util.Scanner;
/**
 *
 * @author aaron
 */
public class CourseManagementControl {
    
    private final CourseDAO courseDAO;
    private final ProgrammeDAO programmeDAO;
    private final TutorDAO tutorDAO;
    private final SemesterDAO semesterDAO;
    private final FacultyDAO facultyDAO;
    private final CourseDetailsDAO courseDetailsDAO;
    private final CourseManagementUI ui;
    private final MapInterface<String, ListInterface<Course>> coursesBySemester;
    private final MapInterface<String, ListInterface<Course>> facultyCoursesMap;
    private MapInterface<String, ListInterface<Course>> semesterCoursesMap = new Map<>();



    public CourseManagementControl() {
        // Initialize the DAOs
        courseDAO = new CourseDAO();
        programmeDAO = new ProgrammeDAO();
        tutorDAO = new TutorDAO();
        semesterDAO = new SemesterDAO(courseDAO);  
        facultyDAO = new FacultyDAO(courseDAO);    
        courseDetailsDAO = new CourseDetailsDAO();
        ui = new CourseManagementUI();  // UI setup for course management
        coursesBySemester = new Map<>(); 
        preloadSemesterCourses(); // Method to preload courses into the map
        facultyCoursesMap = new Map<>();
        preloadFacultyCourses();
    }
    
    Scanner scanner = new Scanner(System.in);
    
    public void runCourseManagementMenu() {
        int userChoice;
        boolean invalidInput;
        do {
            invalidInput = false;
            ui.displayMainMenu();
            userChoice = ui.getUserMenuChoice();

            // Validation for input
            if (userChoice < 1 || userChoice > 10) {
                ui.displayInvalidInputMessage();
                invalidInput = true;
            } else {
                // Switch case structure for handling user choices
                switch (userChoice) {
                    case 1:
                        // Add a programme to courses
                        addProgramToCourse();
                        break;
                    case 2:
                        // Remove a programme from a course
                        removeProgrammeFromCourse();
                        break;
                    case 3:
                        // Add a new course to programmes
                        addNewCourseToProgramme();
                        break;
                    case 4:
                        // Remove a course from a programme
                        removeCourseFromProgramme();
                        break;
                    case 5:
                        // Search courses offered in a semester
                        searchCoursesOfferedInSemester();
                        break;
                    case 6:
                        // Amend course details for a programme
                        amendCourseDetailsForProgramme();
                        break;
                    case 7:
                        // List all courses taken by different faculties
                        listCoursesByFaculty();
                        break;
                    case 8:
                        // List all courses for a programme
                        displayCoursesForProgramme();
                        break;
                    case 9:
                        // Generate Summary Report
                        handleSummaryReportChoice();
                        break;
                    case 10:
                        System.out.println("Thank You For Using Course Management Subsystem. Exiting Now...");
                        break;
                    default:
                        System.out.println("Inavlid Input Option. Please Choose From Option 1 to 10.");
                        break;
                }
            }

            if (invalidInput) {
                continue; // Go to the next iteration of the loop to prompt the user again
            }

            ui.promptPressEnterToContinue(); // Prompt user to continue after valid input
        } while (userChoice != 10);
    }

    public void handleSummaryReportChoice() {
        ui.displaySummaryReportMenu();
        int reportChoice = ui.getSummaryReportChoice();

        // Validation for input
        while (reportChoice != 1 && reportChoice != 2) {
            ui.displayInvalidInputMessage();
            ui.displaySummaryReportMenu();
            reportChoice = ui.getSummaryReportChoice();
        }

        switch (reportChoice) {
            case 1:
                generateCourseAvailabilitySummaryReport();
                break;
            case 2:
                generateCompleteSummaryReport();
                break;
        }
    }
    
    public void createProgramme(String programmeId, String programmeName, String degreeLevel) {
        Programme newProgramme = new Programme(programmeId, programmeName, degreeLevel);
        programmeDAO.saveProgramme(newProgramme);
    }

    public void addCourseToProgramme(String courseId, String programmeId) {
        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            ui.printCourseNotFound(courseId); 
            return;
        }

        Programme programme = programmeDAO.getProgrammeById(programmeId);
        if (programme == null) {
            ui.printProgrammeNotFound(programmeId); 
            return;
        }

        if (programme.isCourseAssociated(courseId)) {
            ui.printCourseAlreadyAssociated(); 
        } else {
            programme.addCourse(course); 
            course.addProgramme(programme); 

            programmeDAO.saveProgrammeCourseAssociation(programmeId, courseId);
            courseDAO.saveCourse(course);
            programmeDAO.saveProgramme(programme);

            ui.printCourseSuccessfullyAdded(); 
        }
    }
    
    public void removeProgrammesFromCourse(String courseId, ListInterface<String> programmeIds) {
        Course course = courseDAO.getCourseById(courseId);
        if (course != null) {
            for (String programmeId : programmeIds) {
                course.removeProgramme(programmeId); 
                ui.printRemoveProgrammeFromCourseSuccess(programmeId, courseId); 
            }
            courseDAO.saveCourse(course); 
        } else {
            ui.printCourseNotFoundForRemoval(courseId); 
        }
    }
    
    public void createAndAssociateCourseWithProgrammes(String courseId, String courseName, int creditHours, ListInterface<String> programmeIds) {
        Course newCourse = new Course(courseId, courseName, creditHours);
        courseDAO.saveCourse(newCourse);

        for (String programmeId : programmeIds) {
            Programme programme = programmeDAO.getProgrammeById(programmeId);
            if (programme != null) {
                programme.addCourse(newCourse); 
                programmeDAO.saveProgrammeCourseAssociation(programmeId, courseId); 
            }
        }
        // Confirmation message 
        ui.printCourseCreatedAndAssociated(courseId, courseName);
    }
    
    public void removeCourseFromProgramme(String courseId, String programmeId) {
        Programme programme = programmeDAO.getProgrammeById(programmeId);
        if (programme == null) {
            ui.printProgrammeNotFound(programmeId);
            return;
        }

        if (!programme.isCourseAssociated(courseId)) {
            ui.printCourseNotAssociated(courseId);
            return;
        }

        programme.removeCourse(courseId); 

        programmeDAO.saveProgramme(programme); 

        programmeDAO.removeProgrammeCourseAssociation(programmeId, courseId); 

        ui.printCourseRemovedFromProgramme(courseId, programmeId);
    }
    
    // display list of programmes
    public void displayAllProgrammes() {
        ListInterface<Programme> programmes = programmeDAO.loadProgrammes();
        if (programmes.isEmpty()) {
            ui.printNoProgrammesAvailable();
            return;
        }

        ui.printProgrammesListTableHeader();
        for (Programme programme : programmes) {
            ui.printProgramme(programme);
        }
    }
    
    // display list of courses
    public void displayAllCourses() {
        ListInterface<Course> courses = courseDAO.loadCourses();
        if (courses.isEmpty()) {
            ui.printNoCoursesAvailable();
            return;
        }

        ui.printCoursesListTableHeader();
        for (Course course : courses) {
            ui.printCourse(course);
        }
    }
    
    //List all courses for a programme
    public void displayCoursesForProgramme() {
        displayAllProgrammes();
        String programmeId = ui.promptForProgrammeId();
        Programme programme = programmeDAO.getProgrammeById(programmeId);

        while (programme == null) {
            ui.displayInvalidProgrammeIdMessage();
            programmeId = ui.promptForProgrammeId();
            programme = programmeDAO.getProgrammeById(programmeId);
        }

        programmeDAO.loadProgrammeCourses(programme); // Load courses into the programme
        ui.printProgrammeDetails(programmeId, programme.getDegreeLevel(), programme.getProgrammeName());

        if (programme.getCourses().isEmpty()) {
            ui.printNoCoursesForProgramme();
            return;
        }

        ui.printCoursesListHeaderTable();
        for (Course course : programme.getCourses().getValues()) {
            ui.printCourseDetails(course.getCourseID(), course.getCourseName());
        }
    }
    
    public boolean addProgramToCourse() {
        String programmeId = "";
        Programme selectedProgramme = null;

        while (true) {
            displayAllProgrammes();
            ui.printEnterProgrammeID(); 
            programmeId = ui.getProgrammeID(); 
            selectedProgramme = programmeDAO.getProgrammeById(programmeId);

            if (selectedProgramme != null) {
                break; 
            } else {
                ui.printInvalidSelection(); 
            }
        }

        ListInterface<String> tempCourseIds = new List<>();
        displayAllCourses();
        ui.printEnterCourseIDs(); 
        String courseId;
        while (!(courseId = ui.getCourseID()).equals("-1")) {
            Course course = courseDAO.getCourseById(courseId);
            if (course != null) {
                if (!tempCourseIds.contains(courseId)) { 
                    tempCourseIds.add(courseId);
                    ui.printCourseSelected(courseId);
                }
            } else {
                ui.printInvalidCourseID(); 
            }
            ui.printEnterAnotherCourseID(); 
        }

        if (tempCourseIds.isEmpty()) {
            ui.printNoValidCoursesSelected();
            return false;
        }

        ui.printChosenCoursesToAdd();
        for (String id : tempCourseIds) {
            ui.printCourseName(courseDAO.getCourseById(id).getCourseName()); 
        }

        while (true) {
            String confirmation = ui.confirmAddition();
            if ("yes".equalsIgnoreCase(confirmation)) {
                break;
            } else if ("no".equalsIgnoreCase(confirmation)) {
                ui.printOperationCancelled();
                return false;
            } else {
                ui.printInvalidYesNoInput();
            }
        }

        boolean anyAdded = false;
        for (String id : tempCourseIds) {
            if (!programmeDAO.isCourseAlreadyAssociated(id, programmeId)) {
                addCourseToProgramme(id, programmeId);
                ui.printCourseSuccessfullyAdded(courseDAO.getCourseById(id).getCourseName(), selectedProgramme.getProgrammeName());
                anyAdded = true;
            } else {
                ui.printCourseAlreadyAssociated(courseDAO.getCourseById(id).getCourseName(), selectedProgramme.getProgrammeName());
            }
        }
        return anyAdded;
    }
    
   public void removeProgrammeFromCourse() {
        ui.printRemoveProgrammeFromCourseHeader();
        String courseId;
        Course selectedCourse;

        while (true) {
            ui.printRemoveProgrammeFromCourseHeader();
            displayAllCourses();
            ui.printEnterCourseID();
            courseId = ui.getCourseID();
            selectedCourse = courseDAO.getCourseById(courseId);
            if (selectedCourse != null) break;
            ui.printInvalidSelection();
        }

        ListInterface<String> associatedProgrammeIds = programmeDAO.getAssociatedProgrammeIdsForCourse(courseId);
        if (associatedProgrammeIds.isEmpty()) {
            ui.printOperationCancelled();
            return;
        }

        ui.printProgrammesListHeader();
        for (int i = 0; i < associatedProgrammeIds.size(); i++) {
            Programme programme = programmeDAO.getProgrammeById(associatedProgrammeIds.get(i));
            if (programme != null) {
                ui.printProgramme(i + 1, programme.getProgrammeName());
            } else {
                // Handle the case where the programme is not found
                System.out.println("Programme with ID " + associatedProgrammeIds.get(i) + " not found.");
            }
        }

        ListInterface<String> programmesToRemove = new List<>();

        while (true) {
            ui.printEnterProgrammeNumber();
            String input = ui.getProgrammeNumber();
            if ("-1".equals(input)) {
                if (programmesToRemove.isEmpty()) {
                    ui.printOperationCancelled(); 
                    return;
                }
                break; 
            }

            try {
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < associatedProgrammeIds.size()) {
                    if (!programmesToRemove.contains(associatedProgrammeIds.get(index))) {
                        programmesToRemove.add(associatedProgrammeIds.get(index));
                        ui.printAddedToRemovalList(programmeDAO.getProgrammeById(associatedProgrammeIds.get(index)).getProgrammeName());
                    } else {
                        ui.printInvalidSelection(); 
                    }
                } else {
                    ui.printInvalidSelection(); 
                }
            } catch (NumberFormatException e) {
                ui.printInvalidSelection(); 
            }
        }

        while (true) {
            String confirmation = ui.confirmRemoval();
            if ("yes".equalsIgnoreCase(confirmation)) {
                for (String progId : programmesToRemove) {
                    tutorDAO.updateTutorTutorialCourseFile(progId, courseId);
                    programmeDAO.removeProgrammeCourseAssociation(progId, courseId); // Perform the removal
                }
                System.out.println("Course has been successfully removed from programme.");
                break; 
            } else if ("no".equalsIgnoreCase(confirmation)) {
                ui.printOperationCancelled();
                break; 
            } else {
                ui.printInvalidYesNoInput(); 
            }
        }
    }
    
    public void addNewCourseToProgramme() {
        String userResponse = "yes"; 
        do {
            ui.printAddNewCourseHeader();
            String courseId = "";
            boolean isValidCourseId = false;
            while (!isValidCourseId) {
                courseId = ui.inputCourseID();
                if (!courseId.matches("[A-Za-z]{4}\\d{4}")) {
                    System.out.println("Invalid Course ID. The right format is like 'BACS1000'. Please try again.");
                } else if (checkCourseIdExists(courseId)) {
                    System.out.println("Course ID already exists. Please enter a different Course ID.");
                } else {
                    isValidCourseId = true;
                }
            }
            String courseName = ui.inputCourseName();
            String creditString = ui.inputCreditHours();
            int credits;
            try {
                credits = Integer.parseInt(creditString);
            } catch (NumberFormatException e) {
                ui.printInvalidCreditInput();
                continue; // Skip to the next iteration of the loop if parsing fails
            }
            
            Course newCourse = new Course(courseId, courseName, credits);
            courseDAO.saveCourse(newCourse); // Simulated DAO operation
            ui.printNewCourseSuccess();
            
            String courseType = ui.inputCourseType();  
            String courseFees = ui.inputCourseFees();
            
            CourseDetails courseDetails = new CourseDetails(courseId, courseType, Double.parseDouble(courseFees));
            courseDetailsDAO.saveCourseDetails(courseDetails);
            System.out.println("\nCourse Details saved successfully.");
            
            String facultyID = ui.chooseFaculty(); // Assuming chooseFaculty is implemented to handle user input
            Faculty faculty = facultyDAO.getFacultyById(facultyID);
            if (faculty != null) {
                faculty.addCourseId(courseId);
                facultyDAO.saveFaculty(faculty);
                updateFacultyCoursesCache(facultyID, newCourse); // Now passing the newCourse object
                System.out.println("\nFaculty added successfully");
            } else {
                System.out.println("\nFaculty not found.");
            }
            
            String semesterID = ui.chooseSemester(); // Assuming chooseSemester is implemented to handle user input
            Semester semester = semesterDAO.getSemesterById(semesterID);
            if (semester != null) {
                semester.getCoursesOffered().add(newCourse);
                semesterDAO.saveSemester(semester);
                updateSemesterCoursesCache(semesterID, newCourse); // Update cache for semester
                System.out.println("\nSemester successfully added.");
            } else {
                System.out.println("\nSemester not found.");
            }
            
            // Display Programmes
            StringBuilder programmesDisplay = new StringBuilder();
            ListInterface<Programme> programmes = programmeDAO.loadProgrammes(); // Simulated DAO operation
            for (int i = 0; i < programmes.size(); i++) {
                programmesDisplay.append((i + 1))
                                  .append(". ")
                                  .append(programmes.get(i).getProgrammeName())
                                  .append(" - ")
                                  .append(programmes.get(i).getDegreeLevel())
                                  .append("\n");
            }
            ui.printAvailableProgrammes(programmesDisplay.toString());

            // Select Programmes
            ListInterface<String> selectedProgrammeIds = new List<>();
            String input;
            do {
                input = ui.inputProgrammeNumber();
                if (!input.equals("-1")) {
                    try {
                        int index = Integer.parseInt(input) - 1;
                        if (index >= 0 && index < programmes.size()) {
                            selectedProgrammeIds.add(programmes.get(index).getProgrammeId());
                            ui.printAddProgrammeSuccess(programmes.get(index).getProgrammeName());
                        } else {
                            ui.printInvalidSelection();
                        }
                    } catch (NumberFormatException e) {
                        ui.printInvalidSelection();
                    }
                }
            } while (!input.equals("-1"));

            // Confirm and Process Association
            if (!selectedProgrammeIds.isEmpty()) {
                StringBuilder selectedProgrammesNames = new StringBuilder("Selected Programmes:\n");
                for (String id : selectedProgrammeIds) {
                    Programme p = programmeDAO.getProgrammeById(id); // Simulated DAO operation
                    selectedProgrammesNames.append("- ").append(p.getProgrammeName()).append("\n");
                }
                ui.printSelectedProgrammes(selectedProgrammesNames.toString());

                String confirmation = ui.confirmAssociation();
                if (confirmation.equalsIgnoreCase("yes")) {
                    for (String programmeId : selectedProgrammeIds) {
                        programmeDAO.saveProgrammeCourseAssociation(programmeId, courseId); // Save each association
                    }
                    ui.printAssociationSuccess();
                } else {
                    ui.printOperationCancelled();
                }
            } else {
                    System.out.println("No programmes selected for association.");
            }

            userResponse = ui.askToAddAnotherCourse(); // Update userResponse based on user input
            while (!userResponse.equals("yes") && !userResponse.equals("no")) {
                ui.printInvalidYesNoInput();
                userResponse = ui.askToAddAnotherCourse();
            }
        } while (userResponse.equals("yes"));

        ui.printThankYouMessage();
    }
    
    private boolean checkCourseIdExists(String courseId) {
        ListInterface<CourseDetails> allDetails = courseDetailsDAO.loadCourseDetails();
        for (CourseDetails details : allDetails) {
            if (details.getCourseId().equals(courseId)) {
                return true; // Course ID exists
            }
        }
        return false; // Course ID does not exist
    }

    private void updateFacultyCoursesCache(String facultyID, Course newCourse) {
        ListInterface<Course> courses = facultyCoursesMap.get(facultyID);
        if (courses == null) {
            courses = new List<>(); // Assuming `List` is your implementation of `ListInterface<Course>`
        }
        // Check if the course is already in the list to prevent duplicates
        boolean exists = false;
        for (Course course : courses) {
            if (course.getCourseID().equals(newCourse.getCourseID())) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            courses.add(newCourse);
            facultyCoursesMap.add(facultyID, courses); // This assumes `add` method in `MapInterface` replaces existing entry
        }
    }

    private void updateSemesterCoursesCache(String semesterID, Course newCourse) {
        ListInterface<Course> courses = semesterCoursesMap.get(semesterID);
        if (courses == null) {
            courses = new List<>(); // Assuming `List` is your implementation of `ListInterface<Course>`
        }
        courses.add(newCourse);
        semesterCoursesMap.add(semesterID, courses); // This assumes `add` method in `MapInterface` replaces existing entry
    }
    
    public void removeCourseFromProgramme() {
        String programmeId = "";
        Programme selectedProgramme = null;
        
        while (true) {
            ui.printProgrammeSelectionHeader();
            displayAllProgrammes();
            ui.printEnterProgrammeId();
            programmeId = ui.getProgrammeId();
            
            selectedProgramme = programmeDAO.getProgrammeById(programmeId);
            if (selectedProgramme == null) {
                ui.printInvalidSelection();
                continue; // Re-prompt for Programme ID
            }
            break; // Exit loop if valid programme is selected
        }

        ListInterface<Course> associatedCourses = selectedProgramme.getAssociatedCourses();
        if (associatedCourses.isEmpty()) {
            ui.printOperationCancelled();
            return;
        }

        int courseNumber = 0;
        while (true) {
            ui.printCoursesListHeader();
            for (int i = 0; i < associatedCourses.size(); i++) {
                Course course = associatedCourses.get(i);
                ui.printCourse(i + 1, course.getCourseName(), course.getCourseID());
            }

            ui.printEnterCourseNumber();
            String courseNumberStr = ui.getCourseNumber();
            try {
                courseNumber = Integer.parseInt(courseNumberStr);
                if (courseNumber < 1 || courseNumber > associatedCourses.size()) {
                    ui.printInvalidSelection();
                    continue; // Re-prompt for course number
                }
                break; // Exit loop if valid course number is selected
            } catch (NumberFormatException e) {
                ui.printInvalidSelection(); // Handle non-integer input
            }
        }

        Course selectedCourse = associatedCourses.get(courseNumber - 1);
        while (true) {
            String confirmation = ui.confirmRemoval();
            if (confirmation.equalsIgnoreCase("yes")) {
                break; // Proceed with removal if confirmation is yes
            } else if (confirmation.equalsIgnoreCase("no")) {
                ui.printOperationCancelled();
                return; // Cancel operation if confirmation is no
            } else {
                ui.printInvalidYesNoInput(); // Re-prompt for confirmation if invalid input
            }
        }

        removeCourseFromProgramme(selectedCourse.getCourseID(), programmeId); // Actual removal logic
    }
    
    private void preloadSemesterCourses() {
        ListInterface<Semester> semesters = semesterDAO.loadSemesters();
        for (Semester semester : semesters) {
            coursesBySemester.add(semester.getName(), semester.getCoursesOffered());  //Hash Map add
        }
    }
    
    public void searchCoursesOfferedInSemester() {
        while (true) {
            String semesterInput = ui.getSemesterSelection();
            if ("exit".equalsIgnoreCase(semesterInput)) break;

            String semesterName = "Semester " + semesterInput;
            ListInterface<Course> courses = coursesBySemester.get(semesterName);

            if (courses == null || courses.isEmpty()) {
                ui.printNoCoursesFoundForSemester(semesterName);
                continue;
            }

            ui.displayCoursesForSemesterHeader(semesterName);
            for (int i = 0; i < courses.size(); i++) {
                Course course = courses.get(i);
                ui.printCourseOption(i + 1, course.getCourseName(), course.getCourseID());
            }

            String courseChoice = ui.getCourseSelection();
            if ("exit".equalsIgnoreCase(courseChoice)) break;

            try {
                int courseIndex = Integer.parseInt(courseChoice) - 1;
                if (courseIndex >= 0 && courseIndex < courses.size()) {
                    Course selectedCourse = courses.get(courseIndex);
                    ui.displayCourseDetails(selectedCourse);
                } else {
                    ui.printInvalidCourseNumber();
                }
            } catch (NumberFormatException e) {
                ui.printInvalidCourseNumber();
            }

            String anotherSearch = ui.askForAnotherSearch();
            if (!"yes".equalsIgnoreCase(anotherSearch)) {
                ui.printThankYouMessage();
                break;
            }
        }
    }
    
    public ListInterface<Course> getCoursesForSemester(String semesterName) {
        ListInterface<Semester> semesters = semesterDAO.loadSemesters();
        for (Semester semester : semesters) {
            if (semester.getName().equalsIgnoreCase(semesterName)) {
                return semester.getCoursesOffered();
            }
        }
        return null; 
    }

    public Course getCourseDetails(String courseId) {
        return courseDAO.getCourseById(courseId);
    }
    
    public void amendCourseDetailsForProgramme() {
        displayAllProgrammes(); 
        String progIdentifier;
        Programme programme = null;
        do {
            ui.printEnterProgrammeIDOrName(); // Prompt user to enter programme ID or name
            progIdentifier = ui.getUserInput(); // Method to capture user input
            programme = programmeDAO.getProgrammeById(progIdentifier);

            if (programme == null) {
                ui.printProgrammeNotFound(); // Notify user if programme is not found
            }
        } while (programme == null);

        ui.printProgrammeDetails(programme.getProgrammeId(), programme.getProgrammeName(), programme.getDegreeLevel());

        ListInterface<String> courseIds = programmeDAO.getAssociatedCourseIDsForProgramme(progIdentifier);
        if (courseIds.isEmpty()) {
            ui.printNoCoursesForProgramme(); // Notify if no courses are associated with the programme
            return;
        }

        for (int i = 0; i < courseIds.size(); i++) {
            Course course = courseDAO.getCourseById(courseIds.get(i));
            if (course != null) {
                ui.printCourseOption(i + 1, course.getCourseName(), course.getCourseID()); // Display course options
            }
        }

        int courseNum = -1;
        do {
            ui.printEnterCourseNumber(); // Ask user to select a course number
            String courseNumber = ui.getCourseSelection();
            try {
                courseNum = Integer.parseInt(courseNumber) - 1;
                if (courseNum < 0 || courseNum >= courseIds.size()) {
                    ui.printInvalidCourseNumber(); // Validate selection
                }
            } catch (NumberFormatException e) {
                ui.printInvalidCourseNumber();
            }
        } while (courseNum < 0 || courseNum >= courseIds.size());

        String selectedCourseId = courseIds.get(courseNum);
        Course selectedCourse = courseDAO.getCourseById(selectedCourseId);
        if (selectedCourse == null) {
            ui.printCourseNotFound();
            return;
        }

        ui.displayCourseDetails(selectedCourse);

        // Initialize amendAnotherDetail here before the loop
        boolean amendAnotherDetail = false;
        do {
            String choice = ui.getCourseAmendmentChoice();
            boolean validChoice = "1".equals(choice) || "2".equals(choice) || "3".equals(choice);
            if (!validChoice) {
                ui.printInvalidSelection();
                continue;
            }

            switch (choice) {
            case "1":
                String newId = ui.getNewCourseID();
                courseDAO.amendCourseId(selectedCourse.getCourseID(), newId, programmeDAO, facultyDAO, semesterDAO);
                ui.printOperationSuccess("Course ID successfully changed.");
                break;
            case "2":
                String newName = ui.getNewCourseName();
                selectedCourse.setCourseName(newName);
                courseDAO.saveCourse(selectedCourse);
                ui.printOperationSuccess("Course Name successfully changed.");
                break;
            case "3":
                try {
                    int newCredits = Integer.parseInt(ui.getNewCourseCredits());
                    selectedCourse.setCredits(newCredits);
                    courseDAO.saveCourse(selectedCourse);
                    ui.printOperationSuccess("Course Credits successfully changed.");
                } catch (NumberFormatException e) {
                    ui.printInvalidCreditInput();
                    continue; 
                }
                break;
            }

            // Ask if user wants to make another change
            amendAnotherDetail = "yes".equalsIgnoreCase(ui.askToAmendAnotherDetail());
        } while (amendAnotherDetail); // Repeat if user wants to amend another detail
    }
    
    private void preloadFacultyCourses() {
        ListInterface<Faculty> faculties = facultyDAO.loadFaculties();
        for (Faculty faculty : faculties) {
            ListInterface<Course> courses = new List<>();
            for (String courseId : faculty.getCoursesTaughtIds()) {
                Course course = courseDAO.getCourseById(courseId);
                if (course != null) {
                    courses.add(course);
                }
            }
            facultyCoursesMap.add(faculty.getFacultyId(), courses);   // HashMap add 
        }
    }
   
    public void listFacultiesAndCoursesControl() {
        ListInterface<Faculty> faculties = facultyDAO.loadFaculties();
        ui.printFacultiesHeader();
        for (int i = 0; i < faculties.size(); i++) {
            ui.printFacultyOption(i + 1, faculties.get(i).getName());
        }

        ui.printPromptForFacultySelection();
        int facultyChoice = Integer.parseInt(ui.getUserInput()) - 1;

        while (facultyChoice < 0 || facultyChoice >= faculties.size()) {
            ui.printInvalidFacultySelection();
            ui.printPromptForFacultySelection();
            facultyChoice = Integer.parseInt(ui.getUserInput()) - 1;
        }

        Faculty selectedFaculty = faculties.get(facultyChoice);
        ui.printSelectedFaculty(selectedFaculty.getName());

        ListInterface<Course> courses = facultyCoursesMap.get(selectedFaculty.getFacultyId()); // HashMap get key
        if (courses == null || courses.isEmpty()) {
            ui.printNoCoursesFound(selectedFaculty.getName());
        } else {
            for (Course course : courses) {
                ui.printCourseOption(course.getCourseID(), course.getCourseName(), course.getCredits());
            }
        }
    }

    public void listCoursesByFaculty() {
        String userResponse;
        do {
            listFacultiesAndCoursesControl();
            ui.printPromptForAnotherView();
            userResponse = ui.getUserInput();
            while (!userResponse.equalsIgnoreCase("yes") && !userResponse.equalsIgnoreCase("no")) {
                ui.printInvalidInput();
                ui.printPromptForAnotherView();
                userResponse = ui.getUserInput();
            }
        } while (userResponse.equalsIgnoreCase("yes"));
        ui.printThankYouForUsingCMS();
    }
    
    public void generateCourseAvailabilitySummaryReport() {
        ListInterface<Course> courses = courseDAO.loadCourses();
        ListInterface<CourseDetails> courseDetailsList = courseDetailsDAO.loadCourseDetails();

        ui.printReportHeader();

        courses.forEach(course -> {
            CourseDetails details = findCourseDetailsByCourseId(course.getCourseID(), courseDetailsList);
            int programmeCount = programmeDAO.countProgrammesOfferingCourse(course.getCourseID());
            int facultyCount = facultyDAO.countFacultiesOfferingCourse(course.getCourseID());

            String status = (details != null) ? details.getCourseType() : "N/A";
            ui.printCourseSummary(course.getCourseID(), course.getCourseName(), status, course.getCredits(), programmeCount, facultyCount);
        });

        summarizeReport(courses, courseDetailsList);
        ui.printReportFooter();

    }

    private CourseDetails findCourseDetailsByCourseId(String courseId, ListInterface<CourseDetails> courseDetailsList) {
        for (CourseDetails details : courseDetailsList) {
            if (details.getCourseId().equals(courseId)) {
                return details;
            }
        }
        return null;
    }
    
    private void summarizeReport(ListInterface<Course> courses, ListInterface<CourseDetails> courseDetailsList) {
        MapInterface<String, Integer> statusCounts = courseDetailsDAO.countCoursesByStatus();
        int totalCourses = courseDetailsDAO.getTotalNumberOfCourses();

        // Print total courses summary using UI class
        ui.printTotalCoursesSummary(
                statusCounts.get("Main"),
                statusCounts.get("Elective"),
                statusCounts.get("Resit"),
                statusCounts.get("Repeat"),
                totalCourses);

        int maxFaculties = 0, minFaculties = Integer.MAX_VALUE;
        int maxProgrammes = 0, minProgrammes = Integer.MAX_VALUE;
        String maxFacultiesCourses = "", minFacultiesCourses = "";
        String maxProgrammesCourses = "", minProgrammesCourses = "";
        boolean isFirstCourse = true;

        for (Course course : courses) {
            int facultyCount = facultyDAO.countFacultiesOfferingCourse(course.getCourseID());
            int programmeCount = programmeDAO.countProgrammesOfferingCourse(course.getCourseID());

            // Check if this is the first course and initialize the min values
            if (isFirstCourse) {
                minFaculties = facultyCount;
                minProgrammes = programmeCount;
                isFirstCourse = false;
            }

            if (facultyCount > maxFaculties) {
                maxFaculties = facultyCount;
                maxFacultiesCourses = course.getCourseName();
            } else if (facultyCount == maxFaculties) {
                maxFacultiesCourses += ", " + course.getCourseName();
            }

            if (programmeCount > maxProgrammes) {
                maxProgrammes = programmeCount;
                maxProgrammesCourses = course.getCourseName();
            } else if (programmeCount == maxProgrammes) {
                maxProgrammesCourses += ", " + course.getCourseName();
            }

            if (!isFirstCourse) { 
                if (facultyCount < minFaculties && facultyCount > 0) {
                    minFaculties = facultyCount;
                    minFacultiesCourses = course.getCourseName();
                } else if (facultyCount == minFaculties) {
                    minFacultiesCourses += ", " + course.getCourseName();
                }

                if (programmeCount < minProgrammes && programmeCount > 0) {
                    minProgrammes = programmeCount;
                    minProgrammesCourses = course.getCourseName();
                } else if (programmeCount == minProgrammes) {
                    minProgrammesCourses += ", " + course.getCourseName();
                }
            }
        }

        int averageCourses = (courses.size() > 0) ? totalCourses / courses.size() : 0;

        ui.printOverallAnalysisHeader();
        ui.printOverallCoursesSummary(totalCourses, maxFacultiesCourses, maxFaculties,
                                      minFacultiesCourses, minFaculties, averageCourses);
        ui.printHighestLowestFacultiesOffered(maxFaculties, maxFacultiesCourses,
                                              minFaculties, minFacultiesCourses);
        ui.printHighestLowestProgrammesOffered(maxProgrammes, maxProgrammesCourses,
                                               minProgrammes, minProgrammesCourses);
        ui.printOverallAnalysisFooter();
    }

    public void generateCompleteSummaryReport() {
        ListInterface<Semester> semesters = semesterDAO.loadSemesters();
        MapInterface<String, Integer> semesterCourseCounts = new Map<>();
        
        ui.printSummaryReportSemesterHeader();

        // Generate the course offerings report for each semester
        for (Semester semester : semesters) {
            printSemesterCourseTable(semester); // This will handle header printing
            semesterCourseCounts.add(semester.getName(), semester.getCoursesOffered().size());
        }

        // Generate the overall analysis report
        generateOverallAnalysisReport(semesterCourseCounts);
        
        ui.printReport2Footer();
    }

    public void printSemesterCourseTable(Semester semester) {
        // Assuming that ui.printCourseTableHeader prints the header like "SEMESTER <name> COURSE OFFERINGS:"
        ui.printCourseTableHeader(semester.getName());

        for (Course course : semester.getCoursesOffered()) {
            ui.printCourseTableRow(course.getCourseID(), course.getCourseName(), course.getCredits());
        }

        ui.printCourseTableFooter(semester.getCoursesOffered().size(), semester.getName());
    }
     
    private void generateOverallAnalysisReport(MapInterface<String, Integer> semesterCourseCounts) {
        int totalCourses = 0;
        int highestOfferings = 0;
        int lowestOfferings = Integer.MAX_VALUE;
        String semesterMostCourses = "";
        String semesterLeastCourses = "";
        ListInterface<String> semesterKeys = semesterCourseCounts.getKeys(); // Assuming getKeys() is implemented

        for (int i = 0; i < semesterKeys.size(); i++) {
            String key = semesterKeys.get(i);
            int courseCount = semesterCourseCounts.get(key);
            totalCourses += courseCount;

            if (courseCount > highestOfferings) {
                highestOfferings = courseCount;
                semesterMostCourses = key;
            }
            if (courseCount < lowestOfferings && courseCount > 0) {
                lowestOfferings = courseCount;
                semesterLeastCourses = key;
            }
        }

        int averageCourses = totalCourses / semesterKeys.size();

        ui.printOverallAnalysisHeader();
        ui.printOverallCoursesSummary(totalCourses, highestOfferings, semesterMostCourses, lowestOfferings, semesterLeastCourses, averageCourses);
        ui.printOverallAnalysisFooter();
    }
}


