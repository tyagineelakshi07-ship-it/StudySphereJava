
package Performance;

import Database.DBConnection;
import java.sql.*;

public class PerformanceReport {

    private int userId;

    public PerformanceReport(int userId) {
        this.userId = userId;
    }

    public void show() {

        String summarySQL =
            "SELECT COUNT(*) AS total_activities, " +
            "SUM(score) AS total_score, " +
            "SUM(max_score) AS total_max, " +
            "AVG((score / max_score) * 100) AS average_percentage " +
            "FROM Performance WHERE user_id = ?";

        String subjectSQL =
            "SELECT subject, COUNT(*) AS activities, " +
            "SUM(score) AS total_score, " +
            "SUM(max_score) AS total_max, " +
            "AVG((score / max_score) * 100) AS average_percentage " +
            "FROM Performance WHERE user_id = ? " +
            "GROUP BY subject ORDER BY subject";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            System.out.println("\n========== PERFORMANCE REPORT ==========");

            // Overall Summary
            try (PreparedStatement ps = con.prepareStatement(summarySQL)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        int total = rs.getInt("total_activities");

                        if (total == 0) {
                            System.out.println("No performance data available!");
                            return;
                        }

                        System.out.println("\nOVERALL SUMMARY");

                        System.out.println("Total Activities: " + total);

                        System.out.println("Total Score: " +
                                rs.getDouble("total_score"));

                        System.out.println("Maximum Score: " +
                                rs.getDouble("total_max"));

                        System.out.printf("Average Percentage: %.2f%%%n",
                                rs.getDouble("average_percentage"));
                    }
                }
            }

            // Subject-wise Summary
            System.out.println("\nSUBJECT-WISE PERFORMANCE");

            try (PreparedStatement ps = con.prepareStatement(subjectSQL)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {

                        System.out.println("\nSubject: " +
                                rs.getString("subject"));

                        System.out.println("Activities: " +
                                rs.getInt("activities"));

                        System.out.println("Total Score: " +
                                rs.getDouble("total_score") + "/" +
                                rs.getDouble("total_max"));

                        System.out.printf("Average: %.2f%%%n",
                                rs.getDouble("average_percentage"));

                        System.out.println("----------------------------");
                    }
                }
            }

            System.out.println("\n========================================");

        } catch (SQLException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }
}
