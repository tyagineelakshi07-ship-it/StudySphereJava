
package ToDoList;

import Database.DBConnection;
import java.sql.*;

public class ViewTasks {

    private int userId;

    public ViewTasks(int userId) {
        this.userId = userId;
    }

    public void show() {

        String sql = "SELECT * FROM Tasks WHERE user_id = ? " +
                     "ORDER BY due_date ASC";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {

                    System.out.println("\n========== ALL TASKS ==========");

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println("\nTask ID: " +
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

                        System.out.println("--------------------------------");
                    }

                    if (!found) {
                        System.out.println("No tasks found!");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error viewing tasks: " + e.getMessage());
        }
    }
}
