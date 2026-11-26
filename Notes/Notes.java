
package Notes;

import java.util.Scanner;

public class Notes {

    private Scanner sc;
    private int userId;

    public Notes(Scanner sc) {
        this(sc, 1);
    }

    public Notes(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void showMenu() {

        int choice;

        do {

            System.out.println("\n========== NOTES MANAGEMENT ==========");
            System.out.println("1. Add Note");
            System.out.println("2. View All Notes");
            System.out.println("3. Search Note");
            System.out.println("4. Update Note");
            System.out.println("5. Delete Note");
            System.out.println("0. Back to Main Menu");

            System.out.print("\nEnter your choice: ");

            try {

                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        new AddNote(sc, userId).add();
                        break;

                    case 2:
                        new ViewNotes(userId).show();
                        break;

                    case 3:
                        new SearchNote(sc, userId).search();
                        break;

                    case 4:
                        new UpdateNote(sc, userId).update();
                        break;

                    case 5:
                        new DeleteNote(sc, userId).delete();
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
