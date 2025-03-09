package control;

import dao.ProgrammeDAO;
import adt.*;
import boundary.TutorialGroupManagementUI;
import dao.StudentDAO;
import dao.TutorialGroupDAO;
import entity.*;
import util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;

public class TutorialGroupManagement<T> {

    private final TutorialGroupManagementUI tutorialGroupManagementUI;

    private StudentDAO studentDAO;
    private ProgrammeDAO programmeDAO;
    private TutorialGroupDAO tutorialGroupDAO;
    private MapInterface<String, Student> students;
    private MapInterface<String, Programme> programmes;

    public TutorialGroupManagement() {
        this.studentDAO = new StudentDAO();
        this.programmeDAO = new ProgrammeDAO();
        this.tutorialGroupDAO = new TutorialGroupDAO();
        this.students = new Map<>();
        this.programmes = new Map<>();
        this.tutorialGroupManagementUI = new TutorialGroupManagementUI();
    }

//    menu
    public void runTutorialGroupManagementUI() {

        int choice = 0;

        do {
            updateVariable();

            try {
                choice = tutorialGroupManagementUI.getMenuChoice();
            } catch (InputMismatchException e) {
                MessageUI.displayInvalidInputMessage();
                tutorialGroupManagementUI.clearInput();
                choice = 9999;
                continue;
            }

            switch (choice) {
                case 0:
                    System.out.println("Thank You For This Subsystem. Exiting Now...");
                    return;
                case 1:
                    addNewTutorialGroup();
                    break;
                case 2:
                    removeTutorialGroup();
                    break;
                case 3:
                    displayAllTutorialGroups();
                    break;
                case 4:
                    addNewStudent();
                    break;
                case 5:
                    removeStudent();
                    break;
                case 6:
                    changeTutorialGroup();
                    break;
                case 7:
                    displayAllStudents();
                    break;
                case 8:
                    mergeTGroupByClassCount();
                    break;
                case 9:
                    mergeTGroupByMaxCapacity();
                    break;
                case 10:
                    generateEnrollmentSummaryReport();
                    break;
                case 11:
                    generateGapAnalysisSummaryReport();
                    break;
                default:
                    MessageUI.displayInvalidInputMessage();
                    break;
            }

        } while (choice != 0);
    }

//    options
    public void addNewTutorialGroup() {
        boolean exit = false;
        boolean isMaxCapacityInput = true;
        boolean isProgrammeIdInput = true;

        String maxCapacity = "";
        String programmeId = "";
        String outputStrProgramme = "";

        TutorialGroup tutorialGroup = null;
        Programme programme = null;

        ListInterface<Programme> programmeList = programmes.getValues();

        if (programmeList.size() > 1) {
            programmeList.sort(new CustomComparator<>(EnumSortingCriterion.IDSTRING));
        }

        for (int i = 0; i < programmeList.size(); i++) {
            outputStrProgramme += (i + 1) + ". " + programmeList.get(i).getProgrammeId() + "\n";
        }

        while (!exit) {

            if (isProgrammeIdInput) {
                tutorialGroupManagementUI.displayAddNewTutorialGroup1(outputStrProgramme);

                // Ask for programme ID input
                programmeId = tutorialGroupManagementUI.inputProgrammeId();

                programme = programmes.get(programmeId);
            }

            // Check for exit condition
            if ("-9999".equals(programmeId)) {
                MessageUI.displayExitOperationMessage();
                return;
            }

            // Check for invalid input condition
            if (programme == null) {
                isProgrammeIdInput = true;
                MessageUI.displayInvalidInputMessage();
                continue;
            } else {
                isProgrammeIdInput = false;
            }

            if (isMaxCapacityInput) {
                tutorialGroupManagementUI.displayAddNewTutorialGroup2();

                // Ask for tutorial group max capacity input
                maxCapacity = tutorialGroupManagementUI.inputTutorialGroupCapacity();
            }

            // Check for exit condition
            if (maxCapacity.equals("-9999")) {
                MessageUI.displayExitOperationMessage();
                return;
            }

            // Check for back condition
            if (maxCapacity.equals("-0000")) {
                isProgrammeIdInput = true;
                MessageUI.displayBackMessage();
                continue;
            }

            // Check for invalid input condition
            try {
                if (Integer.parseInt(maxCapacity) <= 0) {
                    isMaxCapacityInput = true;
                    MessageUI.displayInvalidInputMessage();
                    continue;
                } else {
                    isMaxCapacityInput = false;
                }
            } catch (NumberFormatException e) {
                isMaxCapacityInput = true;
                MessageUI.displayInvalidInputMessage();
                continue;
            }

            // search what is the highest
            ListInterface<TutorialGroup> tGList = programme.getTutorialGroups().getValues();

            String newIdString = "";

            if (tGList.size() > 1) {
                tGList.sort(new CustomComparator<>(EnumSortingCriterion.IDNUMBER));
            }

            if (tGList.size() > 0) {
                // Get largest ID
                String largestId = tGList.get(tGList.size() - 1).getId();

                String numericPart = largestId.substring(1);

                int newId = Integer.parseInt(numericPart) + 1;

                newIdString = "G" + String.valueOf(newId);
            } else {
                newIdString = "G1";
            }

            tutorialGroup = new TutorialGroup(newIdString,
                    Integer.parseInt(maxCapacity), 0, programmeId
            );

            programme.addTutorialGroup(tutorialGroup);

            String resultStr = "Following tutorial group added to programme " + programmeId + ": \n";

            resultStr += "Tutorial Group ID: " + tutorialGroup.getId() + "\n"
                    + "Max Capacity: " + tutorialGroup.getMaxCapacity() + "\n"
                    + "Number of Students: " + tutorialGroup.getNumStudents() + "\n"
                    + "Programme ID: " + tutorialGroup.getProgrammeId() + "\n";

            tutorialGroupManagementUI.displayResult(resultStr);

            MessageUI.displaySuccessMessage();

            writeAllToFile(programmes, students);
            return;
        }
    }

