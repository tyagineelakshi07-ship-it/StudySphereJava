
package ToDoList;

import Database.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UpdateTask {

    private Scanner sc;
    private int userId;

    public UpdateTask(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void update() {

        System.out.println("\n========== UPDATE TASK ==========");

        try {
            System.out.print("Enter Task ID: ");
            int taskId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter New Title: ");
            String title = sc.nextLine().trim();

            System.out.print("Enter New Description: ");
            String description = sc.nextLine().trim();

            System.out.print("Enter New Due Date (YYYY-MM-DD): ");
            LocalDate dueDate = LocalDate.parse(sc.nextLine().trim());

            System.out.print("Enter Priority (Low/Medium/High): ");
            String priority = sc.nextLine().trim();

            System.out.print("Enter Status (Pending/In Progress/Completed): ");
            String status = sc.nextLine().trim();

            if (title.isEmpty()) {
                System.out.println("Title cannot be empty!");
                return;
            }

            if (!priority.equalsIgnoreCase("Low")
                    && !priority.equalsIgnoreCase("Medium")
                    && !priority.equalsIgnoreCase("High")) {
                System.out.println("Invalid priority!");
                return;
            }

            if (!status.equalsIgnoreCase("Pending")
                    && !status.equalsIgnoreCase("In Progress")
                    && !status.equalsIgnoreCase("Completed")) {
                System.out.println("Invalid status!");
                return;
            }

            String sql = "UPDATE Tasks SET title = ?, description = ?, " +
                    "due_date = ?, priority = ?, status = ? " +
                    "WHERE task_id = ? AND user_id = ?";

            try (Connection con = DBConnection.getConnection()) {

                if (con == null) {
                    System.out.println("Database connection failed!");
                    return;
                }

                try (PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setString(1, title);
                    ps.setString(2, description);
                    ps.setDate(3, Date.valueOf(dueDate));

                    ps.setString(4, priority.substring(0, 1).toUpperCase()
                            + priority.substring(1).toLowerCase());

                    String formattedStatus =
                            status.equalsIgnoreCase("In Progress")
                            ? "In Progress"
                            : status.substring(0, 1).toUpperCase()
                                + status.substring(1).toLowerCase();

                    ps.setString(5, formattedStatus);
                    ps.setInt(6, taskId);
                    ps.setInt(7, userId);

                    int rows = ps.executeUpdate();

                    if (rows > 0) {
                        System.out.println("Task updated successfully!");
                    } else {
                        System.out.println("Task not found!");
                    }
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid Task ID!");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date! Use YYYY-MM-DD.");
        } catch (SQLException e) {
            System.out.println("Error updating task: " + e.getMessage());
        }
    }
}

