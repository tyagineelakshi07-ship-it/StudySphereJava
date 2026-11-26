
package YouTubeManager;

import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class DeleteVideo {

    private Scanner sc;
    private int userId;

    public DeleteVideo(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void show() {

        System.out.println("\n========== DELETE VIDEO ==========");

        System.out.print("Enter Video ID to delete: ");

        int id;

        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Video ID!");
            return;
        }

        System.out.print("Are you sure you want to delete? (yes/no): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Deletion cancelled!");
            return;
        }

        String sql =
            "DELETE FROM YouTubeManager " +
            "WHERE video_id = ? AND user_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);
                ps.setInt(2, userId);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Video deleted successfully!");
                } else {
                    System.out.println("Video not found!");
                }
            }

        } catch (Exception e) {
            System.out.println("Error deleting video!");
            e.printStackTrace();
        }
    }
}

