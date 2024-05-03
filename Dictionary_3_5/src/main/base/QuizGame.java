package base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizGame extends Game {
    private List<Question> questions = new ArrayList<>();

    public QuizGame() {
    }
    
    public void start() {
        System.out.println("Welcome to the quiz game!");
        try {
            loadQuestions();
            play();
        } catch (IOException e) {
            System.err.println("Error reading questions file: " + e.getMessage());
        }
    }

    private void loadQuestions() throws IOException {
        String questionsFile = "quizGame.txt";
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

    }

    @Override
    public void play() {

        Scanner scanner = new Scanner(System.in);
        int score = 0;
        for (Question question : questions) {
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
            if (String.valueOf(answer.charAt(0)).equalsIgnoreCase(String.valueOf(question.getAnswer()))) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect! The correct answer is: " + question.getAnswer());
            }
        }
        //scanner.close();
        System.out.println("Your score: " + score + "/" + questions.size());
    }

    public static void main(String[] args) {
        QuizGame g = new QuizGame();
        g.start();
    }
}

class Question {
    private String question;
    private String answer;
    private List<String> options = new ArrayList<>();

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public char getAnswer() {
        return answer.charAt(1);
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void addOption(String option, String text) {
        options.add(option + " " + text);
    }
}