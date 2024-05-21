package com.example.appproject;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Interface extends Application {

    Interpreter interpreter;

    @FXML protected Pane drawingPane;
    @FXML protected Pane cursorPane;
    @FXML protected ListView<Cursor> cursorListView;
    @FXML protected TextField posXField;
    @FXML protected TextField posYField;
    @FXML protected TextField angleField;
    @FXML protected TextField thicknessField;
    @FXML protected TextField redField;
    @FXML protected TextField greenField;
    @FXML protected TextField blueField;
    @FXML protected TextField distanceField;
    @FXML protected TextArea Console;
    @FXML protected ColorPicker colorPicker ;

    protected MapCursor mapCursor = new MapCursor();
    protected int cursorIdCounter = 1;
    protected int selectedCursorId = -1;
    Screen screen = Screen.getPrimary();

    // Obtenir la largeur et la hauteur de l'écran principal
    double screenWidth = screen.getBounds().getWidth();
    double screenHeight = screen.getBounds().getHeight();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Interface.class.getResource("App.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), screenWidth, screenHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dessin de Lignes avec Rectangle Dynamique");
        primaryStage.show();
    }

    @FXML
    protected void initialize() {
        cursorListView.setOnMouseClicked(event -> {
            Cursor selectedCursor = cursorListView.getSelectionModel().getSelectedItem();
            if (selectedCursor != null) {
                selectedCursorId = selectedCursor.getId();
            }
        });
    }

    @FXML
    protected void createCursorButtonClicked() {
        try {
            int posX = Integer.parseInt(posXField.getText());
            int posY = Integer.parseInt(posYField.getText());
            float angle = Float.parseFloat(angleField.getText());
            double thickness = Double.parseDouble(thicknessField.getText());
            int r = Integer.parseInt(redField.getText());
            int g = Integer.parseInt(greenField.getText());
            int b = Integer.parseInt(blueField.getText());

            checkPosition(posX, posY);

            Colorj customColor = new Colorj(r, g, b); // Créer une nouvelle instance de Colorj avec les valeurs RGB
            Cursor newCursor = new Cursor(posX, posY, angle, cursorIdCounter, thickness, true, customColor, 0);

            mapCursor.addCursor(newCursor);
            drawCursor(newCursor);

            cursorIdCounter++;
        } catch (NumberFormatException e) {
            Console.appendText("Error: Invalid input\n");
        } catch (OutOfPositionException e) {
            Console.appendText(e.getMessage() + "\n");
        }
    }

    @FXML
    protected void deleteCursorsButtonClicked() {
        cursorPane.getChildren().clear();
        cursorListView.getItems().clear();
        drawingPane.getChildren().clear();
        cursorIdCounter = 1;
        mapCursor.clearCursors();
    }
    @FXML
    protected void forwardButtonClicked() {
        try {
            int distance = Integer.parseInt(distanceField.getText());
            Cursor cursor = mapCursor.getCursorById(selectedCursorId);
            if (cursor != null) {
                int tempX = cursor.getPositionX();
                int tempY = cursor.getPositionY();
                cursor.forward(distance);

                checkPosition(cursor.getPositionX(), cursor.getPositionY());

                moveCursor(cursor);
                drawLine(tempX, tempY, cursor.getPositionX(), cursor.getPositionY(), cursor.getThickness(), cursor.getColorj()[0], cursor.getColorj()[1], cursor.getColorj()[2]);
            }
        } catch (NumberFormatException e) {
            Console.appendText("Error: Invalid input\n");
        } catch (OutOfPositionException e) {
            Console.appendText(e.getMessage() + "\n");
        }
    }

    @FXML
    protected void saveAsPngButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
        File file = fileChooser.showSaveDialog(drawingPane.getScene().getWindow());
        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int) drawingPane.getWidth(), (int) drawingPane.getHeight());
                drawingPane.snapshot(new SnapshotParameters(), writableImage);
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
            } catch (IOException ex) {
                Console.appendText("Error: Could not save image\n");
            }
            Console.appendText("image save TESTTT");
        }
    }

    protected void drawCursor(Cursor cursor) {
        if (cursor.isVisible()) {
            double cursorSize = 10;
            double angle = Math.toRadians(cursor.getDirection());

            // Calculate the coordinates of the triangle's vertices
            double x1 = cursor.getPositionX() + cursorSize * Math.cos(angle);
            double y1 = cursor.getPositionY() + cursorSize * Math.sin(angle);
            double x2 = cursor.getPositionX() + cursorSize * Math.cos(angle + 2 * Math.PI / 3);
            double y2 = cursor.getPositionY() + cursorSize * Math.sin(angle + 2 * Math.PI / 3);
            double x3 = cursor.getPositionX() + cursorSize * Math.cos(angle + 4 * Math.PI / 3);
            double y3 = cursor.getPositionY() + cursorSize * Math.sin(angle + 4 * Math.PI / 3);

            // Create the triangle shape
            Polygon triangle = new Polygon();
            triangle.getPoints().addAll(x1, y1, x2, y2, x3, y3);

            // Set the stroke width and fill color based on the cursor's properties
            triangle.setStrokeWidth(cursor.getThickness());
            triangle.setFill(Color.rgb(cursor.getColorj()[0], cursor.getColorj()[1], cursor.getColorj()[2]));


            // Add the triangle to the pane
            cursorPane.getChildren().add(triangle);
        }
        if (!cursorListView.getItems().contains(cursor)) {
            cursorListView.getItems().add(cursor);
        }
    }

    protected void moveCursor(Cursor cursor) {
        // Remove old cursor visual representation
        cursorPane.getChildren().remove(cursor.getId() - 1);

        // Update the cursor position in the ListView
        cursorListView.getItems().remove(cursor);
        cursorListView.getItems().add(cursor);

        // Redraw the cursor at its new position
        drawCursor(cursor);
    }

    protected void removeCursor(Cursor cursorToRemove) {
        for (Map.Entry<Integer, Cursor> entry : mapCursor.getCursors().entrySet()) {
            int cursorId = entry.getKey();
            Cursor cursor = entry.getValue();
            if (cursor == cursorToRemove) {
                cursorPane.getChildren().remove(cursorId - 1);
                mapCursor.removeCursor(cursorId);
                cursorListView.getItems().remove(cursor);
                break;
            }
        }
    }

    protected void drawLine(double startX, double startY, double endX, double endY, double stroke, int r, int g, int b) {
        try {
            checkLinePosition(startX, startY, endX, endY);

            Line line = new Line(startX, startY, endX, endY);
            line.setStrokeWidth(stroke);
            Color customColor = Color.rgb(r, g, b);
            line.setStroke(customColor);
            drawingPane.getChildren().add(line);
        } catch (OutOfPositionException e) {
            Console.appendText(e.getMessage() + "\n");
        }
    }

    @FXML
    protected void setBackground(){
        Color selectedColor = colorPicker.getValue();
        drawingPane.setStyle("-fx-background-color: #" + colorToHex(selectedColor) + ";");
        cursorPane.setStyle("-fx-background-color: #" + colorToHex(selectedColor) + ";");
    }

    protected String colorToHex(Color color) {
        return String.format("%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    protected void checkPosition(double x, double y) throws OutOfPositionException {
        if (x < 0 || x > drawingPane.getWidth() || y < 0 || y > drawingPane.getHeight()) {
            throw new OutOfPositionException("Error: Position out of Pane");
        }
    }
    @FXML
    public void scan(){
        String aExec = Console.getText();
        Interpreter.interpret(aExec,this,mapCursor,mapCursor.getCursorById(selectedCursorId));
    }

    protected void checkLinePosition(double startX, double startY, double endX, double endY) throws OutOfPositionException {
        checkPosition(startX, startY);
        checkPosition(endX, endY);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
