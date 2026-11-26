
package ToDoList;

import java.util.Scanner;

public class ToDoList {

    private Scanner sc;
    private int userId;

    public ToDoList(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void showMenu() {

        int choice;

        do {
            System.out.println("\n========== TO-DO LIST ==========");
            System.out.println("1. Add Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. Update Task");
            System.out.println("4. Delete Task");
            System.out.println("5. Search Task");
            System.out.println("0. Back to Main Menu");

            System.out.print("\nEnter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        new AddTask(sc, userId).add();
                        break;

                    case 2:
                        new ViewTasks(userId).show();
                        break;

                    case 3:
                        new UpdateTask(sc, userId).update();
                        break;

                    case 4:
                        new DeleteTask(sc, userId).delete();
                        break;

                    case 5:
                        new SearchTask(sc, userId).search();
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

