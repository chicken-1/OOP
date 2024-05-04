import base.DictionaryManagement;
import base.Word;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
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
    private Button addButton;

    @FXML
    private Button updatedButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button buttonSpeaker;

    @FXML
    private WebView definitionView;

    @FXML
    private HTMLEditor editDefinition;

    @FXML
    private void initialize() {
        dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.insertFromFile("D:\\Projects_workspace\\JAVAFX\\Dictionary_3_5\\src\\main\\base\\dictionaries.txt");

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
    private void handleAddButton(ActionEvent actionEvent) {
        // Create a new dialog for adding a word
        Dialog<Word> dialog = new Dialog<>();
        dialog.setTitle("Add Word");
        dialog.setHeaderText("Enter the new word and its definition:");
        dialog.setHeight(292.00);
        dialog.setWidth(421.00);

        // Create the word and definition fields
        TextField wordField = new TextField();
        wordField.setPromptText("Word");
        TextField definitionField = new TextField();
        definitionField.setPromptText("Definition");

        // Create the dialog content
        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);
        content.add(new Label("Word:"), 0, 0);
        content.add(wordField, 1, 0);
        content.add(new Label("Definition:"), 0, 1);
        content.add(definitionField, 1, 1);

        // Set the dialog content
        dialog.getDialogPane().setContent(content);

        // Create the OK and Cancel buttons
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);

        // Create the result converter
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                Word word = new Word(wordField.getText(), definitionField.getText());
                return word;
            }
            return null;
        });

        // Show the dialog and get the result
        Optional<Word> result = dialog.showAndWait();
        if (result.isPresent()) {
            Word newWord = result.get();
            dictionaryManagement.addWord(newWord);
            dictionaryManagement.exportToFile("D:\\Projects_workspace\\JAVAFX\\Dictionary_3_5\\src\\main\\base\\dictionaries.txt");
            wordList.add(newWord);
            wordListView.getSelectionModel().select(newWord);
        }
    }

    @FXML
    private void handleClickSpeaker(ActionEvent actionEvent) {
        Voice voice;
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();// Allocating Voice
            try {
                voice.setRate(160);
                voice.setStyle("casual");
                voice.setVolume(5);// Setting the volume of the voice
                voice.speak(selectedWord.getWord_target());// Calling speak() method

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } else {
            throw new IllegalStateException("Cannot find voice: kevin16");
        }
    }

    public void showError(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(s);
        alert.showAndWait();
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
            dictionaryManagement.exportToFile("D:\\Projects_workspace\\JAVAFX\\Dictionary_3_5\\src\\main\\base\\dictionaries.txt");
            wordList.remove(selectedWord);
            wordListView.getSelectionModel().clearSelection();
            editDefinition.setVisible(false);
        }
    }

    @FXML
    private void handleUpdateButton(ActionEvent actionEvent) {

        Dialog<Word> dialog = new Dialog<>();
        dialog.setTitle("Update Word");
        dialog.setHeaderText("Enter the new definition of " + selectedWord.getWord_target() + " : ");
        dialog.setHeight(292.00);
        dialog.setWidth(421.00);

        // Create definitionField
        TextField definitionField = new TextField();
        definitionField.setPromptText("Definition");

        // Create the dialog content
        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);
        content.add(new Label("Definition:"), 0, 0);
        content.add(definitionField, 1, 0);

        // Set the dialog content
        dialog.getDialogPane().setContent(content);

        // Create OK and Cancel button
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);

        // Create result converter
        dialog.setResultConverter(buttonType -> {
            if (buttonType == okButtonType) {
                return new Word(selectedWord.getWord_target(), definitionField.getText());
            }
            return null;
        });

        // Show the dialod and get the result
        Optional<Word> result = dialog.showAndWait();
        if (result.isPresent()) {
            Word updatedWord = result.get();
            dictionaryManagement.updatedWord(selectedWord, updatedWord);
            dictionaryManagement.exportToFile("D:\\Projects_workspace\\JAVAFX\\Dictionary_3_5\\src\\main\\base\\dictionaries.txt");
            wordList.set(wordListView.getSelectionModel().getSelectedIndex(), updatedWord);
            wordListView.getSelectionModel().select(updatedWord);
            handleClickListView();

        }
    }
}
