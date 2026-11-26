
package QuizGenerator;

import Database.DBConnection;
import java.sql.*;

public class QuizHistory {

    private int userId;

    public QuizHistory(int userId) {
        this.userId = userId;
    }

    public void showHistory() {

        String sql = "SELECT a.attempt_id, q.title, q.topic, " +
                "a.score, a.total_marks, a.attempted_at " +
                "FROM QuizAttempts a " +
                "JOIN Quizzes q ON a.quiz_id = q.quiz_id " +
                "WHERE a.user_id = ? " +
                "ORDER BY a.attempted_at DESC";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {

                    System.out.println("\n========== QUIZ HISTORY ==========");

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        int score = rs.getInt("score");
                        int total = rs.getInt("total_marks");

                        double percentage = 0;

                        if (total > 0) {
                            percentage = (score * 100.0) / total;
                        }

                        System.out.println("\nAttempt ID: " +
                                rs.getInt("attempt_id"));

                        System.out.println("Subject: " +
                                rs.getString("topic"));

                        System.out.println("Quiz: " +
                                rs.getString("title"));

                        System.out.println("Score: " +
                                score + "/" + total);

                        System.out.printf("Percentage: %.2f%%%n",
                                percentage);

                        System.out.println("Date: " +
                                rs.getTimestamp("attempted_at"));

                        System.out.println("--------------------------------");
                    }

                    if (!found) {
                        System.out.println("No quiz attempts found!");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error loading quiz history: " +
                    e.getMessage());
        }
    }
}
