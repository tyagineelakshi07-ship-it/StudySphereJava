
package Settings;

import Database.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class StudyGoal {

    private Scanner sc;
    private int userId;

    public StudyGoal(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void update() {

        System.out.println("\n========== DAILY STUDY GOAL ==========");

        try {

            System.out.print("Enter Daily Study Goal (Hours): ");

            int hours = Integer.parseInt(sc.nextLine());

            if (hours <= 0 || hours > 24) {
                System.out.println("Please enter hours between 1 and 24!");
                return;
            }

            String sql = "UPDATE UserSettings " +
                         "SET daily_study_goal = ? WHERE user_id = ?";

            try (Connection con = DBConnection.getConnection()) {

                if (con == null) {
                    System.out.println("Database connection failed!");
                    return;
                }

                try (PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setInt(1, hours);
                    ps.setInt(2, userId);

                    int rows = ps.executeUpdate();

                    if (rows > 0) {

                        System.out.println(
                            "Daily study goal updated successfully!"
                        );

                        System.out.println("New Goal: " + hours + " hours");

                    } else {

                        System.out.println(
                            "Settings record not found! " +
                            "Please create a UserSettings record first."
                        );
                    }
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number!");
        } catch (SQLException e) {
            System.out.println("Error updating study goal: " + e.getMessage());
        }
    }
}
