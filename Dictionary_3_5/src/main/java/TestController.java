import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;

public class TestController {
    DraggableMaker draggableMaker = new DraggableMaker();


    @FXML
    private TextField source;
    @FXML
    private HBox targetBox;

    public void initialize() {
        //draggableMaker.makeDraggable(aloField, targetBox);
        //source.setOnMousePressed(eve);
    }


    public void handleDragDetect(MouseEvent mouseEvent) {
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putString(source.getText());

        db.setContent(content);

        mouseEvent.consume();
    }

    public void handleDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasString()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void handleDragDrop(DragEvent dragEvent) {
        String str = dragEvent.getDragboard().getString();
        targetBox.getChildren().clear();
        TextField a = new TextField(str);
        targetBox.getChildren().add(a);
        //dragEvent.acceptTransferModes(TransferMode.COPY);
    }
}

