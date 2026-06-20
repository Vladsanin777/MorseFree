package com.example.morsefree;

import static com.example.morsefree.Morse.Level;
import static com.example.morsefree.Morse.Level.*;

import static com.example.morsefree.Morse.Language;
import static com.example.morsefree.Morse.Language.*;

import java.util.Objects;

public class Morse {
    private int m_data = 0;
    private byte m_length = 0;
    private Language m_language = Language.defaultValue;
    private Level m_level = Level.defaultValue;

    public enum Level {
        E_AND_T, I_AND_M, A_AND_N, S_AND_O,
        U_AND_G, R_AND_K, W_AND_D, H_AND_SH,
        V_AND_CH, F_AND_Q,
        YU_AND_Z, L_AND_Y,
        YA_AND_C, P_AND_X,
        J_AND_B, HARD_SING,
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
        public static Level defaultCirillic() {
            return E_AND_T;
        }
        public static Level defaultNumber() {
            return ONE_AND_SIX;
        }
        public static Level defaultSymbol() {
            return OPEN_BRACKET_AND_CLOSE_BRACKET;
        }
    }

    public enum Language {
        LATIN(defaultLatin().ordinal()),
        CYRILLIC(defaultCirillic().ordinal()),
        NUMBER(defaultNumber().ordinal()),
        SYMBOL(defaultSymbol().ordinal());

        private int m_levelOrdinal;
        private Language(int ordinal) {
            m_levelOrdinal = ordinal;
        }

        public int getLevelOrdinal() {
            return m_levelOrdinal;
        }

        public static Language defaultValue() {
            return LATIN;
        }
    }

    public Morse() {
        this(0, 0);
    }

    public Morse(int data, int length) {
        this(data, length, Language.defaultValue(), Level.defaultValue());
    }
    public Morse(int data, int length, Language language, Level level) {
        if (32 < length || length < 0) {
            clear();
        } else {
            m_length = (byte) length;
            m_data = data & (0x01 << length) - 1;
        }

        m_language = language;
        m_level = level;
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
        return m_data == morse.m_data && m_length == morse.m_length;
    }

    public boolean equals(MorseConst morse) {
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

    public Level getLevel() {
        return m_level;
    }

    public void setLanguage(Language language) {
        m_language = language;
    }

    public void setLevel(Level level) {
        m_level = level;
    }
}
