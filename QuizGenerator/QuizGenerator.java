
package QuizGenerator;

import java.util.Scanner;

public class QuizGenerator {

    private Scanner sc;
    private int userId;

    public QuizGenerator(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void showMenu() {

        int choice = -1;

        do {

            System.out.println("\n========== QUIZ GENERATOR ==========");
            System.out.println("1. Start Quiz");
            System.out.println("2. Quiz History");
            System.out.println("0. Back to Main Menu");

            System.out.print("\nEnter your choice: ");

            try {

                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        QuizSubjects subjects =
                                new QuizSubjects(sc, userId);
                        subjects.showSubjects();
                        break;

                    case 2:
                        QuizHistory history =
                                new QuizHistory(userId);
                        history.showHistory();
                        break;

                    case 0:
                        System.out.println(
                                "Returning to Main Menu...");
                        break;

                    default:
                        System.out.println(
                                "Invalid choice! Try again.");
                        break;
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number!");
                choice = -1;
            }

        } while (choice != 0);
    }
}
