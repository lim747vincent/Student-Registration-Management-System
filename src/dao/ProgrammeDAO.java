/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author SEAH MENG KWANG
 */
import adt.Map;
import java.io.*;
import entity.*;
import adt.List;
import adt.ListInterface;
import adt.MapInterface;

public class ProgrammeDAO {

    private final String filePath = ".\\src\\file\\programmes.txt";
    private final String associationsPath = ".\\src\\file\\programme_course_associations.txt";
    private CourseDAO courseDAO = new CourseDAO(); 
    private static ProgrammeDAO instance;

    public ProgrammeDAO() {
        try {
            new File(filePath).createNewFile(); // Ensures the programme file exists
            new File(associationsPath).createNewFile(); // Ensures the associations file exists
        } catch (IOException e) {
            System.err.println("Initialization error: " + e.getMessage());
        }
    }

    public static synchronized ProgrammeDAO getInstance() {
        if (instance == null) {
            instance = new ProgrammeDAO();
        }
        return instance;
    }

    public void saveProgramme(Programme programme) {
        ListInterface<Programme> programmes = loadProgrammes(); // Load existing programmes
        int existingProgrammeIndex = -1;

        // Check if the programme already exists
        for (int i = 0; i < programmes.size(); i++) {
            if (programme.getProgrammeId().equals(programmes.get(i).getProgrammeId())) {
                existingProgrammeIndex = i;
                break;
            }
        }

        // If exists, remove the old one
        if (existingProgrammeIndex != -1) {
            programmes.removeAt(existingProgrammeIndex);
        }

        programmes.add(programme); // Add the new/updated programme

        // Rewrite the file with updated programmes list
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) { // false to overwrite
            for (Programme prog : programmes) {
                writer.write(programmeToString(prog) + ";");
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to programme file: " + e.getMessage());
        }
    }

    public Programme getProgrammeById(String programmeId) {
        ListInterface<Programme> programmes = loadProgrammes();
        for (Programme programme : programmes) {
            if (programme.getProgrammeId().equals(programmeId)) {
                // Load the associated courses for the programme before returning it.
                loadProgrammeCourses(programme);
                return programme;
            }
        }
        return null; // return null if no programme with the given ID was found
    }

    private String programmeToString(Programme programme) {
        return programme.getProgrammeId() + ";" + programme.getProgrammeName() + ";" + programme.getDegreeLevel();
    }

