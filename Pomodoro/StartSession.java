
package Pomodoro;

import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

public class StartSession {

    private Scanner sc;
    private int userId;

    public StartSession(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void show() {

        System.out.println("\n========== START SESSION ==========");
        System.out.println("1. Focus");
        System.out.println("2. Short Break");
        System.out.println("3. Long Break");

        System.out.print("Select session type: ");

        int choice;

        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice!");
            return;
        }

        String type;

        switch (choice) {
            case 1:
                type = "Focus";
                break;
            case 2:
                type = "Short Break";
                break;
            case 3:
                type = "Long Break";
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        System.out.print("Enter duration in minutes: ");

        int minutes;

        try {
            minutes = Integer.parseInt(sc.nextLine());

            if (minutes < 1 || minutes > 180) {
                System.out.println("Duration must be between 1 and 180 minutes!");
                return;
            }

        } catch (NumberFormatException e) {
            System.out.println("Enter a valid duration!");
            return;
        }

        String sql =
            "INSERT INTO PomodoroSessions " +
            "(user_id, session_type, start_time, " +
            "planned_duration, actual_duration, status) " +
            "VALUES (?, ?, NOW(), ?, 0, 'Running')";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            int sessionId;

            try (PreparedStatement ps = con.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, userId);
                ps.setString(2, type);
                ps.setInt(3, minutes);

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (!rs.next()) {
                        System.out.println("Could not create session!");
                        return;
                    }

                    sessionId = rs.getInt(1);
                }
            }

            System.out.println("\nSession Started!");
            System.out.println("Type: " + type);
            System.out.println("Duration: " + minutes + " minutes");

            // Countdown timer
            int totalSeconds = minutes * 60;

            for (int remaining = totalSeconds;
                 remaining > 0; remaining--) {

                System.out.printf(
                    "\rTime Remaining: %02d:%02d",
                    remaining / 60,
                    remaining % 60
                );

                Thread.sleep(1000);
            }

            System.out.println("\n\nSession Completed!");

            String updateSql =
                "UPDATE PomodoroSessions " +
                "SET end_time = NOW(), actual_duration = ?, " +
                "status = 'Completed' " +
                "WHERE session_id = ? AND user_id = ?";

            try (PreparedStatement ps =
                    con.prepareStatement(updateSql)) {

                ps.setInt(1, minutes);
                ps.setInt(2, sessionId);
                ps.setInt(3, userId);

                ps.executeUpdate();
            }

            System.out.println("Session saved successfully!");

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
            System.out.println("\nTimer interrupted. Session may remain Running.");

        } catch (Exception e) {
            System.out.println("Error starting session!");
            e.printStackTrace();
        }
    }
}
