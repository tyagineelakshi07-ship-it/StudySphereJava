
package Settings;

import Database.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class ChangeUsername {

    private Scanner sc;
    private int userId;

    public ChangeUsername(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void change() {

        System.out.println("\n========== CHANGE USERNAME ==========");

        System.out.print("Enter New Username: ");
        String username = sc.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("Username cannot be empty!");
            return;
        }

        String sql = "UPDATE Users SET full_name = ? WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, username);
                ps.setInt(2, userId);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Username changed successfully!");
                } else {
                    System.out.println("User not found!");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error changing username: " + e.getMessage());
        }
    }
}
