package com.example.appproject;

import java.util.HashSet;
import java.util.Set;

/**
 * The interpret method is used to execute the instructions asked by the user.
 *
 * The instruction takes the form of a String composed of tokens.
 * The first token is the actual instruction (FWD, BWD, HIDE, ...), it is caught with a switch to execute the right
 * behavior for each possible instruction, or throw an exception if no known instructions is detected.
 *
 * For each instruction, the selected Cursor object has its attributes updated and the Interface object, which is
 * the drawing area seen by the user, is updated.
 *
 */

public class Interpreter {
    private static Set<String> existingVariables = new HashSet<>();

    public static void interpret(String instruction, Interface interfaceInstance, MapCursor cursors, Cursor cursor) {
        try {



            String[] tokens = instruction.split(" ");
            switch (tokens[0]) {
                case "FWD":
                    executeFwd(tokens, interfaceInstance, cursor);
                    System.out.println("FWD "+tokens[1]);
                    break;
                case "BWD":
                    executeBwd(tokens, interfaceInstance, cursor);
                    break;
                case "TURN":
                    cursor.turn(Double.parseDouble(tokens[1]));
                    interfaceInstance.moveCursor(cursor);
                    break;
                case "COLOR":
                    cursor.color.setRgb(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                    interfaceInstance.moveCursor(cursor);
                    break;
                case "LOOKAT":
                    executeLookAt(tokens, interfaceInstance, cursors, cursor);
                    break;
                case "THICK":
                    cursor.setThickness(Double.parseDouble(tokens[1]));
                    break;
                case "PRESS":
                    cursor.setOpacity(Double.parseDouble(tokens[1]));
                    break;
                case "MOV":
                    executeMove(tokens, interfaceInstance, cursor);
                    break;
                case "POS":
                    executePos(tokens, interfaceInstance, cursor);
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
                    executeCursor(tokens, interfaceInstance, cursors);
                    break;
                case "SELECT":
                    cursor = cursors.getCursorById(Integer.parseInt(tokens[1]));
                    interfaceInstance.selectedCursorId = Integer.parseInt((tokens[1]));
                    break;
                case "REMOVE":
                    interfaceInstance.removeCursor(cursor);
                    break;
                case "FOR":
                    handleForLoop(tokens, instruction, interfaceInstance, cursors, cursor);
                    break;
                case "IF" :
                    executeIf(tokens, instruction, interfaceInstance, cursors, cursor);
                case "WHILE" :
                    executeWhile(tokens, instruction, interfaceInstance, cursors, cursor);
                default:
                    throw new IllegalArgumentException("Unknown command: " + tokens[0]);
            }
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    /**
     * Executes the FORWARD instruction.
     * It moves the cursor following its direction by the "distance" entered by the user.
     * It takes into account if the user entered a percentage, symbolized with a '%' or not.
     * @param tokens
     * @param interfaceInstance
     * @param cursor
     */
    private static void executeFwd(String[] tokens, Interface interfaceInstance, Cursor cursor) {
        try {
            int distance;

            //If the user enters a percentage, it adapts the forward method so the distance is the percentage of
            //the largest dimension of the canvas between width and height.
            if (tokens[1].endsWith("%")){
                Percentage distance_per = new Percentage(tokens[1]);
                double dimension = Math.max(interfaceInstance.getDrawingPaneWidth(),interfaceInstance.getDrawingPaneHeight());
                distance = (int) Math.round(dimension*distance_per.getValue());
            }
            else {distance = Integer.parseInt(tokens[1]);}
            if (cursor != null) {
                int tempX = cursor.getPositionX();
                int tempY = cursor.getPositionY();
                cursor.forward(distance);
                interfaceInstance.checkPosition(cursor.getPositionX(), cursor.getPositionY());
                interfaceInstance.moveCursor(cursor);
                interfaceInstance.drawLine(tempX, tempY, cursor.getPositionX(), cursor.getPositionY(), cursor.getThickness(), cursor.getColorj()[0], cursor.getColorj()[1], cursor.getColorj()[2]);
            }
        } catch (NumberFormatException e) {
            // Handle invalid number format
        } catch (OutOfPositionException e) {
            // Handle out of position exception
        }
    }

    private static void executeBwd(String[] tokens, Interface interfaceInstance, Cursor cursor) {
        try {
            int distance;
            if (tokens[1].endsWith("%")){
                Percentage distance_per = new Percentage(tokens[1]);
                double dimension = Math.max(interfaceInstance.getDrawingPaneWidth(),interfaceInstance.getDrawingPaneHeight());
                distance = (int) Math.round(dimension*distance_per.getValue());
            }
            else{distance = Integer.parseInt(tokens[1]);}
            if (cursor != null) {
                int tempX = cursor.getPositionX();
                int tempY = cursor.getPositionY();
                cursor.forward(-distance);
                interfaceInstance.checkPosition(cursor.getPositionX(), cursor.getPositionY());
                interfaceInstance.moveCursor(cursor);
                interfaceInstance.drawLine(tempX, tempY, cursor.getPositionX(), cursor.getPositionY(), cursor.getThickness(), cursor.getColorj()[0], cursor.getColorj()[1], cursor.getColorj()[2]);
            }
        } catch (NumberFormatException e) {
            // Handle invalid number format
        } catch (OutOfPositionException e) {
            // Handle out of position exception
        }
    }

    /**
     * Execute the POS instruction.
     */
    private static void executePos(String[] tokens, Interface interfaceInstance, Cursor cursor) {
        if (cursor != null) {
            int tempX = cursor.getPositionX();
            int tempY = cursor.getPositionY();

            if (tokens[1].endsWith("%") && tokens[2].endsWith("%")){
                double canvasHeight = interfaceInstance.getDrawingPaneHeight();
                double canvasWidth = interfaceInstance.getDrawingPaneWidth();
                Percentage abscissa_per = new Percentage(tokens[1]);
                Percentage ordinate_per = new Percentage(tokens[2]);

                System.out.println(canvasWidth);
                System.out.println(abscissa_per.getValue());

                int newPosX = (int) Math.round(canvasWidth*abscissa_per.getValue());
                int newPosY = (int) Math.round(canvasHeight*ordinate_per.getValue());

                cursor.setPositionX(newPosX);
                cursor.setPositionY(newPosY);
            }
            else{
                cursor.setPositionX(Integer.parseInt(tokens[1]));
                cursor.setPositionY(Integer.parseInt(tokens[2]));
            }
            interfaceInstance.moveCursor(cursor);
        }
    }

    /**
     * Execute the MOV instruction.
     * As executePos() but it draws the line between the last position of the cursor and the new one.
     */
    private static void executeMove(String[] tokens, Interface interfaceInstance, Cursor cursor) {
        if (cursor != null) {
            int tempX = cursor.getPositionX();
            int tempY = cursor.getPositionY();

            executePos(tokens,interfaceInstance,cursor);

            interfaceInstance.drawLine(tempX, tempY, cursor.getPositionX(), cursor.getPositionY(), cursor.getThickness(), cursor.getColorj()[0], cursor.getColorj()[1], cursor.getColorj()[2]);
        }
    }

    /**
     * Manages the LOOKAT instructions which can be called with different parameters.
     * The coordinates as integers, a cursor ID as an integer or percentages in abscissa and ordinate of the canvas.
     * */

    private static void executeLookAt(String[] tokens, Interface interfaceInstance, MapCursor mapCursor, Cursor cursor){
        if (tokens.length == 2){
            Cursor cursorToLookAt = mapCursor.getCursorById(Integer.parseInt(tokens[1]));

            cursor.lookAt(cursorToLookAt);
        }
        else if (tokens.length == 3){
            if (tokens[1].endsWith("%") && tokens[2].endsWith("%")){
                double canvasWidth = interfaceInstance.getDrawingPaneWidth();
                double canvasHeight = interfaceInstance.getDrawingPaneHeight();
                
                Percentage abscissa_per = new Percentage(tokens[1]);
                Percentage ordinate_per = new Percentage(tokens[2]);

                cursor.lookAt(abscissa_per,ordinate_per,canvasWidth,canvasHeight);
            }
            else {
                int posX = Integer.parseInt(tokens[1]);
                int posY = Integer.parseInt(tokens[2]);

                cursor.lookAt(posX,posY);
            }
        }

        interfaceInstance.moveCursor(cursor);

    }

    private static void executeCursor(String[] tokens, Interface interfaceInstance, MapCursor cursors) {
        try {
            int cursorId = Integer.parseInt(tokens[1]);
            Cursor existingCursor = cursors.getCursorById(cursorId);
            if (existingCursor != null) {
                interfaceInstance.Console.appendText("Error: Cursor with ID " + cursorId + " already exists\n");
                return;
            }
            Cursor newCursor = new Cursor(cursorId);
            cursors.addCursor(newCursor);
            interfaceInstance.drawCursor(newCursor);
        } catch (NumberFormatException e) {
            interfaceInstance.Console.appendText("Error: Invalid input\n");
        }
    }

    private static void handleForLoop(String[] tokens, String instruction, Interface interfaceInstance, MapCursor cursors, Cursor cursor) {
        if (tokens.length < 5) {
            interfaceInstance.Console.appendText("Error: Invalid FOR loop syntax\n");
            return;
        }

        String variableName = tokens[1];
        if (existingVariables.contains(variableName)) {
            interfaceInstance.Console.appendText("Error: Variable " + variableName + " already exists\n");
            return;
        }

        int from = 0;
        int to;
        int step = 1;

        int currentIndex = 2;
        if (tokens[currentIndex].equals("FROM")) {
            from = Integer.parseInt(tokens[++currentIndex]);
            currentIndex++;
        }

        if (!tokens[currentIndex].equals("TO")) {
            interfaceInstance.Console.appendText("Error: Invalid FOR loop syntax\n");
            return;
        }

        to = Integer.parseInt(tokens[++currentIndex]);
        currentIndex++;

        if (tokens[currentIndex].startsWith("STEP{")) {
            String stepBlock = instruction.substring(instruction.indexOf("STEP{") + 5, instruction.lastIndexOf("}"));
            String[] commands = stepBlock.split(",");
            existingVariables.add(variableName);

            for (int i = from; i <= to; i += step) {
                for (String command : commands) {
                    ///try et remove les variables temporaires créées
                    String modifiedCommand = command.trim().replace(variableName, String.valueOf(i));
                    interpret(modifiedCommand, interfaceInstance, cursors, cursor);
                }
            }

            existingVariables.remove(variableName);
        } else {
            interfaceInstance.Console.appendText("Error: Invalid FOR loop syntax\n");
        }
    }

    private static void executeIf(String[] tokens, String instruction, Interface interfaceInstance, MapCursor cursors, Cursor cursor){
        if (tokens.length<2){
            interfaceInstance.Console.appendText("Error : Invalid IF syntax\n");
            return;
        }
        String condition = instruction.substring(instruction.indexOf("IF") + 3, instruction.indexOf('{')).trim();
        String block = instruction.substring(instruction.indexOf("{")+1, instruction.lastIndexOf("}")).trim();
        if (evaluateBooleanExpression(condition)){
            String[] commands = block.split(",");
            for (String command : commands){
                interpret(command, interfaceInstance, cursors, cursor);
            }
        }
    }
    private static boolean evaluateBooleanExpression(String condition) {
        // Supprimer les espaces inutiles
        condition = condition.replaceAll("\\s+", "");

        // Diviser la condition en parties, en utilisant les opérateurs logiques comme délimiteurs
        String[] logicalParts = condition.split("(\\|\\||&&)");

        // Initialiser le résultat global à true
        boolean globalResult = true;

        // Parcourir chaque partie de la condition logique
        for (String logicalPart : logicalParts) {
            // Vérifier si la partie commence par le non-logique ("!")
            boolean negated = false;
            if (logicalPart.startsWith("!")) {
                negated = true;
                logicalPart = logicalPart.substring(1); // Supprimer le "!"
            }

            // Diviser la partie en utilisant les opérateurs de comparaison comme délimiteurs
            String[] comparisonParts = logicalPart.split("(==|!=|<=|>=|<|>)");

            // Vérifier les opérateurs de comparaison
            if (comparisonParts.length != 2) {
                // Si la partie ne contient pas exactement deux éléments après la division, la condition est invalide
                return false;
            }

            // Extraire les opérateurs et les opérandes
            String operator = logicalPart.replaceAll("[a-zA-Z0-9]", "");
            int operand1 = Integer.parseInt(comparisonParts[0]);
            int operand2 = Integer.parseInt(comparisonParts[1]);

            // Vérifier l'opérateur et évaluer la condition de comparaison
            boolean result = false;
            switch (operator) {
                case "==":
                    result = (operand1 == operand2);
                    break;
                case "!=":
                    result = (operand1 != operand2);
                    break;
                case "<=":
                    result = (operand1 <= operand2);
                    break;
                case ">=":
                    result = (operand1 >= operand2);
                    break;
                case "<":
                    result = (operand1 < operand2);
                    break;
                case ">":
                    result = (operand1 > operand2);
                    break;
                default:
                    // Opérateur de comparaison invalide
                    return false;
            }

            // Si la partie était négative, inverser le résultat
            if (negated) {
                result = !result;
            }

            // Si l'opérateur logique est "||", mettre à jour le résultat global
            if (condition.contains("||")) {
                globalResult = globalResult || result;
            } else if (condition.contains("&&")) {
                // Si l'opérateur logique est "&&", arrêter l'évaluation dès qu'une partie est fausse
                if (!result) {
                    return false;
                }
            } else {
                // Si aucun opérateur logique n'est spécifié, le résultat global est égal au résultat de la partie actuelle
                globalResult = result;
            }
        }

        // Renvoyer le résultat global
        return globalResult;
    }


    private static void executeWhile(String[] tokens, String instruction, Interface interfaceInstance, MapCursor cursors, Cursor cursor){
        if (tokens.length<2){
            interfaceInstance.Console.appendText("Error : Invalid IF syntax\n");
            return;
        }
        String condition = instruction.substring(instruction.indexOf("WHILE") + 3, instruction.indexOf('{')).trim();
        String block = instruction.substring(instruction.indexOf("{")+1, instruction.lastIndexOf("}")).trim();
        while (evaluateBooleanExpression(condition)){
            String[] commands = block.split(",");
            for (String command : commands){
                interpret(command, interfaceInstance, cursors, cursor);
            }
        }
    }

    private static void splitCommand(String instruction){
        if (instruction.contains("{")){

    }
    }

}
