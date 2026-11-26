
package ToDoList;

import Database.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class SearchTask {

    private Scanner sc;
    private int userId;

    public SearchTask(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void search() {

        System.out.println("\n========== SEARCH TASK ==========");

        try {
            System.out.print("Enter Task ID: ");
            int taskId = Integer.parseInt(sc.nextLine());

            String sql = "SELECT * FROM Tasks " +
                    "WHERE task_id = ? AND user_id = ?";

            try (Connection con = DBConnection.getConnection()) {

                if (con == null) {
                    System.out.println("Database connection failed!");
                    return;
                }

                try (PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setInt(1, taskId);
                    ps.setInt(2, userId);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (rs.next()) {

                            System.out.println("\nTask Found!");

                            System.out.println("Task ID: " +
                                    rs.getInt("task_id"));

                            System.out.println("Title: " +
                                    rs.getString("title"));

                            System.out.println("Description: " +
                                    rs.getString("description"));

                            System.out.println("Due Date: " +
                                    rs.getDate("due_date"));

                            System.out.println("Priority: " +
                                    rs.getString("priority"));

                            System.out.println("Status: " +
                                    rs.getString("status"));

                        } else {
                            System.out.println("Task not found!");
                        }
                    }
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid Task ID!");
        } catch (SQLException e) {
            System.out.println("Error searching task: " + e.getMessage());
        }
    }
}

