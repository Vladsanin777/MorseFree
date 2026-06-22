package com.example.morsefree;

import static com.example.morsefree.Morse.Level.*;

import static com.example.morsefree.Morse.Language.*;

import android.util.Log;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Morse {
    private int m_data = 0;
    private byte m_length = 0;
    private Language m_language = Language.defaultValue();

    public enum Level {
        E_AND_T, I_AND_M, A_AND_N, S_AND_O,
        U_AND_G, R_AND_K, W_AND_D, H_AND_SH,
        V_AND_CH, F_AND_Q, YU_AND_Z, L_AND_Y,
        YA_AND_C, P_AND_X, J_AND_B, HARD_SING,
        ONE_AND_SIX, TWO_AND_SEVEN,
        THREE_AND_EIGHT, FOUR_AND_NINE,
        FIVE_AND_ZERO, OPEN_BRACKET_AND_CLOSE_BRACKET,
        POINT_AND_COMMA, SEMICOLON_AND_COLON,
        QUOTES_AND_APOSTROPHE, DASH_AND_SLASH,
        QUESTION_MARK_AND_EXCLAMATION_MARK, AT;

        public static Level defaultValue() {
            return E_AND_T;
        }

        public static Level defaultLatin() {
            return E_AND_T;
        }

        public static Level defaultCyrillic() {
            return E_AND_T;
        }

        public static Level defaultNumber() {
            return ONE_AND_SIX;
        }

        public static Level defaultSymbol() {
            return OPEN_BRACKET_AND_CLOSE_BRACKET;
        }

        public static Level end() {
            return AT;
        }

        public boolean is(Level level) {
            return this == level;
        }
    }

    public enum Language {
        LATIN(defaultLatin().ordinal(), defaultNumber().ordinal()),
        CYRILLIC(defaultCyrillic().ordinal(), defaultNumber().ordinal()),
        NUMBER(defaultNumber().ordinal(), defaultSymbol().ordinal()),
        SYMBOL(defaultSymbol().ordinal(), end().ordinal() + 1);

        private int m_beginOrdinal = 0;
        private int m_endOrdinal = 0;
        private Language(int beginOrdinal, int endOrdinal) {
            m_beginOrdinal = beginOrdinal;
            m_endOrdinal = endOrdinal;
        }

        public int getBeginOrdinal() {
            return m_beginOrdinal;
        }

        public int getEndOrdinal() {
            return m_endOrdinal;
        }

        public static Language defaultValue() {
            return LATIN;
        }

        public boolean is(Language language) {
            return !((this == CYRILLIC && language == LATIN) ||
                    (this == LATIN && language == CYRILLIC));
        }
    }

    public enum Const {
        // Unit 1
        // Level 1
        E_LATIN('E', 0x00, 0x01, LATIN, E_AND_T),
        E_CYRILLIC('Е', 0x00, 0x01, CYRILLIC, E_AND_T),
        T_LATIN('T', 0x01, 0x01, LATIN, E_AND_T),
        T_CYRILLIC('Т', 0x01, 0x01, CYRILLIC, E_AND_T),
        // Unit 2
        // Level 2
        I_LATIN('I', 0x00, 0x02, LATIN, I_AND_M),
        I_CYRILLIC('И', 0x00, 0x02, CYRILLIC, I_AND_M),
        M_LATIN('M', 0x03, 0x02, LATIN, I_AND_M),
        M_CYRILLIC('М', 0x03, 0x02, CYRILLIC, I_AND_M),
        // Level 3
        A_LATIN('A', 0x01, 0x02, LATIN, A_AND_N),
        A_CYRILLIC('А', 0x01, 0x02, CYRILLIC, A_AND_N),
        N_LATIN('N', 0x02, 0x02, LATIN, A_AND_N),
        N_CYRILLIC('Н', 0x02, 0x2, CYRILLIC, A_AND_N),
        // Unit 3
        // Level 4
        S_LATIN('S', 0x00, 0x03, LATIN, S_AND_O),
        S_CYRILLIC('С', 0x00, 0x03, CYRILLIC, S_AND_O),
        O_LATIN('O', 0x07, 0x03, LATIN, S_AND_O),
        O_CYRILLIC('О', 0x07, 0x03, CYRILLIC, S_AND_O),
        // Level 5
        U_LATIN('U', 0x01, 0x03, LATIN, U_AND_G),
        U_CYRILLIC('У', 0x01, 0x03, CYRILLIC, U_AND_G),
        G_LATIN('G', 0x06, 0x03, LATIN, U_AND_G),
        G_CYRILLIC('Г', 0x06, 0x03, CYRILLIC, U_AND_G),
        // Level 6
        R_LATIN('R', 0x02, 0x03, LATIN, R_AND_K),
        R_CYRILLIC('Р', 0x02, 0x03, CYRILLIC, R_AND_K),
        K_LATIN('K', 0x05, 0x03, LATIN, R_AND_K),
        K_CYRILLIC('К', 0x05, 0x03, CYRILLIC, R_AND_K),
        // Level 7
        W_LATIN('W', 0x03, 0x03, LATIN, W_AND_D),
        W_CYRILLIC('В', 0x03, 0x03, CYRILLIC, W_AND_D),
        D_LATIN('D', 0x04, 0x03, LATIN, W_AND_D),
        D_CYRILLIC('Д', 0x04, 0x03, CYRILLIC, W_AND_D),
        // Unit 4
        // Level 8
        H_LATIN('H', 0x00, 0x04, LATIN, H_AND_SH),
        H_CYRILLIC('Х', 0x00, 0x04, CYRILLIC, H_AND_SH),
        SH_CYRILLIC('Ш', 0x0F, 0x04, CYRILLIC, H_AND_SH),
        // Level 9
        V_LATIN('V', 0x01, 0x04, LATIN, V_AND_CH),
        V_CYRILLIC('Ж', 0x01, 0x04, CYRILLIC, V_AND_CH),
        CH_CYRILLIC('Ч', 0x0E, 0x04, CYRILLIC, V_AND_CH),
        // Level 10
        F_LATIN('F', 0x02, 0x04, LATIN, F_AND_Q),
        F_CYRILLIC('Ф', 0x02, 0x4, CYRILLIC, F_AND_Q),
        Q_LATIN('Q', 0x0D, 0x04, LATIN, F_AND_Q),
        Q_CYRILLIC('Щ', 0x0D, 0x04, CYRILLIC, F_AND_Q),
        // Level 11
        YU_CYRILLIC('Ю', 0x03, 0x04, CYRILLIC, YU_AND_Z),
        Z_LATIN('Z', 0x0C, 0x04, LATIN, YU_AND_Z),
        Z_CYRILLIC('З', 0x0C, 0x04, CYRILLIC, YU_AND_Z),
        // Level 12
        L_LATIN('L', 0x04, 0x04, LATIN, L_AND_Y),
        L_CYRILLIC('Л', 0x04, 0x04, CYRILLIC, L_AND_Y),
        Y_LATIN('Y', 0x0B, 0x04, LATIN, L_AND_Y),
        Y_CYRILLIC('Ы', 0x0B, 0x04, CYRILLIC, L_AND_Y),
        // Level 13
        YA_CYRILLIC('Я', 0x05, 0x4, CYRILLIC, YA_AND_C),
        C_LATIN('C', 0x0A, 0x04, LATIN, YA_AND_C),
        C_CYRILLIC('Ц', 0x0A, 0x04, CYRILLIC, YA_AND_C),
        // Level 14
        P_LATIN('P', 0x06, 0x04, LATIN, P_AND_X),
        P_CYRILLIC('P', 0x06, 0x04, CYRILLIC, P_AND_X),
        X_LATIN('X', 0x09, 0x04, LATIN, P_AND_X),
        X_CYRILLIC('Ь', 0x09, 0x04, CYRILLIC, P_AND_X),
        // Level 15
        J_LATIN('J', 0x07, 0x04, LATIN, J_AND_B),
        J_CYRILLIC('Й', 0x07, 0x04, CYRILLIC, J_AND_B),
        B_LATIN('B', 0x08, 0x04, LATIN, J_AND_B),
        B_CYRILLIC('Б', 0x08, 0x04, CYRILLIC, J_AND_B),
        // Level additional
        HARD_SING_CYRILLIC('Ъ', 0x1A, 0x05, CYRILLIC, HARD_SING),
        // Unit 5
        // Level 16
        ONE('1', 0x0F, 0x05, NUMBER, ONE_AND_SIX),
        SIX('9', 0x10, 0x05, NUMBER, ONE_AND_SIX),
        // Level 17
        TWO('2', 0x07, 0x05, NUMBER, TWO_AND_SEVEN),
        SEVEN('7', 0x18, 0x05, NUMBER, TWO_AND_SEVEN),
        // Level 18
        THREE('3', 0x03, 0x05, NUMBER, THREE_AND_EIGHT),
        EIGHT('8', 0x1C, 0x05, NUMBER, THREE_AND_EIGHT),
        // Level 19
        FOUR('4', 0x01, 0x05, NUMBER, FOUR_AND_NINE),
        NINE('9', 0x1E, 0x05, NUMBER, FOUR_AND_NINE),
        // Level 20
        FIVE('5', 0x00, 0x05, NUMBER, FIVE_AND_ZERO),
        ZERO('0', 0x1F, 0x05, NUMBER, FIVE_AND_ZERO),
        // Unit 6
        // Level 21
        OPEN_BRACKET('(', 0x16, 0x06, SYMBOL, OPEN_BRACKET_AND_CLOSE_BRACKET),
        CLOSE_BRACKET(')', 0x2D, 0x06, SYMBOL, OPEN_BRACKET_AND_CLOSE_BRACKET),
        // Level 22
        POINT('.', 0x00, 0x06, SYMBOL, POINT_AND_COMMA),
        COMMA(',', 0x15, 0x06, SYMBOL, POINT_AND_COMMA),
        // Level 23
        SEMICOLON(';', 0x2A, 0x06, SYMBOL, POINT_AND_COMMA),
        COLON(':', 0x38, 0x06, SYMBOL, POINT_AND_COMMA),
        // Level 24
        QUOTES('\"', 0x12, 0x06, SYMBOL, QUOTES_AND_APOSTROPHE),
        APOSTROPHE('\'', 0x1E, 0x06, SYMBOL, QUOTES_AND_APOSTROPHE),
        // Level 25
        DASH_SYMBOL('-', 0x21, 0x06, SYMBOL, DASH_AND_SLASH),
        SLASH_SYMBOL('/', 0x12, 0x05, SYMBOL, DASH_AND_SLASH),
        // Level 26
        QUESTION_MARK('?', 0x0C, 0x06, SYMBOL, QUESTION_MARK_AND_EXCLAMATION_MARK),
        EXCLAMATION_MARK('!', 0x33, 0x06, SYMBOL, QUESTION_MARK_AND_EXCLAMATION_MARK),
        // Level 27
        AT('@', 0x1A, 0x06, SYMBOL, Level.AT);

        private Morse m_morse = null;
        private char m_symbol = '\0';
        private Level m_level = null;

        private final static HashMap<Language, HashMap<Morse, Const>> DATA_TO_SYMBOL =
                new HashMap<Language, HashMap<Morse, Const>>();
        private final static HashMap<Language, HashMap<Level, ArrayList<Const>>> DATA_LEVEL =
                new HashMap<Language, HashMap<Level, ArrayList<Const>>>();
        private final static HashMap<Character, Const> DATA_VALUE =
                new HashMap<Character, Const>();

        private Const(char symbol) {
            this(symbol, 0x00, 0x00);
        }

        private Const(char symbol, int data, int length) {
            this(symbol, data, length,
                    Language.defaultValue(), Level.defaultValue());
        }
        private Const(char symbol, int data, int length,
                           Language language, Level level) {
            m_morse = new Morse(data, length, language);
            m_symbol = symbol;
            m_level = level;
        }

        static {
            postInit();
            for (HashMap<Morse, Const> consts : DATA_TO_SYMBOL.values()) {
                for (Const _const : consts.values()) {
                    Log.d("symbol", _const.toString() + '\t' + _const.getSymbol());
                }
            }
        }

        private static void postInit() {
            for (Language language : Language.values()) {
                HashMap<Level, ArrayList<Const>> levels =
                        new HashMap<Level, ArrayList<Const>>();
                DATA_TO_SYMBOL.put(language, new HashMap<Morse, Const>());
                DATA_LEVEL.put(language, levels);

                for (int beginOrdinal = language.getBeginOrdinal(),
                        endOrdinal = language.getEndOrdinal();
                        beginOrdinal < endOrdinal; beginOrdinal++) {
                    levels.put(Level.values()[beginOrdinal], new ArrayList<Const>());
                }
            }

            for (Const _const : values()) {
                HashMap<Level, ArrayList<Const>> levels =
                        DATA_LEVEL.get(_const.getLanguage());
                HashMap<Morse, Const> currentLanguage =
                        DATA_TO_SYMBOL.get(_const.getLanguage());

                ArrayList<Const> level = null;

                DATA_VALUE.put(_const.m_symbol, _const);

                if (currentLanguage != null) {
                    currentLanguage.put(_const.m_morse, _const);
                }

                if (levels == null) {
                    continue;
                }

                level = levels.get(_const.getLevel());

                if (level == null) {
                    continue;
                }

                level.add(_const);
            }
        }

        public Language getLanguage() {
            return m_morse.getLanguage();
        }

        public Level getLevel() {
            return m_level;
        }

        public char getSymbol() {
            return m_symbol;
        }

        public Morse getMorse() {
            return m_morse.copy();
        }

        public boolean equals(Morse morse) {
            if (morse == null) {
                return false;
            }

            return morse.equals(m_morse);
        }

        private static @Nullable Const findPvt(Language language, Morse morse) {
            HashMap<Morse, Const> currentLanguage = DATA_TO_SYMBOL.get(language);

            if (currentLanguage == null) {
                return null;
            }

            Log.d("count", String.valueOf(currentLanguage.values().size()));

            Log.d("I", String.valueOf(I_LATIN.m_morse.m_data) + '\t'
                    + String.valueOf(I_LATIN.m_morse.m_length));

            Log.d("morse", String.valueOf(morse.m_data) + '\t'
                    + String.valueOf(morse.m_length));

            return currentLanguage.get(morse);
        }

        public static @Nullable Const find(Language language,
                Morse morse, Language defaultLatCyr) {
            Const res = findPvt(language, morse);
            if (res != null) {
                return res;
            }

            if (language == LATIN || language == CYRILLIC) {
                res = findPvt(NUMBER, morse);
                if (res != null) {
                    return res;
                }

                res = findPvt(SYMBOL, morse);
            } else {
                res = findPvt(defaultLatCyr, morse);
                if (res != null) {
                    return res;
                }

                if (language == SYMBOL) {
                    res = findPvt(NUMBER, morse);
                }

                if (language == NUMBER) {
                    res = findPvt(SYMBOL, morse);
                }
            }

            return res;
        }

        public static @Nullable Const find(char symbol) {
            return DATA_VALUE.get(symbol);
        }

        public static @Nullable ArrayList<Const> symbols(
                Language currentLanguage, Level currentLevel) {
            HashMap<Level, ArrayList<Const>> levels =  DATA_LEVEL.get(currentLanguage);

            if (levels == null) {
                return null;
            }

            return levels.get(currentLevel);
        }

        public static @Nullable Const randomSymbolCurrentLevel(
                Language language, Level level) {
            ArrayList<Const> arr = symbols(language, level);

            if (arr == null) {
                return null;
            }

            int size = arr.size();

            if (size == 0) {
                return null;
            }

            return arr.get((int)(Math.random() * size));
        }

        public static Const randomSymbolCurrentAndLessLevel(
                @NonNull Language language, Level level) {
            switch (language) {
                case CYRILLIC:
                    level = (int)(Math.random() * 3) == 0 ?
                            level : Level.values()
                            [((int)(Math.random() * ((level.ordinal() -
                            CYRILLIC.getBeginOrdinal() + 1)))) +
                            CYRILLIC.getBeginOrdinal()];
                    break;
                case LATIN:
                    level = (int)(Math.random() * 3) == 0 ?
                            level : Level.values()
                            [((int)(Math.random() * ((level.ordinal() -
                            LATIN.getBeginOrdinal() + 1)))) +
                            LATIN.getBeginOrdinal()];
                    break;
                case NUMBER:
                    level = (int)(Math.random() * 3) == 0 ?
                            level : Level.values()
                            [((int)(Math.random() * ((level.ordinal() -
                            NUMBER.getBeginOrdinal() + 1)))) +
                            NUMBER.getBeginOrdinal()];
                    break;
                case SYMBOL:
                    level = (int)(Math.random() * 3) == 0 ?
                            level : Level.values()
                            [((int)(Math.random() * ((level.ordinal() -
                            SYMBOL.getBeginOrdinal() + 1)))) +
                            SYMBOL.getBeginOrdinal()];
                    break;
                default:
                    break;
            }
            return randomSymbolCurrentLevel(language, level);
        }

        @androidx.annotation.NonNull
        @Override
        public String toString() {
            return m_morse.toString();
        }
    }

    public Morse() {
        this(0, 0);
    }

    public Morse(int data, int length) {
        this(data, length, Language.defaultValue());
    }
    public Morse(int data, int length, Language language) {
        if (32 < length || length < 0) {
            clear();
        } else {
            m_length = (byte) length;
            m_data = data & (0x01 << length) - 1;
        }

        m_language = language;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Morse morse = (Morse) object;
        return m_data == morse.m_data && m_length == morse.m_length && m_language.is(morse.m_language);
    }

    public boolean equals(Const morse) {
        if (morse == null) {
            return false;
        }

        return morse.equals(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_data, m_length);
    }

    public void clear() {
        m_data = 0;
        m_length = (byte) 0;
    }

    public void addPoint() {
        m_length++;
        m_data <<= 0x01;
    }

    public void addDash() {
        m_length++;
        m_data <<= 0x01;
        m_data |= 0x01;
    }

    public boolean pop() {
        if (m_length == 0) {
            return false;
        }

        m_length--;
        m_data &= (0x01 << m_length) - 1;

        return m_length != 0;
    }

    public boolean isPointOnTop() {
        return ((m_data >> (m_length - 1)) & 0x01) == 0;
    }

    public boolean isDashOnTop() {
        return ((m_data >> (m_length - 1)) & 0x01) == 1;
    }

    public byte getLength() {
        return m_length;
    }

    public int getData() {
        return m_data;
    }

    public Morse copy() {
        return  new Morse(m_data, m_length);
    }

    public Language getLanguage() {
        return m_language;
    }

    public void setLanguage(Language language) {
        m_language = language;
    }

    @Override
    public String toString() {
        if (m_length == 0) {
            return "";
        }

        StringBuilder string = new StringBuilder(m_length);

        for (int i = m_length - 1; i >= 0; i--) {
            int bit = (m_data >> i) & 0x01;

            if (bit == 0) {
                string.append('.');
            } else {
                string.append('-');
            }
        }

        return string.toString();
    }
}