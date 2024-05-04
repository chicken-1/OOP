import base.DictionaryManagement;
import base.Word;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddWordController {
    @FXML
    private TextField txtWord;

    @FXML
    private TextField definition;

    private DictionaryManagement dictionaryManagement;

    public void setDictionaryManagement(DictionaryManagement dictionaryManagement) {
        this.dictionaryManagement = dictionaryManagement;
    }

    @FXML
    private void handleOKButton() {

    }
}
