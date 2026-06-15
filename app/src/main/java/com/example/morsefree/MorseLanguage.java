package com.example.morsefree;

import static com.example.morsefree.MorseLevel.*;

public enum MorseLanguage {
    MORSE_LATIN(MORSE_LEVEL_E_AND_T.ordinal()),
    MORSE_CYRILLIC(MORSE_LEVEL_E_AND_T.ordinal()),
    MORSE_NUMBER(MORSE_LEVEL_ONE_AND_SIX.ordinal()),
    MORSE_SYMBOL(MORSE_LEVEL_OPEN_BRACKET_AND_CLOSE_BRACKET.ordinal());

    private int m_levelOrdinal;
    private MorseLanguage(int ordinal) {
        m_levelOrdinal = ordinal;
    }

    public int getLevelOrdinal() {
        return m_levelOrdinal;
    }

    public static MorseLanguage defaultValue() {
        return MORSE_LATIN;
    }
}
