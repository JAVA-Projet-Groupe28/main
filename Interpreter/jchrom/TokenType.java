package jchrom;

//Enumeration of all the tokens in the language
enum TokenType {
    // Single-character tokens.
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR, PERCENT,

    // One or two character tokens.
    BANG, BANG_EQUAL,
    EQUAL, EQUAL_EQUAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,

    // Literals.
    IDENTIFIER, STRING, NUMBER,

    // Simple instructions.
    FWD, BWD, TURN, MOV, POS, HIDE, SHOW, PRESS,
    COLOR, THICK, LOOKAT,

    // Cursors.
    CURSOR, SELECT, REMOVE,


    // Instruction blocs.
    IF, FOR, WILE, MIMIC, MIRROR, ELSE,

    // Variables.
    NUM, STR, BOOL, DEL,

    // Keywords.
    AND, OR, TRUE, FALSE,

    EOF
}
