/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package control;

import entity.*;
import adt.*;
import boundary.StudentRegistrationManagementUI;
import util.MessageUI;
import dao.*;
import dao.CourseDetailsInitializer;

/**
 *
 * @author yongf
 */
public class StudentRegistrationManagement {

    private ListInterface<Student> studentsList = new List<>();
    private ListInterface<Course> courseList = new List<>();
    private ListInterface<Programme> programmesList = new List<>();
    private ListInterface<RegisterCourse> RegisterCourseList = new List<>();
    private final MapInterface<String, Student> studentsMap = new Map<>();
    private final MapInterface<String, Course> courseMap = new Map<>();
    private final MapInterface<String, Programme> programmesMap = new Map<>();
    private final MapInterface<String, RegisterCourse> regcourseMap = new Map<>();
    private final StudentRegistrationManagementUI studentRegistrationManagementUI = new StudentRegistrationManagementUI();
    StudentDAO studentDAO = new StudentDAO();
    CourseDAO courseDAO = new CourseDAO();
    CourseDetailsDAO courseDetailsDAO = new CourseDetailsDAO();
    registerCourseDAO RegisterCourseDAO = new registerCourseDAO();
    ProgrammeDAO programmeDAO = new ProgrammeDAO();
    TutorialGroupDAO tutorialGroupDAO = new TutorialGroupDAO();

    public StudentRegistrationManagement() {

    }

    public void updatedata() {

        CourseDetailsInitializer detailsInitializer = new CourseDetailsInitializer(courseDetailsDAO);
        detailsInitializer.initialize();

        // Load Students and populate studentsList and studentsMap
        studentsList = studentDAO.loadStudents();
        for (int i = 0; i < studentsList.size(); i++) {
            Student student = studentsList.get(i);
            studentsMap.add(student.getId(), student);
        }

        // Load Courses and populate courseList and courseMap
        courseList = courseDAO.loadCourses();
        for (int i = 0; i < courseList.size(); i++) {
            Course course = courseList.get(i);
            courseMap.add(course.getCourseID(), course);
        }

        // Load RegisterCourses and populate RegisterCourseList and regcourseMap
        RegisterCourseList = RegisterCourseDAO.loadRegisterCourses();
        for (int i = 0; i < RegisterCourseList.size(); i++) {
            RegisterCourse registerCourse = RegisterCourseList.get(i);
            String key = registerCourse.getStudent().getId() + "_" + registerCourse.getCourse().getCourseID();
            regcourseMap.add(key, registerCourse);
        }

        programmesList = programmeDAO.loadProgrammes();
        for (int i = 0; i < programmesList.size(); i++) {
            Programme programme = programmesList.get(i);

            programmesMap.add(programme.getProgrammeId(), programme);

        }
    }

    public void runStudentRegistrationManagement() {
        int choice = 0;
        do {
            updatedata();
            choice = studentRegistrationManagementUI.getMenuChoice();
            switch (choice) {

                case 0:
                    System.out.println("Thank You For This Subsystem. Exiting Now...");
                    return;
                case 1:
                    addNewStudent();
                    break;
                case 2:
                    removeStudent();
                    break;
                case 3:
                    AmendStudentDetails();
                    break;
                case 4:
                    SearchStudentregCourse();
                    break;
                case 5:
                    studentRegisterCourse();
                    break;
                case 6:
                    removeCourseforStudent();
                    break;
                case 7:
                    calculateTotalCourseFee();
                    break;
                case 8:
                    filterStudents();
                    break;
                case 9:
                    summaryReport1();
                    break;
                case 10:
                    summaryReport2();
                    break;
                default:
                    break;
            }
        } while (choice != 0);
    }

    public void addNewStudent() {

        do {
            Student newStudents = null;
            studentRegistrationManagementUI.NewStudentsPage();
            String studentID = studentRegistrationManagementUI.InputnewstduentID();
            if (studentID.equals("-1")) {
                break;
            }
            String studentName = studentRegistrationManagementUI.inputNewStudentName();
            if (studentName.equals("-1")) {
                break;
            }
            int studentAge = studentRegistrationManagementUI.inputNewStudentAge();
            if (studentAge == -1) {
                break;
            }
            String studentGender = studentRegistrationManagementUI.inputNewGender();
            if (studentGender.equals("-1")) {
                break;
            }

            String studentProgramme = studentRegistrationManagementUI.inputProgrammeID(programmesMap);
            if (studentProgramme.equals("-1")) {
                break;
            }

            newStudents = new Student(studentID, studentName, studentAge, studentGender, studentProgramme, "N/A");

            if (studentsMap.containsKey(newStudents.getId())) {
                System.out.println("Error: Student with ID " + newStudents.getId() + " already exists. Please choose a different ID.");
                System.out.println("");
                continue;
            }
            studentsMap.add(newStudents.getId(), newStudents);
            studentsList.add(newStudents);
            studentDAO.saveStudent(newStudents);
            System.out.println("Student added successfully");
            System.out.println("");

            String continueInput = studentRegistrationManagementUI.continueAddingStudent();
            if (!continueInput.equalsIgnoreCase("Y") && !continueInput.equalsIgnoreCase("y")) {
                break;
            }
        } while (true);
    }

