package com.example.morsefree;

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

    private static Function<Morse, Character> m_getterSymbol =
            morse->morse.m_latin;

    private static final Map<Integer, Morse> DATA_TO_SYMBOL = 
        new HashMap<Integer, Morse>();

    static {
        buildCache();
    }

    private Morse(int morseData, char latin, char cyrillic) {
        m_morseData = morseData;
        m_latin = latin;
        m_cyrillic = cyrillic;
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
    public void setLanguage(Language language) {
        switch (language) {
            case LATIN:
                m_getterSymbol = Morse::getLatin;
                break;
            case CYRILLIC:
                m_getterSymbol = Morse::getCyrillic;
                break;
        }
    }

    public char getSymbol() {
        return m_getterSymbol.apply(this);
    }

    public char getLatin() {
        return m_latin;
    }

    public char getCyrillic() {
        return m_cyrillic;
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
