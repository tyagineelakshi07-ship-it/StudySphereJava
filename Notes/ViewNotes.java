
package Notes;

import Database.DBConnection;
import java.sql.*;

public class ViewNotes {

    private int userId;

    public ViewNotes(int userId) {
        this.userId = userId;
    }

    public void show() {

        String sql = "SELECT * FROM Notes WHERE user_id = ? " +
                     "ORDER BY updated_at DESC";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {

                    System.out.println("\n========== ALL NOTES ==========");

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println("\nNote ID: " +
                                rs.getInt("note_id"));

                        System.out.println("Title: " +
                                rs.getString("title"));

                        System.out.println("Content: " +
                                rs.getString("content"));

                        System.out.println("Favorite: " +
                                rs.getBoolean("is_favorite"));

                        System.out.println("Created At: " +
                                rs.getTimestamp("created_at"));

                        System.out.println("Updated At: " +
                                rs.getTimestamp("updated_at"));

                        System.out.println("--------------------------------");
                    }

                    if (!found) {
                        System.out.println("No notes found!");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error viewing notes: " + e.getMessage());
        }
    }
}
