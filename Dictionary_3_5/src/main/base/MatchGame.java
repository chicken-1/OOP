package base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MatchGame extends Game {
    public List<String[]> words;
    public List<String[]> shuffledWords;

    public MatchGame() {
        words = new ArrayList<>();
        shuffledWords = new ArrayList<>();
        loadWords();
        shuffleWords();
    }

    public void loadWords() {
        String wordsFile = "D:\\App\\Scene Builder\\tempOOP\\OOP\\Dictionary_3_5\\src\\main\\base\\dictionaries.txt";
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(wordsFile))) {
            while ((line = reader.readLine())!= null) {
                String[] parts = line.split("\t");
                words.add(parts);
            }
        } catch (IOException e) {
            System.err.println("Error reading words file: " + e.getMessage());
        }
    }

    public void shuffleWords() {
        Collections.shuffle(words);
        shuffledWords.addAll(words.subList(0, 5));
    }

    @Override
    public void play() {
        System.out.println("Welcome to the match game!");
        System.out.println("Match the following words:");
        
        List<String> column1 = new ArrayList<>();
        List<String> column2 = new ArrayList<>();

        for (int i = 0; i < shuffledWords.size(); i++) {
            column1.add(shuffledWords.get(i)[0]);
            column2.add(shuffledWords.get(i)[3]);
        }

        Collections.shuffle(column2);

        for (int i = 0; i < column1.size(); i++) {
            System.out.println((i+1) + ". " + column1.get(i) + "         \t\t" + (char)(97+i) + ". " + column2.get(i));
        }

        Scanner scanner = new Scanner(System.in);
        int score = 0;
        List<String[]> fixedanswers = new ArrayList<>(); // Initialize the fixedanswers list
        System.out.println("Enter the letter of the matching word for each word.");
        for (int i = 0; i < column1.size(); i++) {
            System.out.print(i + 1 + " - ");
            String answer = scanner.nextLine();
            if (answer == null || answer.isEmpty()) {
                System.out.println("You didn't enter anything!");
                continue;
            }
            if (checkAnswer(column2, i, answer)) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect!");
                fixedanswers.add(shuffledWords.get(i));
            }
        }
        //scanner.close();

        System.out.println("Your score: " + score + "/" + column1.size());
        System.out.println("The correct answers are:");
        for (String[] answer : fixedanswers) {
            System.out.println(answer[0] + " - " + answer[3]);
        }

    }

    public boolean checkAnswer(List<String> column2, int match1, String match2) {
        int index2 = match2.charAt(0) - 97;
        //int index1 = match1.charAt(0) - 49;
        return shuffledWords.get(match1)[3].equals(column2.get(index2));
    }

    public static void main(String[] args) {
        MatchGame g = new MatchGame();
        g.play();
    }
}