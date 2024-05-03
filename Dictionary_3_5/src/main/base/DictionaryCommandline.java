package base;

import java.util.List;
import java.util.Scanner;

public class DictionaryCommandline {
    private DictionaryManagement dictionaryManagement;

    public DictionaryCommandline() {
        dictionaryManagement = new DictionaryManagement();
    }

    public void dictionaryAdvanced() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcom to My Application!");
            System.out.println("[0] Exit");
            System.out.println("[1] Add");
            System.out.println("[2] Remove");
            System.out.println("[3] Update");
            System.out.println("[4] Display");
            System.out.println("[5] Lookup");
            System.out.println("[6] Search");
            System.out.println("[7] Game");
            System.out.println("[8] Import from file");
            System.out.println("[9] Export to file");
            System.out.print("Your action: ");

            String input = scanner.nextLine();
            try {
                int action = Integer.parseInt(input);
                switch (action) {
                    case 0:
                        return;
                    case 1:
                        dictionaryManagement.insertFromCommandline();
                        break;
                    case 2: 
                        System.out.print("Enter the word target to remove: ");
                        String wordTarget = scanner.nextLine();
                        dictionaryManagement.removeWord(wordTarget);
                        break;
                    case 3: 
                        System.out.print("Enter the word target to update: ");
                        String wordTargetUpdate = scanner.nextLine();
                        if (wordTargetUpdate.isEmpty()) {
                            System.out.println("Word target cannot be empty.");
                            break;
                        }

                        dictionaryManagement.updateWord(wordTargetUpdate);
                        break;
                    case 4:
                        dictionaryManagement.displayWords();
                        break;
                    case 5:
                        System.out.print("Enter the word target to lookup: ");
                        String wordTargetLookup = scanner.nextLine();
                        Word word = dictionaryManagement.dictionaryLookup(wordTargetLookup);
                        if (word != null) {
                            System.out.println("Word target: " + word.getWord_target());
                            System.out.println("Word explain: " + word.getWord_explain());
                        } else {
                            System.out.println("Word not found!");
                        }
                        break;
                    case 6:
                        System.out.print("Enter the word target to search: ");
                        String wordTargetSearch = scanner.nextLine();
                        List<Word> searchResults = dictionaryManagement.dictionarySearcher(wordTargetSearch);
                        System.out.println("Search results:");
                        for (int i = 0; i < searchResults.size(); i++) {
                            Word word1 = searchResults.get(i);
                            System.out.println((i + 1) + ". " + word1.getWord_target());
                        }
                        break;
                    case 7: 
                        System.out.println("Which game do you want to play?");
                        System.out.println("[1] Match game (vocab game)");
                        System.out.println("[2] Quiz game (grammar game)");
                        System.out.print("Your choice: ");
                        String gameChoice = scanner.nextLine();
                        if (gameChoice.equals("1")) {
                            dictionaryManagement.matchGame();
                        } else if (gameChoice.equals("2")) {
                            dictionaryManagement.quizGame();
                        } else {
                            System.out.println("Invalid choice.");
                        }
                        break;
                    case 8: 
                        System.out.print("Enter the file path: ");
                        String filePath = scanner.nextLine();
                        dictionaryManagement.insertFromFile(filePath);
                        break;
                    case 9:
                        System.out.print("Enter the file path: ");
                        String filePathExport = scanner.nextLine();
                        dictionaryManagement.exportToFile(filePathExport);
                        break;
                    default:
                        System.out.println("Action not supported.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }

            //scanner.close();
        }
    }

    /*public void showAllWords(Dictionary dictionary) {
        System.out.println("No\t| English\t| Vietnamese");
        for (int i = 0; i < dictionary.getWords().size(); i++) {
            System.out.println(i + 1 + "\t| " + dictionary.getWords().get(i).getWord_target() + "  \t| " + dictionary.getWords().get(i).getWord_explain());
        }
    }

    public void dictionaryBasic(Dictionary dictionary) {
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.insertFromCommandline(dictionary);
        showAllWords(dictionary);
    }*/

    public static void main(String[] args) {

        DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
        dictionaryCommandline.dictionaryAdvanced();
        //dictionaryCommandline.dictionaryBasic(dictionary);
        
    }
}