    public void removeTutorialGroup() {
        boolean exit = false;
        boolean isTutorialGroupIdInput = true;
        boolean isProgrammeIdInput = true;

        String tutorialGroupId = "";
        String programmeId = "";
        String outputStrProgramme = "";

        TutorialGroup tutorialGroup = null;
        Programme programme = null;

        ListInterface<Programme> programmeList = programmes.getValues();

        if (programmeList.size() > 1) {
            programmeList.sort(new CustomComparator<>(EnumSortingCriterion.IDSTRING));
        }

        for (int i = 0; i < programmeList.size(); i++) {
            outputStrProgramme += (i + 1) + ". " + programmeList.get(i).getProgrammeId() + "\n";
        }

        while (!exit) {

            if (isProgrammeIdInput) {
                tutorialGroupManagementUI.displayRemoveTutorialGroup1(outputStrProgramme);

                // Ask for programme ID input
                programmeId = tutorialGroupManagementUI.inputProgrammeId();

                programme = programmes.get(programmeId);
            }

            // Check for exit condition
            if ("-9999".equals(programmeId)) {
                MessageUI.displayExitOperationMessage();
                return;
            }

            // Check for invalid input condition
            if (programme == null) {
                isProgrammeIdInput = true;
                MessageUI.displayInvalidInputMessage();
                continue;
            } else {
                isProgrammeIdInput = false;
            }

            if (isTutorialGroupIdInput) {
                ListInterface<TutorialGroup> tutorialGroupList = programme.getTutorialGroups().getValues();

                if (tutorialGroupList.size() > 1) {
                    tutorialGroupList.sort(new CustomComparator<>(EnumSortingCriterion.IDNUMBER));
                }

                String outputStrTGroup = "";

                for (int i = 0; i < tutorialGroupList.size(); i++) {
                    outputStrTGroup += (i + 1) + ". " + tutorialGroupList.get(i).getId() + "\n";
                }

                tutorialGroupManagementUI.displayRemoveTutorialGroup2(outputStrTGroup);

                // Ask for tutorial group input
                tutorialGroupId = tutorialGroupManagementUI.inputTutorialGroupId();

                tutorialGroup = programme.getTutorialGroups().get(tutorialGroupId);
            }

            // Check for exit condition
            if (tutorialGroupId.equals("-9999")) {
                MessageUI.displayExitOperationMessage();
                return;
            }

            // Check for back condition
            if (tutorialGroupId.equals("-0000")) {
                isProgrammeIdInput = true;
                MessageUI.displayBackMessage();
                continue;
            }

            // Check for invalid input condition
            if (tutorialGroup == null) {
                isTutorialGroupIdInput = true;
                MessageUI.displayInvalidInputMessage();
                continue;
            } else {
                isTutorialGroupIdInput = false;
            }

            tutorialGroupDAO.updateTutorialTutorsFile(programmeId, tutorialGroupId);
            //remove tutorial group from programme hashmap list
            programme.getTutorialGroups().remove(tutorialGroupId);

            writeAllToFile(programmes, students);

            MessageUI.displaySuccessMessage();
            return;

        }
    }

    public void displayAllTutorialGroups() {
        boolean exit = false;
        boolean previousInput = false;

        String outputStrProgramme = "";

        ListInterface<Programme> programmeList = programmes.getValues();

        if (programmeList.size() > 1) {
            programmeList.sort(new CustomComparator<>(EnumSortingCriterion.IDSTRING));
        }

        for (int i = 0; i < programmeList.size(); i++) {
            outputStrProgramme += (i + 1) + ". " + programmeList.get(i).getProgrammeId() + "\n";
        }

        while (!exit) {
            String programmeId = "";
            Programme programme = null;
            previousInput = false;

            tutorialGroupManagementUI.displayDisplayAllTutorialGroups(outputStrProgramme);

            if (!previousInput) {
                // Ask for Programme ID input
                programmeId = tutorialGroupManagementUI.inputProgrammeId();

                programme = programmes.get(programmeId);
            }

            // Check for exit condition
            if ("-9999".equals(programmeId)) {
                exit = true;
                MessageUI.displayExitOperationMessage();
                return;
            }

            // Check for back condition
            if (programme == null) {
                previousInput = true;
                MessageUI.displayInvalidInputMessage();
                continue;
            } else {
                previousInput = false;
            }

            ListInterface<TutorialGroup> tGroups = programme.getTutorialGroups().getValues();

            if (tGroups.size() > 1) {
                tGroups.sort(new CustomComparator<>(EnumSortingCriterion.IDNUMBER));
            }

            String outputStr = "No.    Tutorial Group ID         Student Max Capacity          Number Of Students\n";

            for (int i = 0; i < tGroups.size(); i++) {
                String formattedNo = String.format("%-16s", (i + 1));
                String formattedID = String.format("%-25s", tGroups.get(i).getId());
                String formattedCapacity = String.format("%-30s", tGroups.get(i).getMaxCapacity());
                String formattedNumStudents = String.format("%-25s", tGroups.get(i).getNumStudents());

                outputStr += formattedNo + formattedID + formattedCapacity + formattedNumStudents
                        + "\n";
            }

            tutorialGroupManagementUI.listAllTutorialGroups(outputStr);

            MessageUI.displaySuccessMessage();
            return;
        }
    }

