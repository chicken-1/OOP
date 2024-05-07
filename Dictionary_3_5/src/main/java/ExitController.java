import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ExitController extends GeneralController {
    public Button OKBtn;
    public Button cancelBtn;
    private Button gameButton;
    public Stage dialogStage;

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }
    public void handleClickOK(ActionEvent actionEvent) throws IOException {
        dialogStage.setUserData("1");
        Stage stage = (Stage) OKBtn.getScene().getWindow();
        stage.close();

    }

    public void handleClickCancel(ActionEvent actionEvent) {
        dialogStage.setUserData("0");
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
