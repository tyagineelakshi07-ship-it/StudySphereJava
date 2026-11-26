
package ToDoList;

import Database.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AddTask {

    private Scanner sc;
    private int userId;

    public AddTask(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void add() {

        System.out.println("\n========== ADD NEW TASK ==========");

        System.out.print("Enter Task Title: ");
        String title = sc.nextLine().trim();

        System.out.print("Enter Description: ");
        String description = sc.nextLine().trim();

        System.out.print("Enter Due Date (YYYY-MM-DD): ");
        String dateInput = sc.nextLine().trim();

        System.out.print("Priority (Low/Medium/High): ");
        String priority = sc.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("Task title cannot be empty!");
            return;
        }

        if (!priority.equalsIgnoreCase("Low")
                && !priority.equalsIgnoreCase("Medium")
                && !priority.equalsIgnoreCase("High")) {
            System.out.println("Invalid priority!");
            return;
        }

        try {
            LocalDate dueDate = LocalDate.parse(dateInput);

            String sql = "INSERT INTO Tasks " +
                    "(user_id, title, description, due_date, priority, status) " +
                    "VALUES (?, ?, ?, ?, ?, 'Pending')";

            try (Connection con = DBConnection.getConnection()) {

                if (con == null) {
                    System.out.println("Database connection failed!");
                    return;
                }

                try (PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setInt(1, userId);
                    ps.setString(2, title);
                    ps.setString(3, description);
                    ps.setDate(4, Date.valueOf(dueDate));
                    ps.setString(5, priority.substring(0, 1).toUpperCase()
                            + priority.substring(1).toLowerCase());

                    ps.executeUpdate();

                    System.out.println("\nTask added successfully!");
                }
            }

        } catch (DateTimeParseException e) {
            System.out.println("Invalid date! Use YYYY-MM-DD.");
        } catch (SQLException e) {
            System.out.println("Error adding task: " + e.getMessage());
        }
    }
}

