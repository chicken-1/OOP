
import base.DictionaryManagement;
import base.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;


import java.util.List;
import java.util.Optional;


public class SearchController {
    private DictionaryManagement dictionaryManagement;
    private Word selectedWord;
    private final ObservableList<Word> wordList = FXCollections.observableArrayList();

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Word> wordListView;

    @FXML
    private WebView definitionView;

    @FXML
    private ToolBar toolBar;

    @FXML
    private Button updateButton;

    @FXML
    private Button removeButton;

    @FXML
    private HTMLEditor editDefinition;

    @FXML
    private void initialize() {
        dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.insertFromFile("D:\\App\\Scene Builder\\OOP\\Dictionary_3_5\\src\\main\\base\\dictionaries.txt");
        toolBar = new ToolBar();

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
    private void searchFieldAction(KeyEvent event) {
        String search = searchField.getText();
        List<Word> words = dictionaryManagement.dictionarySearcher(search);
        if (words.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No words found");
            alert.showAndWait();
            return;
        }
        wordList.clear();
        wordList.addAll(words);
        wordListView.setItems(wordList);
    }

    @FXML
    private void handleClickListView() {
        this.selectedWord = wordListView.getSelectionModel().getSelectedItem();
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
        /*editDefinition.setHtmlText("<html><body><h1>" + word.getWord_target() + "</h1><p>" + word.getWord_explain() + "</p></body></html>");
        editDefinition.setVisible(true);*/
        definitionView.getEngine().loadContent(
                "<html><head><link rel='stylesheet' type='text/css' href='search.css'></head><body>" +
                        "<div style='font-family: \"SVN-Gilroy Heavy\";" +
                        "font-size: 40px; color: #1D93F3;" +
                        "text-align: center; padding-top: 90px;'>" + word.getWord_target() + "</div>" +
                        "<div style='font-family: \"SVN-Gilroy Medium\";" +
                        "font-size: 20px; color: #000000;" +
                        "text-align: center; padding-top: 20px;'>" + word.getWord_explain() + "</div>" +
                        "</body></html>");

    }

    @FXML
    private void handleUpdateButton(ActionEvent actionEvent) {

    }

    @FXML
    private void handleClickSpeaker(ActionEvent actionEvent) {

    }

    @FXML
    private void handleRemoveButton(ActionEvent actionEvent) {
        // Confirm removal
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm removal");
        confirmAlert.setHeaderText("Remove word: " + selectedWord.getWord_target());
        confirmAlert.setContentText("Are you sure you want to remove this word?");
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // remove the word from dictionary
            dictionaryManagement.removeWord(selectedWord.getWord_target());
            dictionaryManagement.exportToFile("D:\\App\\Scene Builder\\OOP\\Dictionary_3_5\\src\\main\\base\\dictionaries.txt");
            wordList.remove(selectedWord);
            wordListView.getSelectionModel().clearSelection();
            editDefinition.setVisible(false);
        }
    }
}
