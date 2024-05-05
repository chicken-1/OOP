package base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryManagement {
   
    private Dictionary dictionary;

    public DictionaryManagement() {
        dictionary = new Dictionary();
    }

    public void insertFromCommandline() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of words: ");
        // Get the number of words and handle the exception if the input is not an integer or is empty
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer: ");
            scanner.next();
        }
        int n = scanner.nextInt();

        scanner.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.println("Enter the word target: ");
            String word_target = scanner.nextLine();
            while (word_target.isEmpty()) {
                System.out.println("Word target cannot be empty. Please enter again: ");
                word_target = scanner.nextLine();
            }

            System.out.println("Enter the word explain: ");
            String word_explain = scanner.nextLine();
            while (word_explain.isEmpty()) {
                System.out.println("Word explain cannot be empty. Please enter again: ");
                word_explain = scanner.nextLine();
            }

            Word word = new Word(word_target, word_explain);
            this.dictionary.addWord(word);
        }

        //scanner.close();
    }

    public void addWord(Word word) {
        this.dictionary.addWord(word);
    }

    public void insertFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] words = line.split("\t");
                if (words.length > 0) {
                    Word word = new Word(words[0], words[1], words[2], words[3]);
                    this.dictionary.addWord(word);
                } else {
                    System.out.println("Skipping empty line.");
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void removeWord(String wordTarget) {
        Word wordToRemove = dictionary.searchWord(wordTarget);
        if (wordToRemove != null) {
            dictionary.deleteWord(wordToRemove);
        } else {
            System.out.println("Word not found.");
        }

    }

    public void updateWord(String oldWordTarget) {
        Scanner scanner = new Scanner(System.in);
        Word wordToUpdate = dictionary.searchWord(oldWordTarget);
        if (wordToUpdate == null) {
            System.out.println("Word not found.");
            return;
        }
        System.out.print("Enter the new word explain: ");
        String wordExplainUpdate = scanner.nextLine();
        if (wordExplainUpdate == null) {
            System.out.println("Word explain cannot be empty.");
            return;
        }
        Word newWord = new Word(oldWordTarget, wordExplainUpdate);
        this.dictionary.updateWord(wordToUpdate, newWord);
    }

    public void updatedWord(Word wordToUpdate, Word newWord) {
        this.dictionary.updateWord(wordToUpdate, newWord);
    }

    public void displayWords() {
        dictionary.display();
    }

    public Word dictionaryLookup(String wordTarget) {
        return dictionary.searchWord(wordTarget);
    }

    public List<Word> dictionarySearcher(String searchKeyword) {
        return dictionary.searchWords(searchKeyword);
    }

    public void exportToFile(String exportFilename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(exportFilename))) {
            List<Word> words = dictionary.getDictionary();
            for (int i = 0; i < words.size(); i++) {
                Word word = words.get(i);
                bw.write(word.getWord_target() + "\t" + word.getWord_type() + "\t" + word.getWord_pronunciation() + "\t" + word.getWord_explain());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void matchGame() {
        MatchGame game = new MatchGame();
        game.play();
    }

    public void quizGame() {
        QuizGame game = new QuizGame();
        game.start();
    }



}