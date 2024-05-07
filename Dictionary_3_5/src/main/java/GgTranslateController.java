import base.API_Translate;
import base.DictionaryManagement;
import base.Word;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.*;

public class GgTranslateController extends GeneralController{

    public Button enToViBtn;
    private Word translateWord;
    private DictionaryManagement dictionaryManagement;
    private final String filepath = "D:\\Projects_workspace\\JAVAFX\\Dictionary_3_5\\src\\main\\base\\dictionaries.txt";
    private boolean checkEN;

    @FXML
    private Label langFromLabel;

    @FXML
    private Label langToLabel;;

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

        // set up translate from EN to VI
        checkEN = true;
    }

    @FXML
    public void handlePressEnter(KeyEvent keyEvent) throws IOException {
        // // setup the length of 1 line in Area equal to Area's width
        //textToTranslate1.setWrapText(true);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String text = textToTranslate1.getText();
            text = text.replaceAll("\\n$", "");
            System.out.println(text.matches("\\w+"));
            // translate from EN to VI
            if (checkEN) {
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
            } else {
                String result = API_Translate.googleTranslate("vi", "en", text);
                // if result is a word
                if (result.matches("\\w+")) {
                    translateWord = new Word(result.toLowerCase(), text);
                    resultArea.setText(result.toLowerCase());
                } else {
                    String[] lines = result.split("\n");
                    StringBuilder sb = new StringBuilder();
                    for (String line : lines) {
                        sb.append(line).append("\n");
                    }
                    resultArea.setText(sb.toString());
                }
            }

        }
    }

    @FXML
    public void handleAddButton(ActionEvent actionEvent) {
        // if text is not a word
        if (translateWord == null) {
            alertError("Chỉ có thể thêm từ vào từ điển cá nhân!");
        } else {
            // Confirm addition
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Thêm từ");
            confirmAlert.setHeaderText("Thêm từ: " + translateWord.getWord_target());
            confirmAlert.setContentText("Bạn có muốn thêm từ này vào từ điển cá nhân của bạn không?");
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // if this word is already exist
                if (dictionaryManagement.dictionaryLookup(translateWord.getWord_target()) != null){
                    alertError("Từ này đã tồn tại!");
                    return;
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

    public void handleClickVi_En(ActionEvent actionEvent) {
        checkEN = false;
    }

    public void handleClickEn_Vi(ActionEvent actionEvent) {
        checkEN = true;
    }

    public void handleClickSpeaker(ActionEvent actionEvent) {
        String text;
        if (checkEN) {
            text = textToTranslate1.getText();
        } else {
            text = resultArea.getText();
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
                voice.speak(text);// Calling speak() method

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } else {
            throw new IllegalStateException("Cannot find voice: kevin16");
        }
    }
}
