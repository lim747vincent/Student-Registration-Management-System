/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import entity.Course;
import entity.Programme;
import java.util.Scanner;
import java.time.LocalDateTime;

/**
 *
 * @author Seah Meng Kwang 
 */
public class CourseManagementUI {

    Scanner scanner = new Scanner(System.in);
    
    // Display Main Menu
    public void displayMainMenu() {
        System.out.println("\n");
        System.out.println("********** Welcome to Course Management Subsystem **********");
        System.out.println();
        System.out.println("Please Select an Option: ");
        System.out.println("1. Add a programme to courses");
        System.out.println("2. Remove a programme from a course");
        System.out.println("3. Add a new course to programmes");
        System.out.println("4. Remove a course from a programme");
        System.out.println("5. Search courses offered in a semester");
        System.out.println("6. Amend course details for a programme");
        System.out.println("7. List all courses taken by different faculties");
        System.out.println("8. List all courses for a programme");
        System.out.println("9. Generate Summary Report");
        System.out.println("10. Exit");
        System.out.println();
        System.out.println("************************************************************");
        System.out.println();
    }
    
    public void displayCourseDetails(Course course) {
        // Display course details
        System.out.println("\nYou have selected:");
        System.out.println("Course Name: " + course.getCourseName());
        System.out.println("Course Code: " + course.getCourseID());
        System.out.println("Credit Hours: " + course.getCredits());
    }
    
    public String getCourseAmendmentChoice() {
        System.out.println("\nHow would you like to amend the course details?");
        System.out.println("1. Amend Course ID");
        System.out.println("2. Amend Course Name");
        System.out.println("3. Amend Course Credits");
        System.out.println("Please choose an option:");
        return scanner.nextLine().trim();
    }
    
