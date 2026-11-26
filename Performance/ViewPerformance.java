
package Performance;

import Database.DBConnection;
import java.sql.*;

public class ViewPerformance {

    private int userId;

    public ViewPerformance(int userId) {
        this.userId = userId;
    }

    public void show() {

        String sql = "SELECT * FROM Performance " +
                     "WHERE user_id = ? ORDER BY activity_date DESC";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {

                    System.out.println("\n========== ALL PERFORMANCE RECORDS ==========");

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println("\nPerformance ID: " +
                                rs.getInt("performance_id"));

                        System.out.println("Subject: " +
                                rs.getString("subject"));

                        System.out.println("Activity: " +
                                rs.getString("activity_name"));

                        System.out.println("Score: " +
                                rs.getDouble("score"));

                        System.out.println("Maximum Marks: " +
                                rs.getDouble("max_score"));

                        System.out.println("Date: " +
                                rs.getDate("activity_date"));

                        System.out.println("Remarks: " +
                                rs.getString("remarks"));

                        System.out.println("--------------------------------------");
                    }

                    if (!found) {
                        System.out.println("No performance records found!");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error viewing records: " + e.getMessage());
        }
    }
}
