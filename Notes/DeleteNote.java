
package Notes;

import Database.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class DeleteNote {

    private Scanner sc;
    private int userId;

    public DeleteNote(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void delete() {

        System.out.println("\n========== DELETE NOTE ==========");

        try {

            System.out.print("Enter Note ID: ");
            int noteId = Integer.parseInt(sc.nextLine());

            System.out.print("Are you sure you want to delete? (Yes/No): ");
            String confirm = sc.nextLine().trim();

            if (!confirm.equalsIgnoreCase("Yes")) {
                System.out.println("Deletion cancelled!");
                return;
            }

            String sql = "DELETE FROM Notes " +
                    "WHERE note_id = ? AND user_id = ?";

            try (Connection con = DBConnection.getConnection()) {

                if (con == null) {
                    System.out.println("Database connection failed!");
                    return;
                }

                try (PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setInt(1, noteId);
                    ps.setInt(2, userId);

                    int rows = ps.executeUpdate();

                    if (rows > 0) {
                        System.out.println("Note deleted successfully!");
                    } else {
                        System.out.println("Note not found!");
                    }
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid Note ID!");
        } catch (SQLException e) {
            System.out.println("Error deleting note: " + e.getMessage());
        }
    }
}
