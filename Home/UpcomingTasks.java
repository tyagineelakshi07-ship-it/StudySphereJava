
package Home;

import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpcomingTasks {

    private int userId;

    public UpcomingTasks(int userId) {
        this.userId = userId;
    }

    public void show() {

        System.out.println("\n========== UPCOMING TASKS ==========");

        String sql =
            "SELECT task_id, title, due_date, priority, status " +
            "FROM Tasks WHERE user_id = ? " +
            "AND due_date >= CURDATE() " +
            "AND status <> 'Completed' " +
            "ORDER BY due_date ASC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) return;

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                boolean found = false;

                while (rs.next()) {

                    found = true;

                    System.out.println("\nTask ID: " +
                        rs.getInt("task_id"));

                    System.out.println("Title: " +
                        rs.getString("title"));

                    System.out.println("Due Date: " +
                        rs.getDate("due_date"));

                    System.out.println("Priority: " +
                        rs.getString("priority"));

                    System.out.println("Status: " +
                        rs.getString("status"));
                }

                if (!found) {
                    System.out.println("No upcoming tasks!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
