
package QuizGenerator;

import Database.DBConnection;
import java.sql.*;
import java.util.ArrayList;

public class QuizPlay {

    private java.util.Scanner sc;
    private int userId;

    public QuizPlay(java.util.Scanner sc, int userId) {
        this.sc = sc;
        this.userId = userId;
    }

    public void startQuiz(int quizId) {

        ArrayList<QuizQuestion> questions = new ArrayList<>();
        ArrayList<String> answers = new ArrayList<>();
        ArrayList<Boolean> results = new ArrayList<>();

        String sql = "SELECT question_id, question_text, " +
                "JSON_UNQUOTE(JSON_EXTRACT(options, '$[0]')) AS a, " +
                "JSON_UNQUOTE(JSON_EXTRACT(options, '$[1]')) AS b, " +
                "JSON_UNQUOTE(JSON_EXTRACT(options, '$[2]')) AS c, " +
                "JSON_UNQUOTE(JSON_EXTRACT(options, '$[3]')) AS d, " +
                "correct_answer, explanation " +
                "FROM QuizQuestions WHERE quiz_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            // Check that this quiz belongs to the current user
            String check = "SELECT quiz_id FROM Quizzes " +
                           "WHERE quiz_id = ? AND user_id = ?";

            try (PreparedStatement ps = con.prepareStatement(check)) {

                ps.setInt(1, quizId);
                ps.setInt(2, userId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Quiz not found!");
                        return;
                    }
                }
            }

            // Fetch questions
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, quizId);

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {

                        QuizQuestion q = new QuizQuestion(
                            rs.getInt("question_id"),
                            rs.getString("question_text"),
                            rs.getString("a"),
                            rs.getString("b"),
                            rs.getString("c"),
                            rs.getString("d"),
                            rs.getString("correct_answer"),
                            rs.getString("explanation")
                        );

                        questions.add(q);
                    }
                }
            }

            if (questions.isEmpty()) {
                System.out.println("No questions available!");
                return;
            }

            System.out.println("\n========== START QUIZ ==========");

            int score = 0;

            for (int i = 0; i < questions.size(); i++) {

                QuizQuestion q = questions.get(i);

                System.out.println("\nQ" + (i + 1) + ". " + q.question);
                System.out.println("A. " + q.optionA);
                System.out.println("B. " + q.optionB);
                System.out.println("C. " + q.optionC);
                System.out.println("D. " + q.optionD);

                System.out.print("Your answer (A/B/C/D): ");

                String answer = sc.nextLine().trim().toUpperCase();

                while (!answer.equals("A") && !answer.equals("B")
                        && !answer.equals("C") && !answer.equals("D")) {

                    System.out.print("Enter only A, B, C or D: ");
                    answer = sc.nextLine().trim().toUpperCase();
                }

                boolean correct = answer.equalsIgnoreCase(
                        q.correctAnswer.trim());

                answers.add(answer);
                results.add(correct);

                if (correct) {
                    score++;
                    System.out.println("Correct!");
                } else {
                    System.out.println("Incorrect!");
                    System.out.println("Correct Answer: " + q.correctAnswer);
                }

                System.out.println("Explanation: " + q.explanation);
            }

            // Save quiz attempt
            String insertAttempt = "INSERT INTO QuizAttempts " +
                    "(quiz_id, user_id, score, total_marks) " +
                    "VALUES (?, ?, ?, ?)";

            int attemptId = 0;

            try (PreparedStatement ps = con.prepareStatement(
                    insertAttempt, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, quizId);
                ps.setInt(2, userId);
                ps.setInt(3, score);
                ps.setInt(4, questions.size());

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        attemptId = rs.getInt(1);
                    }
                }
            }

            // Save each answer
            String insertAnswer = "INSERT INTO QuizAttemptAnswers " +
                    "(attempt_id, question_id, selected_answer, is_correct) " +
                    "VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(insertAnswer)) {

                for (int i = 0; i < questions.size(); i++) {

                    ps.setInt(1, attemptId);
                    ps.setInt(2, questions.get(i).questionId);
                    ps.setString(3, answers.get(i));
                    ps.setBoolean(4, results.get(i));

                    ps.executeUpdate();
                }
            }

            new QuizResult(userId).showResult(attemptId);

        } catch (SQLException e) {
            System.out.println("Quiz error: " + e.getMessage());
        }
    }
}
