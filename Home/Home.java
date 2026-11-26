
package Home;

import java.util.Scanner;

public class Home {

    private Scanner sc;
    private int userId;

    public Home(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void showMenu() {

        int choice;

        do {
            System.out.println("\n========== HOME MODULE ==========");
            System.out.println("1. Dashboard");
            System.out.println("2. Study Summary");
            System.out.println("3. Upcoming Tasks");
            System.out.println("4. Recent Activity");
            System.out.println("0. Back to Main Menu");

            System.out.print("Enter choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    new Dashboard(userId).show();
                    break;

                case 2:
                    new StudySummary(userId).show();
                    break;

                case 3:
                    new UpcomingTasks(userId).show();
                    break;

                case 4:
                    new RecentActivity(userId).show();
                    break;

                case 0:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 0);
    }
}

