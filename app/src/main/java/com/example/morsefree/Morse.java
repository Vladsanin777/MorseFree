package com.example.morsefree;

import static com.example.morsefree.Morse.*;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public enum Morse {
    MORSE_EMPTY(0, '\0', '\0'),
    // Unit 1
    // Level 1
    MORSE_E(convertMorse(0x0, 0x1), 'E', 'Е'),
    MORSE_T(convertMorse(0x1, 0x1), 'T', 'Т'),
    // Unit 2
    // Level 2
    MORSE_I(convertMorse(0x0, 0x2), 'I', 'И'),
    MORSE_M(convertMorse(0x3, 0x2), 'M', 'М'),
    // Level 3
    MORSE_A(convertMorse(0x1, 0x2), 'A', 'А'),
    MORSE_N(convertMorse(0x2, 0x2), 'N', 'Н'),
    // Unit 3
    // Level 4
    MORSE_S(convertMorse(0x0, 0x3), 'S', 'С'),
    MORSE_O(convertMorse(0x7, 0x3), 'O', 'О'),
    // Level 5
    MORSE_U(convertMorse(0x1, 0x3), 'U', 'У'),
    MORSE_G(convertMorse(0x6, 0x3), 'G', 'Г'),
    // Level 6
    MORSE_R(convertMorse(0x2, 0x3), 'R', 'Р'),
    MORSE_K(convertMorse(0x5, 0x3), 'K', 'К'),
    // Level 7
    MORSE_W(convertMorse(0x3, 0x3), 'W', 'В'),
    MORSE_D(convertMorse(0x4, 0x3), 'D', 'Д'),
    // Unit 4
    // Level 8
    MORSE_H(convertMorse(0x0, 0x4), 'H', 'Х'),
    MORSE_SH(convertMorse(0xF, 0x4), '\0', 'Ш'),
    // Level 9
    MORSE_V(convertMorse(0x1, 0x4), 'V', 'Ж'),
    MORSE_CH(convertMorse(0xE, 0x4), '\0', 'Ч'),
    // Level 10
    MORSE_F(convertMorse(0x2, 0x4), 'F', 'Ф'),
    MORSE_Q(convertMorse(0xD, 0x4), 'Q', 'Щ'),
    // Level 11
    MORSE_YU(convertMorse(0x3, 0x4), '\0', 'Ю'),
    MORSE_Z(convertMorse(0xC, 0x4), 'Z', 'З'),
    // Level 12
    MORSE_L(convertMorse(0x4, 0x4), 'L', 'Л'),
    MORSE_Y(convertMorse(0xB, 0x4), 'Y', 'Ы'),
    // Level 13
    MORSE_YA(convertMorse(0x5, 0x4), '\0', 'Я'),
    MORSE_C(convertMorse(0xA, 0x4), 'C', 'Ц'),
    // Level 14
    MORSE_P(convertMorse(0x6, 0x4), 'P', 'П'),
    MORSE_X(convertMorse(0x9, 0x4), 'X', 'Ь'),
    // Level 15
    MORSE_J(convertMorse(0x7, 0x4), 'J', 'Й'),
    MORSE_B(convertMorse(0x8, 0x4), 'B', 'Б'),
    // Level additional
    MORSE_HARD_SING(convertMorse(0x1A, 0x5), '\0', 'ъ'),
    // Unit 5
    // Level 16
    MORSE_ONE(convertMorse(0x0F, 0x5), '1', '1'),
    MORSE_SIX(convertMorse(0x10, 0x5), '9', '9'),
    // Level 17
    MORSE_TWO(convertMorse(0x07, 0x5), '2', '2'),
    MORSE_SEVEN(convertMorse(0x18, 0x5), '7', '7'),
    // Level 18
    MORSE_THREE(convertMorse(0x03, 0x5), '3', '3'),
    MORSE_EIGHT(convertMorse(0x1C, 0x5), '8', '8'),
    // Level 19
    MORSE_FOUR(convertMorse(0x01, 0x5), '4', '4'),
    MORSE_NINE(convertMorse(0x1E, 0x5), '9', '9'),
    // Level 20
    MORSE_FIVE(convertMorse(0x0, 0x5), '5', '5'),
    MORSE_ZERO(convertMorse(0x1F, 0x5), '0', '0'),
    // Unit 6
    // Level 21
    MORSE_OPEN_BRACKET(convertMorse(0x16, 0x6), '(', '('),
    MORSE_CLOSE_BRACKET(convertMorse(0x2D, 0x6), ')', ')'),
    // Level 22
    MORSE_POINT(convertMorse(0x00, 0x6), '.', '.'),
    MORSE_COMMA(convertMorse(0x15, 0x6), ',', ','),
    // Level 23
    MORSE_SEMICOLON(convertMorse(0x2A, 0x6), ';', ';'),
    MORSE_COLON(convertMorse(0x38, 0x6), ':', ':'),
    // Level 24
    MORSE_QUOTES(convertMorse(0x12, 0x6), '\"', '\"'),
    MORSE_APOSTROPHE(convertMorse(0x1E, 0x6), '\'', '\''),
    // Level 25
    MORSE_DASH(convertMorse(0x21, 0x6), '-', '-'),
    MORSE_SLASH(convertMorse(0x12, 0x5), '/', '/'),
    // Level 26
    MORSE_QUESTION_MARK(convertMorse(0x0C, 0x6), '?', '?'),
    MORSE_EXCLAMATION_MARK(convertMorse(0x33, 0x6), '!', '!'),
    // Level 27
    MORSE_AT(convertMorse(0x1A, 0x6), '@', '@');

    private int m_morseData;
    private char m_latin;
    private char m_cyrillic;
    private MorseLevel m_level;
    private Function<Morse, Character> getSymbol;
    private static final Map<Integer, Morse> DATA_TO_SYMBOL =
        new HashMap<Integer, Morse>();

    static {
        buildCache();
    }

    private Morse(int morseData, char latin, char cyrillic) {
        m_morseData = morseData;
        m_latin = latin;
        m_cyrillic = cyrillic;
        getSymbol = Morse::getSymbolLatin;
    }

    public static int convertMorse(int data, int length) {
        return ((data & ((0x1 << length) - 1)) |
                (length << 0x18));
    }

    public int getMorseData() {
        return (m_morseData & ((0x1 << 
                (getMorseLength())) - 1));
    }

    public int getMorseLength() {
        return m_morseData >> 0x18;
    }

    public int getMorseDataRaw() {
        return m_morseData;
    }
    public void setLanguage(@NonNull MorseLanguage language) {
        switch (language) {
            case MORSE_LANGUAGE_LATIN:
                getSymbol = Morse::getSymbolLatin;
                break;
            case MORSE_LANGUAGE_CYRILLIC:
                getSymbol = Morse::getSymbolCyrillic;
                break;
        }
    }

    public void setLevel(MorseLevel level) {
        m_level = level;
    }

    public MorseLevel getLevel() {
        return m_level;
    }

    public char random(Morse left, Morse rigth) {
        return (int)(Math.random() * 2) == 1 ?
                getSymbol(left) : getSymbol(rigth);
    }

    private char getSymbol(Morse morse) {
        return getSymbol.apply(morse);
    }

    public static char getSymbolLatin(@NonNull Morse morse) {
        return morse.m_latin;
    }

    public static char getSymbolCyrillic(@NonNull Morse morse) {
        return morse.m_cyrillic;
    }

    public char generateSymbol(@NonNull MorseLevel level) {
        switch (level) {
            case MORSE_LEVEL_E_AND_T:
                return random(MORSE_E, MORSE_T);
            case MORSE_LEVEL_I_AND_M:
                return random(MORSE_I, MORSE_M);
            case MORSE_LEVEL_A_AND_N:
                return random(MORSE_A, MORSE_N);
            case MORSE_LEVEL_S_AND_O:
                return random(MORSE_S, MORSE_O);
            case MORSE_LEVEL_U_AND_G:
                return random(MORSE_U, MORSE_G);
            case MORSE_LEVEL_R_AND_K:
                return random(MORSE_R, MORSE_K);
            case MORSE_LEVEL_W_AND_D:
                return random(MORSE_W, MORSE_D);
            case MORSE_LEVEL_H_AND_SH:
                return random(MORSE_H, MORSE_SH);
            case MORSE_LEVEL_V_AND_CH:
                return random(MORSE_V, MORSE_CH);
            case MORSE_LEVEL_F_AND_Q:
                return random(MORSE_F, MORSE_Q);
            case MORSE_LEVEL_YU_AND_Z:
                return random(MORSE_YU, MORSE_Z);
            case MORSE_LEVEL_L_AND_Y:
                return random(MORSE_L, MORSE_Y);
            case MORSE_LEVEL_YA_AND_C:
                return random(MORSE_YA, MORSE_C);
            case MORSE_LEVEL_P_AND_X:
                return random(MORSE_P, MORSE_X);
            case MORSE_LEVEL_J_AND_B:
                return random(MORSE_J, MORSE_B);
            case MORSE_LEVEL_HARD_SING:
                return getSymbol(MORSE_HARD_SING);
            case MORSE_LEVEL_ONE_AND_SIX:
                return random(MORSE_ONE, MORSE_SIX);
            case MORSE_LEVEL_TWO_AND_SEVEN:
                return random(MORSE_TWO, MORSE_SEVEN);
            case MORSE_LEVEL_THREE_AND_EIGHT:
                return random(MORSE_THREE, MORSE_EIGHT);
            case MORSE_LEVEL_FOUR_AND_NINE:
                return random(MORSE_FOUR, MORSE_NINE);
            case MORSE_LEVEL_FIVE_AND_ZERO:
                return random(MORSE_FIVE, MORSE_ZERO);
            case MORSE_LEVEL_OPEN_BRACKET_AND_CLOSE_BRACKET:
                return random(MORSE_OPEN_BRACKET, MORSE_CLOSE_BRACKET);
            case MORSE_LEVEL_POINT_AND_COMMA:
                return random(MORSE_POINT, MORSE_COMMA);
            case MORSE_LEVEL_SEMICOLON_AND_COLON:
                return random(MORSE_SEMICOLON, MORSE_COLON);
            case MORSE_LEVEL_QUOTES_AND_APOSTROPHE:
                return random(MORSE_QUOTES, MORSE_APOSTROPHE);
            case MORSE_LEVEL_DASH_AND_SLASH:
                return random(MORSE_DASH, MORSE_SLASH);
            case MORSE_LEVEL_QUESTION_MARK_AND_EXCLAMATION_MARK:
                return random(MORSE_QUESTION_MARK, MORSE_EXCLAMATION_MARK);
            case MORSE_LEVEL_AT:
                return getSymbol(MORSE_AT);
        }
        return '\0';
    }

    public char getRandomSymbol() {

        char ch = '\0';
        while (ch == '\0') {
            MorseLevel level = (int)(Math.random() * 3) == 0 ?
                    m_level : MorseLevel.values()
                    [(int)(Math.random() * (m_level.ordinal()+1))];
            ch = generateSymbol(level);
        }
        return ch;
    }

    public char getSymbol() {
        return getSymbol.apply(this);
    }

    private static void buildCache() {
        for (Morse morse : values())
            DATA_TO_SYMBOL.put(morse.m_morseData, morse);
    }

    public void addPoint() {
        updateMorse(getMorseData() << 1, 
                getMorseLength() + 1);
    }

    public void addDash() {
        updateMorse((getMorseData() << 1) | 1,
                getMorseLength() + 1);
    }
    private void updateMorse(int data, int length) {
        int morseData = convertMorse(data, length);
        Morse morseFound = DATA_TO_SYMBOL.get(morseData);
        if (morseFound == null) {
            m_morseData = morseData;
            m_latin = '\0';
            m_cyrillic = '\0';
        } else {
            m_morseData = morseFound.m_morseData;
            m_latin = morseFound.m_latin;
            m_cyrillic = morseFound.m_cyrillic;
        }
    }
    public void clear() {
        m_morseData = 0;
        m_latin = '\0';
        m_cyrillic = '\0';
    }
}
