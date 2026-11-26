
package Performance;

import Database.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UpdatePerformance {

    private Scanner sc;
    private int userId;

    public UpdatePerformance(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void update() {

        System.out.println("\n========== UPDATE PERFORMANCE ==========");

        try {
            System.out.print("Enter Performance ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Enter New Subject: ");
            String subject = sc.nextLine().trim();

            System.out.print("Enter New Activity Name: ");
            String activity = sc.nextLine().trim();

            System.out.print("Enter New Score: ");
            double score = Double.parseDouble(sc.nextLine());

            System.out.print("Enter New Maximum Marks: ");
            double maxScore = Double.parseDouble(sc.nextLine());

            System.out.print("Enter New Date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(sc.nextLine().trim());

            System.out.print("Enter New Remarks: ");
            String remarks = sc.nextLine().trim();

            if (subject.isEmpty() || activity.isEmpty()) {
                System.out.println("Subject and activity cannot be empty!");
                return;
            }

            if (score < 0 || maxScore <= 0 || score > maxScore) {
                System.out.println("Invalid marks!");
                return;
            }

            String sql = "UPDATE Performance SET subject = ?, " +
                    "activity_name = ?, score = ?, max_score = ?, " +
                    "activity_date = ?, remarks = ? " +
                    "WHERE performance_id = ? AND user_id = ?";

            try (Connection con = DBConnection.getConnection()) {

                if (con == null) {
                    System.out.println("Database connection failed!");
                    return;
                }

                try (PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setString(1, subject);
                    ps.setString(2, activity);
                    ps.setDouble(3, score);
                    ps.setDouble(4, maxScore);
                    ps.setDate(5, Date.valueOf(date));
                    ps.setString(6, remarks);
                    ps.setInt(7, id);
                    ps.setInt(8, userId);

                    int rows = ps.executeUpdate();

                    if (rows > 0) {
                        System.out.println("Performance updated successfully!");
                    } else {
                        System.out.println("Performance record not found!");
                    }
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numeric values!");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date! Use YYYY-MM-DD.");
        } catch (SQLException e) {
            System.out.println("Error updating record: " + e.getMessage());
        }
    }
}
