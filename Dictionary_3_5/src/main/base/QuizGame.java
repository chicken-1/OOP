package base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizGame extends Game {
    private List<Question> questionList = new ArrayList<>();

    public QuizGame() {
    }
    
    public void start() {
        System.out.println("Welcome to the quiz game!");
        try {
            questionList = loadQuestions();
            play();
        } catch (IOException e) {
            System.err.println("Error reading questions file: " + e.getMessage());
        }
    }

    public List<Question> loadQuestions() throws IOException {
        List<Question> questions = new ArrayList<>();
        String questionsFile = "D:\\App\\Scene Builder\\tempOOP\\OOP\\Dictionary_3_5\\src\\main\\base\\quizGame.txt";
        String line;
        Question question = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(questionsFile))) {
            while ((line = reader.readLine()) != null) {
                if (line.matches("^[0-9].*")) {
                    question = new Question();
                    question.setQuestion(line);
                    questions.add(question);
                } else if (line.startsWith("Answer:")) {
                    String answer = line.substring(line.indexOf(':') + 1).trim();
                    question.setAnswer(answer);
                    
                } else {
                    String option = line.substring(1, 2);
                    String text = line.substring(3);
                    question.addOption(option, text);
                                    
                }
            }
        }
        return questions;
    }

    @Override
    public void play() {

        Scanner scanner = new Scanner(System.in);
        int score = 0;
        for (Question question : questionList) {
            System.out.println(question.getQuestion());
            for (String option : question.getOptions()) {
                System.out.println(option);
            }
            System.out.print("Your answer: ");
            String answer = scanner.nextLine();
            if (answer.isEmpty()) {
                System.out.println("You didn't enter anything!");
                continue;
            }
            if (String.valueOf(answer.charAt(0)).equalsIgnoreCase(String.valueOf(question.getAnswerChar()))) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect! The correct answer is: " + question.getAnswerChar());
            }
        }
        //scanner.close();
        System.out.println("Your score: " + score + "/" + questionList.size());
    }

    public static void main(String[] args) {
        QuizGame g = new QuizGame();
        g.start();
    }
}