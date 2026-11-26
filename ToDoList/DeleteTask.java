
package ToDoList;

import Database.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class DeleteTask {

    private Scanner sc;
    private int userId;

    public DeleteTask(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void delete() {

        System.out.println("\n========== DELETE TASK ==========");

        try {
            System.out.print("Enter Task ID: ");
            int taskId = Integer.parseInt(sc.nextLine());

            System.out.print("Are you sure you want to delete? (Yes/No): ");
            String confirm = sc.nextLine().trim();

            if (!confirm.equalsIgnoreCase("Yes")) {
                System.out.println("Deletion cancelled!");
                return;
            }

            String sql = "DELETE FROM Tasks WHERE task_id = ? AND user_id = ?";

            try (Connection con = DBConnection.getConnection()) {

                if (con == null) {
                    System.out.println("Database connection failed!");
                    return;
                }

                try (PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setInt(1, taskId);
                    ps.setInt(2, userId);

                    int rows = ps.executeUpdate();

                    if (rows > 0) {
                        System.out.println("Task deleted successfully!");
                    } else {
                        System.out.println("Task not found!");
                    }
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid Task ID!");
        } catch (SQLException e) {
            System.out.println("Error deleting task: " + e.getMessage());
        }
    }
}
