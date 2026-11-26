
package Performance;

import Database.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class DeletePerformance {

    private Scanner sc;
    private int userId;

    public DeletePerformance(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void delete() {

        System.out.println("\n========== DELETE PERFORMANCE ==========");

        try {
            System.out.print("Enter Performance ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Are you sure you want to delete? (Yes/No): ");
            String confirm = sc.nextLine().trim();

            if (!confirm.equalsIgnoreCase("Yes")) {
                System.out.println("Deletion cancelled!");
                return;
            }

            String sql = "DELETE FROM Performance " +
                    "WHERE performance_id = ? AND user_id = ?";

            try (Connection con = DBConnection.getConnection()) {

                if (con == null) {
                    System.out.println("Database connection failed!");
                    return;
                }

                try (PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setInt(1, id);
                    ps.setInt(2, userId);

                    int rows = ps.executeUpdate();

                    if (rows > 0) {
                        System.out.println("Performance deleted successfully!");
                    } else {
                        System.out.println("Performance record not found!");
                    }
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid Performance ID!");
        } catch (SQLException e) {
            System.out.println("Error deleting record: " + e.getMessage());
        }
    }
}