    public void displayAllStudents() {
        System.out.println("All Students In TARUMT: ");
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%-1s %-15s %-1s %-15s %-1s %-10s %-1s %-10s %-1s %10S %-1s %-10S %-1s %n", "|", "ID", "|", "Name", "|", "Age", "|", "Gender", "|", "Program ID", "|", "Group ID", "|");
        System.out.println("-----------------------------------------------------------------------------------------");
        for (int i = 0; i < studentsList.size(); i++) {
            Student student = studentsList.get(i);
            System.out.printf("%-1s %-15s %-1s %-15s %-1s %-10s %-1s %-10s %-1s %-10S %-1s %-10S %-1s %n", "|", student.getId(), "|", student.getName(), "|", student.getAge(), "|", student.getGender(), "|", student.getProgrammeId(), "|", student.getTutorialGroupId(), "|");
        }
        System.out.println("-----------------------------------------------------------------------------------------");

    }

    public void removeStudent() {
        do {
            studentRegistrationManagementUI.removeStudentsPage();
            displayAllStudents();
            String studentIdToRemove = studentRegistrationManagementUI.removeStudent();
            if (studentIdToRemove.equals("-1")) {
                break;
            }
            Student removedStudent = studentsMap.remove(studentIdToRemove);

            if (removedStudent != null) {
                System.out.println("Student with ID " + studentIdToRemove + " has been removed.");

                studentsList.remove(removedStudent);
                studentDAO.updateStudentFile(studentsList);
                tutorialGroupDAO.updateTutorialGroupsFile(removedStudent);

                for (int i = 0; i < RegisterCourseList.size(); i++) {
                    RegisterCourse regCourse = RegisterCourseList.get(i);
                    if (regCourse.getStudent().getId().equals(studentIdToRemove)) {
                        RegisterCourseList.remove(regCourse);
                        RegisterCourseDAO.updateRegisterCourseFile(RegisterCourseList);
                    }
                }
            } else {
                System.out.println("Student with ID " + studentIdToRemove + " not found.");

            }

            String continueInput = studentRegistrationManagementUI.continueRemovingStudent();
            if (!continueInput.equalsIgnoreCase("Y") && !continueInput.equalsIgnoreCase("y")) {
                break;
            }
        } while (true);
    }

