package com.example.appproject;

public class Interpreter {


    public static void interpret(String instruction, Interface interfaceInstance, MapCursor cursors, Cursor cursor) {
        String[] tokens = instruction.split((" "));
        switch (tokens[0]) {
            case "FWD":
                System.out.println("FWD");

                try {
                    ///gérer si c'est %
                    int distance = Integer.parseInt(tokens[1]);
                    if (cursor != null) {
                        int tempX = cursor.getPositionX();
                        int tempY = cursor.getPositionY();
                        cursor.forward(distance);

                        interfaceInstance.checkPosition(cursor.getPositionX(), cursor.getPositionY());

                        interfaceInstance.moveCursor(cursor);
                        interfaceInstance.drawLine(tempX, tempY, cursor.getPositionX(), cursor.getPositionY(), cursor.getThickness(), cursor.getColorj()[0], cursor.getColorj()[1], cursor.getColorj()[2]);
                    }
                } catch (NumberFormatException e) {
                    /*Console.appendText("Error: Invalid input\n");*/
                } catch (OutOfPositionException e) {
                    /*Console.appendText(e.getMessage() + "\n");*/
                }

                break;
            case "BWD":

                try {
                    ///gérer si c'est %
                    int distance = Integer.parseInt(tokens[1]);
                    if (cursor != null) {
                        int tempX = cursor.getPositionX();
                        int tempY = cursor.getPositionY();
                        cursor.forward(-distance);

                        interfaceInstance.checkPosition(cursor.getPositionX(), cursor.getPositionY());

                        interfaceInstance.moveCursor(cursor);
                        interfaceInstance.drawLine(tempX, tempY, cursor.getPositionX(), cursor.getPositionY(), cursor.getThickness(), cursor.getColorj()[0], cursor.getColorj()[1], cursor.getColorj()[2]);
                    }
                } catch (NumberFormatException e) {
                    /*Console.appendText("Error: Invalid input\n");*/
                } catch (OutOfPositionException e) {
                    /*Console.appendText(e.getMessage() + "\n");*/
                }
                break;
            case "TURN":
                cursor.setDirection(Double.parseDouble(tokens[1]));
                interfaceInstance.moveCursor(cursor);

                break;
            case "COLOR":
                cursor.color.setRgb(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                interfaceInstance.moveCursor(cursor);

                break;
            case "THICK":
                cursor.setThickness(Double.parseDouble(tokens[1]));
                break;
            case "PRESS":
                break;
            case "MOVE":
                if (cursor != null) {
                    int tempX = cursor.getPositionX();
                    int tempY = cursor.getPositionY();
                    cursor.setPositionX(Integer.parseInt(tokens[1]));
                    cursor.setPositionY(Integer.parseInt(tokens[2]));
                    interfaceInstance.moveCursor(cursor);
                    interfaceInstance.drawLine(tempX, tempY, cursor.getPositionX(), cursor.getPositionY(), cursor.getThickness(), cursor.getColorj()[0], cursor.getColorj()[1], cursor.getColorj()[2]);
                }

                break;
            case "POS":
                if (cursor != null) {
                    cursor.setPositionX(Integer.parseInt(tokens[1]));
                    cursor.setPositionY(Integer.parseInt(tokens[2]));
                    interfaceInstance.moveCursor(cursor);
                }
                break;
            case "HIDE":
                cursor.setHidden(false);
                interfaceInstance.moveCursor(cursor);

                break;
            case "SHOW":
                cursor.setHidden(true);
                interfaceInstance.drawCursor(cursor);
                break;
            case "CURSOR":
                try {
                    int cursorId = Integer.parseInt(tokens[1]);

                    // Vérifier si un curseur avec cet ID existe déjà
                    Cursor existingCursor = cursors.getCursorById(cursorId);
                    if (existingCursor != null) {
                        interfaceInstance.Console.appendText("Error: Cursor with ID " + cursorId + " already exists\n");
                        break;
                    }

                    // Créer un nouveau curseur avec des valeurs par défaut
                    Cursor newCursor = new Cursor(cursorId);

                    // Ajouter le curseur à la liste des curseurs
                    cursors.addCursor(newCursor);

                    // Dessiner le curseur sur l'interface utilisateur
                    interfaceInstance.drawCursor(newCursor);
                } catch (NumberFormatException e) {
                    interfaceInstance.Console.appendText("Error: Invalid input\n");
                }
                break;

            case "SELECT":
                cursor = cursors.getCursorById(Integer.parseInt(tokens[1]));
                break;
            case "REMOVE":
                interfaceInstance.removeCursor(cursor);
                break;
        }

    }
}