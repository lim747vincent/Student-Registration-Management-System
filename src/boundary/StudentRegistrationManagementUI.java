/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.ListInterface;
import adt.MapInterface;
import entity.Programme;
import java.util.Scanner;

/**
 *
 * @author yongf
 */
public class StudentRegistrationManagementUI {

    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        int choice;
        boolean validChoice = false;
        do {

            System.out.println("------------------------------------------------------------------");
            System.out.println("|                  Welcome Student Registration                  |");
            System.out.println("|                 Management Subsystem Menu Page                 |");
            System.out.println("|----------------------------------------------------------------|");
            System.out.println("| 1. Add New Student                                             |");
            System.out.println("| 2. Remove A Student                                            |");
            System.out.println("| 3. Amend Student Details                                       |");
            System.out.println("| 4. Search Students For Registered Courses                      |");
            System.out.println("| 5. Add Students To A Few Courses                               |");
            System.out.println("| 6. Remove A Student From A Course Registration                 |");
            System.out.println("| 7. Calculate Fee Paid For Registered Courses                   |");
            System.out.println("| 8. Filters Students For Courses Based On Criteria              |");
            System.out.println("| 9. Generate Summary Reports(1)                                 |");
            System.out.println("| 10. Generate Summary Reports(2)                                |");
            System.out.println("| 0. Quit                                                        |");
            System.out.println("------------------------------------------------------------------");
            System.out.println("");
            System.out.print("Enter choice (0-10): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                System.out.println("");
                if (choice >= 0 && choice <= 10) {
                    validChoice = true;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 0 and 10.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
                choice = -1;
            }
        } while (!validChoice);

        return choice;
    }

    public void NewStudentsPage() {
        System.out.println("Welcome Add New Student Page");
        System.out.println("[-1] Back Menu Button");
        System.out.println("");
    }

    public void removeStudentsPage() {
        System.out.println("Welcome Remove Student Page");
        System.out.println("[-1] Back Menu Button");
        System.out.println("");
    }

    public void amendStudentsPage() {
        System.out.println("Welcome Amend Student Details Page");
        System.out.println("[-1] Back Menu Button");
        System.out.println("");
    }

    public void StudentsregPage() {
        System.out.println("Welcome Register Courses Page");
        System.out.println("[-1] Back Menu Button");
        System.out.println("");
    }

    public void searchstudentcoursePage() {
        System.out.println("Welcome Search Students for Registered Courses Page");
        System.out.println("[-1] Back Menu Button");
        System.out.println("");
    }

    public void removestudentcoursePage() {
        System.out.println("Welcome Remove Student from A Course Page");
        System.out.println("[-1] Back Menu Button");
        System.out.println("");
    }

    public void calstudentfeePage() {
        System.out.println("Welcome Calculate Fee Paid for Registered Courses Page");
        System.out.println("[-1] Back Menu Button");
        System.out.println("");
    }

    public void FILTERPage() {
        System.out.println("Welcome Filter Page");
        System.out.println("[-1] Back Menu Button");
        System.out.println("");
    }

    public String inputContinue() {
        String continueString;
        boolean isValid = false;

        do {
            isValid = false;

            System.out.print("Enter Continue option (Y/N)): ");
            continueString = scanner.nextLine().toUpperCase();

            if (continueString.equals("Y") || continueString.equals("N")) {
                break;
            }

            System.out.println("Error: Invalid input");
        } while (!isValid);

        System.out.println();
        return continueString;
    }

    public String inputProgrammeID(MapInterface<String, Programme> programmesMap) {
        String programmeID;
        boolean isValid = false;

        do {
            isValid = false;

            ListInterface<Programme> listProgrammes = programmesMap.getValues();

            System.out.println("Available Programmes below:");
            for (int i = 0; i < listProgrammes.size(); i++) {
                System.out.println((i + 1) + ". " + listProgrammes.get(i).getProgrammeId());
            }

            System.out.print("Enter Programme ID: ");
            programmeID = scanner.nextLine().toUpperCase();

            if (programmesMap.containsKey(programmeID)) {

                break;
            }
            if (programmeID.equals("-1")) {
                return programmeID;
            }

            System.out.println("Error: Invalid input");
        } while (!isValid);

        System.out.println();
        return programmeID;
    }