    public void AmendStudentDetails() {
        do {
            studentRegistrationManagementUI.amendStudentsPage();
            displayAllStudents();
            String studentIdToUpdate = studentRegistrationManagementUI.replaceStudentDetails();
            if (studentIdToUpdate.equals("-1")) {
                break;
            }
            Student studentToUpdate = studentsMap.get(studentIdToUpdate);
            if (studentToUpdate == null) {
                System.out.println("Student with ID " + studentIdToUpdate + " not found.");
                continue;
            }

            int choice = studentRegistrationManagementUI.replaceChoice();
            if (choice == -1) {
                break;
            }
            switch (choice) {
                case 1:
                    String newName = studentRegistrationManagementUI.replaceNewName();
                    if (newName.equals("-1")) {
                        return;
                    }
                    studentToUpdate.setName(newName);
                    System.out.println("Name updated successfully.");
                    break;
                case 2:
                    int newAge = studentRegistrationManagementUI.replaceNewAge();
                    if (newAge == -1) {
                        return;
                    }
                    studentToUpdate.setAge(newAge);
                    System.out.println("Age updated successfully.");
                    break;
                case 3:
                    String newGender = studentRegistrationManagementUI.replaceNewGender();
                    if (newGender.equals("-1")) {
                        return;
                    }
                    studentToUpdate.setGender(newGender);
                    System.out.println("Gender updated successfully.");
                    break;
                case 4:
                    String newProgrammeID = studentRegistrationManagementUI.replaceNewProgrammeID(programmesMap);
                    if (newProgrammeID.equals("-1")) {
                        return;
                    }
                    tutorialGroupDAO.updateTutorialGroupsFile(studentToUpdate);
                    studentToUpdate.setProgrammeId(newProgrammeID);
                    studentToUpdate.setTutorialGroupId("N/A");
                    System.out.println("Programme ID updated successfully.");
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
            studentDAO.updateStudentFile(studentsList);

            String continueInput = studentRegistrationManagementUI.continueAmendingStudent();
            if (!continueInput.equalsIgnoreCase("Y") && !continueInput.equalsIgnoreCase("y")) {
                break;
            }
        } while (true);
    }

    public void studentRegisterCourse() {
        int choice;
        do {
            studentRegistrationManagementUI.StudentsregPage();
            displayAllStudents();
            String studentIdToRegisterCourse = studentRegistrationManagementUI.registerCourse();
            if (studentIdToRegisterCourse.equals("-1")) {
                break;
            }
            Student student = studentsMap.get(studentIdToRegisterCourse);
            if (student == null) {
                System.out.println("Student with ID " + studentIdToRegisterCourse + " not found.");
                continue;
            }

            do {
                choice = studentRegistrationManagementUI.registerCourseDetails();
                if (choice == -1) {
                    break;
                }
                Course course = null;

                switch (choice) {
                    case 1:
                        course = selectCourseFromList("Main");
                        break;
                    case 2:
                        course = selectCourseFromList("Elective");
                        break;
                    case 3:
                        course = selectCourseFromList("Resit");
                        break;
                    case 4:
                        course = selectCourseFromList("Repeat");
                        break;
                    default:
                        System.out.println("Invalid option.");
                        return;
                }

                if (course != null) {
                    if (isAlreadyRegistered(student, course)) {
                        System.out.println("Student already registered for course: " + course.getCourseName());
                        continue;
                    }
                }
                if (course != null) {
                    CourseDetails courseDetails = courseDetailsDAO.findCourseDetailsByCourseId(course.getCourseID());
                    if (courseDetails == null) {
                        System.out.println("Course details not found for Course ID " + course.getCourseID());
                        continue;
                    }

                    RegisterCourse regCourse = new RegisterCourse(student, course, courseDetails);
                    RegisterCourseList.add(regCourse);
                    RegisterCourseDAO.saveRegisterCourse(regCourse);
                    System.out.println("[" + course.getCourseName() + "] " + courseDetails.getCourseType() + " course added successfully.");
                } else {
                    System.out.println("No course selected or available for this category.");
                }

                String continueInput = studentRegistrationManagementUI.continueRegisterCourse();
                if (!continueInput.equalsIgnoreCase("Y")) {
                    break;
                }
            } while (true);

            String continueInput = studentRegistrationManagementUI.continueRegisteringCourse();
            if (!continueInput.equalsIgnoreCase("Y")) {
                break;
            }
        } while (true);
    }

    private boolean isAlreadyRegistered(Student student, Course course) {
        for (RegisterCourse regCourse : RegisterCourseList) {
            if (regCourse.getStudent().getId().equals(student.getId()) && regCourse.getCourse().getCourseID().equals(course.getCourseID())) {
                System.out.println("Already registered");
                return true;
            }
        }
        return false;
    }

    private Course selectCourseFromList(String courseType) {
        ListInterface<Course> allCourses = courseDAO.loadCourses();
        ListInterface<CourseDetails> allCourseDetails = courseDetailsDAO.loadCourseDetails();

        // Filtered courses by type
        ListInterface<Course> filteredCourses = new List<>();
        System.out.println("Available " + courseType + " Courses:");
        System.out.println("");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.printf("%1s %-10s %1s %-40s %1s %-10s %1s %-15s %1s %-10s %1s %n", "|", "Course ID", "|", "Course Name", "|", "Credits", "|", "Course Type", "|", "Fees (RM)", "|");
        System.out.println("-----------------------------------------------------------------------------------------------------");

        boolean coursesAvailable = false;
        for (CourseDetails details : allCourseDetails) {
            if (details.getCourseType().trim().equalsIgnoreCase(courseType.trim())) {
                Course course = courseDAO.getCourseById(details.getCourseId());
                if (course != null) {
                    filteredCourses.add(course);
                    System.out.printf("%1s %-10s %1s %-40s %1s %-10s %1s %-15s %1s %-10.2f %1s %n", "|", course.getCourseID(), "|", course.getCourseName(), "|", course.getCredits(), "|", details.getCourseType(), "|", details.getCourseFees(), "|");
                    coursesAvailable = true;
                }
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------------------");

        if (!coursesAvailable) {
            System.out.println("No " + courseType + " courses available.");
            return null;
        }

        // Prompt for Course ID input
        System.out.println("Enter the Course ID of the " + courseType + " course you want to add:");
        String courseId = studentRegistrationManagementUI.inputCourseID();

        // Find the selected course from the filtered list
        for (Course course : filteredCourses) {
            if (course.getCourseID().equalsIgnoreCase(courseId)) {
                return course;
            }
        }

        System.out.println("Course not found. Please ensure you've entered a Course ID from the list above.");
        return null;
    }

    public void displayAllCourses() {
        // Retrieve all courses
        ListInterface<Course> allCourses = courseDAO.loadCourses();

        // Heading
        System.out.println("All Available Courses In TARUMT:");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.printf("%1s %-10s %1s %-40s %1s %-10s %1s %-15s %1s %-10s %1s %n", "|", "Course ID", "|", "Course Name", "|", "Credits", "|", "Course Type", "|", "Fees (RM)", "|");
        System.out.println("-----------------------------------------------------------------------------------------------------");

        // Iterate through all courses and fetch their details
        for (int i = 0; i < allCourses.size(); i++) {
            Course course = allCourses.get(i);
            CourseDetails details = courseDetailsDAO.findCourseDetailsByCourseId(course.getCourseID());

            // Default values for type and fees if details are not found
            String courseType = "N/A";
            double courseFees = 0.0;

            if (details != null) {
                courseType = details.getCourseType();
                courseFees = details.getCourseFees();
            }

            System.out.printf("%1s %-10s %1s %-40s %1s %-10s %1s %-15s %1s %-10.2f %1s %n",
                    "|", course.getCourseID(),
                    "|", course.getCourseName(),
                    "|", course.getCredits(),
                    "|", courseType,
                    "|", courseFees, "|");
        }

        System.out.println("-----------------------------------------------------------------------------------------------------");
    }

    public void SearchStudentregCourse() {
        do {
            studentRegistrationManagementUI.searchstudentcoursePage();
            displayAllCourses();
            String courseId = studentRegistrationManagementUI.searchregcourseid();
            if (courseId.equals("-1")) {
                break;
            }
            // Fetching the selected course and its details
            Course selectedCourse = courseMap.get(courseId);
            if (selectedCourse == null) {
                System.out.println("Course with ID " + courseId + " not found.");
                return;
            }

            CourseDetails courseDetails = courseDetailsDAO.findCourseDetailsByCourseId(courseId);
            if (courseDetails == null) {
                System.out.println("Details for Course with ID " + courseId + " not found.");
                return;
            }

            System.out.println("");
            System.out.println("Course ID            : " + selectedCourse.getCourseID());
            System.out.println("Course Name          : " + selectedCourse.getCourseName());
            System.out.println("Course Type          : " + courseDetails.getCourseType()); // Fetch from CourseDetails
            System.out.println("Course Credit Hour   : " + selectedCourse.getCredits());
            System.out.println("Course Fee           : RM " + courseDetails.getCourseFees()); // Fetch from CourseDetails

            // Initialize a custom List for RegisterCourse
            ListInterface<RegisterCourse> registeredStudents = new List<>();

            // Populate the list with students registered for the selected course
            for (RegisterCourse regCourse : RegisterCourseList) {
                if (regCourse.getCourse().getCourseID().equals(courseId)) {
                    registeredStudents.add(regCourse);
                }
            }

            if (registeredStudents.isEmpty()) {
                System.out.println("No student registered for this course.");
                String continueInput = studentRegistrationManagementUI.continuesearchregStudent();
                if (!continueInput.equalsIgnoreCase("Y") && !continueInput.equalsIgnoreCase("y")) {
                    break;
                }
                continue;
            }

            displayAllStudents();

            String studentIdToCheck = studentRegistrationManagementUI.regstudentid();
            if (studentIdToCheck.equals("-1")) {
                break;
            }
            boolean isRegistered = false;

            // Check if the student is registered for the selected course
            for (RegisterCourse regCourse : registeredStudents) {
                if (regCourse.getStudent().getId().equals(studentIdToCheck)) {
                    isRegistered = true;
                    break;
                }
            }

            if (isRegistered) {
                System.out.println("Student with ID " + studentIdToCheck + " is registered for Course ID " + courseId + ".");
            } else {
                System.out.println("Student with ID " + studentIdToCheck + " is not registered for Course ID " + courseId + ".");
            }
            String continueInput = studentRegistrationManagementUI.continuesearchregStudent();
            if (!continueInput.equalsIgnoreCase("Y") && !continueInput.equalsIgnoreCase("y")) {
                break;
            }

        } while (true);
    }

    private Course selectCourseFromListforremove(String courseType) {
        ListInterface<Course> allCourses = courseDAO.loadCourses();
        ListInterface<CourseDetails> allCourseDetails = courseDetailsDAO.loadCourseDetails();

        // Filtered courses by type
        ListInterface<Course> filteredCourses = new List<>(); 
        System.out.println("Available " + courseType + " Courses:");
        System.out.println("");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.printf("%1s %-10s %1s %-40s %1s %-10s %1s %-15s %1s %-10s %1s %n", "|", "Course ID", "|", "Course Name", "|", "Credits", "|", "Course Type", "|", "Fees (RM)", "|");
        System.out.println("-----------------------------------------------------------------------------------------------------");

        boolean coursesAvailable = false;
        for (CourseDetails details : allCourseDetails) {
            if (details.getCourseType().trim().equalsIgnoreCase(courseType.trim())) {
                Course course = courseDAO.getCourseById(details.getCourseId());
                if (course != null) {
                    filteredCourses.add(course);
                    System.out.printf("%1s %-10s %1s %-40s %1s %-10s %1s %-15s %1s %-10.2f %1s %n", "|", course.getCourseID(), "|", course.getCourseName(), "|", course.getCredits(), "|", details.getCourseType(), "|", details.getCourseFees(), "|");
                    coursesAvailable = true;
                }
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------------------");

        if (!coursesAvailable) {
            System.out.println("No " + courseType + " courses available.");
            return null;
        }

        // Prompt for Course ID input
        System.out.println("Enter the Course ID of the " + courseType + " course you want to remove:");
        String courseId = studentRegistrationManagementUI.inputCourseID();

        // Find the selected course from the filtered list
        for (Course course : filteredCourses) {
            if (course.getCourseID().equalsIgnoreCase(courseId)) {
                return course;
            }
        }

        System.out.println("Course not found. Please ensure you've entered a Course ID from the list above.");
        return null;
    }

    public void removeCourseforStudent() {
        do {
            studentRegistrationManagementUI.removestudentcoursePage();
            int courseTypeChoice = studentRegistrationManagementUI.removeregisterCourseDetails();
            if (courseTypeChoice == -1) {
                break;
            }
            Course course = null;
            switch (courseTypeChoice) {
                case 1:
                    course = selectCourseFromListforremove("Main");
                    break;
                case 2:
                    course = selectCourseFromListforremove("Elective");
                    break;
                case 3:
                    course = selectCourseFromListforremove("Resit");
                    break;
                case 4:
                    course = selectCourseFromListforremove("Repeat");
                    break;
                default:
                    System.out.println("Invalid option.");
                    return;
            }

            if (course == null) {
                System.out.println("No course selected.");
                System.out.println("");
                continue; // Prompt the user again if no course is selected
            }

            // Step 1: Get registered students for the selected course
            ListInterface<RegisterCourse> registeredStudents = new List<>();
            for (RegisterCourse regCourse : RegisterCourseList) {
                if (regCourse.getCourse().getCourseID().equals(course.getCourseID())) {
                    registeredStudents.add(regCourse);
                }
            }

            if (registeredStudents.isEmpty()) {
                System.out.println("No students registered for this course.");

                String option = studentRegistrationManagementUI.inputContinue();

                if (option.equals("Y")) {
                    continue;

                } else {
                    // if type wrong and N then cancel loop
                    break;
                }
            }

            // Display students registered for this course
            System.out.println("Registered students for " + course.getCourseName() + ":");
            System.out.println("-------------------------------------");
            System.out.printf("%-1s %-15s %-1s %-15s %-1s %n", "|", "ID", "|", "Name", "|");
            System.out.println("-------------------------------------");

            for (RegisterCourse regCourse : registeredStudents) {
                System.out.printf("%-1s %-15s %-1s %-15s %-1s %n", "|", regCourse.getStudent().getId(), "|", regCourse.getStudent().getName(), "|");
            }
            System.out.println("-------------------------------------");
            // Step 2: Input student ID to remove
            String studentIdToRemove = studentRegistrationManagementUI.RemoveCourse();
            if (studentIdToRemove.equals("-1")) {
                break;
            }

            // Step 3: Remove the student from the registered course list
            boolean removed = false;
            for (int i = 0; i < RegisterCourseList.size(); i++) {
                RegisterCourse regCourse = RegisterCourseList.get(i);
                if (regCourse.getCourse().getCourseID().equals(course.getCourseID()) && regCourse.getStudent().getId().equals(studentIdToRemove)) {
                    removed = RegisterCourseList.remove(regCourse);
                    break; // Assuming a student can only register once for the same course
                }
            }

            if (removed) {
                System.out.println("Student with ID " + studentIdToRemove + " removed from the course.");
                RegisterCourseDAO.updateRegisterCourseFile(RegisterCourseList); // Persist changes
            } else {
                System.out.println("Student with ID " + studentIdToRemove + " not found in the course registration list.");
            }

            String continueInput = studentRegistrationManagementUI.continueRemoveCourse();
            if (!continueInput.equalsIgnoreCase("Y")) {
                break;
            }
        } while (true);
    }

    public void calculateTotalCourseFee() {
        do {
            studentRegistrationManagementUI.calstudentfeePage();
            displayAllStudents(); // Assuming this method displays all students
            String studentId = studentRegistrationManagementUI.calculateregistercourseFee(); // UI method to get student ID
            if (studentId.equals("-1")) {
                break;
            }
            Student student = studentsMap.get(studentId); // Lookup the student by ID

            if (student == null) {
                System.out.println("Student with ID " + studentId + " not found.");
                return;
            }

            ListInterface<RegisterCourse> registeredCourses = new List<>();
            double totalFee = 0.0;

            for (RegisterCourse regCourse : RegisterCourseList) {
                if (regCourse.getStudent().getId().equals(studentId)) {
                    registeredCourses.add(regCourse);

                    CourseDetails courseDetails = courseDetailsDAO.findCourseDetailsByCourseId(regCourse.getCourse().getCourseID());
                    if (courseDetails != null) {
                        totalFee += courseDetails.getCourseFees(); // Use CourseDetails to get the fees
                    }
                }
            }

            // Display Student Information
            System.out.println("\nStudent Information: ");
            System.out.println("Student ID: " + student.getId());
            System.out.println("Student Name: " + student.getName());
            System.out.println("\nRegistered Courses and Fees:");

            // Table header
            System.out.println("-------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-1s %-15s %-1s %-45s %-1s %-15s %-1s %-15s %-1s %-15s %-1s %n", "|", "Course ID", "|", "Course Name", "|", "Course Type", "|", "Credit Hour", "|", "Course Fee (RM)", "|");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------");

            // Display each registered course and its details
            for (RegisterCourse regCourse : registeredCourses) {
                Course course = regCourse.getCourse();
                CourseDetails details = courseDetailsDAO.findCourseDetailsByCourseId(course.getCourseID()); // Fetch details

                System.out.printf("%-1s %-15s %-1s %-45s %-1s %-15s %-1s %-15s %-1s %15.2f %-1s %n",
                        "|", course.getCourseID(), "|", course.getCourseName(), "|",
                        (details != null ? details.getCourseType() : "N/A"), "|",
                        course.getCredits(), "|",
                        (details != null ? details.getCourseFees() : 0.0), "|");
            }
            System.out.println("-------------------------------------------------------------------------------------------------------------------------");

            // Display total fees
            System.out.printf("%-1s %-99s %-1S %15.2f %-1s %n", "|", "Total fee", "|", totalFee, "|");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------");
            String continueInput = studentRegistrationManagementUI.continuecalStudent();
            if (!continueInput.equalsIgnoreCase("Y") && !continueInput.equalsIgnoreCase("y")) {
                break;
            }
        } while (true);
    }

    public void filterStudents() {
        do {
            studentRegistrationManagementUI.FILTERPage();
            int choice = studentRegistrationManagementUI.getFilterChoice();
            if (choice == -1) {
                return;
            }
            switch (choice) {
                case 1:
                    filterstudentscourseType();
                    break;
                case 2:
                    FilterStudentsForCreditHours();
                    break;

                default:
                    System.out.println("Invalid option.");
                    break;
            }
            String continueInput = studentRegistrationManagementUI.continuecalfilter();
            if (!continueInput.equalsIgnoreCase("Y") && !continueInput.equalsIgnoreCase("y")) {
                break;
            }
        } while (true);
    }

    public void filterstudentscourseType() {
        int choice = studentRegistrationManagementUI.filterstudentscourseType();
        if (choice == -1) {
            return;
        }
        switch (choice) {
            case 1:
                filterStudentsByCourseType("Main");
                break;
            case 2:
                filterStudentsByCourseType("Elective");
                break;
            case 3:
                filterStudentsByCourseType("Resit");
                break;
            case 4:
                filterStudentsByCourseType("Repeat");
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    private void filterStudentsByCourseType(String courseType) {
        // Retrieve all courses

        ListInterface<Course> allCourses = courseDAO.loadCourses();
        ListInterface<CourseDetails> allCourseDetails = courseDetailsDAO.loadCourseDetails();

        ListInterface<Course> filteredCourses = new List<>();
        System.out.println("Available " + courseType + " Students:");

        // Step 1: Iterate through all courses and filter based on courseType
        for (CourseDetails details : allCourseDetails) {
            if (details.getCourseType().trim().equalsIgnoreCase(courseType.trim())) {
                Course course = courseDAO.getCourseById(details.getCourseId());
                if (course != null) {
                    filteredCourses.add(course);
                }
            }
        }

        // Step 2: Iterate through filtered courses and display registered students
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.printf("%1s %-10s %1s %-40s %1s %-10s %1s %-15s %1s %-10s %1s %n", "|", "Course ID", "|", "Course Name", "|", "Credits", "|", "Student ID", "|", "Name", "|");
        System.out.println("-----------------------------------------------------------------------------------------------------");

        for (Course course : filteredCourses) {
            // Step 2a: Get registered students for the selected course
            ListInterface<RegisterCourse> registeredStudents = new List<>();
            for (RegisterCourse regCourse : RegisterCourseList) {
                if (regCourse.getCourse().getCourseID().equals(course.getCourseID())) {
                    registeredStudents.add(regCourse);
                }
            }

            // Step 2b: Display registered students for the selected course
            if (!registeredStudents.isEmpty()) {

                // Iterate through registered students and display their information
                for (RegisterCourse regCourse : registeredStudents) {
                    Student student = regCourse.getStudent();
                    System.out.printf("%1s %-10s %1s %-40s %1s %-10s %1s %-15s %1s %-10s %1s %n",
                            "|", course.getCourseID(), "|", course.getCourseName(), "|", course.getCredits(),
                            "|", student.getId(), "|", student.getName(), "|");
                }

            } else {
                System.out.printf("%1s %-10s %1s %-40s %1s %-10s %1s %-15s %1s %-10s %1s %n",
                        "|", course.getCourseID(), "|", course.getCourseName(), "|", course.getCredits(),
                        "|", "no", "|", "no", "|");

            }

        }
        System.out.println("-----------------------------------------------------------------------------------------------------");

    }

    public void FilterStudentsForCreditHours() {
        int choice = studentRegistrationManagementUI.FilterStudentsForCreditHours();
        if (choice == -1) {
            return;
        }
        switch (choice) {
            case 1:
                filterStudentsByCreditHours(3);
                break;
            case 2:
                filterStudentsByCreditHours(4);
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    private void filterStudentsByCreditHours(int creditHours) {
        // Retrieve all courses

        ListInterface<Course> allCourses = courseDAO.loadCourses();
        ListInterface<CourseDetails> allCourseDetails = courseDetailsDAO.loadCourseDetails();

        ListInterface<Course> filteredCourses = new List<>();
        System.out.println("Available with " + creditHours + " Students:");

        // Step 1: Iterate through all courses and filter based on credit hours
        for (Course course : allCourses) {
            if (course.getCredits() == creditHours) {
                filteredCourses.add(course);
            }
        }

        // Step 2: Iterate through filtered courses and display registered students
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.printf("%1s %-10s %1s %-40s %1s %-10s %1s %-15s %1s %-10s %1s %n", "|", "Course ID", "|", "Course Name", "|", "Credits", "|", "Student ID", "|", "Name", "|");
        System.out.println("-----------------------------------------------------------------------------------------------------");

        for (Course course : filteredCourses) {
            // Step 2a: Get registered students for the selected course
            ListInterface<RegisterCourse> registeredStudents = new List<>();
            for (RegisterCourse regCourse : RegisterCourseList) {
                if (regCourse.getCourse().getCourseID().equals(course.getCourseID())) {
                    registeredStudents.add(regCourse);
                }
            }

            // Step 2b: Display registered students for the selected course
            if (!registeredStudents.isEmpty()) {
                // Iterate through registered students and display their information
                for (RegisterCourse regCourse : registeredStudents) {
                    Student student = regCourse.getStudent();
                    System.out.printf("%1s %-10s %1s %-40s %1s %-10s %1s %-15s %1s %-10s %1s %n",
                            "|", course.getCourseID(), "|", course.getCourseName(), "|", course.getCredits(),
                            "|", student.getId(), "|", student.getName(), "|");
                }
            } else {
                // Print message if no students are registered for the course
                System.out.printf("%1s %-10s %1s %-40s %1s %-10s %1s %-15s %1s %-10s %1s %n",
                        "|", course.getCourseID(), "|", course.getCourseName(), "|", course.getCredits(),
                        "|", "no", "|", "no", "|");

            }
        }
        System.out.println("-----------------------------------------------------------------------------------------------------");

    }

    public void summaryReport1() {
        MapInterface<String, Double> studentTotalFees = new Map<>();
        MapInterface<String, Integer> studentCourseCount = new Map<>();

        // Iterate over all registered courses to accumulate fees and count courses per student
        for (RegisterCourse regCourse : RegisterCourseList) {
            Course course = regCourse.getCourse();
            CourseDetails courseDetails = courseDetailsDAO.findCourseDetailsByCourseId(course.getCourseID());

            if (courseDetails != null) {
                Student student = regCourse.getStudent();
                String studentId = student.getId();
                double courseFee = courseDetails.getCourseFees();

                // Accumulate total fees for each student
                if (studentTotalFees.containsKey(studentId)) {
                    studentTotalFees.add(studentId, studentTotalFees.get(studentId) + courseFee);
                } else {
                    studentTotalFees.add(studentId, courseFee);
                }

                // Increment course count for each student
                if (studentCourseCount.containsKey(studentId)) {
                    studentCourseCount.add(studentId, studentCourseCount.get(studentId) + 1);
                } else {
                    studentCourseCount.add(studentId, 1);
                }
            }
        }

        // Header for the summary report
        System.out.println("==============================================================================");
        System.out.println("          TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.println("                  Student Registration Management Subsystem");
        System.out.println("");
        System.out.println("                           STUDENT SUMMARY REPORT");
        System.out.println("                          --------------------------");
        System.out.println("");

        // Display details for each student
        System.out.println("------------------------------------------------------------------------------");
        System.out.printf("%-1s %-13s %-1s %-15s %-1s %-23s %-1s %-14s %-1s %n", "|", "Students ID", "|", "Students Name", "|", "Total Register Courses", "|", "Total Fee (RM)", "|");
        System.out.println("------------------------------------------------------------------------------");

        int maxCourses = Integer.MIN_VALUE;
        String maxCoursesStudentId = "";
        int minCourses = Integer.MAX_VALUE;
        String minCoursesStudentId = "";

        for (Student student : studentsList) {
            String studentId = student.getId();
            double totalFee = studentTotalFees.containsKey(studentId) ? studentTotalFees.get(studentId) : 0.0;
            int courseCount = studentCourseCount.containsKey(studentId) ? studentCourseCount.get(studentId) : 0;

            // Check for max and min course counts
            if (courseCount > maxCourses) {
                maxCourses = courseCount;
                maxCoursesStudentId = studentId;
            }
            if (courseCount < minCourses) {
                minCourses = courseCount;
                minCoursesStudentId = studentId;
            }

            System.out.printf("%-1s %-13s %-1s %-15s %-1s %-23s %-1s %14.2f %-1s %n", "|", studentId, "|", student.getName(), "|", courseCount, "|", totalFee, "|");
        }
        System.out.println("------------------------------------------------------------------------------");

        // Calculate total fees for all students
        double totalAllStudentsFee = 0.0;
        for (Double fee : studentTotalFees.getValues()) {
            totalAllStudentsFee += fee;
        }
        System.out.println("");

        System.out.println("Total All Students Fees: RM" + totalAllStudentsFee);
        System.out.println("");

        // Display student with max and min courses
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("");

        if (maxCoursesStudentId != null) {
            System.out.println("HIGHEST REGISTRATIONS STUDENT: ");
            Student maxCoursesStudent = findStudentById(maxCoursesStudentId);
            System.out.println("->   [ " + maxCourses + " ] COURSES     < " + maxCoursesStudentId + ">     " + maxCoursesStudent.getName());
            System.out.println("");
        } else {
            System.out.println("->   No registrations available.");
        }

        if (minCoursesStudentId != null) {
            System.out.println("LOWEST REGISTRATIONS STUDENT: ");
            Student minCoursesStudent = findStudentById(minCoursesStudentId);
            System.out.println("->   [ " + minCourses + " ] COURSES     < " + minCoursesStudentId + ">     " + minCoursesStudent.getName());
            System.out.println("");
        } else {
            System.out.println("->   No registrations available.");
        }

        System.out.println("------------------------------------------------------------------------------");

        studentRegistrationManagementUI.summaryreportkey();
    }

    public void summaryReport2() {
        int minAge = studentRegistrationManagementUI.inputMinAge();
        int maxAge = studentRegistrationManagementUI.inputMaxAge();
        // Header for the summary report
        System.out.println("================================================================");
        System.out.println("   TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.println("           Student Registration Management Subsystem");
        System.out.println("");
        System.out.println("                   STUDENT SUMMARY REPORT");
        System.out.println("                  --------------------------");
        System.out.println("");
        System.out.println("Students with age between " + minAge + " and " + maxAge + ":");
        System.out.println("");
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-1s %-15s %-1s %-15s %-1s %-10s %-1s %-10s %-1s %n", "|", "ID", "|", "Name", "|", "Age", "|", "Gender", "|");
        System.out.println("---------------------------------------------------------------");

        int totalStudents = 0;
        for (int i = 0; i < studentsList.size(); i++) {
            Student student = studentsList.get(i);
            int age = student.getAge();
            if (age >= minAge && age <= maxAge) {
                System.out.printf("%-1s %-15s %-1s %-15s %-1s %-10s %-1s %-10s %-1s %n", "|", student.getId(), "|", student.getName(), "|", student.getAge(), "|", student.getGender(), "|");
                totalStudents++;
            }
        }
        System.out.println("---------------------------------------------------------------");
        System.out.println("");

        System.out.println("Total students with age between " + minAge + " and " + maxAge + ": " + totalStudents);
        System.out.println("");
        System.out.println("---------------------------------------------------------------");

        studentRegistrationManagementUI.summaryreportkey();

    }

    private Student findStudentById(String studentId) {
        for (Student student : studentsList) {
            if (student.getId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

}
