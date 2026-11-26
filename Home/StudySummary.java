
package Home;

import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudySummary {

    private int userId;

    public StudySummary(int userId) {
        this.userId = userId;
    }

    public void show() {

        System.out.println("\n========== STUDY SUMMARY ==========");

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) return;

            String sql =
                "SELECT COUNT(*) FROM PomodoroSessions " +
                "WHERE user_id = ? AND status = 'Completed'";

            try (PreparedStatement ps =
                    con.prepareStatement(sql)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        System.out.println(
                            "Completed Focus Sessions: "
                            + rs.getInt(1));
                    }
                }
            }

            sql =
                "SELECT COUNT(*) FROM Tasks " +
                "WHERE user_id = ? AND status = 'Completed'";

            try (PreparedStatement ps =
                    con.prepareStatement(sql)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        System.out.println(
                            "Completed Tasks: " + rs.getInt(1));
                    }
                }
            }

            sql =
                "SELECT AVG((score / max_score) * 100) " +
                "FROM Performance " +
                "WHERE user_id = ? AND max_score > 0";

            try (PreparedStatement ps =
                    con.prepareStatement(sql)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        double avg = rs.getDouble(1);

                        if (rs.wasNull()) {
                            System.out.println(
                                "Average Performance: No data");
                        } else {
                            System.out.printf(
                                "Average Performance: %.2f%%%n",
                                avg);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
