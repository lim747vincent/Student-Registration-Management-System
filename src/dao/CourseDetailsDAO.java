/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author SEAH MENG KWANG
 */
import adt.List;
import adt.ListInterface;
import adt.Map;
import adt.MapInterface;
import entity.CourseDetails;
import java.io.*;

public class CourseDetailsDAO {
    private final String filePath = ".\\src\\file\\courseDetails.txt";

    public CourseDetailsDAO() {
        // Ensure the file exists
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Failed to initialize course details storage: " + e.getMessage());
        }
    }

    public void saveCourseDetails(CourseDetails details) {
        ListInterface<CourseDetails> allDetails = loadCourseDetails();
        // Check if the course detail already exists and remove it
        int index = -1;
        for (int i = 0; i < allDetails.size(); i++) {
            if (allDetails.get(i).getCourseId().equals(details.getCourseId())) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            allDetails.removeAt(index);
        }
        allDetails.add(details);
        // Save the updated list back to the file
        saveAllCourseDetails(allDetails);
    }

    private void saveAllCourseDetails(ListInterface<CourseDetails> detailsList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {  // false to overwrite
            for (CourseDetails details : detailsList) {
                bw.write(details.getCourseId() + ";" + details.getCourseType() + ";" + details.getCourseFees() + ";");
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save course details: " + e.getMessage());
        }
    }

    public ListInterface<CourseDetails> loadCourseDetails() {
        ListInterface<CourseDetails> detailsList = new List<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String id = parts[0];
                    String type = parts[1];
                    double fees = Double.parseDouble(parts[2]);
                    CourseDetails details = new CourseDetails(id, type, fees);
                    detailsList.add(details);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load course details: " + e.getMessage());
        }
        return detailsList;
    }
    
     public MapInterface<String, Integer> countCoursesByStatus() {
        ListInterface<CourseDetails> allDetails = loadCourseDetails();
        MapInterface<String, Integer> statusCounts = new Map<>();

        // Initialize counts for each status
        statusCounts.add("Main", 0);
        statusCounts.add("Elective", 0);
        statusCounts.add("Resit", 0);
        statusCounts.add("Repeat", 0);

        for (int i = 0; i < allDetails.size(); i++) {
            CourseDetails details = allDetails.get(i);
            String status = details.getCourseType().trim();
            // Check if the status already exists in the map, and directly add to update its count
            if (statusCounts.containsKey(status)) {
                int count = statusCounts.get(status);
                statusCounts.add(status, count + 1); 
            } else {
                statusCounts.add(status, 1);
            }
        }
        return statusCounts;
    }

    public int getTotalNumberOfCourses() {
        MapInterface<String, Integer> statusCounts = countCoursesByStatus();

        ListInterface<String> keys = statusCounts.getKeys(); 
        int total = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i); 
            total += statusCounts.get(key); // Sum up the counts for each course status
        }
        return total;
    }
    
    public CourseDetails findCourseDetailsByCourseId(String courseId) {
        CourseDetails foundCourseDetails = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read each line of the file
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");

                // Check if the line contains the correct number of parts and the correct courseId
                if (parts.length == 3 && parts[0].trim().equals(courseId)) {
                    String id = parts[0].trim();
                    String type = parts[1].trim();
                    double fees = Double.parseDouble(parts[2].trim());

                    // Create a new CourseDetails object and break, assuming IDs are unique
                    foundCourseDetails = new CourseDetails(id, type, fees);
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load course details: " + e.getMessage());
        }

        return foundCourseDetails;
    }
}


