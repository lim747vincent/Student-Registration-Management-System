/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Programme;
/**
 *
 * @author SEAH MENG KWANG
 */
public class ProgrammeInitializer {
    private ProgrammeDAO programmeDAO;

    public ProgrammeInitializer(ProgrammeDAO programmeDAO) {
        this.programmeDAO = programmeDAO;
    }

    public void initializeProgrammes() {

        Programme[] programmes = {
            new Programme("RSW", "Software Engineering", "Master's Degree"),
            new Programme("RDS", "Data Science", "Master's Degree"),
            new Programme("RIS", "Interactive Software", "Bachelor's Degree"),
            new Programme("RIT", "Information Technology", "Bachelor's Degree"),
            new Programme("RES", "Engineering Science", "Master's Degree"),
            new Programme("RSM", "Science", "Master's Degree"),
            new Programme("RMM", "Management", "Master's Degree"),
            new Programme("RBA", "Bussiness Administration", "Master's Degree"),
            new Programme("RAB", "Accounting", "Bachelor's Degree"),
            new Programme("RBF", "Banking & Finance", "Bachelor's Degree"),
            new Programme("RCM", "Creative Multimedia", "Bachelor's Degree"),
            new Programme("RFD", "Fashion Design", "Bachelor's Degree"),
            new Programme("RGD", "Graphic Design", "Bachelor's Degree"),
            new Programme("RCT", "Information Security", "Bachelor's Degree"),
            new Programme("RQS", "Quantity Surveying", "Bachelor's Degree"),
            new Programme("RIN", "Nutrition", "Bachelor's Degree")
        };

        // Iterate through the array and save each programme using ProgrammeDAO
        for (Programme programme : programmes) {
            programmeDAO.saveProgramme(programme);
        }
    }
}

