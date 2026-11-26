
package Home;

import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dashboard {

    private int userId;

    public Dashboard(int userId) {
        this.userId = userId;
    }

    public void show() {

        System.out.println("\n========== DASHBOARD ==========");

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) return;

            showCount(con, "Tasks", "Total Tasks");
            showCount(con, "Notes", "Total Notes");
            showCount(con, "YouTubeManager", "Saved Videos");
            showCount(con, "PomodoroSessions", "Pomodoro Sessions");
            showCount(con, "Quizzes", "Quizzes Created");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showCount(Connection con,
                           String table, String label)
            throws Exception {

        String sql = "SELECT COUNT(*) FROM " + table
                   + " WHERE user_id = ?";

        try (PreparedStatement ps =
                con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    System.out.println(
                        label + ": " + rs.getInt(1));
                }
            }
        }
    }
}

