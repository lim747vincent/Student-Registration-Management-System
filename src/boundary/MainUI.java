/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import java.util.Scanner;

/**
 *
 * @author LIM CHEE LEONG
 */
public class MainUI {

    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        int choice;

        System.out.println("");
        System.out.println("======================================");
        System.out.println("\n           Main System\n");
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("Select option below: ");
        System.out.println("1. Student Registration Management Subsystem");
        System.out.println("2. Course Management Subsystem");
        System.out.println("3. Tutorial Group Management Subsystem");
        System.out.println("4. Teaching Assignment Subsystem");
        System.out.println("0. Quit");
        System.out.println("");
        System.out.println("------------------------------------");
        System.out.println("Note:");
        System.out.println("a. Valid input must be [0 - 4]");
        System.out.println("------------------------------------");
        System.out.print("Enter your option: ");

        choice = scanner.nextInt();
        scanner.nextLine();

        System.out.println();

        return choice;
    }

    public void clearInput() {
        scanner.nextLine(); // Read and discard any remaining input
    }
}