    public void addNewStudent() {
        boolean exit = false;
        boolean isStudentIDInput = true;
        boolean isTutorialGroupIDInput = true;
        boolean isContinueInput = true;

        Student student = null;
        TutorialGroup tutorialGroup = null;

        ListInterface<Student> studentsValues = students.getValues();

        String studentId = "";
        String tutorialGroupId = "";
        String continueInput = "";

        while (!exit) {

            if (isStudentIDInput) {

                if (studentsValues.size() > 1) {
                    studentsValues.sort(new CustomComparator<>(EnumSortingCriterion.IDSTRING));
                }

                String outputStrStudents = "No.   Student ID          Full Name            Programme ID       Tutorial Group ID       \n";

                int k = 0;
                for (int i = 0; i < studentsValues.size(); i++) {

                    if (studentsValues.get(i).getTutorialGroupId().equals("N/A")) {
                        String formattedNo = String.format("%-8s", (k + 1));
                        String formattedID = String.format("%-18s", studentsValues.get(i).getId());
                        String formattedFullName = String.format("%-24s", studentsValues.get(i).getName());
                        String formattedPID = String.format("%-22s", studentsValues.get(i).getProgrammeId());
                        String formattedTID = String.format("%-18s", "N/A");

                        outputStrStudents += formattedNo + formattedID + formattedFullName + formattedPID + formattedTID + "\n";
                        k++;
                    }

                }

                tutorialGroupManagementUI.displayAddNewStudent1(outputStrStudents);

                // Ask for Student ID input
                studentId = tutorialGroupManagementUI.inputStudentId();

                student = students.get(studentId);

            }

            // Check for exit condition
            if ("-9999".equals(studentId)) {
                MessageUI.displayExitOperationMessage();
                return;
            }

            // Check for invalid input condition
            if (student == null) {
                isStudentIDInput = true;
                MessageUI.displayInvalidInputMessage();

                continue;
            } else {
                isStudentIDInput = false;
            }

            // Check if student has already joined tutorial group
            if (!student.getTutorialGroupId().equals("N/A") && !continueInput.equals("restart")) {
                isStudentIDInput = true;
                continueInput = "";
                MessageUI.displayInvalidInputMessage();
                continue;
            } else {
                isStudentIDInput = false;
            }

            if (isTutorialGroupIDInput && !continueInput.equals("restart")) {

                Programme studentProgramme = programmes.get(student.getProgrammeId());

                ListInterface<TutorialGroup> tGroups = studentProgramme.getTutorialGroups().getValues();

                if (tGroups.size() > 1) {
                    tGroups.sort(new CustomComparator<>(EnumSortingCriterion.IDNUMBER));
                }

                String outputStr = "No.    ID      Number Of Student / Capacity \n";

                for (int i = 0; i < tGroups.size(); i++) {
                    outputStr += (i + 1) + ".     " + tGroups.get(i).getId() + "               " + tGroups.get(i).getNumStudents() + " / " + tGroups.get(i).getMaxCapacity() + "\n";
                }

                tutorialGroupManagementUI.displayAddNewStudent2(outputStr);

                tutorialGroupId = tutorialGroupManagementUI.inputTutorialGroupId();

                tutorialGroup = studentProgramme.getTutorialGroups().get(tutorialGroupId);
            }

            // Check for exit condition
            if ("-9999".equals(tutorialGroupId)) {
                MessageUI.displayExitOperationMessage();
                return;
            }

            // Check for back condition
            if ("-0000".equals(tutorialGroupId)) {
                isStudentIDInput = true;
                MessageUI.displayBackMessage();
                continue;
            }

            // Check for invalid input condition
            if ((tutorialGroup == null || (tutorialGroup.isFull() && !continueInput.equals("restart")))) {
                isTutorialGroupIDInput = true;

                continueInput = "";

                MessageUI.displayInvalidInputMessage();
                continue;
            } else if (continueInput.equals("restart")) {
            } else {
                isTutorialGroupIDInput = false;

                student.setTutorialGroupId(tutorialGroupId);

                tutorialGroup.addStudent(student);

                String resultStr = "Following student added to tutorial group " + tutorialGroupId + ": \n";

                resultStr += "Student ID: " + student.getId() + "\n"
                        + "Full Name: " + student.getName() + "\n"
                        + "Programme ID: " + student.getProgrammeId() + "\n"
                        + "Tutorial Group ID: " + student.getTutorialGroupId() + "\n";

                tutorialGroupManagementUI.displayResult(resultStr);

                MessageUI.displaySuccessMessage();
            }

            if (isContinueInput) {
                tutorialGroupManagementUI.displayAddNewStudent3();

                continueInput = tutorialGroupManagementUI.inputContinue();
            }

            if (continueInput.equals("Y")) {
                exit = false;
                isContinueInput = true;
                isStudentIDInput = true;
                isTutorialGroupIDInput = true;
                continueInput = "";

                writeAllToFile(programmes, students);
            } else if (continueInput.equals("N")) {
                writeAllToFile(programmes, students);
                MessageUI.displayExitOperationMessage();
                return;
            } else {
                isContinueInput = true;
                MessageUI.displayInvalidInputMessage();
                continueInput = "restart";
                isStudentIDInput = false;
                isTutorialGroupIDInput = false;
                writeAllToFile(programmes, students);
            }

        }

    }

    public void removeStudent() {
        boolean exit = false;

        Student student = null;

        ListInterface<Student> studentsValues = students.getValues();

        while (!exit) {
            String studentId = "";

            if (studentsValues.size() > 1) {
                studentsValues.sort(new CustomComparator<>(EnumSortingCriterion.IDSTRING));
            }

            String outputStrStudents = "No.   Student ID          Full Name            Programme ID       Tutorial Group ID       \n";

            int k = 0;
            for (int i = 0; i < studentsValues.size(); i++) {

                if (!studentsValues.get(i).getTutorialGroupId().equals("N/A")) {
                    String formattedNo = String.format("%-8s", (k + 1));
                    String formattedID = String.format("%-18s", studentsValues.get(i).getId());
                    String formattedFullName = String.format("%-24s", studentsValues.get(i).getName());
                    String formattedPID = String.format("%-22s", studentsValues.get(i).getProgrammeId());
                    String formattedTID = String.format("%-18s", studentsValues.get(i).getTutorialGroupId());

                    outputStrStudents += formattedNo + formattedID + formattedFullName + formattedPID + formattedTID + "\n";
                    k++;
                }

            }

            tutorialGroupManagementUI.displayRemoveStudent1(outputStrStudents);

            // Ask for Student ID input
            studentId = tutorialGroupManagementUI.inputStudentId();

            student = students.get(studentId);

            // Check for exit condition
            if ("-9999".equals(studentId)) {
                MessageUI.displayExitOperationMessage();
                return;
            }

            // Check for invalid input condition
            if (student == null || student.getTutorialGroupId() == null) {
                exit = false;
                MessageUI.displayInvalidInputMessage();
                continue;
            }

            TutorialGroup studentTutorialGroup = programmes.get(student.getProgrammeId()).getTutorialGroups().get(student.getTutorialGroupId());

            studentTutorialGroup.removeStudent(studentId);

            MessageUI.displaySuccessMessage();

            writeAllToFile(programmes, students);
            return;

        }
    }

