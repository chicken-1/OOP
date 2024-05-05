import base.API_Translate;
import base.DictionaryManagement;
import base.Word;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GgTranslateController {
    private Word translateWord;
    private DictionaryManagement dictionaryManagement;
    private final String filepath = "D:\\Projects_workspace\\JAVAFX\\Dictionary_3_5\\src\\main\\base\\dictionaries.txt";
    //private API_Translate apiTranslate;

    @FXML
    private TextArea textToTranslate1;

    @FXML
    private TextArea resultArea;

    @FXML
    public void initialize() {
        dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.insertFromFile(filepath);

        // setup the length of 1 line in Area equal to Area's width
        textToTranslate1.setWrapText(true);
        resultArea.setWrapText(true);

    }

    @FXML
    public void handlePressEnter(KeyEvent keyEvent) throws IOException {
        // // setup the length of 1 line in Area equal to Area's width
        //textToTranslate1.setWrapText(true);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String text = textToTranslate1.getText();
            text = text.replaceAll("\\n$", "");
            System.out.println(text.matches("\\w+"));
            // if text is a word
            if (text.matches("\\w+")) {
                String word_explain = API_Translate.googleTranslate("en", "vi", text).toLowerCase();
                translateWord = new Word(text, word_explain);
                resultArea.setText(word_explain.toLowerCase());
            } else {
                String result = API_Translate.googleTranslate("en", "vi", text);
                String[] lines = result.split("\n");
                StringBuilder sb = new StringBuilder();
                for (String line : lines) {
                    sb.append(line).append("\n");
                }

                // set text to resultArea
                resultArea.setText(sb.toString());
            }
        }
    }

    @FXML
    public void handleAddButton(ActionEvent actionEvent) {
        // if text is not a word
        if (translateWord == null) {
            alertError("Can only add word to personal dictionary!");
        } else {
            // Confirm addition
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm addition");
            confirmAlert.setHeaderText("Add word: " + translateWord.getWord_target());
            confirmAlert.setContentText("Are you sure you want to add this word to your dictionary?");
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // if this word is already exist
                if (dictionaryManagement.dictionaryLookup(translateWord.getWord_target()) != null){
                    alertError("This word already exists!");
                }
                dictionaryManagement.addWord(translateWord);
                dictionaryManagement.exportToFile(filepath);
            }
        }
    }

    public void alertError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
