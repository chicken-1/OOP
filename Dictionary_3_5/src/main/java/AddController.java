import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AddController {
    public Stage dialogStage;
    public Button OKBtn;
    public Button cancelBtn;
    public TextField wordType;
    public TextField wordPronounciation;
    public TextField wordMeaning;
    public TextField word;
    public Label msgAddFromAPI;

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    public void handleClickOK(ActionEvent actionEvent) {
        dialogStage.setUserData("1");
        Stage stage = (Stage) OKBtn.getScene().getWindow();
        stage.close();
    }

    public List<String> getResult() {
        List<String> result = new ArrayList<>();
        result.add(get_text(word.getText()));
        result.add(get_text(wordType.getText()));
        result.add(get_text(wordPronounciation.getText()));
        result.add(get_text(wordMeaning.getText()));

        return result;
    }

    public String get_text(String text) {
        if (text == null || text.isEmpty()) {
            return "Updating";
        }
        return text;
    }

    public void setMsgAddFromAPI(String msg) {
        msgAddFromAPI.setText(msg);
    }

    public void handleClickCancel(ActionEvent actionEvent) {
        dialogStage.setUserData("0");
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
