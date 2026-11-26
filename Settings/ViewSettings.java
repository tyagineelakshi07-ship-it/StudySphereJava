
package Settings;

import Database.DBConnection;
import java.sql.*;

public class ViewSettings {

    private int userId;

    public ViewSettings(int userId) {
        this.userId = userId;
    }

    public void show() {

        String sql =
            "SELECT u.full_name, u.email, " +
            "s.theme, s.notifications_enabled, " +
            "s.daily_study_goal, s.language " +
            "FROM Users u " +
            "LEFT JOIN UserSettings s ON u.user_id = s.user_id " +
            "WHERE u.user_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {

                    System.out.println("\n========== VIEW SETTINGS ==========");

                    if (rs.next()) {

                        System.out.println("\nUSER INFORMATION");

                        System.out.println("User ID: " + userId);
                        System.out.println("Username: " +
                                rs.getString("full_name"));
                        System.out.println("Email: " +
                                rs.getString("email"));

                        System.out.println("\nAPPLICATION PREFERENCES");

                        System.out.println("Theme: " +
                                rs.getString("theme"));

                        System.out.println("Notifications: " +
                                rs.getString("notifications_enabled"));

                        System.out.println("Daily Study Goal: " +
                                rs.getString("daily_study_goal") + " hours");

                        System.out.println("Language: " +
                                rs.getString("language"));

                    } else {

                        System.out.println("User not found!");
                    }

                    System.out.println("\n===================================");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error viewing settings: " + e.getMessage());
        }
    }
}
