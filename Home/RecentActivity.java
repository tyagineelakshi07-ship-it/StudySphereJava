
package Home;

import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RecentActivity {

    private int userId;

    public RecentActivity(int userId) {
        this.userId = userId;
    }

    public void show() {

        System.out.println("\n========== RECENT ACTIVITY ==========");

        String sql =
            "SELECT activity, activity_date FROM (" +

            "SELECT CONCAT('Pomodoro: ', session_type) AS activity, " +
            "start_time AS activity_date " +
            "FROM PomodoroSessions WHERE user_id = ? " +

            "UNION ALL " +

            "SELECT CONCAT('Task: ', title), created_at " +
            "FROM Tasks WHERE user_id = ? " +

            "UNION ALL " +

            "SELECT CONCAT('Note: ', title), created_at " +
            "FROM Notes WHERE user_id = ? " +

            "UNION ALL " +

            "SELECT CONCAT('Video: ', video_title), added_at " +
            "FROM YouTubeManager WHERE user_id = ? " +

            ") AS activities " +
            "ORDER BY activity_date DESC LIMIT 10";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) return;

            for (int i = 1; i <= 4; i++) {
                ps.setInt(i, userId);
            }

            try (ResultSet rs = ps.executeQuery()) {

                boolean found = false;

                while (rs.next()) {

                    found = true;

                    System.out.println("\nActivity: " +
                        rs.getString("activity"));

                    System.out.println("Date: " +
                        rs.getTimestamp("activity_date"));
                }

                if (!found) {
                    System.out.println("No recent activity!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
