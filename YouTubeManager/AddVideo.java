
package YouTubeManager;

import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class AddVideo {

    private Scanner sc;
    private int userId;

    public AddVideo(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void show() {

        System.out.println("\n========== ADD NEW VIDEO ==========");

        System.out.print("Enter Video Title: ");
        String title = sc.nextLine();

        System.out.print("Enter YouTube URL: ");
        String url = sc.nextLine();

        if (!(url.startsWith("https://www.youtube.com/")
                || url.startsWith("https://youtube.com/")
                || url.startsWith("https://youtu.be/"))) {
            System.out.println("Please enter a valid YouTube URL!");
            return;
        }

        System.out.print("Enter Channel Name: ");
        String channel = sc.nextLine();

        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        System.out.print("Enter Description: ");
        String description = sc.nextLine();

        if (title.trim().isEmpty()) {
            System.out.println("Video title cannot be empty!");
            return;
        }

        String sql =
            "INSERT INTO YouTubeManager " +
            "(user_id, video_title, video_url, channel_name, " +
            "category, description) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, userId);
                ps.setString(2, title);
                ps.setString(3, url);
                ps.setString(4, channel);
                ps.setString(5, category);
                ps.setString(6, description);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Video added successfully!");
                } else {
                    System.out.println("Video could not be added!");
                }
            }

        } catch (Exception e) {
            System.out.println("Error adding video!");
            e.printStackTrace();
        }
    }
}
