import base.DictionaryManagement;
import base.Word;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SearchController extends GeneralController {
    public Button translateBtn;
    private DictionaryManagement dictionaryManagement;
    private Word selectedWord;
    private final ObservableList<Word> wordList = FXCollections.observableArrayList();
    private final String filepath = "D:\\App\\Scene Builder\\tempOOP\\OOP\\Dictionary_3_5\\src\\main\\base\\dictionaries.txt";

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
    private Button gameButton;

    @FXML
    private WebView definitionView;

    @FXML
    private HTMLEditor editDefinition;

    @FXML
    private void initialize() {
        dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.insertFromFile(filepath);

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
        search = search.toLowerCase();
        if (!search.equals("")) {
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
        if (search.equals("")) {
            definitionView.getEngine().load(null);
            wordList.clear();
        }
    }

    @FXML
    private void handleClickListView() {
        this.selectedWord = wordListView.getSelectionModel().getSelectedItem();
        if (selectedWord == null) {
            // Show an error message
            noneSelectedWord_alert();
            return;
        }

        // Load the defination for the selected word
        String word_target = selectedWord.getWord_target();
        Word word = dictionaryManagement.dictionaryLookup(word_target);

        // set up presentation of difinitionView
        definitionView.getEngine().loadContent(
                "<html><head><link rel='stylesheet' type='text/css' href='search.css'></head><body>" +
                        "<div style='font-family: \"SVN-Gilroy Heavy\";" +
                        "font-size: 40px; color: #1D93F3;" +
                        "text-align: left; padding-top: 95px;'>" + word.getWord_target() + "</div>" +
                        "<div style='font-family: \"Dexa Round Med Ita\";" +
                        "font-size: 20px; color: #000000;" +
                        "text-align: left; padding-top: 50px;'>" + word.getWord_type() + "</div>" +
                        "<div style='font-family: \"Dexa Round Med\";" +
                        "font-size: 20px; color: #000000;" +
                        "text-align: left; padding-top: 20px;'>" + word.getWord_pronunciation() + "</div>" +
                        "<div style='font-family: \"DejaVu Serif\";" +
                        "font-size: 20px; color: #000000;" +
                        "text-align: left; padding-top: 20px;'>" + word.getWord_explain() + "</div>" +
                        "</body></html>");
    }

    @FXML
    private void handleAddButton(ActionEvent actionEvent) {
        // Create a new dialog for adding a word
        Dialog<Word> dialog = new Dialog<>();
        dialog.setTitle("Thêm từ");
        dialog.setHeight(292.00);
        dialog.setWidth(421.00);

        // Create the word and definition fields
        TextField wordField = new TextField();
        wordField.setPromptText("Từ:");
        TextField wordTypeFeild = new TextField();
        wordTypeFeild.setPromptText("Loại từ:");
        TextField pronunciationField = new TextField();
        pronunciationField.setPromptText("Phát âm:");
        TextField definitionField = new TextField();
        definitionField.setPromptText("Nghĩa:");


        // Create the dialog content
        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);
        content.add(new Label("Từ:"), 0, 0);
        content.add(wordField, 1, 0);
        content.add(new Label("Loại từ:"), 0, 1);
        content.add(wordTypeFeild, 1, 1);
        content.add(new Label("Phát âm:"), 0, 2);
        content.add(pronunciationField, 1, 2);
        content.add(new Label("Nghĩa:"), 0, 3);
        content.add(definitionField, 1, 3);


        // Set the dialog content
        dialog.getDialogPane().setContent(content);

        // Create the OK and Cancel buttons
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);

        // Create the result converter
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType && !wordField.getText().trim().isEmpty()) {
                Word word = new Word(wordField.getText(), wordTypeFeild.getText(), pronunciationField.getText(), definitionField.getText());
                return word;
            }
            return null;
        });

        // Show the dialog and get the result
        Optional<Word> result = dialog.showAndWait();
        if (result.isPresent()) {
            Word newWord = result.get();
            dictionaryManagement.addWord(newWord);
            dictionaryManagement.exportToFile(filepath);
            //wordList.add(newWord);
            wordListView.getSelectionModel().select(newWord);
        }
    }

    @FXML
    private void handleClickSpeaker(ActionEvent actionEvent) {
        if (selectedWord == null) {
            noneSelectedWord_alert();
            return;
        }
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

    @FXML
    private void handleRemoveButton(ActionEvent actionEvent) {
        if (selectedWord == null) {
            noneSelectedWord_alert();
            return;
        }
        // Confirm removal
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Xóa");
        confirmAlert.setHeaderText("Xóa từ: " + selectedWord.getWord_target());
        confirmAlert.setContentText("Bạn có muốn xóa từ này khỏi từ điển cá nhân của bạn?");
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // remove the word from dictionary
            dictionaryManagement.removeWord(selectedWord.getWord_target());
            dictionaryManagement.exportToFile(filepath);
            wordList.remove(selectedWord);
            wordListView.getSelectionModel().clearSelection();
            editDefinition.setVisible(false);
        }
    }

    @FXML
    private void handleUpdateButton(ActionEvent actionEvent) {

        if (selectedWord == null) {
            noneSelectedWord_alert();
            return;
        }
        Dialog<Word> dialog = new Dialog<>();
        dialog.setTitle("Cập nhật");
        dialog.setHeaderText("Cập nhật từ " + selectedWord.getWord_target() + " : ");
        dialog.setHeight(292.00);
        dialog.setWidth(421.00);

        // Create definitionField
        TextField wordTypeFeild = new TextField();
        wordTypeFeild.setPromptText("Loại từ:");
        TextField pronunciationField = new TextField();
        pronunciationField.setPromptText("Phát âm:");
        TextField definitionField = new TextField();
        definitionField.setPromptText("Nghĩa:");

        // Create the dialog content
        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);
        content.add(new Label("Loại từ:"), 0, 0);
        content.add(wordTypeFeild, 1, 0);
        content.add(new Label("Phát âm:"), 0, 1);
        content.add(pronunciationField, 1, 1);
        content.add(new Label("Nghĩa:"), 0, 2);
        content.add(definitionField, 1, 2);

        // Set the dialog content
        dialog.getDialogPane().setContent(content);

        // Create OK and Cancel button
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);

        // Create result converter
        dialog.setResultConverter(buttonType -> {
            if (buttonType == okButtonType) {
                return new Word(selectedWord.getWord_target(), wordTypeFeild.getText().replace(" ", ""), pronunciationField.getText().replace(" ", ""), definitionField.getText());
            }
            return null;
        });

        // Show the dialod and get the result
        Optional<Word> result = dialog.showAndWait();
        if (result.isPresent()) {
            Word updatedWord = result.get();
            dictionaryManagement.updatedWord(selectedWord, updatedWord);
            dictionaryManagement.exportToFile(filepath);
            wordList.set(wordListView.getSelectionModel().getSelectedIndex(), updatedWord);
            wordListView.getSelectionModel().select(updatedWord);
            handleClickListView();

        }
    }

    public void noneSelectedWord_alert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText("Chưa có từ nào được chọn.");
        alert.setContentText("Hãy chọn một từ để thực hiện thao tác này.");
        alert.showAndWait();
    }
}
