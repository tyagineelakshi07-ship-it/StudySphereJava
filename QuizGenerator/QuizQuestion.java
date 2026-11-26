package QuizGenerator;

public class QuizQuestion {

    int questionId;
    String question;
    String optionA;
    String optionB;
    String optionC;
    String optionD;
    String correctAnswer;
    String explanation;

    public QuizQuestion(
            int questionId,
            String question,
            String optionA,
            String optionB,
            String optionC,
            String optionD,
            String correctAnswer,
            String explanation) {

        this.questionId = questionId;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }
}
