
package Pomodoro;

import java.util.Scanner;

public class Pomodoro {

    private Scanner sc;
    private int userId;

    public Pomodoro(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void showMenu() {

        int choice;

        do {
            System.out.println("\n========== POMODORO TIMER ==========");
            System.out.println("1. Start New Session");
            System.out.println("2. View Session History");
            System.out.println("3. Update Running Session");
            System.out.println("4. Pomodoro Report");
            System.out.println("0. Back to Main Menu");

            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        new StartSession(sc, userId).show();
                        break;

                    case 2:
                        new SessionHistory(userId).show();
                        break;

                    case 3:
                        new UpdateSession(sc, userId).show();
                        break;

                    case 4:
                        new PomodoroReport(userId).show();
                        break;

                    case 0:
                        System.out.println("Returning to Main Menu...");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
                choice = -1;
            }

        } while (choice != 0);
    }
}
