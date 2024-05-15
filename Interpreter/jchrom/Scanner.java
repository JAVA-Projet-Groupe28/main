package projetgl.chromatynk.Interpreter.jchrom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static projetgl.chromatynk.Interpreter.jchrom.TokenType.*;

/**
 * Scanner class : store the row source code in a string,
 * add generated tokens from the source in a list until
 * there is no token available in the source.
 *
 *
 *
 */
class Scanner {
    private final String source;
    private final List<projetgl.chromatynk.Interpreter.jchrom.Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    Scanner(String source) {
        this.source = source;
    }

    /**
     * While the scanner is not at the end of the source code
     * evaluate the token and add it in the list if it's in the lexeme.
     *
     */
     public List<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme.
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    /**
     * Method that analyse the token of single character
     * is its correspond to the lexeme
     *
     */
    /**
     * Scan the token and verify if its correspond to the lexeme, if not,
     * Show an error with the line.
     */
    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(':
                addToken(LEFT_PAREN);
                break;
            case ')':
                addToken(RIGHT_PAREN);
                break;
            case '{':
                addToken(LEFT_BRACE);
                break;
            case '}':
                addToken(RIGHT_BRACE);
                break;
            case ',':
                addToken(COMMA);
                break;
            case '.':
                addToken(DOT);
                break;
            case '-':
                addToken(MINUS);
                break;
            case '+':
                addToken(PLUS);
                break;
            case ';':
                addToken(SEMICOLON);
                break;
            case '*':
                addToken(STAR);
                break;
            case '%':
                addToken(PERCENT);
                break;
                // Operators
            case '!':
                addToken(match('=') ? BANG_EQUAL : BANG);
                break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                addToken(match('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER);
                break;
            case '/':
                if (match('/')) {
                    // A comment goes until the end of the line.
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(SLASH);
                }
                break;
            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace.
                break;
            case '\n':
                line++;
                break;
            case '"': string(); break;
















            // show an error if the character does not match the lexeme.
            default:
                Jchrom.error(line, "Unexpected character.");
                break;
        }
    }
    private char advance() {
        return source.charAt(current++);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    /**
     * Look at the next character if its match the lexeme/switch statement.
     *
     * @param expected char
     * @return bool
     */
    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    /**
     * Similar to advance() but if it goes until it's the end of the line.
     * @return str
     */
    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }









}

