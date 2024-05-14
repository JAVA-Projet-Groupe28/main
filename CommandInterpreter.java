package projetgl.chromatynk;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CommandInterpreter {
    private Cursor cursor;
    private GraphicsContext gc;
    private int loopIndex = 0;

    public CommandInterpreter(Cursor cursor, GraphicsContext gc) {
        this.cursor = cursor;
        this.gc = gc;
    }

    public void interpret(String command) {
        if (command.startsWith("for")) {
            interpretForLoop(command);
        } else if (command.startsWith("if")) {
            interpretIfStatement(command);
        } else {
            executeCommand(command);
        }
    }

    private void interpretForLoop(String command) {
        String header = command.substring(command.indexOf("for") + 3, command.indexOf("{")).trim();
        String[] headerParts = header.split(" ");
        int start = Integer.parseInt(headerParts[2]);
        int end = Integer.parseInt(headerParts[4]);
        String body = command.substring(command.indexOf("{") + 1, command.lastIndexOf("}"));

        for (loopIndex = start; loopIndex <= end; loopIndex++) {
            String[] commands = body.split(";");
            for (String cmd : commands) {
                interpret(cmd.trim());
            }
        }
    }

    private void interpretIfStatement(String command) {
        String condition = command.substring(command.indexOf("(") + 1, command.indexOf(")"));
        String[] parts = condition.split("==");
        int value = Integer.parseInt(parts[1].trim());

        if (loopIndex == value) {
            String thenBody = command.substring(command.indexOf("{") + 1, command.lastIndexOf("}"));
            interpret(thenBody.trim());
        }
    }

    private void executeCommand(String command) {
        String[] parts = command.split(" ");
        switch (parts[0]) {
            case "fwd":
                cursor.forward(Integer.parseInt(parts[1]));
                break;
            case "bwd":
                cursor.backward(Integer.parseInt(parts[1]));
                break;
            case "turn":
                cursor.turn(Double.parseDouble(parts[1]));
                break;
            case "color":
                cursor.setColor(parts[1]);
                gc.setStroke(Color.web(cursor.getColor()));
                break;
            case "thickness":
                cursor.setThickness(Integer.parseInt(parts[1]));
                gc.setLineWidth(cursor.getThickness());
                break;
        }
        updateDrawing();  // Mettre à jour le dessin après chaque commande
    }

    private void updateDrawing() {
        gc.lineTo(cursor.getX(), cursor.getY());
        gc.stroke();
        gc.beginPath();
        gc.moveTo(cursor.getX(), cursor.getY());
    }
}

