
package QuizGenerator;

import Database.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class QuizSubjects {

    private Scanner sc;
    private int userId;

    public QuizSubjects(Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void showSubjects() {

        String sql = "SELECT quiz_id, title, topic, total_questions " +
                "FROM Quizzes WHERE user_id = ? AND title IN " +
                "('SQL Basics Practice', 'Java Programming', " +
                "'Python Programming', 'Computer Fundamentals') " +
                "ORDER BY quiz_id";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {

                    System.out.println("\n========== SELECT SUBJECT ==========");

                    boolean found = false;

                    while (rs.next()) {

                        found = true;

                        System.out.println("\nQuiz ID: " +
                                rs.getInt("quiz_id"));

                        System.out.println("Subject: " +
                                rs.getString("topic"));

                        System.out.println("Title: " +
                                rs.getString("title"));

                        System.out.println("Total Questions: " +
                                rs.getInt("total_questions"));

                        System.out.println("--------------------------------");
                    }

                    if (!found) {
                        System.out.println("No quizzes found!");
                        System.out.println("Please insert quiz data first.");
                        return;
                    }
                }
            }

            System.out.print("\nEnter Quiz ID to attempt (0 to go back): ");

            int quizId = Integer.parseInt(sc.nextLine());

            if (quizId != 0) {
                new QuizPlay(sc, userId).startQuiz(quizId);
            }

        } catch (NumberFormatException e) {

            System.out.println("Please enter a valid Quiz ID!");

        } catch (SQLException e) {

            System.out.println("Error loading subjects: " + e.getMessage());
        }
    }
}
