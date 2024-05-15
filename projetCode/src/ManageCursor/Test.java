package com.example.demo5;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Test extends Application {
    private List<Line> drawnLines = new ArrayList<>();
    private int selectedCursorId = 1;
    Cursor selectedCursor;


    private List<Cursor> visibleCursors = new ArrayList<>();
    private Pane cursorPane;
    private Pane drawingPane;
    private MapCursor mapCursor = new MapCursor();
    private ListView<Cursor> cursorListView;

    private int cursorIdCounter = 1;

    protected void drawLine(double startX, double startY, double endX, double endY, int stroke, int r, int g, int b) {
        double maxStartX = drawingPane.getWidth() - 5;
        double maxStartY = drawingPane.getHeight() - 5;
        double maxEndX = drawingPane.getWidth() - 5;
        double maxEndY = drawingPane.getHeight() - 5;

        startX = Math.max(0, Math.min(startX, maxStartX));
        startY = Math.max(0, Math.min(startY, maxStartY));
        endX = Math.max(0, Math.min(endX, maxEndX));
        endY = Math.max(0, Math.min(endY, maxEndY));

        Line line = new Line(startX, startY, endX, endY);
        line.setStrokeWidth(stroke);
        Color customColor = Color.rgb(r, g, b);
        line.setStroke(customColor);
        drawnLines.add(line);

        drawingPane.getChildren().add(line);
    }

    private void drawCursor(Cursor cursor) {
        double size = 10; // Taille du carré
        Rectangle cursorRect = new Rectangle(cursor.getPositionX(), cursor.getPositionY(), size, size);
        cursorRect.setStrokeWidth(cursor.getThickness());
        System.out.println(cursor.getColorj()[0]);
        cursorRect.setFill(Color.rgb(cursor.getColorj()[0],cursor.getColorj()[1],cursor.getColorj()[2]));

        cursorPane.getChildren().add(cursorRect);
        visibleCursors.add(cursor);
        cursorListView.getItems().add(cursor);
        System.out.println(cursor.toString());
    }
    private void removeCursor(Cursor cursorToRemove) {
        for (int i = 0; i < visibleCursors.size(); i++) {
            Cursor cursor = visibleCursors.get(i);
            if (cursor == cursorToRemove) {
                cursorPane.getChildren().remove(i);

                visibleCursors.remove(i);

                cursorListView.getItems().remove(i);

                break;
            }
        }
    }
    private void clearCursors() {
        cursorIdCounter = 1;
        cursorPane.getChildren().clear();
        visibleCursors.clear();
        cursorListView.getItems().clear();
    }

    private void clearDrawnLines() {
        drawingPane.getChildren().removeAll(drawnLines);

        drawnLines.clear();
    }

    private void clear(){
        clearCursors();
        clearDrawnLines();
    }



    private void moveCursor(Cursor cursor){
        removeCursor(cursor);
        drawCursor(cursor);

    }
    private void forward(int dist, Cursor cursor){
        int tempX = cursor.getPositionX();
        int tempY = cursor.getPositionY();
        cursor.forward(dist);
        moveCursor(cursor);
        drawLine(tempX,tempY,cursor.getPositionX(),cursor.getPositionY(),cursor.getThickness(),cursor.getColorj()[0],cursor.getColorj()[1],cursor.getColorj()[2]);

    }
    private Parent createContent() {
        GridPane contentPane = new GridPane();
        contentPane.setGridLinesVisible(true);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(35);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(35);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        contentPane.getColumnConstraints().addAll(col1, col2, col3);

        cursorListView = new ListView<>();

        drawingPane = new Pane();
        cursorPane = new Pane();
        contentPane.add(drawingPane, 0, 0, 2, 1);
        drawingPane.minWidthProperty().bind(contentPane.widthProperty().multiply(0.7));
        drawingPane.maxWidthProperty().bind(contentPane.widthProperty().multiply(0.7));
        drawingPane.minHeightProperty().bind(drawingPane.widthProperty().multiply(0.5));
        drawingPane.maxHeightProperty().bind(drawingPane.widthProperty().multiply(0.5));
        drawingPane.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));


        contentPane.add(cursorPane, 0, 0, 2, 1);
        cursorPane.minWidthProperty().bind(contentPane.widthProperty().multiply(0.7));
        cursorPane.maxWidthProperty().bind(contentPane.widthProperty().multiply(0.7));
        cursorPane.minHeightProperty().bind(cursorPane.widthProperty().multiply(0.5));
        cursorPane.maxHeightProperty().bind(cursorPane.widthProperty().multiply(0.5));

        Pane interactivePane = createInteractivePane();
        Button saveButton = new Button("Save");
        Button deleteCursorsButton = new Button("Supprimer tous les curseurs");

        deleteCursorsButton.setOnAction(event -> {
            clear();
        });
        Button forwardButton = new Button("Forward");
        TextField distanceField = new TextField();
        distanceField.setPromptText("Distance");

        forwardButton.setOnAction(event -> {
            String distanceText = distanceField.getText();
                try {
                    int distance = Integer.parseInt(distanceText);
                    forward(distance,mapCursor.getCursorById(selectedCursorId));
                    moveCursor(mapCursor.getCursorById(selectedCursorId));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

        });
        cursorListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectCursor) -> {
            if (selectCursor instanceof Cursor) {
                selectedCursor = (Cursor) selectCursor;
                selectedCursorId = selectedCursor.getId();
            }
        });


        contentPane.add(cursorListView, 0, 3, 2, 1);
        contentPane.add(interactivePane, 0, 1, 2, 1);
        contentPane.add(saveButton, 2, 2, 1, 1);
        contentPane.add(deleteCursorsButton, 0, 2, 1, 1);
        contentPane.add(forwardButton, 2, 1,1,1);
        contentPane.add(distanceField, 2, 2,1,1);

        contentPane.setPadding(new Insets(10));
        return contentPane;
    }

    private Pane createInteractivePane() {
        TextField posXField = new TextField();
        posXField.setPromptText("Position X");
        TextField posYField = new TextField();
        posYField.setPromptText("Position Y");
        TextField angleField = new TextField();
        angleField.setPromptText("Angle");
        TextField thicknessField = new TextField();
        thicknessField.setPromptText("Épaisseur");
        TextField redField = new TextField();
        redField.setPromptText("Rouge (0-255)");
        TextField greenField = new TextField();
        greenField.setPromptText("Vert (0-255)");
        TextField blueField = new TextField();
        blueField.setPromptText("Bleu (0-255)");

        Button createCursorButton = new Button("Créer Curseur");
        createCursorButton.setOnAction(event -> {
            try {
                int posX = Integer.parseInt(posXField.getText());
                int posY = Integer.parseInt(posYField.getText());
                float angle = Float.parseFloat(angleField.getText());
                int thickness = Integer.parseInt(thicknessField.getText());
                int r = Integer.parseInt(redField.getText());
                int g = Integer.parseInt(greenField.getText());
                int b = Integer.parseInt(blueField.getText());

                Colorj customColor = new Colorj(r, g, b); // Créer une nouvelle instance de Colorj avec les valeurs RGB
                Cursor newCursor = new Cursor(posX, posY, angle, cursorIdCounter, thickness, false, customColor);


                mapCursor.addCursor(newCursor);
                drawCursor(newCursor);
                cursorIdCounter++;

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        gridPane.add(posXField, 0, 0);
        gridPane.add(posYField, 1, 0);
        gridPane.add(angleField, 0, 1);
        gridPane.add(thicknessField, 1, 1);
        gridPane.add(redField, 0, 2);
        gridPane.add(greenField, 1, 2);
        gridPane.add(blueField, 0, 3);
        gridPane.add(createCursorButton, 0, 4);

        return gridPane;
    }

    @Override
    public void start(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Parent content = createContent();
        Scene scene = new Scene(content, screenBounds.getWidth(), screenBounds.getHeight(), Color.GRAY);
        stage.setScene(scene);
        stage.setTitle("Dessin de Lignes avec Rectangle Dynamique");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
