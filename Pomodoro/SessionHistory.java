
package Pomodoro;

import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SessionHistory {

    private int userId;

    public SessionHistory(int userId) {
        this.userId = userId;
    }

    public void show() {

        System.out.println("\n========== SESSION HISTORY ==========");

        String sql =
            "SELECT session_id, session_type, start_time, " +
            "planned_duration, actual_duration, status " +
            "FROM PomodoroSessions " +
            "WHERE user_id = ? " +
            "ORDER BY start_time DESC";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println("\nSession ID: "
                            + rs.getInt("session_id"));

                        System.out.println("Type: "
                            + rs.getString("session_type"));

                        System.out.println("Start Time: "
                            + rs.getTimestamp("start_time"));

                        System.out.println("Planned Duration: "
                            + rs.getInt("planned_duration") + " minutes");

                        System.out.println("Actual Duration: "
                            + rs.getInt("actual_duration") + " minutes");

                        System.out.println("Status: "
                            + rs.getString("status"));

                        System.out.println("----------------------------");
                    }

                    if (!found) {
                        System.out.println("No sessions found!");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error loading session history!");
            e.printStackTrace();
        }
    }
}

