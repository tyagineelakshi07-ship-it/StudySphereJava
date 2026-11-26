
package About;

import java.util.Scanner;

public class About {

    private Scanner sc;

    public About(Scanner sc) {
        this.sc = sc;
    }

    public void showMenu() {

        int choice;

        do {

            System.out.println("\n========== ABOUT STUDYSPHERE ==========");
            System.out.println("1. About Us");
            System.out.println("2. Project Information");
            System.out.println("3. Features");
            System.out.println("4. Contact Us");
            System.out.println("0. Back to Main Menu");

            System.out.print("\nEnter your choice: ");

            try {

                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        new AboutUs().show();
                        break;

                    case 2:
                        new ProjectInfo().show();
                        break;

                    case 3:
                        new Features().show();
                        break;

                    case 4:
                        new ContactUs(sc).show();
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
