
package Settings;

import java.util.Scanner;

public class Logout {

    private Scanner sc;

    public Logout(Scanner sc) {
        this.sc = sc;
    }

    public void logout() {

        System.out.println("\n========== LOGOUT ==========");

        System.out.print("Are you sure you want to logout? (Yes/No): ");

        String choice = sc.nextLine().trim();

        if (choice.equalsIgnoreCase("Yes")) {

            System.out.println("\nYou have been logged out successfully!");

        } else if (choice.equalsIgnoreCase("No")) {

            System.out.println("Logout cancelled!");

        } else {

            System.out.println("Invalid choice!");
        }
    }
}
