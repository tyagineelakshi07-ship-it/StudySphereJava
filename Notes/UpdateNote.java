
package Notes;

import Database.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class UpdateNote {

    private Scanner sc;
    private int userId;

    public UpdateNote(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void update() {

        System.out.println("\n========== UPDATE NOTE ==========");

        try {

            System.out.print("Enter Note ID: ");
            int noteId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter New Title: ");
            String title = sc.nextLine().trim();

            System.out.println("Enter New Content:");
            String content = sc.nextLine().trim();

            if (title.isEmpty() || content.isEmpty()) {
                System.out.println("Title and content cannot be empty!");
                return;
            }

            String sql = "UPDATE Notes SET title = ?, content = ?, " +
                    "updated_at = CURRENT_TIMESTAMP " +
                    "WHERE note_id = ? AND user_id = ?";

            try (Connection con = DBConnection.getConnection()) {

                if (con == null) {
                    System.out.println("Database connection failed!");
                    return;
                }

                try (PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setString(1, title);
                    ps.setString(2, content);
                    ps.setInt(3, noteId);
                    ps.setInt(4, userId);

                    int rows = ps.executeUpdate();

                    if (rows > 0) {
                        System.out.println("Note updated successfully!");
                    } else {
                        System.out.println("Note not found!");
                    }
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid Note ID!");
        } catch (SQLException e) {
            System.out.println("Error updating note: " + e.getMessage());
        }
    }
}
