package control;

import boundary.MainUI;
import java.util.InputMismatchException;
import util.*;

/**
 *
 * @author LIM CHEE LEONG
 */
public class Main {

    private final MainUI mainUI = new MainUI();
    private final TutorialGroupManagement tutorialGroupManagement = new TutorialGroupManagement();
    private final StudentRegistrationManagement studentRegistrationManagement = new StudentRegistrationManagement();
    private final TeachingAssignmentManagement teachingAssignmentManagement = new TeachingAssignmentManagement();
    private final CourseManagementControl courseManagement = new CourseManagementControl();

    public void runMainUI() {

        int choice = 0;
        boolean isLoopMenu = false;

        do {
            isLoopMenu = false;

            try {
                choice = mainUI.getMenuChoice();

                switch (choice) {
                    case 0:
                        MessageUI.displayExitMessage();
                        System.exit(0);
                        break;
                    case 1:
                        studentRegistrationManagement.runStudentRegistrationManagement();
                        break;
                    case 2:
                        courseManagement.runCourseManagementMenu();
                        break;
                    case 3:
                        tutorialGroupManagement.runTutorialGroupManagementUI();
                        break;
                    case 4:
                        teachingAssignmentManagement.runTeachingAssignmentMenuUI();
                        break;
                    default:
                        MessageUI.displayInvalidInputMessage();
                        break;
                }
            } catch (InputMismatchException e) {
                MessageUI.displayInvalidInputMessage();
                isLoopMenu = true;
                mainUI.clearInput();

            }

        } while (choice != 0 || isLoopMenu);
    }

//    main
    public static void main(String[] args) {
        Main main = new Main();

        main.runMainUI();

    }

}
