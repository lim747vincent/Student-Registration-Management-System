package util;

public class MessageUI {

    public static void displayExitMessage() {
        System.out.println("\nExiting system");
    }

    public static void displaySuccessMessage() {
        System.out.println("\nAction successfully completed");
    }

    public static void displayRepeatActionMessage() {
        System.out.println("\nRestarting action.");
    }

    public static void displayFailMessage() {
        System.out.println("\nAction fail to complete");
    }

    public static void displayRepeatedIdMessage() {
        System.out.println("\nRepeated ID exist. Please insert unique ID");
    }

    public static void displayBackMessage() {
        System.out.println("\nBack to previous action.");
    }

    public static void displayInvalidInputMessage() {
        System.out.println("\nInvalid input. Please enter a valid input.");
    }

    public static void displayExitOperationMessage() {
        System.out.println("\nExiting this operation.");
    }
}