    public void displayAllStudents() {
        boolean exit = false;
        boolean isProgrammeIDInput = true;
        boolean isTutorialGroupIDInput = true;

        Programme programme = null;
        TutorialGroup tutorialGroup = null;

        String outputStrProgramme = "";

        ListInterface<Programme> programmeList = programmes.getValues();

        if (programmeList.size() > 1) {
            programmeList.sort(new CustomComparator<>(EnumSortingCriterion.IDSTRING));
        }

        for (int i = 0; i < programmeList.size(); i++) {
            outputStrProgramme += (i + 1) + ". " + programmeList.get(i).getProgrammeId() + "\n";
        }

        while (!exit) {
            String programmeId = "";
            String tutorialGroupId = "";

            if (isProgrammeIDInput) {
                tutorialGroupManagementUI.displayDisplayAllStudents1(outputStrProgramme);

                // Ask for Programme ID input
                programmeId = tutorialGroupManagementUI.inputProgrammeId();

                programme = programmes.get(programmeId);
            }

            // Check for exit condition
            if ("-9999".equals(programmeId)) {
                MessageUI.displayExitOperationMessage();
                return;
            }

            // Check for back condition
            if (programme == null) {
                isProgrammeIDInput = true;
                MessageUI.displayInvalidInputMessage();
                continue;
            } else {
                isProgrammeIDInput = false;
            }

            ListInterface<TutorialGroup> tGroups = programme.getTutorialGroups().getValues();

            if (tGroups.size() > 1) {
                tGroups.sort(new CustomComparator<>(EnumSortingCriterion.IDNUMBER));
            }

            String outputStr = "No.    Tutorial Group ID         Student Max Capacity          Number Of Students\n";

            for (int i = 0; i < tGroups.size(); i++) {
                String formattedNo = String.format("%-16s", (i + 1));
                String formattedID = String.format("%-25s", tGroups.get(i).getId());
                String formattedCapacity = String.format("%-30s", tGroups.get(i).getMaxCapacity());
                String formattedNumStudents = String.format("%-25s", tGroups.get(i).getNumStudents());

                outputStr += formattedNo + formattedID + formattedCapacity + formattedNumStudents
                        + "\n";
            }

            tutorialGroupManagementUI.displayDisplayAllStudents2(outputStr);

            if (isTutorialGroupIDInput) {
                // Ask for tutorial group id input
                tutorialGroupId = tutorialGroupManagementUI.inputTutorialGroupId();

                tutorialGroup = programme.getTutorialGroups().get(tutorialGroupId);
            }

            // Check for exit condition
            if ("-9999".equals(tutorialGroupId)) {
                MessageUI.displayExitOperationMessage();
                return;
            }

            // Check for back condition
            if ("-0000".equals(tutorialGroupId)) {
                isProgrammeIDInput = true;
                MessageUI.displayBackMessage();
                continue;
            }

            if (tutorialGroup == null) {
                isTutorialGroupIDInput = true;
                MessageUI.displayInvalidInputMessage();
                continue;
            } else {
                isTutorialGroupIDInput = false;
            }

            ListInterface<Student> studentsList = tutorialGroup.getStudents().getValues();

            if (studentsList.size() > 1) {
                studentsList.sort(new CustomComparator<>(EnumSortingCriterion.NAME));
            }

            String outputStrStudents = "No.   Student ID          Full Name            Programme ID       Tutorial Group ID       \n";

            for (int i = 0; i < studentsList.size(); i++) {
                String formattedNo = String.format("%-8s", (i + 1));
                String formattedID = String.format("%-18s", studentsList.get(i).getId());
                String formattedFullName = String.format("%-24s", studentsList.get(i).getName());
                String formattedPID = String.format("%-22s", studentsList.get(i).getProgrammeId());
                String formattedTID = String.format("%-18s", studentsList.get(i).getTutorialGroupId());

                outputStrStudents += formattedNo + formattedID + formattedFullName + formattedPID + formattedTID + "\n";
            }

            tutorialGroupManagementUI.listAllStudents(outputStrStudents);

            MessageUI.displaySuccessMessage();
            return;

        }
    }

    public void changeTutorialGroup() {
        boolean exit = false;
        boolean isStudentIDInput = true;
        boolean isTutorialGroupIDInput = true;

        String studentId = "";
        String studentTutorialGroupId = "";

        Student student = null;
        TutorialGroup studentTutorialGroup = null;

        ListInterface<Student> studentsValues = students.getValues();

        while (!exit) {

            if (isStudentIDInput) {
                if (studentsValues.size() > 1) {
                    studentsValues.sort(new CustomComparator<>(EnumSortingCriterion.IDSTRING));
                }

                String outputStrStudents = "No.   Student ID          Full Name            Programme ID       Tutorial Group ID       \n";

                int k = 0;
                for (int i = 0; i < studentsValues.size(); i++) {

                    if (!studentsValues.get(i).getTutorialGroupId().equals("N/A")) {
                        String formattedNo = String.format("%-8s", (k + 1));
                        String formattedID = String.format("%-18s", studentsValues.get(i).getId());
                        String formattedFullName = String.format("%-24s", studentsValues.get(i).getName());
                        String formattedPID = String.format("%-22s", studentsValues.get(i).getProgrammeId());
                        String formattedTID = String.format("%-18s", studentsValues.get(i).getTutorialGroupId());

                        outputStrStudents += formattedNo + formattedID + formattedFullName + formattedPID + formattedTID + "\n";
                        k++;
                    }

                }

                tutorialGroupManagementUI.displayChangeTutorialGroup1(outputStrStudents);

                // Ask for Student ID input
                studentId = tutorialGroupManagementUI.inputStudentId();

                student = students.get(studentId);
            }

            // Check for exit condition
            if ("-9999".equals(studentId)) {
                MessageUI.displayExitOperationMessage();
                return;
            }

            // Check for invalid input condition
            if (student == null || student.getTutorialGroupId() == null) {
                isStudentIDInput = true;
                MessageUI.displayInvalidInputMessage();
                continue;
            } else {
                isStudentIDInput = false;
            }

            if (isTutorialGroupIDInput) {

                // Get tutorial group list
                ListInterface<TutorialGroup> tutorialGroupList = programmes.get(student.getProgrammeId()).getTutorialGroups().getValues();

                if (tutorialGroupList.size() > 1) {
                    tutorialGroupList.sort(new CustomComparator<>(EnumSortingCriterion.IDNUMBER));
                }

                String outputStr = "No.    Tutorial Group ID         Student Max Capacity          Number Of Students\n";

                int j = 0;
                for (int i = 0; i < tutorialGroupList.size(); i++) {
                    if ((tutorialGroupList.get(i).isFull() != true) && !(tutorialGroupList.get(i).getId().equals(student.getTutorialGroupId()))) {
                        String formattedNo = String.format("%-16s", (i + 1));
                        String formattedID = String.format("%-25s", tutorialGroupList.get(i).getId());
                        String formattedCapacity = String.format("%-30s", tutorialGroupList.get(i).getMaxCapacity());
                        String formattedNumStudents = String.format("%-25s", tutorialGroupList.get(i).getNumStudents());

                        outputStr += formattedNo + formattedID + formattedCapacity + formattedNumStudents
                                + "\n";
                        j++;
                    }
                }

                tutorialGroupManagementUI.displayChangeTutorialGroup2(outputStr);

                //Ask for tutorial group ID input
                studentTutorialGroupId = tutorialGroupManagementUI.inputTutorialGroupId();

                studentTutorialGroup = programmes.get(student.getProgrammeId()).getTutorialGroups().get(studentTutorialGroupId);
            }

            // Check for exit condition
            if ("-9999".equals(studentTutorialGroupId)) {
                MessageUI.displayExitOperationMessage();
                return;
            }

            // Check for back condition
            if ("-0000".equals(studentTutorialGroupId)) {
                isStudentIDInput = true;
                MessageUI.displayBackMessage();
                continue;
            }

            // Check for invalid input condition
            if (studentTutorialGroup == null || studentTutorialGroup.isFull() || (studentTutorialGroupId.equals(student.getTutorialGroupId()))) {
                isTutorialGroupIDInput = true;
                MessageUI.displayInvalidInputMessage();
                continue;
            } else {
                isTutorialGroupIDInput = false;
            }

            TutorialGroup tGroup = programmes.get(student.getProgrammeId()).getTutorialGroups().get(student.getTutorialGroupId());

            // remove student from own TG in hashmap
            tGroup.removeStudent(studentId);

            //update student TG
            student.setTutorialGroupId(studentTutorialGroupId);

            // add student to selected TG in hashmap
            studentTutorialGroup.addStudent(student);

            MessageUI.displaySuccessMessage();

            writeAllToFile(programmes, students);
            return;

        }
    }

