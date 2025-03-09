/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.Map;
import adt.List;
import adt.ListInterface;
import adt.MapInterface;
import entity.Student;
import entity.TutorialGroup;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author LIM CHEE LEONG
 */
public class TutorialGroupDAO {

    private final String filePath = ".\\src\\file\\tutorialGroup.txt";

    public ListInterface<TutorialGroup> loadTutorialGroupList() {
        ListInterface<TutorialGroup> tutorialGroups = new List<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                TutorialGroup tutorialGroup = stringToTutorialGroup(line);
                if (tutorialGroup != null) {
                    tutorialGroups.add(tutorialGroup);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load tutorialGroup: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
        return tutorialGroups;
    }

    private TutorialGroup stringToTutorialGroup(String line) {
        String[] parts = line.split(";");

        if (parts.length == 4) {
            try {
                String id = parts[1].trim();
                int maxCapacity = Integer.parseInt(parts[2]);
                int numStudents = Integer.parseInt(parts[3]);

                String programmeId = parts[0].trim();

                return new TutorialGroup(id, maxCapacity, numStudents, programmeId);
            } catch (NumberFormatException e) {
                System.err.println("Failed to parse data: " + e.getMessage());
                e.printStackTrace(); // Print stack trace for debugging
                return null;
            }
        } else {
            System.err.println("Invalid data format: " + line);
            return null;
        }
    }

    public void updateTutorialGroupsFile(Student removedStudent) {
        ListInterface<TutorialGroup> tutorialGroupList = loadTutorialGroupList();

        File myObj = new File(filePath);
        try {
            myObj.createNewFile();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        try (FileWriter myWriter = new FileWriter(filePath)) {
            for (int i = 0; i < tutorialGroupList.size(); i++) {

                TutorialGroup tutorialGroup = tutorialGroupList.get(i);

                try {

                    if (tutorialGroup.getProgrammeId().equals(removedStudent.getProgrammeId()) && tutorialGroup.getId().equals(removedStudent.getTutorialGroupId())) {

                        myWriter.write(tutorialGroup.getProgrammeId() + ";");
                        myWriter.write(tutorialGroup.getId() + ";");
                        myWriter.write(tutorialGroup.getMaxCapacity() + ";");
                        int studentCounter = tutorialGroup.getNumStudents();

                        if (tutorialGroup.getNumStudents() > 0) {
                            studentCounter = studentCounter - 1;

                            String stringNum = String.valueOf(studentCounter);
                            myWriter.write(stringNum);
                        } else {

                            myWriter.write("0");
                        }

                        myWriter.write(";\n");
                    } else {

                        String stringNum = String.valueOf(tutorialGroup.getNumStudents());

                        myWriter.write(tutorialGroup.getProgrammeId() + ";");
                        myWriter.write(tutorialGroup.getId() + ";");
                        myWriter.write(tutorialGroup.getMaxCapacity() + ";");
                        myWriter.write(stringNum + ";");
                        myWriter.write("\n");
                    }

                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }

            myWriter.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void writeTutorialGroupsToFileList(ListInterface<TutorialGroup> tutorialGroups) {
        File myObj = new File(filePath);
        try {
            myObj.createNewFile();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        try (FileWriter myWriter = new FileWriter(filePath)) {
            for (int i = 0; i < tutorialGroups.size(); i++) {

                TutorialGroup tutorialGroup = tutorialGroups.get(i);

                try {

                    myWriter.write(tutorialGroup.getProgrammeId() + ";");
                    myWriter.write(tutorialGroup.getId() + ";");
                    myWriter.write(tutorialGroup.getMaxCapacity() + ";");

                    String stringNum = String.valueOf(tutorialGroup.getNumStudents());
                    myWriter.write(stringNum);
                    myWriter.write(";\n");

                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void writeTutorialGroupsToFileHashMap(MapInterface<String, TutorialGroup> tutorialGroups) {
        File myObj = new File(filePath);
        try {
            myObj.createNewFile();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        ListInterface<TutorialGroup> listTutorialGroups = tutorialGroups.getValues();

        try (FileWriter myWriter = new FileWriter(filePath)) {
            for (int i = 0; i < listTutorialGroups.size(); i++) {

                TutorialGroup tutorialGroup = listTutorialGroups.get(i);

                try {

                    myWriter.write(tutorialGroup.getProgrammeId() + ";");
                    myWriter.write(tutorialGroup.getId() + ";");
                    myWriter.write(tutorialGroup.getMaxCapacity() + ";");

                    String stringNum = String.valueOf(tutorialGroup.getNumStudents());
                    myWriter.write(stringNum);
                    myWriter.write(";\n");

                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public MapInterface<String, TutorialGroup> loadTutorialGroupsHashMap() {
        MapInterface<String, TutorialGroup> tutorialGroups = new Map<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                TutorialGroup tutorialGroup = stringToTutorialGroup(line);
                tutorialGroups.add(tutorialGroup.getId(), tutorialGroup);
            }
        } catch (IOException e) {
            System.err.println("Failed to load students: " + e.getMessage());
        }
        return tutorialGroups;
    }

    public TutorialGroup getTutorialId(String programmeId, String tutorialId) {
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.split(";");

                String id = parts[1].trim();
                String tGroupCapacity = parts[2].trim();
                String tGroupNumStudents = parts[3].trim();
                String tGroupProgrammeId = parts[0].trim();

                int numTGroupCapacity = Integer.parseInt(tGroupCapacity);
                int numTGroupNumStudents = Integer.parseInt(tGroupNumStudents);

                if (tGroupProgrammeId.equalsIgnoreCase(programmeId) && id.equalsIgnoreCase(tutorialId)) {

                    TutorialGroup tGroup = new TutorialGroup(id, numTGroupCapacity, numTGroupNumStudents, tGroupProgrammeId);

                    return tGroup;
                }
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }

        return null; // Return null if tutor with the given ID is not found
    }

    public void saveTutorialToTutor(String programmeID, String tutorialID, String tutorID, String tutorName) {
        try (FileWriter writer = new FileWriter(".\\src\\file\\TutorialTutors.txt", true)) {
            // Append new tutor-course mapping to the file
            writer.write(programmeID + ";" + tutorialID + ";" + tutorID + ";" + tutorName + ";\n");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public ListInterface<String> loadTutorialTutor() {
        ListInterface<String> tutorTutorialCouseLines = new List<>();
        try (BufferedReader br = new BufferedReader(new FileReader(".\\src\\file\\TutorialTutors.txt"))) {
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

    public void updateTutorialTutorsFile(String programmeid, String tutorialGroupId) {
        ListInterface<String> tutorTutorialCouseLines = loadTutorialTutor();

        File myObj = new File(".\\src\\file\\TutorialTutors.txt");
        try {
            myObj.createNewFile();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        try (FileWriter myWriter = new FileWriter(".\\src\\file\\TutorialTutors.txt")) {
            for (int i = 0; i < tutorTutorialCouseLines.size(); i++) {
                String line = tutorTutorialCouseLines.get(i);

                // Split the line by semicolon to get programId and courseId
                String[] parts = line.split(";");
                if (parts.length == 4) { // Ensure there are enough parts
                    String lineProgrammeId = parts[0]; // Assuming RSW is at index 0
                    String lineTutorialGroupId = parts[1]; // Assuming G1 is at index 1

                    // Check if the line matches the programId and tutorialGroupId to be removed
                    if (!lineProgrammeId.equals(programmeid) || !lineTutorialGroupId.equals(tutorialGroupId)) {
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
