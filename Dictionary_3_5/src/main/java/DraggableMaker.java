import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;

public class DraggableMaker {
    private double mouseX;
    private double mouseY;

    public void makeDraggable(TextField node, HBox target) {
        node.setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();

            Dragboard db = node.startDragAndDrop(TransferMode.ANY);

            db.setDragView(node.snapshot(null, null));

            ClipboardContent content = new ClipboardContent();
            content.putString(node.getId());
            db.setContent(content);

            event.consume();
        });

       node.setOnMouseDragged(event -> {
            node.setLayoutX(event.getScreenX() - mouseX - 300);
            node.setLayoutY(event.getScreenY() - mouseY + 20);
        });

        node.setOnDragDone(event -> {
            // Remove the node from its original parent
            node.getParent().getChildrenUnmodifiable().remove(node);

            // Add the node to the target
            target.getChildren().add(node);
        });



        target.setOnDragOver(event -> {
            // Allow the drag operation
            event.acceptTransferModes(TransferMode.ANY);
        });
/*
        target.setOnDragDropped(event -> {
            // Get the node being dragged
            Node draggedNode = (Node<String>) event.getDragboard().getContent(DataFormat.STRING);

            // Add the node to the target
            target.getChildren().add(draggedNode);

            event.setDropCompleted(true);
        });*/



    }
}
