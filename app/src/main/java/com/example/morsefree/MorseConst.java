package com.example.morsefree;

import static com.example.morsefree.Morse.Language;
import static com.example.morsefree.Morse.Language.*;
import static com.example.morsefree.Morse.Level;
import static com.example.morsefree.Morse.Level.*;

import android.util.Log;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public enum MorseConst extends Morse {
    // Unit 1
    // Level 1
    E_LATIN(LATIN, E_AND_T, new Morse(0x0, 0x1),'E'),
    E_CYRILLIC(CYRILLIC, E_AND_T, new Morse(0x0, 0x1), 'Е'),
    T_LATIN(LATIN, E_AND_T, new Morse(0x1, 0x1), 'T'),
    T_CYRILLIC(CYRILLIC, E_AND_T, new Morse(0x1, 0x1), 'Т'),
    // Unit 2
    // Level 2
    I_LATIN(LATIN, I_AND_M, new Morse(0x0, 0x2), 'I'),
    I_CYRILLIC(CYRILLIC, I_AND_M, new Morse(0x0, 0x2), 'И'),
    M_LATIN(LATIN, I_AND_M, new Morse(0x3, 0x2), 'M'),
    M_CYRILLIC(CYRILLIC, I_AND_M, new Morse(0x3, 0x2), 'М'),
    // Level 3
    A_LATIN(LATIN, A_AND_N, new Morse(0x1, 0x2), 'A'),
    A_CYRILLIC(CYRILLIC, A_AND_N, new Morse(0x1, 0x2), 'А'),
    N_LATIN(LATIN, A_AND_N, new Morse(0x2, 0x2), 'N'),
    N_CYRILLIC(CYRILLIC, A_AND_N, new Morse(0x2, 0x2), 'Н'),
    // Unit 3
    // Level 4
    S_LATIN(LATIN, S_AND_O, new Morse(0x0, 0x3), 'S'),
    S_CYRILLIC(CYRILLIC, S_AND_O, new Morse(0x0, 0x3), 'С'),
    O_LATIN(LATIN, S_AND_O, new Morse(0x7, 0x3), 'O'),
    O_CYRILLIC(CYRILLIC, S_AND_O, new Morse(0x7, 0x3), 'О'),
    // Level 5
    U_LATIN(LATIN, U_AND_G, new Morse(0x1, 0x3), 'U'),
    U_CYRILLIC(CYRILLIC, U_AND_G, new Morse(0x1, 0x3), 'У'),
    G_LATIN(LATIN, U_AND_G, new Morse(0x6, 0x3), 'G'),
    G_CYRILLIC(CYRILLIC, U_AND_G, new Morse(0x6, 0x3), 'Г'),
    // Level 6
    R_LATIN(LATIN, R_AND_K, new Morse(0x2, 0x3), 'R'),
    R_CYRILLIC(CYRILLIC, R_AND_K, new Morse(0x2, 0x3), 'Р'),
    K_LATIN(LATIN, R_AND_K, new Morse(0x5, 0x3), 'K'),
    K_CYRILLIC(CYRILLIC, R_AND_K, new Morse(0x5, 0x3), 'К'),
    // Level 7
    W_LATIN(LATIN, W_AND_D, new Morse(0x3, 0x3), 'W'),
    W_CYRILLIC(CYRILLIC, W_AND_D, new Morse(0x3, 0x3), 'В'),
    D_LATIN(LATIN, W_AND_D, new Morse(0x4, 0x3), 'D'),
    D_CYRILLIC(CYRILLIC, W_AND_D, new Morse(0x4, 0x3), 'Д'),
    // Unit 4
    // Level 8
    H_LATIN(LATIN, H_AND_SH, new Morse(0x0, 0x4), 'H'),
    H_CYRILLIC(CYRILLIC, H_AND_SH, new Morse(0x0, 0x4), 'Х'),
    SH_CYRILLIC(CYRILLIC, H_AND_SH, new Morse(0xF, 0x4), 'Ш'),
    // Level 9
    V_LATIN(LATIN, V_AND_CH, new Morse(0x1, 0x4), 'V'),
    V_CYRILLIC(CYRILLIC, V_AND_CH, new Morse(0x1, 0x4), 'Ж'),
    CH_CYRILLIC(CYRILLIC, V_AND_CH, new Morse(0xE, 0x4), 'Ч'),
    // Level 10
    F_LATIN(LATIN, F_AND_Q, new Morse(0x2, 0x4), 'F'),
    F_CYRILLIC(CYRILLIC, F_AND_Q, new Morse(0x2, 0x4), 'Ф'),
    Q_LATIN(LATIN, F_AND_Q, new Morse(0xD, 0x4), 'Q'),
    Q_CYRILLIC(CYRILLIC, F_AND_Q, new Morse(0xD, 0x4), 'Щ'),
    // Level 11
    YU_CYRILLIC(CYRILLIC, YU_AND_Z, new Morse(0x3, 0x4), 'Ю'),
    Z_LATIN(LATIN, YU_AND_Z, new Morse(0xC, 0x4), 'Z'),
    Z_CYRILLIC(CYRILLIC, YU_AND_Z, new Morse(0xC, 0x4), 'З'),
    // Level 12
    L_LATIN(LATIN, L_AND_Y, new Morse(0x4, 0x4), 'L'),
    L_CYRILLIC(CYRILLIC, L_AND_Y, new Morse(0x4, 0x4), 'Л'),
    Y_LATIN(LATIN, L_AND_Y, new Morse(0xB, 0x4), 'Y'),
    Y_CYRILLIC(CYRILLIC, L_AND_Y, new Morse(0xB, 0x4), 'Ы'),
    // Level 13
    YA_CYRILLIC(CYRILLIC, YA_AND_C, new Morse(0x5, 0x4), 'Я'),
    C_LATIN(LATIN, YA_AND_C, new Morse(0xA, 0x4), 'C'),
    C_CYRILLIC(CYRILLIC, YA_AND_C, new Morse(0xA, 0x4), 'Ц'),
    // Level 14
    P_LATIN(LATIN, P_AND_X, new Morse(0x6, 0x4), 'P'),
    P_CYRILLIC(CYRILLIC, P_AND_X, new Morse(0x6, 0x4), 'P'),
    X_LATIN(LATIN, P_AND_X, new Morse(0x9, 0x4), 'X'),
    X_CYRILLIC(MORSE_CYRILLIC, P_AND_X, new Morse(0x9, 0x4), 'Ь'),
    // Level 15
    J_LATIN(LATIN, J_AND_B, new Morse(0x7, 0x4), 'J'),
    J_CYRILLIC(CYRILLIC, J_AND_B, new Morse(0x7, 0x4), 'Й'),
    B_LATIN(LATIN, J_AND_B, new Morse(0x8, 0x4), 'B'),
    B_CYRILLIC(CYRILLIC, J_AND_B, new Morse(0x8, 0x4), 'Б'),
    // Level additional
    HARD_SING_CYRILLIC(CYRILLIC, HARD_SING, new Morse(0x1A, 0x5), 'Ъ'),
    // Unit 5
    // Level 16
    ONE(NUMBER, ONE_AND_SIX, new Morse(0x0F, 0x5), '1'),
    SIX(NUMBER, ONE_AND_SIX, new Morse(0x10, 0x5), '9'),
    // Level 17
    TWO(NUMBER, TWO_AND_SEVEN, new Morse(0x07, 0x5), '2'),
    SEVEN(NUMBER, TWO_AND_SEVEN, new Morse(0x18, 0x5), '7'),
    // Level 18
    THREE(NUMBER, THREE_AND_EIGHT, new Morse(0x03, 0x5), '3'),
    EIGHT(NUMBER, THREE_AND_EIGHT, new Morse(0x1C, 0x5), '8'),
    // Level 19
    FOUR(NUMBER, FOUR_AND_NINE, new Morse(0x01, 0x5), '4'),
    NINE(NUMBER, FOUR_AND_NINE, new Morse(0x1E, 0x5), '9'),
    // Level 20
    FIVE(NUMBER, FIVE_AND_ZERO, new Morse(0x0, 0x5), '5'),
    ZERO(NUMBER, FIVE_AND_ZERO, new Morse(0x1F, 0x5), '0'),
    // Unit 6
    // Level 21
    OPEN_BRACKET(SYMBOL, OPEN_BRACKET_AND_CLOSE_BRACKET, new Morse(0x16, 0x6), '('),
    CLOSE_BRACKET(SYMBOL, OPEN_BRACKET_AND_CLOSE_BRACKET, new Morse(0x2D, 0x6), ')'),
    // Level 22
    POINT(SYMBOL, POINT_AND_COMMA, new Morse(0x00, 0x6), '.'),
    COMMA(SYMBOL, POINT_AND_COMMA, new Morse(0x15, 0x6), ','),
    // Level 23
    SEMICOLON(SYMBOL, POINT_AND_COMMA, new Morse(0x2A, 0x6), ';'),
    COLON(SYMBOL, POINT_AND_COMMA, new Morse(0x38, 0x6), ':'),
    // Level 24
    QUOTES(SYMBOL, QUOTES_AND_APOSTROPHE, new Morse(0x12, 0x6), '\"'),
    APOSTROPHE(SYMBOL, QUOTES_AND_APOSTROPHE, new Morse(0x1E, 0x6), '\''),
    // Level 25
    DASH_SYMBOL(SYMBOL, DASH_AND_SLASH, new Morse(0x21, 0x6), '-'),
    SLASH_SYMBOL(SYMBOL, DASH_AND_SLASH, new Morse(0x12, 0x5), '/'),
    // Level 26
    QUESTION_MARK(SYMBOL, QUESTION_MARK_AND_EXCLAMATION_MARK, new Morse(0x0C, 0x6), '?'),
    EXCLAMATION_MARK(SYMBOL, QUESTION_MARK_AND_EXCLAMATION_MARK, new Morse(0x33, 0x6), '!'),
    // Level 27
    AT(SYMBOL, AT, new Morse(0x1A, 0x6), '@');

    private final Morse m_morse;
    private final char m_symbol;
    private final MorseLevel m_level;
    private final MorseLanguage m_language;
    private static HashMap<Language, HashMap<Level, ArrayList<MorseConst>>> DATA_TO_SYMBOL =
            new HashMap<Language, HashMap<Level, ArrayList<MorseConst>>>();

    private MorseConst(Language language, Level level,
                       Morse morse, char symbol) {
        m_morse = morse;
        m_symbol = symbol;
        m_level = level;
        m_language = language;
    }

    static {
        postInit();
    }

    private static void postInit() {
        for (MorseConst morse : values()) {
            Log.d("init", morse.m_language + " " + morse.m_level);
            HashMap<MorseLevel, ArrayList<MorseConst>> map = DATA_TO_SYMBOL.get(morse.m_language);

            if (map == null) {
                DATA_TO_SYMBOL.put(morse.m_language,
                        map = new HashMap<MorseLevel, ArrayList<MorseConst>>());
            }

            ArrayList<MorseConst> arr = map.get(morse.m_level);

            if (arr == null) {
                map.put(morse.m_level,
                        arr = new ArrayList<MorseConst>());
            }

            arr.add(morse);
        }
    }

    public boolean equals(Morse morse) {
        if (morse == null) {
            return false;
        }

        return morse.equals(m_morse);
    }

    public static MorseConst find(MorseLanguage language, Morse morse) {
        for (MorseConst morseConst : values()) {
            if (morseConst.equals(morse) &&
                    language == morseConst.getLanguage()) {
                return morseConst;
            }
        }
        return null;
    }

    public static @Nullable MorseConst find(char symbol) {
        for (MorseConst morseConst : values()) {
            if (morseConst.getSymbol() == symbol) {
                return morseConst;
            }
        }

        return null;
    }

    public static @Nullable ArrayList<MorseConst> symbols(
            MorseLanguage language, MorseLevel level) {
        HashMap<MorseLevel, ArrayList<MorseConst>> map =  DATA_TO_SYMBOL.get(language);

        if (map == null) {
            return null;
        }

        return map.get(level);
    }

    public static @Nullable MorseConst randomSymbolCurrentLevel(
            MorseLanguage language, MorseLevel level) {
        ArrayList<MorseConst> arr = symbols(language, level);

        if (arr == null) {
            return null;
        }

        int size = arr.size();

        if (size == 0) {
            return null;
        }

        return arr.get((int)(Math.random() * size));
    }

    public static MorseConst randomSymbolCurrentAndLessLevel(
            @NonNull MorseLanguage language, MorseLevel level) {
        switch (language) {
            case MORSE_CYRILLIC:
                level = (int)(Math.random() * 3) == 0 ?
                        level : MorseLevel.values()
                        [((int)(Math.random() * ((level.ordinal() -
                        MORSE_CYRILLIC.getLevelOrdinal() + 1)))) +
                        MORSE_CYRILLIC.getLevelOrdinal()];
                break;
            case MORSE_LATIN:
                level = (int)(Math.random() * 3) == 0 ?
                        level : MorseLevel.values()
                        [((int)(Math.random() * ((level.ordinal() -
                                MORSE_LATIN.getLevelOrdinal() + 1)))) +
                                MORSE_LATIN.getLevelOrdinal()];
                break;
            case MORSE_NUMBER:
                level = (int)(Math.random() * 3) == 0 ?
                        level : MorseLevel.values()
                        [((int)(Math.random() * ((level.ordinal() -
                                MORSE_NUMBER.getLevelOrdinal() + 1)))) +
                                MORSE_NUMBER.getLevelOrdinal()];
                break;
            case MORSE_SYMBOL:
                level = (int)(Math.random() * 3) == 0 ?
                        level : MorseLevel.values()
                        [((int)(Math.random() * ((level.ordinal() -
                                MORSE_SYMBOL.getLevelOrdinal() + 1)))) +
                                MORSE_SYMBOL.getLevelOrdinal()];
                break;
            default:
                break;
        }
        return randomSymbolCurrentLevel(language, level);
    }
}
