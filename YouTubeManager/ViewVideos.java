
package YouTubeManager;

import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewVideos {

    private int userId;

    public ViewVideos(int userId) {
        this.userId = userId;
    }

    public void show() {

        System.out.println("\n========== SAVED YOUTUBE VIDEOS ==========");

        String sql =
            "SELECT video_id, video_title, video_url, " +
            "channel_name, category, description, is_favorite, added_at " +
            "FROM YouTubeManager WHERE user_id = ? " +
            "ORDER BY added_at DESC";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println("\nVideo ID: "
                            + rs.getInt("video_id"));

                        System.out.println("Title: "
                            + rs.getString("video_title"));

                        System.out.println("URL: "
                            + rs.getString("video_url"));

                        System.out.println("Channel: "
                            + rs.getString("channel_name"));

                        System.out.println("Category: "
                            + rs.getString("category"));

                        System.out.println("Description: "
                            + rs.getString("description"));

                        System.out.println("Favorite: "
                            + (rs.getBoolean("is_favorite") ? "Yes" : "No"));

                        System.out.println("Added At: "
                            + rs.getTimestamp("added_at"));

                        System.out.println("------------------------------");
                    }

                    if (!found) {
                        System.out.println("No saved videos found!");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error viewing videos!");
            e.printStackTrace();
        }
    }
}
