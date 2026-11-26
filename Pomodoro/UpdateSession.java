
package Pomodoro;

import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UpdateSession {

    private Scanner sc;
    private int userId;

    public UpdateSession(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void show() {

        System.out.println("\n========== UPDATE SESSION ==========");

        String selectSql =
            "SELECT session_id, session_type, start_time " +
            "FROM PomodoroSessions " +
            "WHERE user_id = ? AND status = 'Running'";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(selectSql)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println(
                            "ID: " + rs.getInt("session_id")
                        );

                        System.out.println(
                            "Type: " + rs.getString("session_type")
                        );

                        System.out.println(
                            "Start: " + rs.getTimestamp("start_time")
                        );
                    }

                    if (!found) {
                        System.out.println("No running sessions found!");
                        return;
                    }
                }
            }

            System.out.print("\nEnter Session ID: ");

            int id;

            try {
                id = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Session ID!");
                return;
            }

            System.out.println("\n1. Mark as Completed");
            System.out.println("2. Mark as Interrupted");

            System.out.print("Enter choice: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice!");
                return;
            }

            String status;

            if (choice == 1) {
                status = "Completed";
            } else if (choice == 2) {
                status = "Interrupted";
            } else {
                System.out.println("Invalid choice!");
                return;
            }

            String updateSql =
                "UPDATE PomodoroSessions " +
                "SET end_time = NOW(), " +
                "actual_duration = TIMESTAMPDIFF(MINUTE, start_time, NOW()), " +
                "status = ? " +
                "WHERE session_id = ? AND user_id = ? " +
                "AND status = 'Running'";

            try (PreparedStatement ps = con.prepareStatement(updateSql)) {

                ps.setString(1, status);
                ps.setInt(2, id);
                ps.setInt(3, userId);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Session updated successfully!");
                } else {
                    System.out.println("Session not found or already updated!");
                }
            }

        } catch (Exception e) {
            System.out.println("Error updating session!");
            e.printStackTrace();
        }
    }
}

