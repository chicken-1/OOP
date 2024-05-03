
import base.Dictionary;
import base.DictionaryManagement;
import base.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

//import javax.script.CompiledScript;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;


public class SearchController {
    private DictionaryManagement dictionaryManagement;
    private final ObservableList<Word> wordList = FXCollections.observableArrayList();

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Word> wordListView;

    @FXML
    private WebView definitionView;

    @FXML
    private void initialize() {
        dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.insertFromFile("dictionary.txt");
        wordListView.setCellFactory(lv -> new ListCell<Word>() {
            @Override
            protected void updateItem(Word word, boolean empty) {
                super.updateItem(word, empty);
                if (empty || word == null) {
                    setText(null);
                } else {
                    setText(word.getWord_target());
                }
            }
        });
    }

    @FXML
    private void searchFieldAction(ActionEvent event) {
        String search = searchField.getText();
        List<Word> words = dictionaryManagement.dictionarySearcher(search);
        wordList.clear();
        wordList.addAll(words);
        wordListView.setItems(wordList);
    }

    @FXML
    private void handleClickListView() {
        Word selectedWord = wordListView.getSelectionModel().getSelectedItem();
        if (selectedWord == null) {
            // Show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No word selected");
            alert.setContentText("Please select a word");
            alert.showAndWait();
            return;
        }

        // Load the defination for the selected word
        String word_target = selectedWord.getWord_target();
        Word word = dictionaryManagement.dictionaryLookup(word_target);
        definitionView.getEngine().loadContent(word.getWord_explain());

    }
}
