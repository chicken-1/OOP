import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorController extends GeneralController {
    public Button OKBtn;
    public Stage dialogStage;
    public Label errorMsg;

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    public void handleClickOK(ActionEvent actionEvent) throws IOException {
        dialogStage.setUserData("1");
        Stage stage = (Stage) OKBtn.getScene().getWindow();
        stage.close();
    }

    public void setErrorMsg(String msg) {
        errorMsg.setText(msg);
    }
}
