package dao;

import adt.*;
import entity.Course;
import entity.Student;
import entity.Tutor;
import entity.TutorialGroup;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TutorDAO {

    private final String FILEPATH = ".\\src\\file\\Tutor.txt"; // File path for storing tutor data

    public TutorDAO() {
        // Ensure the file exists
        try {
            Path path = Paths.get(FILEPATH);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            System.err.println("Failed to create tutor file: " + e.getMessage());
        }
    }

    public void saveTutor(Tutor tutor) {
        ListInterface<Tutor> tutors = loadTutors(); // Load existing tutors from the file
        int existingTutorIndex = -1;

        // Check if the tutor already exists in the list and find its index
        for (int i = 0; i < tutors.size(); i++) {
            if (tutors.get(i).getTutorID().equals(tutor.getTutorID())) {
                existingTutorIndex = i;
                break;
            }
        }

        if (existingTutorIndex != -1) {
            tutors.remove(tutors.get(existingTutorIndex)); // Remove the existing tutor
        }
        tutors.add(tutor); // Add new or updated tutor

        // Rewrite the updated tutor list back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILEPATH, true))) { // false to overwrite
            for (int i = 0; i < tutors.size(); i++) {
                Tutor t = tutors.get(i);
                String tutorStr = String.join(";", t.getTutorID(), t.getTutorName(), t.getQualification(), t.getPosition(), t.getFaculty());
                bw.write(tutorStr + ";");
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.err.println("Failed to save the tutor: " + e.getMessage());
        }
    }

    public void saveTutorCourse(String tutorID, String tutorName, String courseID, String courseName, String classType) {
        try (FileWriter writer = new FileWriter(".\\src\\file\\TutorCourse.txt", true)) {
            // Append new tutor-course mapping to the file
            writer.write("\n" + tutorID + ";" + tutorName + ";" + courseID + ";" + courseName + ";" + classType + ";");

            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void saveTutorTutorialCourse(String tutorID, String tutorName, String programmeID, String tutorialID, String courseID, String courseName) {
        try (FileWriter writer = new FileWriter(".\\src\\file\\TutorTutorialCourse.txt", true)) {
            // Append new tutor-course mapping to the file
            writer.write("\n" + tutorID + ";" + tutorName + ";" + programmeID + ";" + tutorialID + ";" + courseID + ";" + courseName + ";");
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public ListInterface<Tutor> loadTutors() {
        ListInterface<Tutor> tutors = new List<>(); // Use your custom List ADT
        // Read the courses.txt file and deserialize each line into a Course object
        try (BufferedReader br = new BufferedReader(new FileReader(FILEPATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) { // Ensure that there are exactly three parts for ID, name, and credits
                    String tutorID = parts[1];
                    String tutorName = parts[0];
                    String qualification = parts[2];
                    String position = parts[3];
                    String faculty = parts[4];
                    tutors.add(new Tutor(tutorID, tutorName, qualification, position, faculty));
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load tutors: " + e.getMessage());
        }
        return tutors;
    }

    private String tutorToString(Tutor tutor) {
        // Serialize tutor object to a string
        return String.join(";", tutor.getTutorID(), tutor.getTutorName(), tutor.getQualification(), tutor.getPosition(), tutor.getFaculty());
    }

    private Tutor stringToTutor(String line) {
        // Deserialize string to a Tutor object
        String[] parts = line.split(";");
        if (parts.length == 5) {
            String tutorID = parts[1].trim();
            String tutorName = parts[0].trim();
            String tutorQualification = parts[2].trim();
            String tutorPosition = parts[3].trim();
            String tutorFaculty = parts[4].trim();
            return new Tutor(tutorID, tutorName, tutorQualification, tutorPosition, tutorFaculty);
        } else {

            return null;
        }
    }

    public Tutor getTutorId(String tutorId) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILEPATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                Tutor tutor = stringToTutor(line);
                if (tutor != null && tutor.getTutorID().equalsIgnoreCase(tutorId)) {
                    return tutor;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return null; // Return null if tutor with the given ID is not found
    }

    public static ListInterface<String> getRelatedCoursesForTutorial(String programmeId) {
        ListInterface<String> relatedCourses = new List<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(".\\src\\file\\programme_course_associations.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2 && parts[0].equals(programmeId)) {
                    relatedCourses.add(parts[1]); // Add the course ID associated with the tutorial
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return relatedCourses;
    }

    private Course stringToCourse(String line) {
        String[] parts = line.split(";");
        if (parts.length >= 2) {
            String courseId = parts[0].trim(); // Trim to remove any leading or trailing whitespace
            String courseName = parts[1].trim();
            String classType = parts[2].trim();// Trim to remove any leading or trailing whitespace
            return new Course(courseId, courseName, classType);
        } else {
            // Handle the case where the input string does not contain both course ID and course name
            return null; // or throw an exception, depending on your application logic
        }
    }

    public static ListInterface<String> getTutorCourses(String tutorId) {
        ListInterface<String> tutorCourses = new List<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(".\\src\\file\\TutorCourse.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5 && parts[0].equals(tutorId)) {
                    tutorCourses.add(parts[2]); // Add the course ID assigned to the tutor
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tutorCourses;
    }

    public static ListInterface<String> getTutorTutorial(String tutorId, String programmeId, String tutorialId) {
        ListInterface<String> tutorTutorials = new List<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(".\\src\\file\\TutorialTutors.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4 && parts[0].equals(programmeId) && parts[1].equals(tutorialId) && parts[2].equals(tutorId)) {
                    tutorTutorials.add(parts[1]); // Add the course ID assigned to the tutor
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tutorTutorials;
    }

    public static ListInterface<String> getTutorTutorialCourse(String tutorId, String programmeId, String tutorialId) {
        ListInterface<String> tutorTutorials = new List<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(".\\src\\file\\TutorialTutors.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4 && parts[0].equals(programmeId) && parts[1].equals(tutorialId) && parts[2].equals(tutorId)) {
                    tutorTutorials.add(parts[1]); // Add the course ID assigned to the tutor
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tutorTutorials;
    }

    public static ListInterface<String[]> checkTutorCourse(String tutorId) {
        BufferedReader br = null;
        ListInterface<String[]> tutorCourses = new List<>();
        try {
            br = new BufferedReader(new FileReader(".\\src\\file\\TutorCourse.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5 && parts[0].equals(tutorId)) {
                    String[] courseInfo = {parts[2], parts[4]}; // Course ID and class type
                    tutorCourses.add(courseInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tutorCourses;
    }

    public static ListInterface<String> getTutorsAssignedToCourse(String courseId) {
        ListInterface<String> tutors = new List<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(".\\src\\file\\TutorCourse.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5 && parts[2].equals(courseId)) {
                    tutors.add(parts[0]); // Add tutor ID assigned to the course
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tutors;
    }

    public static ListInterface<String> getTutorialAssignedToTutor(String tutorId, String courseId, String programmeId, String tutorialId) {
        ListInterface<String> tutorial = new List<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(".\\src\\file\\TutorTutorialCourse.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 6 && parts[0].equals(tutorId) && parts[4].equals(courseId) && parts[2].equals(programmeId) && parts[3].equals(tutorialId)) {
                    tutorial.add(parts[3].trim()); // Add tutorial ID assigned to the tutor
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tutorial;
    }

    public static MapInterface<String, ListInterface<String>> readTutorCourses(String filePath) {
        MapInterface<String, ListInterface<String>> tutorCoursesMap = new Map<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) { // Assuming the file structure is courseID, courseName, tutorID, tutorName, classType
                    String tutorID = parts[0];
                    String courseID = parts[2];
                    String courseName = parts[3];
                    String classType = parts[4];

                    if (!tutorCoursesMap.containsKey(tutorID)) {
                        tutorCoursesMap.add(tutorID, new List<>());
                    }
                    tutorCoursesMap.get(tutorID).add(courseID + " " + courseName + " [" + classType + "]");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tutorCoursesMap;
    }

    public static MapInterface<String, String> readTutorCourses2(String filePath) {
        MapInterface<String, String> courseMap = new Map<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) { // Assuming the file structure is tutorID, tutorName, courseID, courseName, classType
                    String courseID = parts[2];
                    String courseName = parts[3];

                    // Store the course ID and course name directly in the map
                    courseMap.add(courseID, courseName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return courseMap;
    }

    public static MapInterface<String, ListInterface<String>> readTutorTutorialCourseFile(String filePath) {
        MapInterface<String, ListInterface<String>> courseTutorMap = new Map<>();
        MapInterface<String, String> courseNameMap = new Map<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 6) {
                    String courseID = parts[3];
                    String courseName = parts[4];
                    String tutorID = parts[0];
                    String tutorName = parts[1];
                    String tutorialGroup = parts[2];

                    if (!courseTutorMap.containsKey(courseID)) {
                        courseTutorMap.add(courseID, new List<>());
                        courseNameMap.add(courseID, courseName);
                    }

                    courseTutorMap.get(courseID).add(String.format("%-14s | %-16s | %s", tutorID, tutorName, tutorialGroup));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return courseTutorMap;
    }

    public static MapInterface<String, ListInterface<String>> readTutorTutorialCourse(String filePath) {
        MapInterface<String, ListInterface<String>> courseTutorMap = new Map<>();
        MapInterface<String, String> courseNameMap = new Map<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 6) { // Assuming the file structure is courseID, courseName, classType, tutorID, tutorName, tutorialGroup
                    String courseID = parts[4];
                    String courseName = parts[5];
                    String tutorID = parts[0];
                    String tutorName = parts[1];
                    String programmeID = parts[2];
                    String tutorialID = parts[3];

                    if (!courseTutorMap.containsKey(courseID)) {
                        courseTutorMap.add(courseID, new List<>());
                        courseNameMap.add(courseID, courseName);
                    }

                    courseTutorMap.get(courseID).add(String.format("%-14s | %-16s | %-13s | %s", tutorID, tutorName, programmeID, tutorialID));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return courseTutorMap;
    }

    public static MapInterface<String, Tutor> searchTutorsForCourseByClassType(String courseID, String classType) {
        MapInterface<String, Tutor> tutorsMap = new Map<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(".\\src\\file\\TutorCourse.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 5 && parts[2].equalsIgnoreCase(courseID) && parts[4].equalsIgnoreCase(classType)) {
                    String tutorId = parts[0];
                    // Assuming Tutor constructor takes tutor ID and name
                    Tutor tutor = new Tutor(parts[0], parts[1]);
                    tutorsMap.add(tutorId, tutor);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CourseTutors.txt: " + e.getMessage());
        }

        return tutorsMap;
    }

    public static MapInterface<String, String> getTutorNamesMap(String filePath) {

        MapInterface<String, String> tutorName = new Map<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) { // Assuming the file structure is tutorID, tutorName, courseID, courseName, classType
                    String tutorID = parts[0];
                    String tutorNames = parts[1];

                    // Store the course ID and course name directly in the map
                    tutorName.add(tutorID, tutorNames);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tutorName;
    }

    public ListInterface<String> loadTutorTutorialCourse() {
        ListInterface<String> tutorTutorialCouseLines = new List<>();
        try (BufferedReader br = new BufferedReader(new FileReader(".\\src\\file\\TutorTutorialCourse.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                tutorTutorialCouseLines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Failed to load tutorialGroup: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
        return tutorTutorialCouseLines;
    }

    public void updateTutorTutorialCourseFile(String programId, String courseId) {
        ListInterface<String> tutorTutorialCouseLines = loadTutorTutorialCourse();

        File myObj = new File(".\\src\\file\\TutorTutorialCourse.txt");
        try {
            myObj.createNewFile();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        try (FileWriter myWriter = new FileWriter(".\\src\\file\\TutorTutorialCourse.txt")) {
            for (int i = 0; i < tutorTutorialCouseLines.size(); i++) {
                String line = tutorTutorialCouseLines.get(i);

                // Split the line by semicolon to get programId and courseId
                String[] parts = line.split(";");
                if (parts.length == 6) { // Ensure there are enough parts
                    String lineProgramId = parts[2]; // Assuming RSW is at index 2
                    String lineCourseId = parts[4]; // Assuming ABBE1033 is at index 4

                    // Check if the line matches the programId and courseId to be removed
                    if (!lineProgramId.equals(programId) || !lineCourseId.equals(courseId)) {
                        // Write the line to the file if it doesn't match
                        myWriter.write(line + "\n");
                    }
                }
            }

            myWriter.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
