
package QuizGenerator;

import Database.DBConnection;
import java.sql.*;

public class QuizResult {

    private int userId;

    public QuizResult(int userId) {
        this.userId = userId;
    }

    public void showResult(int attemptId) {

        String sql = "SELECT q.title, a.score, a.total_marks, " +
                "a.attempted_at FROM QuizAttempts a " +
                "JOIN Quizzes q ON a.quiz_id = q.quiz_id " +
                "WHERE a.attempt_id = ? AND a.user_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, attemptId);
                ps.setInt(2, userId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        int score = rs.getInt("score");
                        int total = rs.getInt("total_marks");

                        double percentage = 0;

                        if (total > 0) {
                            percentage = (score * 100.0) / total;
                        }

                        System.out.println("\n========== QUIZ RESULT ==========");
                        System.out.println("Quiz: " + rs.getString("title"));
                        System.out.println("Correct Answers: " + score);
                        System.out.println("Total Questions: " + total);

                        System.out.printf("Percentage: %.2f%%%n", percentage);

                        System.out.println("Attempted At: " +
                                rs.getTimestamp("attempted_at"));

                        System.out.println("=================================");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Result error: " + e.getMessage());
        }
    }
}
