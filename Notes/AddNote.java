
package Notes;

import Database.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class AddNote {

    private Scanner sc;
    private int userId;

    public AddNote(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void add() {

        System.out.println("\n========== ADD NEW NOTE ==========");

        System.out.print("Enter Note Title: ");
        String title = sc.nextLine().trim();

        System.out.println("Enter Note Content:");
        String content = sc.nextLine().trim();

        if (title.isEmpty() || content.isEmpty()) {
            System.out.println("Title and content cannot be empty!");
            return;
        }

        String sql = "INSERT INTO Notes " +
                "(user_id, title, content, is_favorite) " +
                "VALUES (?, ?, ?, false)";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, userId);
                ps.setString(2, title);
                ps.setString(3, content);

                ps.executeUpdate();

                System.out.println("\nNote added successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error adding note: " + e.getMessage());
        }
    }
}
