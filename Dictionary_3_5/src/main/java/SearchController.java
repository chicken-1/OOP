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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SearchController extends GeneralController {
    public Button translateBtn;
    private DictionaryManagement dictionaryManagement;
    private Word selectedWord;
    private final ObservableList<Word> wordList = FXCollections.observableArrayList();
    private final String filepath = "D:\\Projects_workspace\\JAVAFX\\Dictionary_3_5\\src\\main\\base\\dictionaries.txt";

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
    private void searchFieldAction(KeyEvent event) throws IOException {
        String search = searchField.getText();
        search = search.toLowerCase();
        if (!search.equals("")) {
            List<Word> words = dictionaryManagement.dictionarySearcher(search);
            if (words.isEmpty()) {
                alertError("Không tìm thấy!");
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
    private void handleClickListView() throws IOException {
        this.selectedWord = wordListView.getSelectionModel().getSelectedItem();
        if (selectedWord == null) {
            // Show an error message
            alertError("Chưa có từ nào được chọn!");
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
                        "text-align: center; padding-top: 95px;'>" + word.getWord_target() + "</div>" +
                        "<div style='font-family: \"Dexa Round Med Ita\";" +
                        "font-size: 20px; color: #000000;" +
                        "text-align: center; padding-top: 50px;'>" + word.getWord_type() + "</div>" +
                        "<div style='font-family: \"Dexa Round Med\";" +
                        "font-size: 20px; color: #000000;" +
                        "text-align: center; padding-top: 20px;'>" + word.getWord_pronunciation() + "</div>" +
                        "<div style='font-family: \"DejaVu Serif\";" +
                        "font-size: 20px; color: #000000;" +
                        "text-align: center; padding-top: 20px;'>" + word.getWord_explain() + "</div>" +
                        "</body></html>");
    }

    @FXML
    private void handleAddButton(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("addWord.fxml"));
        Parent root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(updatedButton.getScene().getWindow());
        dialogStage.setScene(new Scene(root));
        dialogStage.setTitle("Thêm từ");

        AddController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        dialogStage.showAndWait();
        // if click OK
        if (dialogStage.getUserData().equals("1")) {
            // if word field is not null
            List<String> r = controller.getResult();
            System.out.println(r.get(0) + " " + r.get(1) + " " + r.get(2) + " " + r.get(3));

            if (!r.get(0).equals("Updating")) {
                System.out.println("hhello");
                Word newWord = new Word(r.get(0), r.get(1), r.get(2), r.get(3));
                if (dictionaryManagement.dictionaryLookup(newWord.getWord_target()) != null) {
                    alertError("Từ này đã tồn tại!");
                    return;
                }
                dictionaryManagement.addWord(newWord);
                dictionaryManagement.exportToFile(filepath);
                //wordList.add(newWord);
                wordListView.getSelectionModel().select(newWord);
            }
        }
    }

    @FXML
    private void handleClickSpeaker(ActionEvent actionEvent) throws IOException {
        if (selectedWord == null) {
            alertError("Chưa có từ nào được chọn!");
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
    private void handleRemoveButton(ActionEvent actionEvent) throws IOException {
        if (selectedWord == null) {
            alertError("Chưa có từ nào được chọn!");
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("removeWord.fxml"));
        Parent root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(removeButton.getScene().getWindow());
        dialogStage.setScene(new Scene(root));
        dialogStage.setTitle("Xóa từ");

        ExitController exitController = loader.getController();
        exitController.setDialogStage(dialogStage);
        dialogStage.showAndWait();

        if (exitController.dialogStage.getUserData().equals("1")) {
            dictionaryManagement.removeWord(selectedWord.getWord_target());
            dictionaryManagement.exportToFile(filepath);
            wordList.remove(selectedWord);
            wordListView.getSelectionModel().clearSelection();
            definitionView.setVisible(false);
    }
        }

    @FXML
    private void handleUpdateButton(ActionEvent actionEvent) throws IOException {

        if (selectedWord == null) {
            alertError("Chưa có từ nào được chọn!");
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("updateWord.fxml"));
        Parent root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(updatedButton.getScene().getWindow());
        dialogStage.setScene(new Scene(root));
        dialogStage.setTitle("Cập nhật từ");

        UpdateController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.init(selectedWord.getWord_target());
        dialogStage.showAndWait();

        // if click OK
        if (dialogStage.getUserData().equals("1")) {
            List<String> r = controller.getResult();

            if (!r.get(0).equals("Updating") && !r.get(1).equals("Updating") && !r.get(2).equals("Updating")) {
                Word updatedWord = new Word(selectedWord.getWord_target(), r.get(0), r.get(1), r.get(2));
                System.out.println(updatedWord.getWord_explain() + " " + updatedWord.getWord_pronunciation() + " " + updatedWord.getWord_type());
                dictionaryManagement.updatedWord(selectedWord, updatedWord);
                dictionaryManagement.exportToFile(filepath);
                wordList.set(wordListView.getSelectionModel().getSelectedIndex(), updatedWord);
                wordListView.getSelectionModel().select(updatedWord);
                handleClickListView();
            }
        }



    }

    public void alertError(String errorMsg) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("error.fxml"));
        Parent root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(updatedButton.getScene().getWindow());
        dialogStage.setScene(new Scene(root));
        dialogStage.setTitle("Lỗi");

        ErrorController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setErrorMsg(errorMsg);
        dialogStage.showAndWait();
    }
}
