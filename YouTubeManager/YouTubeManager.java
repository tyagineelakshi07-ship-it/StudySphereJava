
package YouTubeManager;

import java.util.Scanner;

public class YouTubeManager {

    private Scanner sc;
    private int userId;

    public YouTubeManager(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void showMenu() {

        int choice = -1;

        do {
            System.out.println("\n========== YOUTUBE MANAGER ==========");
            System.out.println("1. Add New Video");
            System.out.println("2. View Saved Videos");
            System.out.println("3. Update Video");
            System.out.println("4. Delete Video");
            System.out.println("0. Back to Main Menu");

            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        new AddVideo(sc, userId).show();
                        break;

                    case 2:
                        new ViewVideos(userId).show();
                        break;

                    case 3:
                        new UpdateVideo(sc, userId).show();
                        break;

                    case 4:
                        new DeleteVideo(sc, userId).show();
                        break;

                    case 0:
                        System.out.println("Returning to Main Menu...");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number!");
            }

        } while (choice != 0);
    }
}