    public ListInterface<Programme> loadProgrammes() {
        ListInterface<Programme> programmes = new List<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    Programme programme = new Programme(parts[0].trim(), parts[1].trim(), parts[2].trim());
                    programmes.add(programme);
                }
            }
        } catch (IOException e) {
            System.err.println("Load programmes error: " + e.getMessage());
        }
        return programmes;
    }

    public void loadProgrammeCourses(Programme programme) {
        try (BufferedReader reader = new BufferedReader(new FileReader(associationsPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2 && parts[0].trim().equals(programme.getProgrammeId())) {
                    Course course = courseDAO.getCourseById(parts[1].trim());
                    if (course != null) {
                        programme.addCourse(course);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Load programme courses error: " + e.getMessage());
        }
    }

    public void saveProgrammeCourseAssociation(String programmeId, String courseId) {
        // check if the association already exists to prevent duplicates.
        ListInterface<String> associatedProgrammes = getAssociatedProgrammeIdsForCourse(courseId);
        if (associatedProgrammes.contains(programmeId)) {
            System.out.println("Association between Programme ID " + programmeId + " and Course ID " + courseId + " already exists.");
            return; // Exit if the association already exists.
        }

        // Proceed to save the new association if it doesn't exist.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(associationsPath, true))) { // true to append
            writer.write(programmeId + ";" + courseId); // Removed the extra semicolon at the end
            writer.newLine();
            System.out.println("Association between Programme ID " + programmeId + " and Course ID " + courseId + " saved successfully.");
        } catch (IOException e) {
            System.err.println("Save association error: " + e.getMessage());
        }
    }

    public void removeProgrammeCourseAssociation(String programmeId, String courseId) {
        // Create a new empty List to hold the remaining associations
        ListInterface<String> remainingAssociations = new List<>();

        // Read the existing associations and filter out the one to remove
        try (BufferedReader reader = new BufferedReader(new FileReader(associationsPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().equals(programmeId + ";" + courseId)) { // Check if the line should be kept
                    remainingAssociations.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading associations file: " + e.getMessage());
        }

        // Rewrite the file without the removed association
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(associationsPath, false))) { // false to overwrite
            for (String association : remainingAssociations) {
                writer.write(association); // Write the association directly without adding an extra semicolon
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error rewriting associations file: " + e.getMessage());
        }
    }

    public ListInterface<String> getAssociatedProgrammeIdsForCourse(String courseId) {
        ListInterface<String> associatedProgrammeIds = new List<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(associationsPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2 && parts[1].trim().equals(courseId)) {
                    associatedProgrammeIds.add(parts[0].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the programme_course_associations file: " + e.getMessage());
        }
        return associatedProgrammeIds;
    }

    public ListInterface<String> getAssociatedCourseIDsForProgramme(String programmeId) {
        ListInterface<String> associatedCourseIds = new List<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(associationsPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2 && parts[0].trim().equals(programmeId)) {
                    associatedCourseIds.add(parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the programme_course_associations file: " + e.getMessage());
        }
        return associatedCourseIds;
    }

    public synchronized void updateCourseIdInAssociations(String oldCourseId, String newCourseId) {
        ListInterface<String> updatedAssociations = new List<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(associationsPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(oldCourseId)) {
                    line = line.replace(oldCourseId, newCourseId);
                }
                updatedAssociations.add(line.replace(",", ";")); // Ensuring no commas are left in the data
            }
        } catch (IOException e) {
            System.err.println("Error reading associations file: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(associationsPath, false))) {
            for (String association : updatedAssociations) {
                writer.write(association + ";");
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating associations file: " + e.getMessage());
        }
    }

    public ListInterface<String> getProgrammeIdsForCourse(String courseId) {
        ListInterface<String> associatedProgrammeIds = new List<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(associationsPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2 && parts[1].trim().equals(courseId)) {
                    associatedProgrammeIds.add(parts[0].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the associations file: " + e.getMessage());
        }
        return associatedProgrammeIds;
    }

    public ListInterface<Programme> getProgrammesOfferingCourse(String courseId) {
        ListInterface<String> programmeIds = getProgrammeIdsForCourse(courseId);
        ListInterface<Programme> programmesOfferingCourse = new List<>();

        for (String programmeId : programmeIds) {
            Programme programme = getProgrammeById(programmeId);
            if (programme != null) {
                programmesOfferingCourse.add(programme);
            }
        }
        return programmesOfferingCourse;
    }

    public int countProgrammesOfferingCourse(String courseId) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(associationsPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2 && parts[1].trim().equals(courseId)) {
                    count++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the associations file: " + e.getMessage());
        }
        return count;
    }

    public String getAssociationsPath() {
        return associationsPath;
    }
    
    // check if the course ID and programme ID is already associated
    public boolean isCourseAlreadyAssociated(String courseId, String programmeId) {
        ListInterface<String> associatedCourseIds = getAssociatedCourseIDsForProgramme(programmeId);
        return associatedCourseIds.contains(courseId);
    }

    public MapInterface<String, Programme> loadProgrammesHashMap() {
        MapInterface<String, Programme> programmes = new Map<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    Programme programme = new Programme(parts[0].trim(), parts[1].trim(), parts[2].trim());
                    programmes.add(programme.getProgrammeId(), programme);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load students: " + e.getMessage());
        }
        return programmes;
    }
}


