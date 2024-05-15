package projetgl.chromatynk.Interpreter.jchrom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
//import java.util.Scanner;

import projetgl.chromatynk.Interpreter.jchrom.Scanner;

class Token {
    final projetgl.chromatynk.Interpreter.jchrom.TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    Token(projetgl.chromatynk.Interpreter.jchrom.TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    @Override
    public String toString() {
        return type + " " + lexeme + " " + literal;
    }
}
