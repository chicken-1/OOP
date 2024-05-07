import base.DictionaryManagement;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class Game3Controller extends GameController {

    @FXML
    private ImageView correct;
    @FXML
    private ImageView incorrect;
    @FXML
    private Label correctAnswer;
    @FXML
    private Label correctAnsTittle;
    @FXML
    private Button exitBtn;
    @FXML
    private AnchorPane anchorGamePane;
    @FXML
    private TextField answer;
    @FXML
    private Button nextBtn;

    private String word;

    public void initialize() {
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.insertFromFile("D:\\Projects_workspace\\JAVAFX\\Dictionary_3_5\\src\\main\\base\\dictionaries.txt");
        Random rand = new Random();
        int randomNum = rand.nextInt(dictionaryManagement.dictionary.size());
        word = dictionaryManagement.dictionary.getWord(randomNum).getWord_target();
        correctAnsTittle.setVisible(false);
        correctAnswer.setVisible(false);
        correct.setVisible(false);
        incorrect.setVisible(false);
        answer.setText("");
        System.out.println(word);
    }

    @FXML
    public void handleClickSpeaker(ActionEvent actionEvent) {
        setupSpeaker(160);
    }

    @FXML
    public void handleClickSlow(ActionEvent actionEvent) {
        setupSpeaker(80);

    }

    @FXML
    public void handleClickExit(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("exit.fxml"));
        Parent root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(exitBtn.getScene().getWindow());
        dialogStage.setScene(new Scene(root));
        dialogStage.setTitle("Thoát");

        ExitController exitController = loader.getController();
        exitController.setDialogStage(dialogStage);
        dialogStage.showAndWait();

        if (exitController.dialogStage.getUserData().equals("1")) {
            anchorGamePane.getChildren().clear();
        }
    }

    @FXML
    public void handleClickSubmit(ActionEvent actionEvent) {
        String Answer = answer.getText().toLowerCase().replace(" ", "");
        System.out.println(Answer.equals(word));
        if (!Answer.equals(word)) {
            correctAnsTittle.setVisible(true);
            correctAnswer.setVisible(true);
            correctAnswer.setText(word);
            incorrect.setVisible(true);
        } else {
            correct.setVisible(true);
        }
    }

    public void setupSpeaker (int rate) {
        Voice voice;
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();// Allocating Voice
            try {
                voice.setRate(rate);
                voice.setStyle("casual");
                voice.setVolume(5);// Setting the volume of the voice
                voice.speak(word);// Calling speak() method

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } else {
            throw new IllegalStateException("Cannot find voice: kevin16");
        }
    }

    @FXML
    public void handleClickNext(ActionEvent actionEvent) {
        initialize();
    }
}