    public void mergeTGroupByClassCount() {
        boolean exit = false;
        boolean isClassCountInput = true;
        boolean isProgrammeIdInput = true;

        String classCount = "";
        String programmeId = "";
        String outputStrProgramme = "";

        TutorialGroup tutorialGroup = null;
        Programme programme = null;

        ListInterface<Programme> programmeList = programmes.getValues();

        if (programmeList.size() > 1) {
            programmeList.sort(new CustomComparator<>(EnumSortingCriterion.IDSTRING));
        }

        for (int i = 0; i < programmeList.size(); i++) {
            outputStrProgramme += (i + 1) + ". " + programmeList.get(i).getProgrammeId() + "\n";
        }

        while (!exit) {

            if (isProgrammeIdInput) {
                tutorialGroupManagementUI.displayMergeTGroupByNumOfGroups1(outputStrProgramme);

                // Ask for programme ID input
                programmeId = tutorialGroupManagementUI.inputProgrammeId();

                programme = programmes.get(programmeId);
            }

            // Check for exit condition
            if ("-9999".equals(programmeId)) {
                MessageUI.displayExitOperationMessage();
                return;
            }

            // Check for invalid input condition
            if (programme == null) {
                isProgrammeIdInput = true;
                MessageUI.displayInvalidInputMessage();
                continue;
            } else {
                isProgrammeIdInput = false;
            }

            if (isClassCountInput) {
                tutorialGroupManagementUI.displayMergeTGroupByNumOfGroups2();

                // Ask for tutorial group count input
                classCount = tutorialGroupManagementUI.inputClassCount();
            }

            // Check for exit condition
            if (classCount.equals("-9999")) {
                MessageUI.displayExitOperationMessage();
                return;
            }

            // Check for back condition
            if (classCount.equals("-0000")) {
                isProgrammeIdInput = true;
                MessageUI.displayBackMessage();
                continue;
            }

            // Check for invalid input condition
            try {
                if (Integer.parseInt(classCount) <= 0) {
                    isClassCountInput = true;
                    MessageUI.displayInvalidInputMessage();
                    continue;
                } else {
                    isClassCountInput = false;
                }
            } catch (NumberFormatException e) {
                isClassCountInput = true;
                MessageUI.displayInvalidInputMessage();
                continue;
            }

            //create new empty tutorial groups hashmap
            MapInterface<String, TutorialGroup> mergedTutorialGroup = new Map<>();

            //loop till all students in student hashmap inserted
            int counter = 1;
            int counterTG = 1;

            double totalStudentInTG = 0;

            ListInterface<Student> studentsList = students.getValues();
            boolean isCreateGroup = false;

            for (int i = 0; i < studentsList.size(); i++) {
                if ((studentsList.get(i).getProgrammeId().equals(programmeId)) && !(studentsList.get(i).getTutorialGroupId().equals("N/A"))) {
                    totalStudentInTG++;
                }
            }

            double avgMaxCapacity = totalStudentInTG / Double.parseDouble(classCount);

            int roundedMaxCapacity = (int) Math.ceil(avgMaxCapacity);

            int listSize = studentsList.size();

            // Loop until all students are assigned to tutorial groups
            while (counterTG <= Integer.parseInt(classCount) && listSize > 0) {
                // Create a new empty tutorial group
                TutorialGroup newTGroup = new TutorialGroup(("G" + counterTG), roundedMaxCapacity, 0, programmeId);

                // Add students to the new tutorial group until it reaches its maximum capacity or no more students are available
                while (!newTGroup.isFull() && !(listSize == 0)) {

                    Student student = studentsList.get(counter - 1);

                    if (!(student.getTutorialGroupId().equals("N/A")) && (student.getProgrammeId().equals(programmeId))) {

                        // update student to new tutorial group id
                        student.setTutorialGroupId(newTGroup.getId());

                        // Add the student to the tutorial group
                        newTGroup.addStudent(student);

                        isCreateGroup = true;

                    }

                    counter++;
                    listSize--;
                }

                if (isCreateGroup) {
                    // Add the newly created tutorial group to the merged tutorial group map
                    mergedTutorialGroup.add(newTGroup.getId(), newTGroup);
                    isCreateGroup = false;
                }

                counterTG++;
            }

            //set tutorial groups hashmap in programmes to new values
            programmes.get(programmeId).setTutorialGroups(mergedTutorialGroup);

            MessageUI.displaySuccessMessage();

            writeAllToFile(programmes, students);
            return;
        }
    }

