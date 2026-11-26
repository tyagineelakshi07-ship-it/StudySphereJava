
package Performance;

import Database.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AddPerformance {

    private Scanner sc;
    private int userId;

    public AddPerformance(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void add() {

        System.out.println("\n========== ADD PERFORMANCE ==========");

        System.out.print("Enter Subject: ");
        String subject = sc.nextLine().trim();

        System.out.print("Enter Activity Name: ");
        String activity = sc.nextLine().trim();

        try {
            System.out.print("Enter Score: ");
            double score = Double.parseDouble(sc.nextLine());

            System.out.print("Enter Maximum Marks: ");
            double maxScore = Double.parseDouble(sc.nextLine());

            System.out.print("Enter Activity Date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(sc.nextLine().trim());

            System.out.print("Enter Remarks: ");
            String remarks = sc.nextLine().trim();

            if (subject.isEmpty() || activity.isEmpty()) {
                System.out.println("Subject and activity cannot be empty!");
                return;
            }

            if (score < 0 || maxScore <= 0 || score > maxScore) {
                System.out.println("Invalid score or maximum marks!");
                return;
            }

            String sql = "INSERT INTO Performance " +
                    "(user_id, subject, activity_name, score, max_score, " +
                    "activity_date, remarks) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = DBConnection.getConnection()) {

                if (con == null) {
                    System.out.println("Database connection failed!");
                    return;
                }

                try (PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setInt(1, userId);
                    ps.setString(2, subject);
                    ps.setString(3, activity);
                    ps.setDouble(4, score);
                    ps.setDouble(5, maxScore);
                    ps.setDate(6, Date.valueOf(date));
                    ps.setString(7, remarks);

                    ps.executeUpdate();

                    System.out.println("\nPerformance added successfully!");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numeric marks!");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date! Use YYYY-MM-DD.");
        } catch (SQLException e) {
            System.out.println("Error adding performance: " + e.getMessage());
        }
    }
}
