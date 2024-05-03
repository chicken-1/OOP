package base;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    private List<Word> words = new ArrayList<Word>();

    public Dictionary() {
        words = new ArrayList<Word>();
    }

    public void addWord(Word word) {
        words.add(word);
    }

    public List<Word> getDictionary() {
        return words;
    }

    public void deleteWord(Word word) {
        words.remove(word);
    }

    public Word searchWord(String wordTarget) {
        for (Word word : words) {
            if (word.getWord_target().equals(wordTarget)) {
                return word;
            }
        }
        return null;
    }

    public void updateWord(Word wordToUpdate, Word newWord) {
        int index = words.indexOf(wordToUpdate);
        if (index != -1) 
            words.set(index, newWord);
        }

    public void display() {
        System.out.println("No     \t|English     \t|Vietnamese");
        for (int i = 0; i < words.size(); i++) {
            System.out.println(i + 1 + "     \t|" + words.get(i).getWord_target() + "     \t|" + words.get(i).getWord_explain());
        }
    }

    public List<Word> searchWords(String searchKeyword) {
        List<Word> searchResults = new ArrayList<Word>();
        for (Word word : words) {
            if (word.getWord_target().contains(searchKeyword)) {
                searchResults.add(word);
            }
        }
        return searchResults;
    }

    public int size() {
        return words.size();
    }

    public Word getWord(int index) {
        return words.get(index);
    }
}