    public void mergeTGroupByMaxCapacity() {
        boolean exit = false;
        boolean isMaxCapacityInput = true;
        boolean isProgrammeIdInput = true;

        String maxCapacity = "";
        String programmeId = "";
        String outputStrProgramme = "";

        TutorialGroup tutorialGroup = null;
        Programme programme = null;

        ListInterface<Programme> programmeList = programmes.getValues();

        if (programmeList.size() > 1) {
            programmeList.sort(new CustomComparator<>(EnumSortingCriterion.IDSTRING));
        }

        for (int i = 0; i < programmeList.size(); i++) {
            outputStrProgramme += (i + 1) + ". " + programmeList.get(i).getProgrammeId() + "\n";
        }

        while (!exit) {

            if (isProgrammeIdInput) {
                tutorialGroupManagementUI.displayMergeTGroupByMaxCapacity1(outputStrProgramme);

                // Ask for programme ID input
                programmeId = tutorialGroupManagementUI.inputProgrammeId();

                programme = programmes.get(programmeId);
            }

            // Check for exit condition
            if ("-9999".equals(programmeId)) {
                MessageUI.displayExitOperationMessage();
                return;
            }

            // Check for invalid input condition
            if (programme == null) {
                isProgrammeIdInput = true;
                MessageUI.displayInvalidInputMessage();
                continue;
            } else {
                isProgrammeIdInput = false;
            }

            if (isMaxCapacityInput) {
                tutorialGroupManagementUI.displayMergeTGroupByMaxCapacity2();

                // Ask for tutorial group max capacity input
                maxCapacity = tutorialGroupManagementUI.inputTutorialGroupCapacity();
            }

            // Check for exit condition
            if (maxCapacity.equals("-9999")) {
                MessageUI.displayExitOperationMessage();
                return;
            }

            // Check for back condition
            if (maxCapacity.equals("-0000")) {
                isProgrammeIdInput = true;
                MessageUI.displayBackMessage();
                continue;
            }

            // Check for invalid input condition
            try {
                if (Integer.parseInt(maxCapacity) <= 0) {
                    isMaxCapacityInput = true;
                    MessageUI.displayInvalidInputMessage();
                    continue;
                } else {
                    isMaxCapacityInput = false;
                }
            } catch (NumberFormatException e) {
                isMaxCapacityInput = true;
                MessageUI.displayInvalidInputMessage();
                continue;
            }

            //create new empty tutorial groups hashmap
            MapInterface<String, TutorialGroup> mergedTutorialGroup = new Map<>();

            //loop till all students in student hashmap inserted
            int counter = 1;
            int counterTG = 1;

            ListInterface<Student> studentsList = students.getValues();

            int listSize = studentsList.size();
            boolean isCreateGroup = false;

            // Loop until all students are assigned to tutorial groups
            while (listSize != 0) {
                // Create a new empty tutorial group
                TutorialGroup newTGroup = new TutorialGroup(("G" + counterTG), Integer.parseInt(maxCapacity), 0, programmeId);

                // Add students to the new tutorial group until it reaches its maximum capacity or no more students are available
                while (!newTGroup.isFull() && !(listSize == 0)) {

                    Student student = studentsList.get(counter - 1);

                    if (!(student.getTutorialGroupId().equals("N/A")) && (student.getProgrammeId().equals(programmeId))) {

                        // update student to new tutorial group id
                        student.setTutorialGroupId(newTGroup.getId());

                        // Add the student to the tutorial group
                        newTGroup.addStudent(student);

                        isCreateGroup = true;
                    }

                    counter++;
                    listSize--;
                }

                if (isCreateGroup) {
                    // Add the newly created tutorial group to the merged tutorial group map
                    mergedTutorialGroup.add(newTGroup.getId(), newTGroup);
                    isCreateGroup = false;
                }

                counterTG++;
            }

            //set tutorial groups hashmap in programmes to new values
            programmes.get(programmeId).setTutorialGroups(mergedTutorialGroup);

            MessageUI.displaySuccessMessage();

            writeAllToFile(programmes, students);
            return;

        }
    }

    public void generateEnrollmentSummaryReport() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();

        String date = dateFormat.format(currentDate);

        String outputStrTGDetails = "Programme ID        Tutorial Group ID        Student Max Capacity          No. Of Students Enrolled\n";
        String outputStrEnrollmentStatistic
                = "Programme ID          Total Student Enrolled          Total Student             Enrollment Rate (%)               Rate Category\n";

        ListInterface<Programme> programmesList = programmes.getValues();

        if (programmesList.size() > 1) {
            programmesList.sort(new CustomComparator<>(EnumSortingCriterion.IDSTRING));
        }

        String programmeHighestTG = "";
        String programmeLowestTG = "";
        int PreviousHighestPNumTG = 0;
        int PreviousLowestPNumTG = 0;
        int poorCounter = 0;
        int mediumCounter = 0;
        int goodCounter = 0;
        String programmeHighestER = "";
        String programmeLowestER = "";
        double PreviousHighestER = 0.0;
        double PreviousLowestER = 0.0;

