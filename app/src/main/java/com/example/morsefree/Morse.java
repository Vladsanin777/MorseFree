package com.example.morsefree;

import static com.example.morsefree.MorseLanguage.*;
import static com.example.morsefree.MorseLevel.*;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public enum Morse {
    // Unit 1
    // Level 1
    MORSE_E_LATIN(MORSE_LATIN, MORSE_LEVEL_E_AND_T, serial(0x0, 0x1),'E'),
    MORSE_E_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_E_AND_T, serial(0x0, 0x1), 'Е'),
    MORSE_T_LATIN(MORSE_LATIN, MORSE_LEVEL_E_AND_T, serial(0x1, 0x1), 'T'),
    MORSE_T_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_E_AND_T, serial(0x1, 0x1), 'Т'),
    // Unit 2
    // Level 2
    MORSE_I_LATIN(MORSE_LATIN, MORSE_LEVEL_I_AND_M, serial(0x0, 0x2), 'I'),
    MORSE_I_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_I_AND_M, serial(0x0, 0x2), 'И'),
    MORSE_M_LATIN(MORSE_LATIN, MORSE_LEVEL_I_AND_M, serial(0x3, 0x2), 'M'),
    MORSE_M_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_I_AND_M, serial(0x3, 0x2), 'М'),
    // Level 3
    MORSE_A_LATIN(MORSE_LATIN, MORSE_LEVEL_A_AND_N, serial(0x1, 0x2), 'A'),
    MORSE_A_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_A_AND_N, serial(0x1, 0x2), 'А'),
    MORSE_N_LATIN(MORSE_LATIN, MORSE_LEVEL_A_AND_N, serial(0x2, 0x2), 'N'),
    MORSE_N_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_A_AND_N, serial(0x2, 0x2), 'Н'),
    // Unit 3
    // Level 4
    MORSE_S_LATIN(MORSE_LATIN, MORSE_LEVEL_S_AND_O, serial(0x0, 0x3), 'S'),
    MORSE_S_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_S_AND_O, serial(0x0, 0x3), 'С'),
    MORSE_O_LATIN(MORSE_LATIN, MORSE_LEVEL_S_AND_O, serial(0x7, 0x3), 'O'),
    MORSE_O_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_S_AND_O, serial(0x7, 0x3), 'О'),
    // Level 5
    MORSE_U_LATIN(MORSE_LATIN, MORSE_LEVEL_U_AND_G, serial(0x1, 0x3), 'U'),
    MORSE_U_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_U_AND_G, serial(0x1, 0x3), 'У'),
    MORSE_G_LATIN(MORSE_LATIN, MORSE_LEVEL_U_AND_G, serial(0x6, 0x3), 'G'),
    MORSE_G_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_U_AND_G, serial(0x6, 0x3), 'Г'),
    // Level 6
    MORSE_R_LATIN(MORSE_LATIN, MORSE_LEVEL_R_AND_K, serial(0x2, 0x3), 'R'),
    MORSE_R_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_R_AND_K, serial(0x2, 0x3), 'Р'),
    MORSE_K_LATIN(MORSE_LATIN, MORSE_LEVEL_R_AND_K, serial(0x5, 0x3), 'K'),
    MORSE_K_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_R_AND_K, serial(0x5, 0x3), 'К'),
    // Level 7
    MORSE_W_LATIN(MORSE_LATIN, MORSE_LEVEL_W_AND_D, serial(0x3, 0x3), 'W'),
    MORSE_W_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_W_AND_D, serial(0x3, 0x3), 'В'),
    MORSE_D_LATIN(MORSE_LATIN, MORSE_LEVEL_W_AND_D, serial(0x4, 0x3), 'D'),
    MORSE_D_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_W_AND_D, serial(0x4, 0x3), 'Д'),
    // Unit 4
    // Level 8
    MORSE_H_LATIN(MORSE_LATIN, MORSE_LEVEL_H_AND_SH, serial(0x0, 0x4), 'H'),
    MORSE_H_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_H_AND_SH, serial(0x0, 0x4), 'Х'),
    MORSE_SH_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_H_AND_SH, serial(0xF, 0x4), 'Ш'),
    // Level 9
    MORSE_V_LATIN(MORSE_LATIN, MORSE_LEVEL_V_AND_CH, serial(0x1, 0x4), 'V'),
    MORSE_V_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_V_AND_CH, serial(0x1, 0x4), 'Ж'),
    MORSE_CH_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_V_AND_CH, serial(0xE, 0x4), 'Ч'),
    // Level 10
    MORSE_F_LATIN(MORSE_LATIN, MORSE_LEVEL_F_AND_Q, serial(0x2, 0x4), 'F'),
    MORSE_F_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_F_AND_Q, serial(0x2, 0x4), 'Ф'),
    MORSE_Q_LATIN(MORSE_LATIN, MORSE_LEVEL_F_AND_Q, serial(0xD, 0x4), 'Q'),
    MORSE_Q_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_F_AND_Q, serial(0xD, 0x4), 'Щ'),
    // Level 11
    MORSE_YU_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_YU_AND_Z, serial(0x3, 0x4), 'Ю'),
    MORSE_Z_LATIN(MORSE_LATIN, MORSE_LEVEL_YU_AND_Z, serial(0xC, 0x4), 'Z'),
    MORSE_Z_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_YU_AND_Z, serial(0xC, 0x4), 'З'),
    // Level 12
    MORSE_L_LATIN(MORSE_LATIN, MORSE_LEVEL_L_AND_Y, serial(0x4, 0x4), 'L'),
    MORSE_L_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_L_AND_Y, serial(0x4, 0x4), 'Л'),
    MORSE_Y_LATIN(MORSE_LATIN, MORSE_LEVEL_L_AND_Y, serial(0xB, 0x4), 'Y'),
    MORSE_Y_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_L_AND_Y, serial(0xB, 0x4), 'Ы'),
    // Level 13
    MORSE_YA_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_YA_AND_C, serial(0x5, 0x4), 'Я'),
    MORSE_C_LATIN(MORSE_LATIN, MORSE_LEVEL_YA_AND_C, serial(0xA, 0x4), 'C'),
    MORSE_C_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_YA_AND_C, serial(0xA, 0x4), 'Ц'),
    // Level 14
    MORSE_P_LATIN(MORSE_LATIN, MORSE_LEVEL_P_AND_X, serial(0x6, 0x4), 'P'),
    MORSE_P_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_P_AND_X, serial(0x6, 0x4), 'P'),
    MORSE_X_LATIN(MORSE_LATIN, MORSE_LEVEL_P_AND_X, serial(0x9, 0x4), 'X'),
    MORSE_X_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_P_AND_X, serial(0x9, 0x4), 'Ь'),
    // Level 15
    MORSE_J_LATIN(MORSE_LATIN, MORSE_LEVEL_J_AND_B, serial(0x7, 0x4), 'J'),
    MORSE_J_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_J_AND_B, serial(0x7, 0x4), 'Й'),
    MORSE_B_LATIN(MORSE_LATIN, MORSE_LEVEL_J_AND_B, serial(0x8, 0x4), 'B'),
    MORSE_B_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_J_AND_B, serial(0x8, 0x4), 'Б'),
    // Level additional
    MORSE_HARD_SING_CYRILLIC(MORSE_CYRILLIC, MORSE_LEVEL_HARD_SING, serial(0x1A, 0x5), 'Ъ'),
    // Unit 5
    // Level 16
    MORSE_ONE_NUMBER(MORSE_NUMBER, MORSE_LEVEL_ONE_AND_SIX, serial(0x0F, 0x5), '1'),
    MORSE_SIX_NUMBER(MORSE_NUMBER, MORSE_LEVEL_ONE_AND_SIX, serial(0x10, 0x5), '9'),
    // Level 17
    MORSE_TWO_NUMBER(MORSE_NUMBER, MORSE_LEVEL_TWO_AND_SEVEN, serial(0x07, 0x5), '2'),
    MORSE_SEVEN_NUMBER(MORSE_NUMBER, MORSE_LEVEL_TWO_AND_SEVEN, serial(0x18, 0x5), '7'),
    // Level 18
    MORSE_THREE_NUMBER(MORSE_NUMBER, MORSE_LEVEL_THREE_AND_EIGHT, serial(0x03, 0x5), '3'),
    MORSE_EIGHT_NUMBER(MORSE_NUMBER, MORSE_LEVEL_THREE_AND_EIGHT, serial(0x1C, 0x5), '8'),
    // Level 19
    MORSE_FOUR_NUMBER(MORSE_NUMBER, MORSE_LEVEL_FOUR_AND_NINE, serial(0x01, 0x5), '4'),
    MORSE_NINE_NUMBER(MORSE_NUMBER, MORSE_LEVEL_FOUR_AND_NINE, serial(0x1E, 0x5), '9'),
    // Level 20
    MORSE_FIVE_NUMBER(MORSE_NUMBER, MORSE_LEVEL_FIVE_AND_ZERO, serial(0x0, 0x5), '5'),
    MORSE_ZERO_NUMBER(MORSE_NUMBER, MORSE_LEVEL_FIVE_AND_ZERO, serial(0x1F, 0x5), '0'),
    // Unit 6
    // Level 21
    MORSE_OPEN_BRACKET_SYMBOL(MORSE_SYMBOL, MORSE_LEVEL_OPEN_BRACKET_AND_CLOSE_BRACKET, serial(0x16, 0x6), '('),
    MORSE_CLOSE_BRACKET_SYMBOL(MORSE_SYMBOL, MORSE_LEVEL_OPEN_BRACKET_AND_CLOSE_BRACKET, serial(0x2D, 0x6), ')'),
    // Level 22
    MORSE_POINT_SYMBOL(MORSE_SYMBOL, MORSE_LEVEL_POINT_AND_COMMA, serial(0x00, 0x6), '.'),
    MORSE_COMMA_SYMBOL(MORSE_SYMBOL, MORSE_LEVEL_POINT_AND_COMMA, serial(0x15, 0x6), ','),
    // Level 23
    MORSE_SEMICOLON_SYMBOL(MORSE_SYMBOL, MORSE_LEVEL_POINT_AND_COMMA, serial(0x2A, 0x6), ';'),
    MORSE_COLON_SYMBOL(MORSE_SYMBOL, MORSE_LEVEL_POINT_AND_COMMA, serial(0x38, 0x6), ':'),
    // Level 24
    MORSE_QUOTES_SYMBOL(MORSE_SYMBOL, MORSE_LEVEL_QUOTES_AND_APOSTROPHE, serial(0x12, 0x6), '\"'),
    MORSE_APOSTROPHE_SYMBOL(MORSE_SYMBOL, MORSE_LEVEL_QUOTES_AND_APOSTROPHE, serial(0x1E, 0x6), '\''),
    // Level 25
    MORSE_DASH_SYMBOL(MORSE_SYMBOL, MORSE_LEVEL_DASH_AND_SLASH, serial(0x21, 0x6), '-'),
    MORSE_SLASH_SYMBOL(MORSE_SYMBOL, MORSE_LEVEL_DASH_AND_SLASH, serial(0x12, 0x5), '/'),
    // Level 26
    MORSE_QUESTION_MARK_SYMBOL(MORSE_SYMBOL, MORSE_LEVEL_QUESTION_MARK_AND_EXCLAMATION_MARK, serial(0x0C, 0x6), '?'),
    MORSE_EXCLAMATION_MARK_SYMBOL(MORSE_SYMBOL, MORSE_LEVEL_QUESTION_MARK_AND_EXCLAMATION_MARK, serial(0x33, 0x6), '!'),
    // Level 27
    MORSE_AT_SYMBOL(MORSE_SYMBOL, MORSE_LEVEL_AT, serial(0x1A, 0x6), '@');

    private final int m_data;
    private final char m_symbol;
    private final MorseLevel m_level;
    private final MorseLanguage m_language;
    private static HashMap<MorseLanguage, HashMap<MorseLevel, ArrayList<Morse>>> DATA_TO_SYMBOL =
            new HashMap<MorseLanguage, HashMap<MorseLevel, ArrayList<Morse>>>();

    private Morse(MorseLanguage language, MorseLevel level,
                  int morseData, char symbol) {
        m_data = morseData;
        m_symbol = symbol;
        m_level = level;
        m_language = language;
    }

    private void postInit() {
        for (Morse morse : values()) {
            HashMap<MorseLevel, ArrayList<Morse>> map = DATA_TO_SYMBOL.get(morse.m_language);

            if (map == null) {
                map = DATA_TO_SYMBOL.put(morse.m_language,
                        new HashMap<MorseLevel, ArrayList<Morse>>());
            }

            ArrayList<Morse> arr = map.get(morse.m_level);

            if (arr == null) {
                arr = map.put(morse.m_level,
                        new ArrayList<Morse>());
            }

            arr.add(morse);
        }
    }

    public static int serial(int data, int length) {
        return ((data & ((0x1 << length) - 1)) |
                (length << 0x18));
    }

    public static int addPointRaw(int dataRaw) {
        return addPointRaw(data(dataRaw), length(dataRaw));
    }

    public static int addPointRaw(int data, int length) {
        return serial(data << 1, length + 1);
    }

    public static int addDashRaw(int dataRaw) {
        return addDashRaw(data(dataRaw), length(dataRaw));
    }

    public static int addDashRaw(int data, int length) {
        return serial((data << 1) | 0x1, length + 1);
    }

    public static int length(int dataRaw) {
        return dataRaw >> 18;
    }

    public static int data(int dataRaw) {
        return (dataRaw & ((0x1 <<
                (length(dataRaw))) - 1));
    }

    public static int empty() {
        return serial(0, 0);
    }

    public int data() {
        return data(m_data);
    }

    public int length() {
        return length(m_data);
    }

    public int dataRaw() {
        return m_data;
    }

    public MorseLanguage language() {
        return m_language;
    }

    public char symbol() {
        return m_symbol;
    }

    public static Morse findMorse(MorseLanguage language, int data, int length) {
        return findMorse(language, serial(data, length));
    }
    public static Morse findMorse(MorseLanguage language, int dataRaw) {
        Morse foundMorse = null;
        for (Morse morse : values()) {
            if (morse.dataRaw() == dataRaw && language == morse.language()) {
                foundMorse = morse;
                break;
            }
        }
        return foundMorse;
    }

    public static ArrayList<Morse> symbols(
            MorseLanguage language, MorseLevel level) {
        HashMap<MorseLevel, ArrayList<Morse>> map =  DATA_TO_SYMBOL.get(language);

        if (map == null) {
            return null;
        }

        return map.get(level);
    }

    public static Morse randomSymbolCurrentLevel(
            MorseLanguage language, MorseLevel level) {
        ArrayList<Morse> arr = symbols(language, level);

        if (arr == null) {
            return null;
        }

        int size = arr.size();

        if (size == 0) {
            return null;
        }

        return arr.get((int)(Math.random() * size));
    }

    public static Morse randomSymbolCurrentAndLessLevel(
            MorseLanguage language, MorseLevel level) {
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