    public String InputnewstduentID() {
        String pattern = "[a-zA-Z]{3}\\d{4}|-1";

        String id = null;

        boolean isValid = false;
        do {
            System.out.print("Enter New Student ID (3 alphabets + 4 digits): ");
            id = scanner.nextLine();

            if (id.isEmpty()) {
                System.out.println("Error: Id cannot be empty.");
            } else {
                // Check if the entered ID matches the pattern
                if (!id.matches(pattern)) {
                    System.out.println("Invalid Student ID. Please enter exactly 3 alphabets followed by 4 digits.");
                    continue;
                }

                isValid = true;

            }
        } while (!isValid);

        System.out.println("");

        return id;
    }

    public String inputNewStudentName() {
        String pattern = "[a-zA-Z\\s]*|-1";
        String name = null;
        boolean isValid = false;
        do {
            System.out.print("Enter New Student Name: ");
            name = scanner.nextLine();
            if (name.isEmpty()) {
                System.out.println("Error: Name cannot be empty.");
            } else {
                // Check if the entered name matches the pattern
                if (!name.matches(pattern)) {
                    System.out.println("Error: Invalid input. Name must contain only letters or be '-1'.");
                    continue;
                }
                isValid = true;
            }
        } while (!isValid);
        System.out.println("");
        return name;
    }

