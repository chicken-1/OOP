
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
        dictionaryManagement.insertFromFile("D:\\Projects_workspace\\JAVAFX\\Dictionary_3_5\\src\\main\\base\\dictionaries.txt");
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
        /*editDefinition.setHtmlText("<html><body><h1>" + word.getWord_target() + "</h1><p>" + word.getWord_explain() + "</p></body></html>");
        editDefinition.setVisible(true);*/
        definitionView.getEngine().loadContent("<html><head><link rel='stylesheet' type='text/css' href='style.css'></head><body>" +
                "<div class='word-target'>" + word.getWord_target() + "</div>" +
                "<div class='word-explain'>" + word.getWord_explain() + "</div>" +
                "</body></html>");
    }

    @FXML
    private void handleUpdateButton(ActionEvent actionEvent) {

    }

    @FXML
    private void handleRemoveButton(ActionEvent actionEvent) {

    }

    @FXML
    private void handleClickSpeaker(ActionEvent actionEvent) {

    }
}
