package com.example.appproject;


import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class ChoiceListCase {

    private static Interpreter interpreter;
    private static Interface drawingInterface;
    private static MapCursor mapCursor;
    private static Cursor cursor;
    private static MapVariable variables;
    private static int numLine;

    public static void main(String[] args) {
        drawingInterface = new Interface();
        mapCursor = new MapCursor();
        cursor = new Cursor(0);
        mapCursor.addCursor(cursor);
        variables = new MapVariable();
        interpreter = new Interpreter();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            showMenu();
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("q")) {
                break;
            }

            handleChoice(choice, scanner, new ArrayList<>());
        }
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("Please select an instruction:");
        System.out.println("1. FWD");
        System.out.println("2. BWD");
        System.out.println("3. TURN");
        System.out.println("4. MOV");
        System.out.println("5. POS");
        System.out.println("6. HIDE");
        System.out.println("7. SHOW");
        System.out.println("8. PRESS");
        System.out.println("9. COLOR");
        System.out.println("10. THICK");
        System.out.println("11. CURSOR");
        System.out.println("12. SELECT");
        System.out.println("13. REMOVE");
        System.out.println("14. FOR");
        System.out.println("15. IF");
        System.out.println("16. WHILE");
        System.out.println("17. MIMIC");
        System.out.println("18. MIRROR");
        System.out.println("19. NUM");
        System.out.println("20. STR");
        System.out.println("21. BOOL");
        System.out.println("22. DEL");
        System.out.println("end. End Block");
        System.out.println("q. Quit");
        System.out.print("Choice: ");
    }

    private static void handleChoice(String choice, Scanner scanner, List<String> blockInstructions) {
        String nomInstruction = "";
        String params = "";

        switch (choice) {
            case "1":
                nomInstruction = "FWD";
                System.out.println("Enter value: ");
                params = scanner.nextLine();
                break;
            case "2":
                nomInstruction = "BWD";
                System.out.println("Enter value: ");
                params = scanner.nextLine();
                break;
            case "3":
                nomInstruction = "TURN";
                System.out.println("Enter value: ");
                params = scanner.nextLine();
                break;
            case "4":
                nomInstruction = "MOV";
                System.out.println("Enter x and y values (separated by space): ");
                params = scanner.nextLine();
                break;
            case "5":
                nomInstruction = "POS";
                System.out.println("Enter x and y values (separated by space): ");
                params = scanner.nextLine();
                break;
            case "6":
                nomInstruction = "HIDE";
                break;
            case "7":
                nomInstruction = "SHOW";
                break;
            case "8":
                nomInstruction = "PRESS";
                System.out.println("Enter value: ");
                params = scanner.nextLine();
                break;
            case "9":
                nomInstruction = "COLOR";
                System.out.println("Enter color (e.g., #RRGGBB or 'red green blue'): ");
                params = scanner.nextLine();
                break;
            case "10":
                nomInstruction = "THICK";
                System.out.println("Enter value: ");
                params = scanner.nextLine();
                break;
            case "11":
                nomInstruction = "CURSOR";
                System.out.println("Enter cursor ID: ");
                params = scanner.nextLine();
                break;
            case "12":
                nomInstruction = "SELECT";
                System.out.println("Enter cursor ID: ");
                params = scanner.nextLine();
                break;
            case "13":
                nomInstruction = "REMOVE";
                break;
            case "14":
                nomInstruction = "FOR";
                System.out.println("Enter FOR loop parameters (name FROM start TO end STEP step): ");
                params = scanner.nextLine();
                params += " {";
                params += getBlockInstructions(scanner);
                params += "}";
                break;
            case "15":
                nomInstruction = "IF";
                System.out.println("Enter IF condition: ");
                params = scanner.nextLine();
                params += " {";
                params += getBlockInstructions(scanner);
                params += "}";
                break;
            case "16":
                nomInstruction = "WHILE";
                System.out.println("Enter WHILE condition: ");
                params = scanner.nextLine();
                params += " {";
                params += getBlockInstructions(scanner);
                params += "}";
                break;
            case "17":
                nomInstruction = "MIMIC";
                System.out.println("Enter MIMIC parameters: ");
                params = scanner.nextLine();
                break;
            case "18":
                nomInstruction = "MIRROR";
                System.out.println("Enter MIRROR parameters: ");
                params = scanner.nextLine();
                break;
            case "19":
                nomInstruction = "NUM";
                System.out.println("Enter NUM variable name and value: ");
                params = scanner.nextLine();
                break;
            case "20":
                nomInstruction = "STR";
                System.out.println("Enter STR variable name and value: ");
                params = scanner.nextLine();
                break;
            case "21":
                nomInstruction = "BOOL";
                System.out.println("Enter BOOL variable name and value: ");
                params = scanner.nextLine();
                break;
            case "22":
                nomInstruction = "DEL";
                System.out.println("Enter variable name to delete: ");
                params = scanner.nextLine();
                break;
            case "end":
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                return;
        }

        if (!nomInstruction.isEmpty()) {
            blockInstructions.add(nomInstruction + " " + params);
        } else {
            executeInstruction(nomInstruction, params);
        }
    }

    private static String getBlockInstructions(Scanner scanner) {
        List<String> blockInstructions = new ArrayList<>();
        while (true) {
            showMenu();
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("end")) {
                break;
            }
            handleChoice(choice, scanner, blockInstructions);
        }
        return String.join(";", blockInstructions);
    }

    private static void executeInstruction(String nomInstruction, String params) {
        String command = nomInstruction + " " + params;
        interpreter.interpret(command, drawingInterface, mapCursor, cursor, variables, numLine);
        System.out.println("Executed: " + command);
    }
}


