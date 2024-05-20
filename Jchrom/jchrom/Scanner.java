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

    /**
     * If the current position has reached the end of the source
     *
     * @return bool
     */
    private boolean isAtEnd() {
        if(current >= source.length()){
            return true;
        }
        return false;
    }

    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("and",    AND);
        keywords.put("else",   ELSE);
        keywords.put("false",  FALSE);
        keywords.put("for",    FOR);
        keywords.put("if",     IF);
        keywords.put("or",     OR);
        keywords.put("true",   TRUE);
        keywords.put("while",  WHILE);
        keywords.put("fwd


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

















            // Look after numbers by default.
            // Display an error if the character does not match the lexeme
            default:
                if (isDigit(c)) {
                    number();
                }
                else if(isAlpha(c)){
                    identifier();
                }


                else {
                    Jchrom.error(line, "Unexpected character.");
                }

                break;
        }
    }

    /**
     * Go to the next character
     * @return char
     */
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
     * Similar to advance() but return '\0' is it's the end of the source.
     * Or return the character at the current position.
     * @return current character str
     */
    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }


    /**
     * Read all the characters in the string sequence and add them in one token.
     *
     */
    private void string() {
        // continue reading while the character does not match comma or the end of the file.
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }

        if (isAtEnd()) {
            Jchrom.error(line, "Unterminated string.");
            return;
        }

        // The closing ".
        advance();

        // Trim the surrounding quotes.
        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }

    /**
     * Return true if it's a number.
     * @param c char
     * @return bool
     */
    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * While it's a number, save the integer part and when it's reach ".", save the digit part.
     * We must verify if there is a digit after the "." with peekNext().
     * The number will be a double.
     */
    private void number() {
        while (isDigit(peek())) advance();

        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
            // Consume the "."
            advance();

            while (isDigit(peek())) advance();
        }

        addToken(NUMBER,
                Double.parseDouble(source.substring(start, current)));
    }

    /**
     * If the next character is a number, return the number.
     * @return char
     */
    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) advance();

        addToken(IDENTIFIER);
    }

    private boolean isAlpha(char c) {
        if (c >= 'a' && c <= 'z') return true;
        else if (c >= 'A' && c <= 'Z') return true;
        else return c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }
























}

