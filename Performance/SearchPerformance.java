
package Performance;

import Database.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class SearchPerformance {

    private Scanner sc;
    private int userId;

    public SearchPerformance(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void search() {

        System.out.println("\n========== SEARCH PERFORMANCE ==========");

        try {
            System.out.print("Enter Performance ID: ");
            int id = Integer.parseInt(sc.nextLine());

            String sql = "SELECT * FROM Performance " +
                    "WHERE performance_id = ? AND user_id = ?";

            try (Connection con = DBConnection.getConnection()) {

                if (con == null) {
                    System.out.println("Database connection failed!");
                    return;
                }

                try (PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setInt(1, id);
                    ps.setInt(2, userId);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (rs.next()) {

                            System.out.println("\nRecord Found!");

                            System.out.println("ID: " +
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

                        } else {
                            System.out.println("Performance record not found!");
                        }
                    }
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid Performance ID!");
        } catch (SQLException e) {
            System.out.println("Error searching record: " + e.getMessage());
        }
    }
}
