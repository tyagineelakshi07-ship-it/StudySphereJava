
package YouTubeManager;

import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class UpdateVideo {

    private Scanner sc;
    private int userId;

    public UpdateVideo(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void show() {

        System.out.println("\n========== UPDATE VIDEO ==========");

        System.out.print("Enter Video ID: ");

        int id;

        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Video ID!");
            return;
        }

        System.out.print("Enter New Video Title: ");
        String title = sc.nextLine();

        System.out.print("Enter New YouTube URL: ");
        String url = sc.nextLine();

        if (!(url.startsWith("https://www.youtube.com/")
                || url.startsWith("https://youtube.com/")
                || url.startsWith("https://youtu.be/"))) {
            System.out.println("Please enter a valid YouTube URL!");
            return;
        }

        System.out.print("Enter New Channel Name: ");
        String channel = sc.nextLine();

        System.out.print("Enter New Category: ");
        String category = sc.nextLine();

        System.out.print("Enter New Description: ");
        String description = sc.nextLine();

        if (title.trim().isEmpty()) {
            System.out.println("Title cannot be empty!");
            return;
        }

        String sql =
            "UPDATE YouTubeManager SET video_title = ?, " +
            "video_url = ?, channel_name = ?, category = ?, " +
            "description = ? WHERE video_id = ? AND user_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, title);
                ps.setString(2, url);
                ps.setString(3, channel);
                ps.setString(4, category);
                ps.setString(5, description);
                ps.setInt(6, id);
                ps.setInt(7, userId);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Video updated successfully!");
                } else {
                    System.out.println("Video not found!");
                }
            }

        } catch (Exception e) {
            System.out.println("Error updating video!");
            e.printStackTrace();
        }
    }
}
