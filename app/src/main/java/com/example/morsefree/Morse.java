package com.example.morsefree;

import static com.example.morsefree.MorseLanguage.*;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public enum Morse {
    MORSE_EMPTY(0, MORSE_LATIN, '\0'),
    // Unit 1
    // Level 1
    MORSE_E_LATIN(convertMorse(0x0, 0x1), MORSE_LATIN,'E'),
    MORSE_E_CYRILLIC(convertMorse(0x0, 0x1), MORSE_CYRILLIC, 'Е'),
    MORSE_T_LATIN(convertMorse(0x1, 0x1), MORSE_LATIN, 'T'),
    MORSE_T_CYRILLIC(convertMorse(0x1, 0x1), MORSE_CYRILLIC, 'Т'),
    // Unit 2
    // Level 2
    MORSE_I_LATIN(convertMorse(0x0, 0x2), MORSE_LATIN, 'I'),
    MORSE_I_CYRILLIC(convertMorse(0x0, 0x2), MORSE_CYRILLIC, 'И'),
    MORSE_M_LATIN(convertMorse(0x3, 0x2), MORSE_LATIN, 'M'),
    MORSE_M_CYRILLIC(convertMorse(0x3, 0x2), MORSE_CYRILLIC, 'М'),
    // Level 3
    MORSE_A_LATIN(convertMorse(0x1, 0x2), MORSE_LATIN, 'A'),
    MORSE_A_CYRILLIC(convertMorse(0x1, 0x2), MORSE_CYRILLIC, 'А'),
    MORSE_N_LATIN(convertMorse(0x2, 0x2), MORSE_LATIN, 'N'),
    MORSE_N_CYRILLIC(convertMorse(0x2, 0x2), MORSE_CYRILLIC, 'Н'),
    // Unit 3
    // Level 4
    MORSE_S_LATIN(convertMorse(0x0, 0x3), MORSE_LATIN, 'S'),
    MORSE_S_CYRILLIC(convertMorse(0x0, 0x3), MORSE_CYRILLIC, 'С'),
    MORSE_O_LATIN(convertMorse(0x7, 0x3), MORSE_LATIN, 'O'),
    MORSE_O_CYRILLIC(convertMorse(0x7, 0x3), MORSE_CYRILLIC, 'О'),
    // Level 5
    MORSE_U_LATIN(convertMorse(0x1, 0x3), MORSE_LATIN, 'U'),
    MORSE_U_CYRILLIC(convertMorse(0x1, 0x3), MORSE_CYRILLIC, 'У'),
    MORSE_G_LATIN(convertMorse(0x6, 0x3), MORSE_LATIN, 'G'),
    MORSE_G_CYRILLIC(convertMorse(0x6, 0x3), MORSE_CYRILLIC, 'Г'),
    // Level 6
    MORSE_R_LATIN(convertMorse(0x2, 0x3), MORSE_LATIN, 'R'),
    MORSE_R_CYRILLIC(convertMorse(0x2, 0x3), MORSE_CYRILLIC, 'Р'),
    MORSE_K_LATIN(convertMorse(0x5, 0x3), MORSE_LATIN, 'K'),
    MORSE_K_CYRILLIC(convertMorse(0x5, 0x3), MORSE_CYRILLIC, 'К'),
    // Level 7
    MORSE_W_LATIN(convertMorse(0x3, 0x3), MORSE_LATIN, 'W'),
    MORSE_W_CYRILLIC(convertMorse(0x3, 0x3), MORSE_CYRILLIC, 'В'),
    MORSE_D_LATIN(convertMorse(0x4, 0x3), MORSE_LATIN, 'D'),
    MORSE_D_CYRILLIC(convertMorse(0x4, 0x3), MORSE_CYRILLIC, 'Д'),
    // Unit 4
    // Level 8
    MORSE_H_LATIN(convertMorse(0x0, 0x4), MORSE_LATIN, 'H'),
    MORSE_H_CYRILLIC(convertMorse(0x0, 0x4), MORSE_CYRILLIC, 'Х'),
    MORSE_SH_CYRILLIC(convertMorse(0xF, 0x4), MORSE_CYRILLIC, 'Ш'),
    // Level 9
    MORSE_V_LATIN(convertMorse(0x1, 0x4), MORSE_LATIN, 'V'),
    MORSE_V_CYRILLIC(convertMorse(0x1, 0x4), MORSE_CYRILLIC, 'Ж'),
    MORSE_CH_CYRILLIC(convertMorse(0xE, 0x4), MORSE_CYRILLIC, 'Ч'),
    // Level 10
    MORSE_F_LATIN(convertMorse(0x2, 0x4), MORSE_LATIN, 'F'),
    MORSE_F_CYRILLIC(convertMorse(0x2, 0x4), MORSE_CYRILLIC, 'Ф'),
    MORSE_Q_LATIN(convertMorse(0xD, 0x4), MORSE_LATIN, 'Q'),
    MORSE_Q_CYRILLIC(convertMorse(0xD, 0x4), MORSE_CYRILLIC, 'Щ'),
    // Level 11
    MORSE_YU_CYRILLIC(convertMorse(0x3, 0x4), MORSE_CYRILLIC, 'Ю'),
    MORSE_Z_LATIN(convertMorse(0xC, 0x4), MORSE_LATIN, 'Z'),
    MORSE_Z_CYRILLIC(convertMorse(0xC, 0x4), MORSE_CYRILLIC, 'З'),
    // Level 12
    MORSE_L_LATIN(convertMorse(0x4, 0x4), MORSE_LATIN, 'L'),
    MORSE_L_CYRILLIC(convertMorse(0x4, 0x4), MORSE_CYRILLIC, 'Л'),
    MORSE_Y_LATIN(convertMorse(0xB, 0x4), MORSE_LATIN, 'Y'),
    MORSE_Y_CYRILLIC(convertMorse(0xB, 0x4), MORSE_CYRILLIC, 'Ы'),
    // Level 13
    MORSE_YA_CYRILLIC(convertMorse(0x5, 0x4), MORSE_CYRILLIC, 'Я'),
    MORSE_C_LATIN(convertMorse(0xA, 0x4), MORSE_LATIN, 'C'),
    MORSE_C_CYRILLIC(convertMorse(0xA, 0x4), MORSE_CYRILLIC, 'Ц'),
    // Level 14
    MORSE_P_LATIN(convertMorse(0x6, 0x4), MORSE_LATIN, 'P'),
    MORSE_P_CYRILLIC(convertMorse(0x6, 0x4), MORSE_CYRILLIC, 'P'),
    MORSE_X_LATIN(convertMorse(0x9, 0x4), MORSE_LATIN, 'X'),
    MORSE_X_CYRILLIC(convertMorse(0x9, 0x4), MORSE_CYRILLIC, 'Ь'),
    // Level 15
    MORSE_J_LATIN(convertMorse(0x7, 0x4), MORSE_LATIN, 'J'),
    MORSE_J_CYRILLIC(convertMorse(0x7, 0x4), MORSE_CYRILLIC, 'Й'),
    MORSE_B_LATIN(convertMorse(0x8, 0x4), MORSE_LATIN, 'B'),
    MORSE_B_CYRILLIC(convertMorse(0x8, 0x4), MORSE_CYRILLIC, 'Б'),
    // Level additional
    MORSE_HARD_SING_CYRILLIC(convertMorse(0x1A, 0x5), MORSE_CYRILLIC, 'Ъ'),
    // Unit 5
    // Level 16
    MORSE_ONE_NUMBER(convertMorse(0x0F, 0x5), MORSE_NUMBER, '1'),
    MORSE_SIX_NUMBER(convertMorse(0x10, 0x5), MORSE_NUMBER, '9'),
    // Level 17
    MORSE_TWO_NUMBER(convertMorse(0x07, 0x5), MORSE_NUMBER, '2'),
    MORSE_SEVEN_NUMBER(convertMorse(0x18, 0x5), MORSE_NUMBER, '7'),
    // Level 18
    MORSE_THREE_NUMBER(convertMorse(0x03, 0x5), MORSE_NUMBER, '3'),
    MORSE_EIGHT_NUMBER(convertMorse(0x1C, 0x5), MORSE_NUMBER, '8'),
    // Level 19
    MORSE_FOUR_NUMBER(convertMorse(0x01, 0x5), MORSE_NUMBER, '4'),
    MORSE_NINE_NUMBER(convertMorse(0x1E, 0x5), MORSE_NUMBER, '9'),
    // Level 20
    MORSE_FIVE_NUMBER(convertMorse(0x0, 0x5), MORSE_NUMBER, '5'),
    MORSE_ZERO_NUMBER(convertMorse(0x1F, 0x5), MORSE_NUMBER, '0'),
    // Unit 6
    // Level 21
    MORSE_OPEN_BRACKET_SYMBOL(convertMorse(0x16, 0x6), MORSE_SYMBOL, '('),
    MORSE_CLOSE_BRACKET_SYMBOL(convertMorse(0x2D, 0x6), MORSE_SYMBOL, ')'),
    // Level 22
    MORSE_POINT_SYMBOL(convertMorse(0x00, 0x6), MORSE_SYMBOL, '.'),
    MORSE_COMMA_SYMBOL(convertMorse(0x15, 0x6), MORSE_SYMBOL, ','),
    // Level 23
    MORSE_SEMICOLON_SYMBOL(convertMorse(0x2A, 0x6), MORSE_SYMBOL, ';'),
    MORSE_COLON_SYMBOL(convertMorse(0x38, 0x6), MORSE_SYMBOL, ':'),
    // Level 24
    MORSE_QUOTES_SYMBOL(convertMorse(0x12, 0x6), MORSE_SYMBOL, '\"'),
    MORSE_APOSTROPHE_SYMBOL(convertMorse(0x1E, 0x6), MORSE_SYMBOL, '\''),
    // Level 25
    MORSE_DASH_SYMBOL(convertMorse(0x21, 0x6), MORSE_SYMBOL, '-'),
    MORSE_SLASH_SYMBOL(convertMorse(0x12, 0x5), MORSE_SYMBOL, '/'),
    // Level 26
    MORSE_QUESTION_MARK_SYMBOL(convertMorse(0x0C, 0x6), MORSE_SYMBOL, '?'),
    MORSE_EXCLAMATION_MARK_SYMBOL(convertMorse(0x33, 0x6), MORSE_SYMBOL, '!'),
    // Level 27
    MORSE_AT_SYMBOL(convertMorse(0x1A, 0x6), MORSE_SYMBOL, '@');

    private int m_morseData;
    private char m_symbol;
    private MorseLanguage m_language;
    private MorseLevel m_level;
    private Function<Morse, Character> getSymbol;
    private static final Map<Integer, Morse> DATA_TO_SYMBOL =
        new HashMap<Integer, Morse>();

    static {
        buildCache();
    }

    private Morse(int morseData, MorseLanguage language, char symbol) {
        m_morseData = morseData;
        m_language = language;
        m_symbol = symbol;
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
        return m_symbol;
    }

    char random_e_and_t() {
        Morse morse = null;
        switch (m_language) {
            case MORSE_CYRILLIC:
                morse = Math.random() < 0.5 ? MORSE_E_CYRILLIC : MORSE_T_CYRILLIC;
                break;
            default:
                morse = Math.random() < 0.5 ? MORSE_E_LATIN : MORSE_T_LATIN;
                break;
        }
        return morse.m_symbol;
    }

    char random_i_and_m() {
        Morse morse = null;
        switch (m_language) {
            case MORSE_CYRILLIC:
                morse = Math.random() < 0.5 ? MORSE_I_CYRILLIC : MORSE_M_CYRILLIC;
                break;
            default:
                morse = Math.random() < 0.5 ? MORSE_I_LATIN : MORSE_M_LATIN;
                break;
        }
        return morse.m_symbol;
    }

    char random_a_and_n() {
        Morse morse = null;
        switch (m_language) {
            case MORSE_CYRILLIC:
                morse = Math.random() < 0.5 ? MORSE_A_CYRILLIC : MORSE_N_CYRILLIC;
                break;
            default:
                morse = Math.random() < 0.5 ? MORSE_A_LATIN : MORSE_N_LATIN;
                break;
        }
        return morse.m_symbol;
    }

    char random_s_and_o() {
        Morse morse = null;
        switch (m_language) {
            case MORSE_CYRILLIC:
                morse = Math.random() < 0.5 ? MORSE_S_CYRILLIC : MORSE_O_CYRILLIC;
                break;
            default:
                morse = Math.random() < 0.5 ? MORSE_S_LATIN : MORSE_O_LATIN;
                break;
        }
        return morse.m_symbol;
    }

    char random_u_and_g() {
        Morse morse = null;
        switch (m_language) {
            case MORSE_CYRILLIC:
                morse = Math.random() < 0.5 ? MORSE_U_CYRILLIC : MORSE_G_CYRILLIC;
                break;
            default:
                morse = Math.random() < 0.5 ? MORSE_U_LATIN : MORSE_G_LATIN;
                break;
        }
        return morse.m_symbol;
    }

    char random_r_and_k() {
        Morse morse = null;
        switch (m_language) {
            case MORSE_CYRILLIC:
                morse = Math.random() < 0.5 ? MORSE_R_CYRILLIC : MORSE_K_CYRILLIC;
                break;
            default:
                morse = Math.random() < 0.5 ? MORSE_R_LATIN : MORSE_K_LATIN;
                break;
        }
        return morse.m_symbol;
    }

    char random_w_and_d() {
        Morse morse = null;
        switch (m_language) {
            case MORSE_CYRILLIC:
                morse = Math.random() < 0.5 ? MORSE_W_CYRILLIC : MORSE_D_CYRILLIC;
                break;
            default:
                morse = Math.random() < 0.5 ? MORSE_W_LATIN : MORSE_D_LATIN;
                break;
        }
        return morse.m_symbol;
    }

    char random_h_and_sh() {
        Morse morse = null;
        switch (m_language) {
            case MORSE_CYRILLIC:
                morse = Math.random() < 0.5 ? MORSE_H_CYRILLIC : MORSE_SH_CYRILLIC;
                break;
            default:
                morse = MORSE_H_LATIN;
                break;
        }
        return morse.m_symbol;
    }

    char random_v_and_ch() {
        Morse morse = null;
        switch (m_language) {
            case MORSE_CYRILLIC:
                morse = Math.random() < 0.5 ? MORSE_V_CYRILLIC : MORSE_CH_CYRILLIC;
                break;
            default:
                morse = MORSE_V_LATIN;
                break;
        }
        return morse.m_symbol;
    }

    char random_f_and_q() {
        Morse morse = null;
        switch (m_language) {
            case MORSE_CYRILLIC:
                morse = Math.random() < 0.5 ? MORSE_F_CYRILLIC : MORSE_Q_CYRILLIC;
                break;
            default:
                morse = Math.random() < 0.5 ? MORSE_F_LATIN : MORSE_Q_LATIN;
                break;
        }
        return morse.m_symbol;
    }

    char random_yu_and_z() {
        Morse morse = null;
        switch (m_language) {
            case MORSE_CYRILLIC:
                morse = Math.random() < 0.5 ? MORSE_YU_CYRILLIC : MORSE_Z_CYRILLIC;
                break;
            default:
                morse = MORSE_Z_LATIN;
                break;
        }
        return morse.m_symbol;
    }

    char random_l_and_y() {
        Morse morse = null;
        switch (m_language) {
            case MORSE_CYRILLIC:
                morse = Math.random() < 0.5 ? MORSE_L_CYRILLIC : MORSE_Y_CYRILLIC;
                break;
            default:
                morse = Math.random() < 0.5 ? MORSE_L_LATIN : MORSE_Y_LATIN;
                break;
        }
        return morse.m_symbol;
    }

    char random_ya_and_c() {
        Morse morse = null;
        switch (m_language) {
            case MORSE_CYRILLIC:
                morse = Math.random() < 0.5 ? MORSE_YA_CYRILLIC : MORSE_C_CYRILLIC;
                break;
            default:
                morse = MORSE_C_LATIN;
                break;
        }
        return morse.m_symbol;
    }

    char random_p_and_x() {
        Morse morse = null;
        switch (m_language) {
            case MORSE_CYRILLIC:
                morse = Math.random() < 0.5 ? MORSE_P_CYRILLIC : MORSE_X_CYRILLIC;
                break;
            default:
                morse = Math.random() < 0.5 ? MORSE_P_LATIN : MORSE_X_LATIN;
                break;
        }
        return morse.m_symbol;
    }

    char random_j_and_b() {
        Morse morse = null;
        switch (m_language) {
            case MORSE_CYRILLIC:
                morse = Math.random() < 0.5 ? MORSE_J_CYRILLIC : MORSE_B_CYRILLIC;
                break;
            default:
                morse = Math.random() < 0.5 ? MORSE_J_LATIN : MORSE_B_LATIN;
                break;
        }
        return morse.m_symbol;
    }

    char random_hard_sing() {
        Morse morse = null;
        switch (m_language) {
            case MORSE_CYRILLIC:
                morse = MORSE_HARD_SING_CYRILLIC;
                break;
            default:
                morse = MORSE_EMPTY;
                break;
        }
        return morse.m_symbol;
    }

    char random_one_and_six() {
        Morse morse = Math.random() < 0.5 ? MORSE_ONE_NUMBER : MORSE_SIX_NUMBER;
        return morse.m_symbol;
    }

    char random_two_and_seven() {
        Morse morse = Math.random() < 0.5 ? MORSE_TWO_NUMBER : MORSE_SEVEN_NUMBER;
        return morse.m_symbol;
    }

    char random_three_and_eight() {
        Morse morse = Math.random() < 0.5 ? MORSE_THREE_NUMBER : MORSE_EIGHT_NUMBER;
        return morse.m_symbol;
    }

    char random_four_and_nine() {
        Morse morse = Math.random() < 0.5 ? MORSE_FOUR_NUMBER : MORSE_NINE_NUMBER;
        return morse.m_symbol;
    }

    char random_five_and_zero() {
        Morse morse = Math.random() < 0.5 ? MORSE_FIVE_NUMBER : MORSE_ZERO_NUMBER;
        return morse.m_symbol;
    }

    char random_open_bracket_and_close_bracket() {
        Morse morse = Math.random() < 0.5 ? MORSE_OPEN_BRACKET_SYMBOL : MORSE_CLOSE_BRACKET_SYMBOL;
        return morse.m_symbol;
    }

    char random_point_and_comma() {
        Morse morse = Math.random() < 0.5 ? MORSE_POINT_SYMBOL : MORSE_COMMA_SYMBOL;
        return morse.m_symbol;
    }

    char random_semicolon_and_colon() {
        Morse morse = Math.random() < 0.5 ? MORSE_SEMICOLON_SYMBOL : MORSE_COLON_SYMBOL;
        return morse.m_symbol;
    }

    char random_quotes_and_apostrophe() {
        Morse morse = Math.random() < 0.5 ? MORSE_QUOTES_SYMBOL : MORSE_APOSTROPHE_SYMBOL;
        return morse.m_symbol;
    }

    char random_dash_and_slash() {
        Morse morse = Math.random() < 0.5 ? MORSE_DASH_SYMBOL : MORSE_SLASH_SYMBOL;
        return morse.m_symbol;
    }

    char random_question_mark_and_exclamation_mark() {
        Morse morse = Math.random() < 0.5 ? MORSE_QUESTION_MARK_SYMBOL : MORSE_EXCLAMATION_MARK_SYMBOL;
        return morse.m_symbol;
    }

    char random_at() {
        Morse morse = MORSE_AT_SYMBOL;
        return morse.m_symbol;
    }

    public char generateSymbol(@NonNull MorseLevel level) {
        switch (level) {
            case MORSE_LEVEL_E_AND_T:
                return random_e_and_t();
            case MORSE_LEVEL_I_AND_M:
                return random_i_and_m();
            case MORSE_LEVEL_A_AND_N:
                return random_a_and_n();
            case MORSE_LEVEL_S_AND_O:
                return random_s_and_o();
            case MORSE_LEVEL_U_AND_G:
                return random_u_and_g();
            case MORSE_LEVEL_R_AND_K:
                return random_r_and_k();
            case MORSE_LEVEL_W_AND_D:
                return random_w_and_d();
            case MORSE_LEVEL_H_AND_SH:
                return random_h_and_sh();
            case MORSE_LEVEL_V_AND_CH:
                return random_v_and_ch();
            case MORSE_LEVEL_F_AND_Q:
                return random_f_and_q();
            case MORSE_LEVEL_YU_AND_Z:
                return random_yu_and_z();
            case MORSE_LEVEL_L_AND_Y:
                return random_l_and_y();
            case MORSE_LEVEL_YA_AND_C:
                return random_ya_and_c();
            case MORSE_LEVEL_P_AND_X:
                return random_p_and_x();
            case MORSE_LEVEL_J_AND_B:
                return random_j_and_b();
            case MORSE_LEVEL_HARD_SING:
                return random_hard_sing();
            case MORSE_LEVEL_ONE_AND_SIX:
                return random_one_and_six();
            case MORSE_LEVEL_TWO_AND_SEVEN:
                return random_two_and_seven();
            case MORSE_LEVEL_THREE_AND_EIGHT:
                return random_three_and_eight();
            case MORSE_LEVEL_FOUR_AND_NINE:
                return random_four_and_nine();
            case MORSE_LEVEL_FIVE_AND_ZERO:
                return random_five_and_zero();
            case MORSE_LEVEL_OPEN_BRACKET_AND_CLOSE_BRACKET:
                return random_open_bracket_and_close_bracket();
            case MORSE_LEVEL_POINT_AND_COMMA:
                return random_point_and_comma();
            case MORSE_LEVEL_SEMICOLON_AND_COLON:
                return random_semicolon_and_colon();
            case MORSE_LEVEL_QUOTES_AND_APOSTROPHE:
                return random_quotes_and_apostrophe();
            case MORSE_LEVEL_DASH_AND_SLASH:
                return random_dash_and_slash();
            case MORSE_LEVEL_QUESTION_MARK_AND_EXCLAMATION_MARK:
                return random_question_mark_and_exclamation_mark();
            case MORSE_LEVEL_AT:
                return random_at();
        }
        return '\0';
    }

    public char getRandomSymbol() {

        char ch = '\0';
        MorseLevel level = null;
        while (ch == '\0') {
            switch (m_language) {
                case MORSE_CYRILLIC:
                    level = (int)(Math.random() * 3) == 0 ?
                            m_level : MorseLevel.values()
                            [((int)(Math.random() * ((m_level.ordinal() -
                            MORSE_CYRILLIC.getLevelOrdinal() + 1)))) +
                            MORSE_CYRILLIC.getLevelOrdinal()];
                    break;
                case MORSE_LATIN:
                    level = (int)(Math.random() * 3) == 0 ?
                            m_level : MorseLevel.values()
                            [((int)(Math.random() * ((m_level.ordinal() -
                                    MORSE_LATIN.getLevelOrdinal() + 1)))) +
                                    MORSE_LATIN.getLevelOrdinal()];
                    break;
                case MORSE_NUMBER:
                    level = (int)(Math.random() * 3) == 0 ?
                            m_level : MorseLevel.values()
                            [((int)(Math.random() * ((m_level.ordinal() -
                                    MORSE_NUMBER.getLevelOrdinal() + 1)))) +
                                    MORSE_NUMBER.getLevelOrdinal()];
                    break;
                case MORSE_SYMBOL:
                    level = (int)(Math.random() * 3) == 0 ?
                            m_level : MorseLevel.values()
                            [((int)(Math.random() * ((m_level.ordinal() -
                                    MORSE_SYMBOL.getLevelOrdinal() + 1)))) +
                                    MORSE_SYMBOL.getLevelOrdinal()];
                    break;
                default:
                    break;
            }
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
            m_symbol = '\0';
        } else {
            m_morseData = morseFound.m_morseData;
            m_symbol = morseFound.m_symbol;
        }
    }

    public void clear() {
        m_morseData = 0;
        m_symbol = '\0';
    }

    public static Morse getEmpty() {
        return new Morse(0, MORSE_LATIN, '\0');
    }

    public void setLanguage(MorseLanguage language) {
        m_language = language;
    }
}