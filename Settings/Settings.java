
package Settings;

import java.util.Scanner;

public class Settings {

    private Scanner sc;
    private int userId;

    public Settings(Scanner sc) {
        this(sc, 1);
    }

    public Settings(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void showMenu() {

        int choice;

        do {

            System.out.println("\n========== SETTINGS ==========");
            System.out.println("1. Change Username");
            System.out.println("2. Login");
            System.out.println("3. Logout");
            System.out.println("4. Study Goal");
            System.out.println("5. View Settings");
            System.out.println("0. Back to Main Menu");

            System.out.print("\nEnter your choice: ");

            try {

                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        new ChangeUsername(sc, userId).change();
                        break;

                    case 2:
                        new Login(sc).login();
                        break;

                    case 3:
                        new Logout(sc).logout();
                        break;

                    case 4:
                        new StudyGoal(sc, userId).update();
                        break;

                    case 5:
                        new ViewSettings(userId).show();
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
