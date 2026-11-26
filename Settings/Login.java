
package Settings;

import Database.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class Login {

    private Scanner sc;

    public Login(Scanner sc) {
        this.sc = sc;
    }

    public int login() {

        System.out.println("\n========== USER LOGIN ==========");

        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("Email and password are required!");
            return -1;
        }

        String sql = "SELECT user_id, full_name FROM Users WHERE email = ? AND password_hash = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return -1;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, email);
                ps.setString(2, password);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        System.out.println("Login Successful!");
                        System.out.println("Welcome, " + rs.getString("full_name"));
                        System.out.println("User ID: " + rs.getInt("user_id"));
                        int userId = rs.getInt("user_id");

                        return userId;
                    } else {
                        System.out.println("Invalid email or password!");
                        return -1;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
            return -1;
        }
    }
}
