package boundary;

import java.util.Scanner;

public class TutorialGroupManagementUI {

    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        int choice = -1;

        System.out.println("");
        System.out.println("======================================");
        System.out.println("\nTutorial Group Management Subsystem\n");
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("Select option below: ");
        System.out.println("1. Add a tutorial group to a programme");
        System.out.println("2. Remove a tutorial groups for a programme");
        System.out.println("3. List all tutorial groups for a programme");
        System.out.println("4. Adding students to a tutorial group");
        System.out.println("5. Remove a student from a tutorial group");
        System.out.println("6. Change the tutorial group for a student ");
        System.out.println("7. List all students in a tutorial group and a programme");
        System.out.println("8. Merge tutorial groups based on requested number of groups");
        System.out.println("9. Merge tutorial groups based on max student capacity per group");
        System.out.println("10. Generate enrollment summary report");
        System.out.println("11. Generate gap analysis summary report");
        System.out.println("0. Quit");
        System.out.println("");
        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Valid input must be [0 - 11]");
        System.out.println("------------------------------------");
        System.out.print("Enter your option: ");

        choice = scanner.nextInt();
        scanner.nextLine();

        System.out.println();

        return choice;
    }

    public void clearInput() {
        scanner.nextLine();
    }

    public void displayGapAnalysisSummaryReport(String date, String outputStrProgrammeDetails, String outputStrSummaryMetrics) {
        System.out.println("=========================================================================================================================================================================================\n");
        System.out.println("                                                                                Gap Analysis Summary Report\n");
        System.out.println("=========================================================================================================================================================================================\n");
        System.out.println("Generated on : " + date + "\n");
        System.out.println("Tutorial Group Details: \n");
        System.out.println(outputStrProgrammeDetails);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("Summary metrics:");
        System.out.println(outputStrSummaryMetrics);
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }

    public void displayEnrollmentSummaryReport(String date, String outputStrTGDetails, String outputStrEnrollmentStatistic, String outputStrSummaryMetrics) {
        System.out.println("=================================================================================================================\n");
        System.out.println("                                                   Enrollment Summary Report\n");
        System.out.println("=================================================================================================================\n");
        System.out.println("Generated on : " + date + "\n");
        System.out.println("Tutorial Group Details: \n");
        System.out.println(outputStrTGDetails);
        System.out.println("-------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("Tutorial group enrollment statistic for each programme: \n");
        System.out.println(outputStrEnrollmentStatistic);
        System.out.println("-------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("Summary metrics:");
        System.out.println(outputStrSummaryMetrics);
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }

    public void displayMergeTGroupByNumOfGroups1(String outputStr) {
        System.out.println("");
        System.out.println("=======================================================================");
        System.out.println("\n   Merge tutorial groups based on requested number of groups (1)\n");
        System.out.println("=======================================================================");
        System.out.println("");
        System.out.println("Select available programme ID below: ");

        System.out.println(outputStr);

        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Input must be valid programme ID");
        System.out.println("b. Input is case senstive");
        System.out.println("c. Enter [-9999] to exit this operation");
        System.out.println("d. If no available programme, it will show empty list");
        System.out.println("------------------------------------");
    }

    public void displayMergeTGroupByNumOfGroups2() {
        System.out.println("");
        System.out.println("=======================================================================");
        System.out.println("\n   Merge tutorial groups based on requested number of groups (2)\n");
        System.out.println("=======================================================================");
        System.out.println("");
        System.out.println("");
        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Input must be valid");
        System.out.println("b. Input is case senstive");
        System.out.println("c. Enter [-9999] to exit this operation");
        System.out.println("d. Enter [-0000] to back previous page");
        System.out.println("e. New tutorial group's max capacity is based on average number of total students in selected programme");
        System.out.println("f. Input only accept more 0");
        System.out.println("g. Student randomly pick and assign to groups");
        System.out.println("h. Student without tutorial groups id will not add into any group");
        System.out.println("i. Additional new tutorial group will not created if all students with tutorial group id in selected programme is assigned. Eg: total 3 student will create 3 different group where each group's max capacity is 1");
        System.out.println("j. If tutorial group do no have student, when select to merge. It will remove it existing groups");
        System.out.println("------------------------------------");
    }

    public void displayMergeTGroupByMaxCapacity1(String outputStr) {
        System.out.println("");
        System.out.println("=======================================================================");
        System.out.println("\n Merge tutorial groups based on new max student capacity per group (1)\n");
        System.out.println("=======================================================================");
        System.out.println("");
        System.out.println("Select available programme ID below: ");

        System.out.println(outputStr);

        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Input must be valid programme ID");
        System.out.println("b. Input is case senstive");
        System.out.println("c. Enter [-9999] to exit this operation");
        System.out.println("d. If no available programme, it will show empty list");
        System.out.println("------------------------------------");
    }

    public void displayMergeTGroupByMaxCapacity2() {
        System.out.println("");
        System.out.println("=======================================================================");
        System.out.println("\n Merge tutorial groups based on new max student capacity per group (2)\n");
        System.out.println("=======================================================================");
        System.out.println("");
        System.out.println("");
        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Input must be valid");
        System.out.println("b. Input is case senstive");
        System.out.println("c. Enter [-9999] to exit this operation");
        System.out.println("d. Enter [-0000] to back previous page");
        System.out.println("e. New tutorial group will created based on new max student capacity input");
        System.out.println("f. Tutorial group max capacity input only accept more 0");
        System.out.println("g. Student randomly pick and assign to groups");
        System.out.println("h. Student without tutorial groups id will not add into any group");
        System.out.println("i. If tutorial group do no have student, when select to merge. It will remove it existing groups");
        System.out.println("j. If select programme without tutorial group, it will no create new tutorial group");
        System.out.println("------------------------------------");
    }

    public void displayRemoveTutorialGroup1(String outputStr) {
        System.out.println("");
        System.out.println("================================================");
        System.out.println("\nRemove a tutorial groups for a programme (1)\n");
        System.out.println("===============================================");
        System.out.println("");
        System.out.println("Select available programme ID below: ");

        System.out.println(outputStr);

        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Input must be valid programme ID");
        System.out.println("b. Input is case senstive");
        System.out.println("c. Enter [-9999] to exit this operation");
        System.out.println("d. If no available programme, it will show empty list");
        System.out.println("------------------------------------");
    }

    public void displayRemoveTutorialGroup2(String outputStr) {
        System.out.println("");
        System.out.println("================================================");
        System.out.println("\nRemove a tutorial groups for a programme (2)\n");
        System.out.println("===============================================");
        System.out.println("");
        System.out.println("Select available tutorial group ID below: ");

        System.out.println(outputStr);

        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Input must be valid ID (tutorial group exist in database)");
        System.out.println("b. Input is case senstive");
        System.out.println("c. Enter [-9999] to exit this operation");
        System.out.println("c. Enter [-0000] to back previous page");
        System.out.println("d. If no available tutorial group, it will show empty list");
        System.out.println("------------------------------------");
    }

    public void displayChangeTutorialGroup1(String outputStr) {
        System.out.println("");
        System.out.println("=========================================================");
        System.out.println("\n     Change the tutorial group for a student (1)\n");
        System.out.println("=========================================================");
        System.out.println("");
        System.out.println("Select available student below: \n");
        System.out.println(outputStr);
        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Input must be valid Student ID (Joined tutorial group & student record exist in database)");
        System.out.println("b. Input is case senstive");
        System.out.println("c. Enter [-9999] to exit this operation");
        System.out.println("d. Student without tutorial group cannot perform this action");
        System.out.println("e. If no available student, it will show empty list");
        System.out.println("------------------------------------");
    }

    public void displayChangeTutorialGroup2(String outputStr) {
        System.out.println("");
        System.out.println("=========================================================");
        System.out.println("\n     Change the tutorial group for a student (2)\n");
        System.out.println("=========================================================");
        System.out.println("");
        System.out.println("Select available tutorial group ID for the student's programme below: \n");
        System.out.println(outputStr);
        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Input must be valid ID");
        System.out.println("b. Input is case senstive");
        System.out.println("c. Enter [-9999] to exit this operation");
        System.out.println("d. Student can only change to tutorial group that different with current group and within his programme");
        System.out.println("e. Student without tutorial group cannot perform this action");
        System.out.println("f. If there is no available tutorial group, it will show empty list. You should create new tutorial group for this programme first before performing current action");
        System.out.println("g. Tutorial group with full capacity is invalid");
        System.out.println("h.  Enter [-0000] to back to previous page");
        System.out.println("------------------------------------");
    }

    public void displayAddNewTutorialGroup1(String outputStr) {
        System.out.println("");
        System.out.println("===========================================");
        System.out.println("\nAdd a tutorial group to a programme (1)\n");
        System.out.println("===========================================");
        System.out.println("");
        System.out.println("Select available programme ID below: ");

        System.out.println(outputStr);

        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Input must be valid programme ID");
        System.out.println("b. Input is case senstive");
        System.out.println("c. Enter [-9999] to exit this operation");
        System.out.println("d. If no available programme, it will show empty list");
        System.out.println("------------------------------------");
    }

    public void displayAddNewTutorialGroup2() {
        System.out.println("");
        System.out.println("===========================================");
        System.out.println("\nAdd a tutorial group to a programme (2)\n");
        System.out.println("===========================================");
        System.out.println("");
        System.out.println("");
        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Tutorial group ID will auto assign when created");
        System.out.println("b. Input is case senstive");
        System.out.println("c. Enter [-9999] in any input will exit this operation");
        System.out.println("d. Enter [-0000] to back to previous page");
        System.out.println("e. Tutorial group max capacity input only accept more 0");
        System.out.println("f. Tutorial group's number of students joined is 0 when added to a programme");
        System.out.println("------------------------------------");
    }

    public void displayDisplayAllTutorialGroups(String outputStr) {
        System.out.println("");
        System.out.println("===========================================");
        System.out.println("\nList all tutorial groups for a programme\n");
        System.out.println("===========================================");
        System.out.println("");
        System.out.println("Select available programme ID below: ");

        System.out.println(outputStr);

        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Input must be valid programme ID");
        System.out.println("b. Input is case senstive");
        System.out.println("c. Enter [-9999] to exit this operation");
        System.out.println("d. If no tutorial group, the result will show nothing");
        System.out.println("------------------------------------");
    }

    public void displayRemoveStudent1(String outputStr) {
        System.out.println("");
        System.out.println("=========================================================");
        System.out.println("\n       Remove a student from a tutorial group (1)\n");
        System.out.println("=========================================================");
        System.out.println("");
        System.out.println("Select available student below: \n");
        System.out.println(outputStr);
        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Input must be valid Student ID (Joined tutorial group & student record exist in database)");
        System.out.println("b. Input is case senstive");
        System.out.println("c. Enter [-9999] to exit this operation");
        System.out.println("d. Student will removed from his joined tutorial group");
        System.out.println("e. If no available student, it will show empty list");
        System.out.println("------------------------------------");
    }

    public void displayAddNewStudent1(String outputStr) {
        System.out.println("");
        System.out.println("=========================================================");
        System.out.println("\n       Adding students to a tutorial group (1)\n");
        System.out.println("=========================================================");
        System.out.println("");
        System.out.println("Select available student below: \n");
        System.out.println(outputStr);
        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Input must be valid ID (Not joined any tutorial group & student record exist in database)");
        System.out.println("b. Input is case senstive");
        System.out.println("c. Enter [-9999] to exit this operation");
        System.out.println("d. Student only can join tutorial group under their programme");
        System.out.println("e. N/A is no joined any tutorial group");
        System.out.println("f. If no available student, it will show empty list");
        System.out.println("------------------------------------");
    }

    public void displayAddNewStudent2(String outputStr) {
        System.out.println("");
        System.out.println("===============================================================");
        System.out.println("\n           Adding students to a tutorial group (2)\n");
        System.out.println("===============================================================");
        System.out.println("");
        System.out.println("Select available tutorial group ID for the student's programme below: \n");

        System.out.println(outputStr);
        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Input must be valid ID");
        System.out.println("b. Input is case senstive");
        System.out.println("c. Enter [-9999] to exit this operation");
        System.out.println("d. Enter [-0000] to back to previous page");
        System.out.println("e. Student only can join tutorial group under their programme");
        System.out.println("f. Student unable to join tutorial group that is full. Solution is create new tutorial group or join group that is not full");
        System.out.println("e. If no available tutorial group, it will show empty list");
        System.out.println("------------------------------------");
    }

    public void displayAddNewStudent3() {
        System.out.println("");
        System.out.println("===============================================================");
        System.out.println("\n           Adding students to a tutorial group (3)\n");
        System.out.println("===============================================================");
        System.out.println("");
        System.out.println("Do you wish to continue add student to a tutorial group? (Y/N)");
        System.out.println("");
        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Input is case senstive");
        System.out.println("b. Enter [N] in input will exit this operation");
        System.out.println("c. Enter [Y] in input will continue this operation");
        System.out.println("------------------------------------");
    }

    public void displayResult(String outputStr) {
        System.out.println("");
        System.out.println("======================================");
        System.out.println("\n              Result\n");
        System.out.println("======================================");
        System.out.println("");
        System.out.println("");
        System.out.println(outputStr);
    }

    public void displayDisplayAllStudents1(String outputStr) {
        System.out.println("");
        System.out.println("=========================================================");
        System.out.println("\nList all students in a tutorial group and a programme (1)\n");
        System.out.println("=========================================================");
        System.out.println("");
        System.out.println("Select available programme ID below: \n");

        System.out.println(outputStr);

        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Input must be valid ID");
        System.out.println("b. Input is case senstive");
        System.out.println("c. Enter [-9999] to exit this operation");
        System.out.println("------------------------------------");
    }

    public void displayDisplayAllStudents2(String outputStr) {
        System.out.println("");
        System.out.println("=========================================================");
        System.out.println("\nList all students in a tutorial group and a programme (2)\n");
        System.out.println("=========================================================");
        System.out.println("");
        System.out.println("Select available tutorial group ID below: \n");

        System.out.println(outputStr);

        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Input must be valid ID");
        System.out.println("b. Input is case senstive");
        System.out.println("c. Enter [-9999] to exit this operation");
        System.out.println("d. Enter [-0000] to back to previous page");
        System.out.println("e. If no available tutorial group, it will show empty list");
        System.out.println("e. If no students in selected tutorial group, result will show empty list");
        System.out.println("------------------------------------");
    }

    public void listAllStudents(String outputStr) {
        System.out.println("");
        System.out.println("========================================================================================");
        System.out.println("\n                   List of All Students For Selected Tutorial Group\n");
        System.out.println("========================================================================================");
        System.out.println("");
        System.out.println(outputStr);
    }

    public void listAllTutorialGroups(String outputStr) {
        System.out.println("");
        System.out.println("==============================================================");
        System.out.println("\n    List of All Tutorial Groups For Selected Programme\n");
        System.out.println("==============================================================");
        System.out.println("");
        System.out.println(outputStr);
    }

    public String inputTutorialGroupId() {
        System.out.print("Enter tutorial group ID: ");
        String groupId = scanner.nextLine();
        return groupId;
    }

    public String inputTutorialGroupCapacity() {
        System.out.print("Enter new tutorial group's max capacity: ");
        String capacity = scanner.nextLine();

        return capacity;
    }

    public String inputClassCount() {
        System.out.print("Enter number of tutorial groups needed: ");
        String classCount = scanner.nextLine();

        return classCount;
    }

    public String inputProgrammeId() {
        System.out.print("Enter programme ID: ");
        String programmeId = scanner.nextLine();
        return programmeId;
    }

    public String inputStudentId() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        return studentId;
    }

    public String inputStudentFirstName() {
        System.out.print("Enter Student's first name: ");
        String firstName = scanner.nextLine();
        return firstName;
    }

    public String inputStudentLastName() {
        System.out.print("Enter Student's last name: ");
        String lastName = scanner.nextLine();
        return lastName;
    }

    public String inputStudentProgram() {
        System.out.print("Enter Student's program: ");
        String program = scanner.nextLine();
        return program;
    }

    public String inputContinue() {
        System.out.print("Enter option: ");
        String option = scanner.nextLine();
        return option;
    }
}
