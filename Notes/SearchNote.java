
package Notes;

import Database.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class SearchNote {

    private Scanner sc;
    private int userId;

    public SearchNote(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void search() {

        System.out.println("\n========== SEARCH NOTE ==========");

        try {

            System.out.print("Enter Note ID: ");
            int noteId = Integer.parseInt(sc.nextLine());

            String sql = "SELECT * FROM Notes " +
                    "WHERE note_id = ? AND user_id = ?";

            try (Connection con = DBConnection.getConnection()) {

                if (con == null) {
                    System.out.println("Database connection failed!");
                    return;
                }

                try (PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setInt(1, noteId);
                    ps.setInt(2, userId);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (rs.next()) {

                            System.out.println("\nNote Found!");

                            System.out.println("Note ID: " +
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

                        } else {
                            System.out.println("Note not found!");
                        }
                    }
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid Note ID!");
        } catch (SQLException e) {
            System.out.println("Error searching note: " + e.getMessage());
        }
    }
}