        for (int i = 0; i < programmesList.size(); i++) {
            int totalStudentEnrolled = 0;
            int totalStudent = 0;

            ListInterface<TutorialGroup> tutorialGroupList = programmesList.get(i).getTutorialGroups().getValues();

            if (tutorialGroupList.size() > PreviousHighestPNumTG) {
                programmeHighestTG = programmesList.get(i).getProgrammeId();

                PreviousHighestPNumTG = tutorialGroupList.size();
            } else if (tutorialGroupList.size() == PreviousHighestPNumTG) {
                if (programmeHighestTG.equals("")) {
                    programmeHighestTG = programmesList.get(i).getProgrammeId();
                } else {
                    programmeHighestTG += ", " + programmesList.get(i).getProgrammeId();
                }
            }

            if (tutorialGroupList.size() != 0) {
                if (tutorialGroupList.size() < PreviousLowestPNumTG || programmeLowestTG.equals("")) {
                    programmeLowestTG = programmesList.get(i).getProgrammeId();

                    PreviousLowestPNumTG = tutorialGroupList.size();
                } else if (tutorialGroupList.size() == PreviousLowestPNumTG) {
                    programmeLowestTG += ", " + programmesList.get(i).getProgrammeId();
                }
            }

            ListInterface<Student> allStudent = students.getValues();
            for (int k = 0; k < allStudent.size(); k++) {
                if (allStudent.get(k).getProgrammeId().equals(programmesList.get(i).getProgrammeId())) {
                    totalStudent++;
                }
            }

            if (tutorialGroupList.size() == 0) {
                String formattedProgrammeID = String.format("%-26s", programmesList.get(i).getProgrammeId());
                String formattedTGroupID = String.format("%-25s", "N/A");
                String formattedCapacity = String.format("%-30s", "N/A");
                String formattedNumStudents = String.format("%-25s", "0");

                outputStrTGDetails += formattedProgrammeID + formattedTGroupID + formattedCapacity + formattedNumStudents
                        + "\n";
            } else {
                if (tutorialGroupList.size() > 1) {
                    tutorialGroupList.sort(new CustomComparator<>(EnumSortingCriterion.IDNUMBER));
                }

                for (int j = 0; j < tutorialGroupList.size(); j++) {
                    TutorialGroup tutorialGroup = tutorialGroupList.get(j);

                    String formattedProgrammeID = String.format("%-26s", tutorialGroup.getProgrammeId());
                    String formattedTGroupID = String.format("%-25s", tutorialGroup.getId());
                    String formattedCapacity = String.format("%-30s", tutorialGroup.getMaxCapacity());
                    String formattedNumStudents = String.format("%-25s", tutorialGroup.getNumStudents());

                    totalStudentEnrolled += tutorialGroup.getNumStudents();

                    outputStrTGDetails += formattedProgrammeID + formattedTGroupID + formattedCapacity + formattedNumStudents
                            + "\n";
                }
            }

            double calculation = (Double.valueOf(totalStudentEnrolled) / Double.valueOf(totalStudent));
            double roundOff = calculation / 100 * 100;

            String rateCategory = "";

            if (roundOff > 0.8) {
                rateCategory = "Good";
                goodCounter++;
            } else if (roundOff >= 0.6 && roundOff <= 0.8) {
                rateCategory = "Medium";
                mediumCounter++;
            } else {
                if (totalStudent != 0) {

                    poorCounter++;
                }
                rateCategory = "Poor";
            }

            if (totalStudent == 0) {
                rateCategory = "N/A";

            }

            if (roundOff > PreviousHighestER) {
                programmeHighestER = programmesList.get(i).getProgrammeId();

                PreviousHighestER = roundOff;
            } else if (roundOff == PreviousHighestER) {
                if (programmeHighestER.equals("")) {
                    programmeHighestER = programmesList.get(i).getProgrammeId();
                } else {
                    programmeHighestER += ", " + programmesList.get(i).getProgrammeId();
                }
            }

            if (!rateCategory.equals("N/A")) {
                if (roundOff < PreviousLowestER || programmeLowestER.equals("")) {
                    programmeLowestER = programmesList.get(i).getProgrammeId();
                    PreviousLowestER = roundOff;
                } else if (roundOff == PreviousLowestER) {
                    programmeLowestER += ", " + programmesList.get(i).getProgrammeId();
                }
            }

            String formattedProgrammeID = String.format("%-35s", programmesList.get(i).getProgrammeId());
            String formattedTotalStudentEnrolled = String.format("%-25s", totalStudentEnrolled);
            String formattedTotalStudent = String.format("%-30s", totalStudent);
            String formattedEnrollmentRate = String.format("%-25.2f", (roundOff));

            outputStrEnrollmentStatistic
                    += formattedProgrammeID + formattedTotalStudentEnrolled + formattedTotalStudent + formattedEnrollmentRate + rateCategory + "\n";
        }

        outputStrTGDetails += "\nNote: \n";
        outputStrTGDetails += "1. N/A (Not available) when programme has 0 tutorial group\n";

        outputStrEnrollmentStatistic += "\nNote: \n";
        outputStrEnrollmentStatistic += "1. Enrollment rate > 0.8 is Good\n";
        outputStrEnrollmentStatistic += "2. Enrollment rate >= 0.6 & <= 0.8 is Medium\n";
        outputStrEnrollmentStatistic += "3. Enrollment rate < 0.6 is Poor\n";
        outputStrEnrollmentStatistic += "4. Formula for enrollment rate is total student enrolled / total student\n";
        outputStrEnrollmentStatistic += "5. N/A (Not available) when programme has 0 total student\n";
        outputStrEnrollmentStatistic += "6. Programme with N/A rate category will not calculated in summary metrics\n";

        String outputStrSummaryMetrics
                = "Programme with highest number of tutorial group: " + programmeHighestTG + "\n";

        outputStrSummaryMetrics
                += "Programme with lowest number of tutorial group: " + programmeLowestTG + "\n";

        outputStrSummaryMetrics
                += "Programme with  highest enrollment rate: " + programmeHighestER + "\n";

        outputStrSummaryMetrics
                += "Programme with lowest enrollment rate: " + programmeLowestER + "\n";
        outputStrSummaryMetrics
                += "Total number of programme in poor category: " + poorCounter + "\n";
        outputStrSummaryMetrics
                += "Total number of programme in medium category: " + mediumCounter + "\n";
        outputStrSummaryMetrics
                += "Total number of programme in good category: " + goodCounter + "\n";

        outputStrSummaryMetrics
                += "\nComment: \n";
        if (poorCounter > 0) {
            outputStrSummaryMetrics += "There is " + poorCounter + " programme with poor enrollment performance. Please pay attention on these programme\n";
        } else {
            outputStrSummaryMetrics += "There is no programme with poor enrollment performance. Please continue optimzing enrollment rate for all programme\n";
        }

