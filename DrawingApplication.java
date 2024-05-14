package projetgl.chromatynk;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

public class DrawingApplication extends Application {
    private CommandInterpreter interpreter;
    private Canvas canvas;
    private GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();

        Cursor cursor = new Cursor();
        interpreter = new CommandInterpreter(cursor, gc);

        // Initial drawing settings
        gc.beginPath();
        gc.setStroke(Color.web(cursor.getColor()));
        gc.setLineWidth(cursor.getThickness());
        gc.moveTo(cursor.getX(), cursor.getY());

        TextArea commandInput = new TextArea();
        commandInput.setPromptText("Enter command (e.g., 'fwd 100', 'turn 90', 'color #FF0000', 'thickness 5')");

        commandInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String command = commandInput.getText().trim();
                interpreter.interpret(command);
                updateDrawing(cursor);  // Update drawing based on the cursor's new position
                commandInput.clear();
                event.consume();
            }
        });

        root.setCenter(canvas);
        root.setBottom(commandInput);

        Scene scene = new Scene(root, 800, 650);
        primaryStage.setTitle("Drawing Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateDrawing(Cursor cursor) {
        gc.lineTo(cursor.getX(), cursor.getY());
        gc.stroke();
        gc.beginPath();  // Begin a new drawing path
        gc.moveTo(cursor.getX(), cursor.getY());  // Move to the new cursor location
    }

    public static void main(String[] args) {
        launch(args);
    }
}