    public int getUserMenuChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose from option 1 to 10: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        return choice;
    }

    public void displayInvalidInputMessage() {
        System.out.println("\nInvalid input. Please enter a number between 1 and 10.");
    }

    public void displayExitMessage() {
        System.out.println("\nExiting the Course Management Subsystem. Goodbye!");
    }

    public void printOperationSuccess(String message) {
        System.out.println("\n" + message); // Print the success message for the amendment
    }
    
    public void printEnterProgrammeIDOrName() {
        System.out.println("\nPlease enter the Programme ID or Name for which you want to amend course details:");
    }
    
    public String getUserInput() {
        return scanner.nextLine().trim();
    }

    public void print(String message) {
        System.out.println(message);
    }

    public String getNewCourseID() {
        String input;
        // Regular expression to validate the course ID format
        String pattern = "^[A-Za-z]{4}\\d{4}$";

        while (true) {
            System.out.print("\nEnter New Course ID: ");
            input = scanner.nextLine();

            // Check if the input matches the pattern
            if (input.matches(pattern)) {
                return input; // Return the input if it's valid
            } else {
                System.out.println("Invalid Course ID. Please enter a valid ID. etc. format BACS1044.");
            }
        }
    }

    public String getNewCourseName() {
        System.out.print("\nEnter New Course Name: ");
        return scanner.nextLine();
    }

    public String getNewCourseCredits() {
        String input;
        while (true) {
            System.out.print("\nEnter New Course Credits (3 or 4 only): ");
            input = scanner.nextLine();

            // Validate that the input is either "3" or "4"
            if (input.equals("3") || input.equals("4")) {
                return input; // Return the input if it's valid
            } else {
                System.out.println("Invalid entry. Please enter 3 or 4 credits only.");
            }
        }
    }

    public String askToAmendAnotherDetail() {
        System.out.print("\nWould you like to change another detail for the same course? (yes/no): ");
        return scanner.nextLine().trim().toLowerCase();
    }

    public String getSemesterSelection() {
        System.out.println("\nAvailable semesters:\n1. Semester 1\n2. Semester 2\n3. Semester 3");
        System.out.print("\nPlease enter the semester you want to search for courses: ");
        return scanner.nextLine().trim();
    }

    public String getCourseSelection() {
        System.out.print("\nPlease enter a course number for more details, or type 'exit' to return: ");
        return scanner.nextLine().trim();
    }

    public String askForAnotherSearch() {
        System.out.print("\nWould you like to search for more courses? (yes/no): ");
        return scanner.nextLine().trim().toLowerCase();
    }

    public void printNoCoursesFoundForSemester(String semesterName) {
        System.out.println("\nNo courses found for " + semesterName);
    }

    public void displayCoursesForSemesterHeader(String semesterName) {
        System.out.println("\n\nCourses Offered in " + semesterName + ":\n");
    }

    public void printCourseOption(int index, String courseName, String courseId) {
        System.out.println(index + ". " + courseName + " (" + courseId + ")");
    }

    public void askForCourseDetailsSelection() {
        System.out.print("\nPlease enter a course number for more details or 'exit' to return: ");
    }

    public void printInvalidCourseNumber() {
        System.out.println("\nInvalid course number.");
    }
    
    public String promptForProgrammeId() {
        System.out.print("\nPlease enter the Programme ID: ");
        return scanner.nextLine().trim();
    }
    
    public void displayInvalidProgrammeIdMessage() {
        System.out.println("\nInvalid Programme ID. Please try again.");
    }
    
    public void printProgrammeDetails(String programmeId, String degreeLevel, String programmeName) {
        System.out.println("\nProgramme ID: " + programmeId + " - " + degreeLevel + " in " + programmeName);
        System.out.println("\nAvailable courses for this Programme:");
    }

    public void printNoCoursesForProgramme() {
        System.out.println("\nThere are no courses associated with this programme.");
    }

    public void printCoursesListHeaderTable() {
        String headerFormat = "| %-15s | %-50s |%n";
        System.out.format(headerFormat, "Course ID", "Course Name");
        System.out.println(String.format("%0" + 70 + "d", 0).replace("0","-"));
    }

    public void printCourseDetails(String courseId, String courseName) {
        String courseFormat = "| %-15s | %-50s |%n";
        System.out.format(courseFormat, courseId, courseName);
    }

    public void printProgrammeNotFound() {
        System.out.println("\nProgramme not found.");
    }
    
    public void printCourseNotFound() {
        System.out.println("\nCourse not found.");
    }
    
    public void printSelectedCourseName(String courseName) {
        System.out.println("\nYou have selected the course: " + courseName);
    }

    public void printNoAssociatedProgrammes() {
        System.out.println("\nNo programmes are associated with this course.");
    }

    public void printAssociatedProgrammesHeader() {
        System.out.println("\nAssociated Programmes:");
    }

    public void printProgramme(int index, String programmeName, String programmeId) {
        System.out.println(index + ". " + programmeName + " - " + programmeId);
    }
    
    public void printNoCoursesAvailable() {
        System.out.println("\nNo courses available.");
    }

    public void printCoursesListTableHeader() {
        System.out.printf("| %-10s | %-50s | %-15s |%n", "Course ID", "Course Name", "Credits");
        System.out.println(String.format("%0" + 79 + "d", 0).replace("0","-"));
    }

    public void printCourse(Course course) {
        System.out.printf("| %-10s | %-50s | %-15d |%n", course.getCourseID(), course.getCourseName(), course.getCredits());
    }
    
    public void printNoProgrammesAvailable() {
        System.out.println("\nNo programmes available.");
    }

    public void printProgrammesListTableHeader() {
        System.out.printf("| %-10s | %-50s | %-20s |%n", "Programme ID", "Programme Name", "Degree Level");
        System.out.println(String.format("%0" + 88 + "d", 0).replace("0","-"));
    }

    public void printProgramme(Programme programme) {
        System.out.printf("| %-10s | %-50s | %-20s |%n", programme.getProgrammeId(), programme.getProgrammeName(), programme.getDegreeLevel());
    }
    
    public void printCourseNotAssociated(String courseId) {
        System.out.println("\nCourse ID: " + courseId + " is not associated with the Programme.");
    }

    public void printCourseRemovedFromProgramme(String courseId, String programmeId) {
        System.out.println("\nCourse ID: " + courseId + " successfully removed from Programme ID: " + programmeId + ".");
    }
    
    public void printCourseCreatedAndAssociated(String courseId, String courseName) {
        System.out.println("\nThe course " + courseId + " - " + courseName + " has been successfully added to the selected programmes.");
    }
    
    public void printRemoveProgrammeFromCourseSuccess(String programmeId, String courseId) {
        System.out.println("\nRemoved Programme ID: " + programmeId + " from Course ID: " + courseId);
    }

    public void printCourseNotFoundForRemoval(String courseId) {
        System.out.println("\nCourse with ID " + courseId + " not found.");
    }
    
     public void printCourseNotFound(String courseId) {
        System.out.println("\nCourse with ID " + courseId + " not found.");
    }

    public void printProgrammeNotFound(String programmeId) {
        System.out.println("\nProgramme with ID " + programmeId + " not found.");
    }

    public void printCourseAlreadyAssociated() {
        System.out.println("\nCourse is already associated with the Programme.");
    }

    public void printCourseSuccessfullyAdded() {
        System.out.println("\nCourse successfully added to Programme.");
    }
    
    public void printSelectProgrammeHeader() {
        System.out.println("\n\n********** Select a Programme **********");
    }

    public void printEnterProgrammeID() {
        System.out.print("\nEnter Programme ID to select: ");
    }
    
    // Method to print the message when a course is successfully added to a programme
    public void printCourseSuccessfullyAdded(String courseName, String programmeName) {
        System.out.println("The course '" + courseName + "' has been successfully added to the programme '" + programmeName + "'.");
    }

    // Method to print the message when a course is already associated with a programme
    public void printCourseAlreadyAssociated(String courseName, String programmeName) {
        System.out.println("The course '" + courseName + "' is already associated with the programme '" + programmeName + "'.");
    }

    public String getProgrammeID() {
        return scanner.nextLine().trim();
    }

    public void printAvailableCoursesHeader() {
        System.out.println("\n\nAvailable Courses:");
    }

    public void printEnterCourseIDs() {
        System.out.println("\nEnter Course IDs to add to the Programme (type '-1' to finish): ");
    }

    public void printEnterAnotherCourseID() {
        System.out.print("\nEnter another Course ID to add to the Programme (or type '-1' to finish): ");
    }

    public void printCourseSelected(String courseId) {
        System.out.println("\nCourse ID: " + courseId + " selected.");
    }

    public void printInvalidCourseID() {
        System.out.println("\nInvalid Course ID or Course not found.");
    }

    public void printNoValidCoursesSelected() {
        System.out.println("\nNo valid courses selected for addition.");
    }

    public void printChosenCoursesToAdd() {
        System.out.println("\nYou have chosen to add the following courses:");
    }

    public void printCourseName(String courseName) {
        System.out.println("- " + courseName);
    }
    
    public void printRemoveProgrammeFromCourseHeader() {
        System.out.println("\n\n********** Remove a Programme from a Course **********\n");
    }

    public void printEnterCourseID() {
        System.out.print("\nEnter the Course ID from which you want to remove a programme: ");
    }

    public String getCourseID() {
        return scanner.nextLine().trim();
    }

    public void printProgrammesListHeader() {
        System.out.println("\n\nAssociated Programmes:");
    }

    public void printProgramme(int index, String programmeName) {
        System.out.println(index + ". " + programmeName);
    }

    public void printEnterProgrammeNumber() {
        System.out.print("\nEnter the number(s) of the programme(s) you wish to remove (type '-1' to finish): ");
    }

    public String getProgrammeNumber() {
        return scanner.nextLine().trim();
    }

    public void printAddedToRemovalList(String programmeName) {
        System.out.println("\nAdded to removal list: " + programmeName);
    }
    
    public void printProgrammeSelectionHeader() {
        System.out.println("\n\n********** Remove a Course from a Programme **********\n");
    }

    public void printEnterProgrammeId() {
        System.out.print("\nPlease enter the Programme ID from which you want to remove a course: ");
    }

    public String getProgrammeId() {
        return scanner.nextLine().trim();
    }

    public void printCoursesListHeader() {
        System.out.println("\n\nAssociated Courses:");
    }

    public void printCourse(int index, String courseName, String courseId) {
        System.out.println(index + ". " + courseName + " - " + courseId);
    }

    public void printEnterCourseNumber() {
        System.out.print("\nPlease enter the course number you wish to remove: ");
    }

    public String getCourseNumber() {
        return scanner.nextLine().trim();
    }

    public String confirmRemoval() {
        System.out.print("\nPlease confirm the removal (yes/no): ");
        return scanner.nextLine().trim();
    }
    
    public void printAddNewCourseHeader() {
        System.out.println("\n\n********** Add New Course **********");
        System.out.println("\nPlease enter the new course details.");
    }

    public String inputCourseID() {
        System.out.print("\nEnter Course ID: ");
        return scanner.nextLine();
    }

    public String inputCourseName() {
        System.out.print("\nEnter Course Name: ");
        return scanner.nextLine();
    }

    public String inputCreditHours() {
        String input;
        while (true) {
            System.out.print("\nEnter Credit Hours (3 or 4 only): ");
            input = scanner.nextLine();

            // Check if the input is either "3" or "4"
            if (input.equals("3") || input.equals("4")) {
                return input; // Return the input if it's valid
            } else {
                System.out.println("Invalid input. Please enter 3 or 4 credit hours only.");
            }
        }
    }

    public void printInvalidCreditInput() {
        System.out.println("\nInvalid input for credit hours. Please enter a number.\n");
    }

    public String askToAddAnotherCourse() {
        System.out.print("\nWould you like to add another new course? (yes/no): ");
        return scanner.nextLine().trim().toLowerCase();
    }

    public void printInvalidYesNoInput() {
        System.out.println("\nInvalid input. Please type 'yes' or 'no'.\n");
    }

    public void printThankYouMessage() {
        System.out.println("\nThank you for using the Course Management Subsystem.");
    }

    public void printNewCourseSuccess() {
        System.out.println("\nNew course created successfully!");
    }

    public void printAvailableProgrammes(String programmes) {
        System.out.println("\n\nAvailable Programmes:");
        System.out.println(programmes);
    }

    public String inputProgrammeNumber() {
        System.out.print("\nEnter another programme number (or type '-1' to finish): ");
        return scanner.nextLine().trim();
    }

    public void printInvalidSelection() {
        System.out.println("\nInvalid selection. Please try again.\n");
    }

    public void printAddProgrammeSuccess(String programmeName) {
        System.out.println("\nAdded " + programmeName + " to the list.");
    }

    public void printSelectedProgrammes(String selectedProgrammes) {
        System.out.println("\n\nYou have selected to associate the course with the following programmes:");
        System.out.println(selectedProgrammes);
    }

    public String confirmAssociation() {
        System.out.print("\nPlease confirm the association of the new course with the selected programmes (yes/no): ");
        return scanner.nextLine().trim();
    }

    public void printAssociationSuccess() {
        System.out.println("\nThe course has been successfully added to the selected programmes.");
    }

    public void printOperationCancelled() {
        System.out.println("\nOperation cancelled.");
    }
    
    public void promptForFacultySelection() {
        System.out.print("\nPlease enter the number of the faculty to view the courses they offer: ");
    }

    public void showInvalidSelectionMessage() {
        System.out.println("\nInvalid selection. Please try again.\n");
    }
    
    public void printEnterFacultyNumber() {
        System.out.print("\nPlease enter the number of the faculty to view the courses they offer: ");
    }

    public void printWouldYouLikeToViewAnotherFaculty() {
        System.out.print("\nWould you like to view courses for another faculty? (yes/no): ");
    }
    
    
    public void printFacultiesHeader() {
        System.out.println("\nFaculties:");
    }

    public void printFacultyOption(int index, String facultyName) {
        System.out.println(index + ". " + facultyName);
    }

    public void printPromptForFacultySelection() {
        System.out.print("\nPlease enter the number of the faculty to view the courses they offer: ");
    }

    public void printSelectedFaculty(String facultyName) {
        System.out.println("\nYou have selected:");
        System.out.println("\nFaculty: " + facultyName);
    }

    public void printNoCoursesFound(String facultyName) {
        System.out.println("\nNo courses found for " + facultyName + ".");
    }

    public void printCourseOption(String courseId, String courseName, int credits) {
        System.out.println("- " + courseId + ": " + courseName + " (" + credits + " credits)");
    }

    public void printCourseNotFoundDebug(String courseId) {
        System.out.println("\nCourse with ID " + courseId + " not found."); // For debugging
    }

    public void printInvalidFacultySelection() {
        System.out.println("\nInvalid faculty selection.");
    }

    public void printPromptForAnotherView() {
        System.out.print("\nWould you like to view courses for another faculty? (yes/no): ");
    }

    public void printInvalidInput() {
        System.out.println("\nInvalid input. Please type 'yes' or 'no'.");
    }

    public void printThankYouForUsingCMS() {
        System.out.println("\nThank you for using the Course Management Subsystem.");
    }
    
    public void printSummaryReportSemesterHeader() {
        
        System.out.println("==============================================================================================================================");
        System.out.println("                                        MULTI-SEMESTER COURSE OFFERING SUMMARY REPORT                                         ");
        System.out.println("==============================================================================================================================");
    }
    
    public void displayMenuForReport() {
        System.out.println("\n\n********** Generate Summary Report **********\n");
        System.out.println("Please choose which report to generate:");
        System.out.println("1. Course Availability Summary Report");
        System.out.println("2. Multi-Semester Course Offering Summary Report");
        System.out.print("\nEnter your choice (1/2): ");
    }
    
    public void printReportHeader() {
        System.out.println("======================================================================================================================================");
        System.out.println("                                                 COURSE AVAILABILITY SUMMARY REPORT                                                   ");
        System.out.println("======================================================================================================================================");
        System.out.println("Generated at: " + LocalDateTime.now());
        System.out.println("+--------+------------------------------------------------------+----------+-------------+----------------------+---------------------+");
        System.out.println("| CODE   | COURSE NAME                                          | STATUS   | CREDIT HOUR | PROGRAMMES OFFERED   | FACULTIES OFFERED   |");
        System.out.println("+--------+------------------------------------------------------+----------+-------------+----------------------+---------------------+");
    }

    public void printCourseSummary(String courseId, String courseName, String status, int credits, int programmeCount, int facultyCount) {
        System.out.printf("| %-6s | %-50s | %-8s | %-11d | %-20d | %-19d |\n",
                courseId, courseName, status, credits, programmeCount, facultyCount);
    }

    public void printReportFooter() {
        System.out.println("==============================================================================================================================");
        System.out.println("END OF THE COURSE AVAILABILITY SUMMARY REPORT");
        System.out.println("==============================================================================================================================");
    }
    
    public void printReport2Footer() {
        System.out.println("==============================================================================================================================");
        System.out.println("END OF THE MULTI-SEMESTER COURSE OFFERING SUMMARY REPORT");
        System.out.println("==============================================================================================================================");
    }
    
    public void printTotalCoursesSummary(int totalMain, int totalElective, int totalResit, int totalRepeat, int totalCourses) {
        System.out.println("\nTotal " + totalCourses + " Courses: " +
                totalMain + " Main | " +
                totalElective + " Elective | " +
                totalResit + " Resit | " +
                totalRepeat + " Repeat");
    }

    public void printHighestLowestFacultiesOffered(int maxFaculties, String maxFacultiesCourses, int minFaculties, String minFacultiesCourses) {
        System.out.println("\n----------------------------------------------------");
        System.out.println("HIGHEST FACULTIES OFFERED:");
        System.out.println("->[" + maxFaculties + " FACULTIES] " + maxFacultiesCourses);

        System.out.println("\nLOWEST FACULTIES OFFERED:");
        System.out.println("->[" + minFaculties + " FACULTIES] " + minFacultiesCourses);
        System.out.println("\n[NOTE: 0 FACULTY/PROGRAMME OFFERED IS NOT COUNTED]");
    }

    public void printHighestLowestProgrammesOffered(int maxProgrammes, String maxProgrammesCourses, int minProgrammes, String minProgrammesCourses) {
        System.out.println("\n----------------------------------------------------");
        System.out.println("HIGHEST PROGRAMMES OFFERED:");
        System.out.println("->[" + maxProgrammes + " PROGRAMMES] " + maxProgrammesCourses);

        System.out.println("\nLOWEST PROGRAMMES OFFERED:");
        System.out.println("->[" + minProgrammes + " PROGRAMMES] " + minProgrammesCourses);
        System.out.println("\n[NOTE: 0 FACULTY/PROGRAMME OFFERED IS NOT COUNTED]\n");
    }
    
    public void printSemesterHeader(String semesterName) {
        System.out.println("SEMESTER " + semesterName + " COURSE OFFERINGS:");
    }
    
    public void printCourseTableHeader(String semesterName) {
        System.out.println("SEMESTER " + semesterName + " COURSE OFFERINGS:");
        System.out.println("+------------+----------------------------------------+-------------+");
        System.out.println("|     CODE   | COURSE NAME                            | CREDIT HOUR |");
        System.out.println("+------------+----------------------------------------+-------------+");
    }

    public void printCourseTableRow(String courseID, String courseName, int credits) {
        System.out.printf("| %-6s | %-40s | %-11d |\n", courseID, courseName, credits);
    }

    public void printCourseTableFooter(int totalCourses, String semesterName) {
        System.out.println("+--------+--------------------------------------------+-------------+");
        System.out.println("| Total Courses for " + semesterName + ": " + String.format("%-2d", totalCourses) + "             \t\t\t    |");
        System.out.println("+--------+--------------------------------------------+-------------+\n");
    }
    
    public void printOverallAnalysisHeader() {
        System.out.println("----------------------------------------------------");
        System.out.println("OVERALL SEMESTER COURSE OFFERING ANALYSIS:\n");
    }

    public void printOverallCoursesSummary(int totalCourses, int highestOfferings, String semesterMostCourses, int lowestOfferings, String semesterLeastCourses, int averageCourses) {
        System.out.println("- Total of " + totalCourses + " courses offered across all semesters.");
        System.out.println("- The highest number of courses (" + highestOfferings + ") are offered in " + semesterMostCourses + ".");
        System.out.println("- The lowest number of courses (" + lowestOfferings + ") are offered in " + semesterLeastCourses + ".");
        System.out.println("- On average, there are " + averageCourses + " courses per semester.");
    }

    public void printOverallAnalysisFooter() {
        System.out.println("----------------------------------------------------");
    }
    
    public void printOverallCoursesSummary(int totalCourses, String semesterMostCourses, int highestOfferings, String semesterLeastCourses, int lowestOfferings, int averageCourses) {
        System.out.println("- Total of " + totalCourses + " courses offered across all semesters.");
        System.out.println("- The highest number of courses (" + highestOfferings + ") are offered in " + semesterMostCourses + ".");
        System.out.println("- The lowest number of courses (" + lowestOfferings + ") are offered in " + semesterLeastCourses + ".");
        System.out.println("- On average, there are " + averageCourses + " courses per semester.");
    }
    
    public void displaySummaryReportMenu() {
        System.out.println("\nChoose which summary report to view:");
        System.out.println("\n1. Course Availability Summary Report");
        System.out.println("2. Multi-Semester Course Offering Summary Report");
        System.out.print("\nEnter your choice (1 or 2): ");
    }

    public int getSummaryReportChoice() {
        int choice = scanner.nextInt(); // Get the integer input from the user
        scanner.nextLine(); // Consume the newline left-over
        return choice;
    }
    
    public void promptPressEnterToContinue() {
        System.out.println("\nPress Enter to return to the main menu...");
        scanner.nextLine(); // Consume the newline character left by scanner.nextInt()
    }
    
    public String inputCourseType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPlease choose a course type from the following options:");
        System.out.println("1. Main Course");
        System.out.println("2. Elective");
        System.out.println("3. Resit");
        System.out.println("4. Repeat");
        System.out.print("Enter your choice (1-4): ");

        while (true) {
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1": return "Main";
                case "2": return "Elective";
                case "3": return "Resit";
                case "4": return "Repeat";
                default:
                    System.out.print("Invalid input. Please enter a number between 1 and 4: ");
            }
        }
    }
    
    public String inputCourseFees() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the course fees (e.g., 1500.00): ");

        while (true) {
            String input = scanner.nextLine().trim();
            try {
                Double.parseDouble(input);  // Attempt to parse the input to validate it's a double
                return input;  // Return the valid input
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid decimal number for the fees: ");
            }
        }
    }
    
    public String confirmAddition() {
        System.out.print("\nPlease confirm the add (yes/no): ");
        return scanner.nextLine().trim();
    }
    
    public String chooseFaculty() {
        System.out.println("Choose a Faculty for this new course:");
        System.out.println("1. Faculty of Science");
        System.out.println("2. Faculty of Engineering");
        System.out.println("3. Faculty of Arts");
        System.out.println("4. Faculty of Business");
        System.out.println("5. Faculty of Computing");
        System.out.print("\nEnter the number corresponding to the Faculty: ");

        int choice = scanner.nextInt();  // scanner is a java.util.Scanner object
        scanner.nextLine(); // consume the leftover newline

        switch (choice) {
            case 1: return "FAC001";
            case 2: return "FAC002";
            case 3: return "FAC003";
            case 4: return "FAC004";
            case 5: return "FAC005";
            default: 
                System.out.println("Invalid selection, please choose a valid number.");
                return chooseFaculty();  // Recursively prompt until a valid choice is made
        }
    }
    
    public String chooseSemester() {
        System.out.println("Choose a Semester for this new course:");
        System.out.println("1. Semester 1");
        System.out.println("2. Semester 2");
        System.out.println("3. Semester 3");
        System.out.print("\nEnter the number corresponding to the Semester: ");

        int choice = scanner.nextInt();  // scanner is a java.util.Scanner object
        scanner.nextLine(); // consume the leftover newline

        switch (choice) {
            case 1: return "SEM001";
            case 2: return "SEM002";
            case 3: return "SEM003";
            default: 
                System.out.println("Invalid selection, please choose a valid number.");
                return chooseSemester();  // Recursively prompt until a valid choice is made
        }
    }

}