        tutorialGroupManagementUI.displayEnrollmentSummaryReport(date, outputStrTGDetails, outputStrEnrollmentStatistic, outputStrSummaryMetrics);
    }

    public void generateGapAnalysisSummaryReport() {
        int goodCounter = 0;
        int mediumCounter = 0;
        int poorCounter = 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();

        String date = dateFormat.format(currentDate);

        String outputStrProgrammeDetails = "Programme ID        Total Available Capacity / Max Capacity          No. Of Enrolled / Total Student            Remaining Capacity Rate             Capacity Category\n";

        ListInterface<Programme> programmesList = programmes.getValues();

        if (programmesList.size() > 1) {
            programmesList.sort(new CustomComparator<>(EnumSortingCriterion.IDSTRING));
        }

        for (int i = 0; i < programmesList.size(); i++) {
            int totalStudentEnrolled = 0;
            int totalAvailableCapacity = 0;
            int totalMaxCapacity = 0;
            int totalStudent = 0;

            //get total student
            ListInterface<Student> studentList = students.getValues();

            for (int j = 0; j < studentList.size(); j++) {
                Student student = studentList.get(j);

                if (student.getProgrammeId().equals(programmesList.get(i).getProgrammeId())) {
                    totalStudent++;
                }
            }

            // get total max capacity, available capacity and student enrrolled
            ListInterface<TutorialGroup> tutorialGroupList = programmesList.get(i).getTutorialGroups().getValues();

            for (int j = 0; j < tutorialGroupList.size(); j++) {
                TutorialGroup tutorialGroup = tutorialGroupList.get(j);

                totalMaxCapacity += tutorialGroup.getMaxCapacity();
                totalStudentEnrolled += tutorialGroup.getNumStudents();
                totalAvailableCapacity += (tutorialGroup.getMaxCapacity() - tutorialGroup.getNumStudents());
            }

            int remainingCapacity = totalAvailableCapacity - (totalStudent - totalStudentEnrolled);

            double ratio, roundOff;

            if (remainingCapacity <= 0) {
                ratio = 0;
                roundOff = 0;
            } else {
                ratio = Double.valueOf(remainingCapacity) / Double.valueOf(totalAvailableCapacity);

                roundOff = ratio * 100 / 100;

            }

            String capacityCategory = "";

            if (ratio > 0.7) {
                capacityCategory = "Good";
                goodCounter++;
            } else if (ratio >= 0.4) {
                capacityCategory = "Medium";
                mediumCounter++;
            } else {
                capacityCategory = "Poor";
                poorCounter++;
            }

            String combineString1 = totalAvailableCapacity + " / " + totalMaxCapacity;
            String combineString2 = totalStudentEnrolled + " / " + totalStudent;

            String formattedProgrammeID = String.format("%-35s", programmesList.get(i).getProgrammeId());
            String formattedAvailableCapacity = String.format("%-45s", combineString1);
            String formattedTotalStudent = String.format("%-42s", combineString2);
            String formattedRate = String.format("%-32.2f", roundOff);
            String formattedCapacityCategory = String.format("%-25s", capacityCategory);

            // write into string output
            outputStrProgrammeDetails += formattedProgrammeID + formattedAvailableCapacity + formattedTotalStudent + formattedRate + formattedCapacityCategory
                    + "\n";
        }

        outputStrProgrammeDetails += "\nNote: \n";
        outputStrProgrammeDetails += "1. Remaining capacity rate > 0.7 is Good\n";
        outputStrProgrammeDetails += "2. Remaining capacity rate >= 0.4 and =< 0.7 is Medium\n";
        outputStrProgrammeDetails += "3. Remaining capacity rate < 0.4 is Poor\n";
        outputStrProgrammeDetails += "4. Formula for remaining capacity rate is (total available capacity - total student that have not registered tutorial group) / total available capacity\n";

        String outputStrSummaryMetrics = "";
        outputStrSummaryMetrics += "Number of programme with good capacity category : " + goodCounter + "\n";
        outputStrSummaryMetrics += "Number of programme with medium capacity category : " + mediumCounter + "\n";
        outputStrSummaryMetrics += "Number of programme with poor capacity category : " + poorCounter + "\n";

        outputStrSummaryMetrics += "\nComment: \n";

        if (poorCounter > 0) {
            outputStrSummaryMetrics += "There is " + poorCounter + " programme with poor capacity rate. Please pay attention on these programme\n";
        } else {
            outputStrSummaryMetrics += "There is no programme with poor capacity rate. Please continue optimzing capacity rate for all programme\n";
        }

        tutorialGroupManagementUI.displayGapAnalysisSummaryReport(date, outputStrProgrammeDetails, outputStrSummaryMetrics);
    }

    private void updateVariable() {
        //update students variable
        this.students = studentDAO.loadStudentsHashMap();

        //update tutorial group variable
        //update programme variable
        MapInterface<String, Programme> programmesOri = programmeDAO.loadProgrammesHashMap();

        ListInterface<Programme> programmesList = programmesOri.getValues();

        ListInterface<TutorialGroup> tGroupsList = tutorialGroupDAO.loadTutorialGroupList();

        ListInterface<Student> studentsList = students.getValues();

        for (int i = 0; i < programmesList.size(); i++) {
            Programme programme = programmesList.get(i);

            for (int j = 0; j < tGroupsList.size(); j++) {
                TutorialGroup tutorialGroup = tGroupsList.get(j);

                if (tutorialGroup.getProgrammeId().equals(programme.getProgrammeId())) {

                    programme.getTutorialGroups().add(tutorialGroup.getId(), tutorialGroup);

                    for (int k = 0; k < studentsList.size(); k++) {
                        Student student = studentsList.get(k);

                        if (student.getProgrammeId().equals(programme.getProgrammeId()) && student.getTutorialGroupId().equals(tutorialGroup.getId())) {
                            tutorialGroup.getStudents().add(student.getId(), student);

                        }

                    }

                }
            }

        }

programmes = programmesOri;

    }

    private void writeAllToFile(MapInterface<String, Programme> programmesMap, MapInterface<String, Student> studentsOriMap) {
        ListInterface<TutorialGroup> tutorialGroupsFileList = new List<>();
        ListInterface<Student> studentsFileList = new List<>();

        ListInterface<Programme> programmesList = programmesMap.getValues();

        for (int i = 0; i < programmesList.size(); i++) {
            Programme programme = programmesList.get(i);

            ListInterface<TutorialGroup> tGroupsList = programme.getTutorialGroups().getValues();

            for (int j = 0; j < tGroupsList.size(); j++) {
                TutorialGroup tutorialGroup = tGroupsList.get(j);
                tutorialGroupsFileList.add(tutorialGroup);

                ListInterface<Student> studentList = tutorialGroup.getStudents().getValues();

                for (int k = 0; k < studentList.size(); k++) {
                    Student student = studentList.get(k);

                    studentsOriMap.remove(student.getId());

                    studentsFileList.add(student);

                }

            }

        }

        ListInterface<Student> listOriStudents = studentsOriMap.getValues();

        for (int i = 0; i < listOriStudents.size(); i++) {
            Student student = listOriStudents.get(i);

            Student temporaryStudent = null;

            temporaryStudent = new Student(student.getId(), student.getName(), student.getAge(), student.getGender(), student.getProgrammeId(), "N/A");

            studentsFileList.add(temporaryStudent);
        }

        tutorialGroupDAO.writeTutorialGroupsToFileList(tutorialGroupsFileList);
        studentDAO.writeStudentsToFileList(studentsFileList);

    }
}
