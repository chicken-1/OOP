import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Result2Controller {
    public Stage dialogStage;
    public Button OKBtn;
    public Label point;

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    public void init(int score) {
        point.setText(String.valueOf(score));
    }
    public void handleClickOK(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) OKBtn.getScene().getWindow();
        stage.close();

    }
}
