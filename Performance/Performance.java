
package Performance;

import java.util.Scanner;

public class Performance {

    private Scanner sc;
    private int userId;

    public Performance(Scanner sc) {
        this(sc, 1);
    }

    public Performance(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void showMenu() {

        int choice;

        do {
            System.out.println("\n========== PERFORMANCE MANAGEMENT ==========");
            System.out.println("1. Add Performance");
            System.out.println("2. View Performance");
            System.out.println("3. Search Performance");
            System.out.println("4. Update Performance");
            System.out.println("5. Delete Performance");
            System.out.println("6. Performance Report");
            System.out.println("0. Back to Main Menu");

            System.out.print("\nEnter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        new AddPerformance(sc, userId).add();
                        break;

                    case 2:
                        new ViewPerformance(userId).show();
                        break;

                    case 3:
                        new SearchPerformance(sc, userId).search();
                        break;

                    case 4:
                        new UpdatePerformance(sc, userId).update();
                        break;

                    case 5:
                        new DeletePerformance(sc, userId).delete();
                        break;

                    case 6:
                        new PerformanceReport(userId).show();
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