    public int inputNewStudentAge() {
        int age;
        do {
            System.out.print("Enter New Student Age: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid age.");
                System.out.println("");
                System.out.print("Enter New Student Age: ");
                scanner.nextLine();
            }
            age = scanner.nextInt();
            if (age < -1 || age > 99) {
                System.out.println("Age must be a positive integer And 2 Digit Number.");
                System.out.println("");

            }
        } while (age < -1 || age > 99);
        System.out.println("");
        return age;
    }

    public String inputNewGender() {
        String gender;
        do {
            scanner.nextLine();
            System.out.print("Enter New Student Gender(MALE/FEMALE): ");
            gender = scanner.nextLine().toUpperCase();
            if (!isValidGender(gender)) {
                System.out.println("Error: Gender must be 'MALE' or 'FEMALE'.");
            }
        } while (!isValidGender(gender));
        System.out.println("");
        return gender;
    }

    private boolean isValidGender(String gender) {
        return gender.equals("MALE") || gender.equals("FEMALE") || gender.equals("-1");
    }

    public String continueAddingStudent() {
        String continueAdd;
        do {
            System.out.print("Do you want to continue add student(Y/N): ");
            continueAdd = scanner.nextLine().toUpperCase();
            if (!isValidInput(continueAdd)) {
                System.out.println("Error: Please enter 'Y' or 'N'.");
            }
        } while (!isValidInput(continueAdd));
        System.out.println();
        return continueAdd;
    }

    public String continuesearchregStudent() {
        String continueAdd;
        do {
            System.out.print("Do you want to continue search register student(Y/N): ");
            continueAdd = scanner.nextLine().toUpperCase();
            if (!isValidInput(continueAdd)) {
                System.out.println("Error: Please enter 'Y' or 'N'.");
            }
        } while (!isValidInput(continueAdd));
        System.out.println();
        return continueAdd;
    }

    private boolean isValidInput(String input) {
        return input.equals("Y") || input.equals("N");
    }

    public String removeStudent() {
        String pattern = "[a-zA-Z]{3}\\d{4}|-1";

        String id = null;

        boolean isValid = false;
        do {
            System.out.print("Enter Remove Student ID (3 alphabets + 4 digits): ");
            id = scanner.nextLine();

            if (id.isEmpty()) {
                System.out.println("Error: Id cannot be empty.");
            } else {
                // Check if the entered ID matches the pattern
                if (!id.matches(pattern)) {
                    System.out.println("Invalid Student ID. Please enter exactly 3 alphabets followed by 4 digits.");
                    continue;
                }

                isValid = true;

            }
        } while (!isValid);

        System.out.println("");

        return id;
    }

    public String continueRemovingStudent() {
        String continueRemove;
        do {
            System.out.print("Do you want to continue remove student(Y/N): ");
            continueRemove = scanner.nextLine().toUpperCase();
            if (!isValidInput(continueRemove)) {
                System.out.println("Error: Please enter 'Y' or 'N'.");
            }
        } while (!isValidInput(continueRemove));
        System.out.println();
        return continueRemove;
    }

    public String replaceStudentDetails() {
        String pattern = "[a-zA-Z]{3}\\d{4}|-1";

        String id = null;

        boolean isValid = false;
        do {
            System.out.print("Enter Student ID with replace (3 alphabets + 4 digits): ");
            id = scanner.nextLine();

            if (id.isEmpty()) {
                System.out.println("Error: Id cannot be empty.");
            } else {
                // Check if the entered ID matches the pattern
                if (!id.matches(pattern)) {
                    System.out.println("Invalid Student ID. Please enter exactly 3 alphabets followed by 4 digits.");
                    continue;
                }

                isValid = true;

            }
        } while (!isValid);

        System.out.println("");

        return id;
    }

    public int replaceChoice() {
        int choice;
        do {
            System.out.println("The Title of Replace: ");
            System.out.println("1.Student Name ");
            System.out.println("2.Student Age ");
            System.out.println("3.Student Gender ");
            System.out.println("4.Student Programme ID ");
            System.out.println("-----------------------");
            System.out.println("Notice: ");
            System.out.println("1. Replace programme ID will result student's assigned tutorial group removed");
            System.out.println("-----------------------");
            System.out.print("Enter your Choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
            choice = scanner.nextInt();
            if (choice < -1 || choice > 4) {
                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        } while (choice < -1 || choice > 4);
        scanner.nextLine();
        return choice;

    }

    public String replaceNewName() {

        String pattern = "[a-zA-Z\\s]*|-1";
        String name = null;
        boolean isValid = false;
        do {
            System.out.print("Enter Student New Name: ");
            name = scanner.nextLine();
            if (name.isEmpty()) {
                System.out.println("Error: Name cannot be empty.");
            } else {
                // Check if the entered name matches the pattern
                if (!name.matches(pattern)) {
                    System.out.println("Error: Invalid input. Name must contain only letters or be '-1'.");
                    continue;
                }
                isValid = true;
            }
        } while (!isValid);
        System.out.println("");
        return name;
    }

    public int replaceNewAge() {
        int age;
        do {
            System.out.print("Enter Student New Age: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid age.");
                System.out.println("");
                System.out.print("Enter New Age: ");
                scanner.nextLine();
            }
            age = scanner.nextInt();
            scanner.nextLine();

            if (age < -1 || age > 99) {
                System.out.println("Age must be a positive integer.");
            }
        } while (age < -1 || age > 99);
        System.out.println("");

        return age;
    }

    public String replaceNewGender() {
        String gender;
        do {
            System.out.print("Enter Student New Gender(MALE/FEMALE): ");
            gender = scanner.nextLine().toUpperCase();
            if (!isValidGender(gender)) {
                System.out.println("Error: Gender must be 'MALE' or 'FEMALE'.");
            }
        } while (!isValidGender(gender));
        System.out.println();
        return gender;
    }

    public String replaceNewProgrammeID(MapInterface<String, Programme> programmesMap) {

        String programmeID;
        boolean isValid = false;
        do {
            isValid = false;

            ListInterface<Programme> listProgrammes = programmesMap.getValues();

            System.out.println("Available Programmes below:");
            for (int i = 0; i < listProgrammes.size(); i++) {
                System.out.println((i + 1) + ". " + listProgrammes.get(i).getProgrammeId());
            }

            System.out.print("Enter Programme ID: ");
            programmeID = scanner.nextLine().toUpperCase();

            if (programmesMap.containsKey(programmeID)) {
                break;
            } else if (programmeID.equals("-1")) {
                return programmeID;
            }

            System.out.println("Error: Invalid input");
        } while (!isValid);

        System.out.println();
        return programmeID;
    }

    public String continueAmendingStudent() {
        String continueAmend;
        do {
            System.out.print("Do you want to continue amend student details(Y/N): ");
            continueAmend = scanner.nextLine().toUpperCase();
            if (!isValidInput(continueAmend)) {
                System.out.println("Error: Please enter 'Y' or 'N'.");
            }
        } while (!isValidInput(continueAmend));
        System.out.println();
        return continueAmend;
    }

    public String registerCourse() {

        String pattern = "[a-zA-Z]{3}\\d{4}|-1";

        String id = null;

        boolean isValid = false;
        do {
            System.out.print("Enter The Student ID to Register Course (3 alphabets + 4 digits): ");
            id = scanner.nextLine();

            if (id.isEmpty()) {
                System.out.println("Error: Id cannot be empty.");
            } else {
                // Check if the entered ID matches the pattern
                if (!id.matches(pattern)) {
                    System.out.println("Invalid Student ID. Please enter exactly 3 alphabets followed by 4 digits.");
                    continue;
                }

                isValid = true;

            }
        } while (!isValid);

        System.out.println("");

        return id;
    }

    public int registerCourseDetails() {
        int select;
        do {
            System.out.println("");
            System.out.println("Welcome Register Course Page:  ");
            System.out.println("Type of Course:  ");
            System.out.println("1.Main Course ");
            System.out.println("2.Elective Course ");
            System.out.println("3.Resit Course ");
            System.out.println("4.Repeat Course ");
            System.out.println("");
            System.out.print("Please Select the Type of Course to Register: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.nextLine(); // Read and discard non-integer input
                System.out.print("Please Select the Type of Course to Register: ");
            }
            select = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline character after reading an int

            if (select < -1 || select > 4) {
                System.out.println("Invalid input! Please select a number between 1 and 4.");
            }
        } while (select < -1 || select > 4);
        return select;
    }

    public String inputCourseID() {
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine().trim();

        while (!isValidCourseID(courseId)) {
            System.out.println("Invalid Course ID format. The right format is like 'BACS1000'. Please try again.");
            System.out.print("Enter Course ID: ");
            courseId = scanner.nextLine().trim();
        }

        return courseId;
    }

    // validation method for course ID
    private boolean isValidCourseID(String courseId) {
        return courseId.matches("[A-Za-z]{4}\\d{4}|-1");
    }

    public String continueRegisteringCourse() {
        String continueregcourse;
        do {
            System.out.print("Do you want to continue Register Courses to Students(Y/N): ");
            continueregcourse = scanner.nextLine().toUpperCase();
            if (!isValidInput(continueregcourse)) {
                System.out.println("Error: Please enter 'Y' or 'N'.");
            }
        } while (!isValidInput(continueregcourse));
        System.out.println();
        return continueregcourse;
    }

    public String continueRegisterCourse() {
        String continueregcourse;
        do {
            System.out.print("Do you want to continue Register Courses to this Student(Y/N): ");
            continueregcourse = scanner.nextLine().toUpperCase();
            if (!isValidInput(continueregcourse)) {
                System.out.println("Error: Please enter 'Y' or 'N'.");
            }
        } while (!isValidInput(continueregcourse));
        System.out.println();
        return continueregcourse;
    }

    public String searchregcourseid() {

        System.out.print("Enter Register Course ID: ");
        String regcourseid = scanner.nextLine().trim();

        // You could also include validation here, for example:
        while (!isValidCourseID(regcourseid)) {
            System.out.println("Invalid Course ID format. The right format is like 'BACS1000'. Please try again.");
            System.out.print("Enter Register Course ID: ");
            regcourseid = scanner.nextLine().trim();
        }
        System.out.println("");
        return regcourseid;
    }

    public String RemoveCourse() {

        String pattern = "[a-zA-Z]{3}\\d{4}|-1";

        String studentId = null;

        boolean isValid = false;
        do {
            System.out.print("Please enter the Student ID to Remove The Course (3 alphabets + 4 digits): ");
            studentId = scanner.nextLine();

            if (studentId.isEmpty()) {
                System.out.println("Error: Id cannot be empty.");
            } else {
                // Check if the entered ID matches the pattern
                if (!studentId.matches(pattern)) {
                    System.out.println("Invalid Student ID. Please enter exactly 3 alphabets followed by 4 digits.");
                    continue;
                }

                isValid = true;

            }
        } while (!isValid);

        System.out.println("");

        return studentId;
    }

    public int removeregisterCourseDetails() {
        int select;
        do {
            System.out.println("Welcome Remove Course Page:  ");
            System.out.println("Type of Course:  ");
            System.out.println("1.Main Course ");
            System.out.println("2.Elective Course ");
            System.out.println("3.Resit Course ");
            System.out.println("4.Repeat Course ");
            System.out.print("Please Select the Type of Course to Remove: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid integer.");
                System.out.print("Please Select the Type of Course to Remove: ");
                scanner.nextLine();
            }
            select = scanner.nextInt();
            scanner.nextLine();

            if (select < -1 || select > 4) {
                System.out.println("Invalid input! Please select either 1 - 4.");
            }
        } while (select < -1 || select > 4);
        return select;
    }

    public String regstudentid() {

        String pattern = "[a-zA-Z]{3}\\d{4}|-1";

        String regstudentID = null;

        boolean isValid = false;
        do {
            System.out.print("Enter Student ID to check if registered for this course (3 alphabets + 4 digits): ");
            regstudentID = scanner.nextLine();

            if (regstudentID.isEmpty()) {
                System.out.println("Error: Id cannot be empty.");
            } else {
                // Check if the entered ID matches the pattern
                if (!regstudentID.matches(pattern)) {
                    System.out.println("Invalid Student ID. Please enter exactly 3 alphabets followed by 4 digits.");
                    continue;
                }

                isValid = true;

            }
        } while (!isValid);

        System.out.println("");

        return regstudentID;
    }

    public String calculateregistercourseFee() {

        String pattern = "[a-zA-Z]{3}\\d{4}|-1";

        String studentid = null;

        boolean isValid = false;
        do {
            System.out.print("Enter the Student ID to Calculate his/her Registered Course Fee (3 alphabets + 4 digits): ");
            studentid = scanner.nextLine();

            if (studentid.isEmpty()) {
                System.out.println("Error: Id cannot be empty.");
            } else {
                // Check if the entered ID matches the pattern
                if (!studentid.matches(pattern)) {
                    System.out.println("Invalid Student ID. Please enter exactly 3 alphabets followed by 4 digits.");
                    continue;
                }

                isValid = true;

            }
        } while (!isValid);

        System.out.println("");

        return studentid;
    }

    public String continuecalStudent() {
        String continueAdd;
        do {
            System.out.print("Do you want to continue Calculate other student fee(Y/N): ");
            continueAdd = scanner.nextLine().toUpperCase();
            if (!isValidInput(continueAdd)) {
                System.out.println("Error: Please enter 'Y' or 'N'.");
            }
        } while (!isValidInput(continueAdd));
        System.out.println();
        return continueAdd;
    }

    public String continueCalculatingFee() {
        String continuecalfee;
        do {
            System.out.print("Do you want to continue Calculate other Student Total Course Fee(Y/N): ");
            continuecalfee = scanner.nextLine().toUpperCase();
            if (!isValidInput(continuecalfee)) {
                System.out.println("Error: Please enter 'Y' or 'N'.");
            }
        } while (!isValidInput(continuecalfee));
        System.out.println();
        return continuecalfee;
    }

    public int getFilterChoice() {
        int filterChoice;
        do {
            System.out.println("1. Filter Students For Course Type");
            System.out.println("2. Filter Students For Credit Hours");
            System.out.println("");
            System.out.print("Enter Choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid integer.");
                System.out.print("Enter Choice: ");
                scanner.nextLine();
            }
            filterChoice = scanner.nextInt();
            if (filterChoice < -1 || filterChoice > 2) {
                System.out.println("Invalid input! Please enter a number between 1 and 2.");
            }
        } while (filterChoice < -1 || filterChoice > 2);
        return filterChoice;
    }

    public int filterstudentscourseType() {
        int select;
        do {
            System.out.println("");
            System.out.println("Welcome Filter Course Type:  ");
            System.out.println("Type of Course:  ");
            System.out.println("1.Main Course ");
            System.out.println("2.Elective Course ");
            System.out.println("3.Resit Course ");
            System.out.println("4.Repeat Course ");
            System.out.print("Please Select the Type of Course to Filter: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.nextLine();
            }
            select = scanner.nextInt();
            scanner.nextLine();

            if (select < -1 || select > 4) {
                System.out.println("Invalid input! Please select either 1 - 4.");
            }
        } while (select < -1 || select > 4);
        System.out.println("");

        return select;
    }

    public int FilterStudentsForCreditHours() {
        int select;
        do {
            System.out.println("");
            System.out.println("Welcome Filter Course Type:  ");
            System.out.println("Type of Course:  ");
            System.out.println("1. 3 Credit");
            System.out.println("2. 4 Credit ");
            System.out.print("Please Select the Credit Hours of Course to Filter: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.nextLine(); // Consume non-integer input
            }
            select = scanner.nextInt();
            scanner.nextLine(); // Consume the rest of the line including the newline

            if (select < -1 || select > 2) {
                System.out.println("Invalid input! Please select either 1 - 2.");
            }
        } while (select < -1 || select > 2);
        System.out.println("");

        return select;
    }

    public int inputMinAge() {
        int filterminAge;
        do {
            System.out.print("Enter Min Age: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid age.");
                System.out.println("");
                System.out.print("Enter Min Age: ");
                scanner.nextLine();
            }
            filterminAge = scanner.nextInt();
            if (filterminAge <= 0 || filterminAge > 99) {
                System.out.println("Age must be a positive integer.");
            }
        } while (filterminAge <= 0 || filterminAge > 99);
        System.out.println();
        return filterminAge;
    }

    public int inputMaxAge() {
        int filtermaxAge;
        do {
            System.out.print("Enter Max Age: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid age.");
                System.out.println("");
                System.out.print("Enter Max Age: ");
                scanner.nextLine();
            }
            filtermaxAge = scanner.nextInt();
            if (filtermaxAge <= 0 || filtermaxAge > 99) {
                System.out.println("Age must be a positive integer.");
            }
        } while (filtermaxAge <= 0 || filtermaxAge > 99);
        scanner.nextLine();

        return filtermaxAge;
    }

    public String continueRemovingCourse() {
        String continueRemovingCourse;
        do {
            System.out.print("Do you want to remove student for this course(Y/N): ");
            continueRemovingCourse = scanner.nextLine().toUpperCase();
            if (!isValidInput(continueRemovingCourse)) {
                System.out.println("Error: Please enter 'Y' or 'N'.");
            }
        } while (!isValidInput(continueRemovingCourse));
        System.out.println();
        return continueRemovingCourse;
    }

    public String continueRemoveCourse() {
        String continueRemovingCourse;
        do {
            System.out.print("Do you want to remove student for other course(Y/N): ");
            continueRemovingCourse = scanner.nextLine().toUpperCase();
            if (!isValidInput(continueRemovingCourse)) {
                System.out.println("Error: Please enter 'Y' or 'N'.");
            }
        } while (!isValidInput(continueRemovingCourse));
        System.out.println();
        return continueRemovingCourse;
    }

    public String continueRemovingOtherCourses() {
        String continueRemovingOtherCourses;
        do {
            System.out.print("Do you want to remove student for Course(Y/N): ");
            continueRemovingOtherCourses = scanner.nextLine().toUpperCase();
            if (!isValidInput(continueRemovingOtherCourses)) {
                System.out.println("Error: Please enter 'Y' or 'N'.");
            }
        } while (!isValidInput(continueRemovingOtherCourses));
        System.out.println();
        return continueRemovingOtherCourses;
    }

    public String continuecalfilter() {
        String continueAdd;
        do {
            System.out.print("Do you want to continue filter(Y/N): ");
            continueAdd = scanner.nextLine().toUpperCase();
            if (!isValidInput(continueAdd)) {
                System.out.println("Error: Please enter 'Y' or 'N'.");
            }
        } while (!isValidInput(continueAdd));
        System.out.println();
        return continueAdd;
    }

    public void summaryreportkey() {
        System.out.println("Press <ENTER> key to continue...");
        scanner.nextLine();

    }

}
