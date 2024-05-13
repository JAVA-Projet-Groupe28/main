package com.example.demo3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Test extends Application {

    private List<Line> lines = new ArrayList<>();
    private Pane drawingPane;
    /*
    private Rectangle rectangle;
        */
    private void drawLine(double startX, double startY, double endX, double endY,int stroke,int r,int g, int b) {
        double maxStartX = drawingPane.getWidth() - 5; // 5 est la marge
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

        lines.add(line);
        drawingPane.getChildren().add(line);
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
        contentPane.getColumnConstraints().addAll(col1, col2,col3);

        /*
        rectangle = new Rectangle();
        rectangle.setFill(Color.BLUE);


        rectangle.widthProperty().bind(contentPane.widthProperty().multiply(0.7));
        rectangle.heightProperty().bind(rectangle.widthProperty().multiply(0.5));

        */
        drawingPane = new Pane();
        contentPane.add(drawingPane, 0, 0, 2, 1);
        drawingPane.minWidthProperty().bind(contentPane.widthProperty().multiply(0.7));
        drawingPane.maxWidthProperty().bind(contentPane.widthProperty().multiply(0.7));

        drawingPane.minHeightProperty().bind(drawingPane.widthProperty().multiply(0.5));
        drawingPane.maxHeightProperty().bind(drawingPane.widthProperty().multiply(0.5));
        drawingPane.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
        Pane interactivePane = createInteractivePane();


        Button save = new Button("save");
        /*enregistrement du Pane drawingPane il manque encore le fait d'enregistrer le fichier
        et faut rajouter le lien avec le bouton save
         */
        WritableImage image = drawingPane.snapshot(null, null);
        File file = new File("pane_snapshot.png");
        /**/


        contentPane.add(interactivePane, 0, 1, 2, 1);
        contentPane.add(save, 2, 2, 1, 1);
        contentPane.setGridLinesVisible(true);
        return contentPane;
    }

    private Pane createInteractivePane() {
        TextField startXField = new TextField();
        startXField.setPromptText("Coordonnée x de départ");
        TextField startYField = new TextField();
        startYField.setPromptText("Coordonnée y de départ");
        TextField endXField = new TextField();
        endXField.setPromptText("Coordonnée x d'arrivée");
        TextField endYField = new TextField();
        endYField.setPromptText("Coordonnée y d'arrivée");
        TextField strokeField = new TextField();
        strokeField.setPromptText("Épaisseur");
        TextField redField = new TextField();
        redField.setPromptText("Rouge (0-255)");
        TextField greenField = new TextField();
        greenField.setPromptText("Vert (0-255)");
        TextField blueField = new TextField();
        blueField.setPromptText("Bleu (0-255)");

        Button drawLineButton = new Button("Dessiner la Ligne");
        Button clearLinesButton = new Button("Effacer toutes les Lignes");

        drawLineButton.setOnAction(event -> {
            try {
                double startX = Double.parseDouble(startXField.getText());
                double startY = Double.parseDouble(startYField.getText());
                double endX = Double.parseDouble(endXField.getText());
                double endY = Double.parseDouble(endYField.getText());
                int stroke = Integer.parseInt(strokeField.getText());
                int r = Integer.parseInt(redField.getText());
                int g = Integer.parseInt(greenField.getText());
                int b = Integer.parseInt(blueField.getText());

                drawLine(startX, startY, endX, endY, stroke, r, g, b);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });

        clearLinesButton.setOnAction(event -> {
            drawingPane.getChildren().removeAll(lines);
            lines.clear();
        });


        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));


        gridPane.add(startXField, 0, 0);
        gridPane.add(startYField, 1, 0);
        gridPane.add(endXField, 0, 1);
        gridPane.add(endYField, 1, 1);
        gridPane.add(strokeField, 0, 2);
        gridPane.add(redField, 0, 3);
        gridPane.add(greenField, 1, 3);
        gridPane.add(blueField, 0, 4);
        gridPane.add(drawLineButton, 0, 5);
        gridPane.add(clearLinesButton, 1, 5);
        gridPane.setGridLinesVisible(true);
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
