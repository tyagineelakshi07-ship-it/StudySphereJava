
package Pomodoro;

import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PomodoroReport {

    private int userId;

    public PomodoroReport(int userId) {
        this.userId = userId;
    }

    public void show() {

        System.out.println("\n========== POMODORO REPORT ==========");

        String sql =
            "SELECT COUNT(*) AS total_sessions, " +
            "SUM(CASE WHEN status = 'Completed' THEN 1 ELSE 0 END) AS completed, " +
            "SUM(CASE WHEN status = 'Interrupted' THEN 1 ELSE 0 END) AS interrupted, " +
            "SUM(CASE WHEN session_type = 'Focus' " +
            "AND status = 'Completed' THEN actual_duration ELSE 0 END) AS focus_minutes " +
            "FROM PomodoroSessions WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        System.out.println(
                            "Total Sessions: " +
                            rs.getInt("total_sessions"));

                        System.out.println(
                            "Completed Sessions: " +
                            rs.getInt("completed"));

                        System.out.println(
                            "Interrupted Sessions: " +
                            rs.getInt("interrupted"));

                        System.out.println(
                            "Total Focus Time: " +
                            rs.getInt("focus_minutes") + " minutes");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error generating report!");
            e.printStackTrace();
        }
    }
}

