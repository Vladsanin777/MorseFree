package com.example.morsefree;

import android.content.Context;
import android.util.AttributeSet;

public class MorseGraphViewOutput extends MorseGraphView {
    private boolean m_isLess = false;
    private int m_lengthSentence = 1;
    private boolean m_isRandomLengthSentence;

    public MorseGraphViewOutput(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public String newSentence(int length) {
        StringBuilder sentence = new StringBuilder(length);
        if (m_isLess) {
            while (sentence.length() < length) {
                char symbol = randomSymbolCurrentAndLessLevel();
                if (symbol != '\0') {
                    sentence.append(symbol);
                }
            }
        } else {
            while (sentence.length() < length) {
                char symbol = randomSymbolCurrentLevel();
                if (symbol != '\0') {
                    sentence.append(symbol);
                }
            }
        }
        return sentence.toString();
    }

    void updateSentence() {
        String text = null;
        if (m_isRandomLengthSentence) {
            text = newSentence(((int)(Math.random() * (m_lengthSentence - 1))) + 1);
        } else {
            text = newSentence(m_lengthSentence);
        }
        setText(text);
    }



    char randomSymbolCurrentAndLessLevel() {
        Morse.Const morse = Morse.Const.randomSymbolCurrentAndLessLevel(m_language, m_level);
        if (morse != null) {
            return morse.getSymbol();
        }
        return '\0';
    }

    char randomSymbolCurrentLevel() {
        Morse.Const morse = Morse.Const.randomSymbolCurrentLevel(m_language, m_level);
        if (morse != null) {
            return morse.getSymbol();
        }
        return '\0';
    }

    public boolean getIsLess() {
        return m_isLess;
    }

    public void setIsLess(boolean isLess) {
        m_isLess = isLess;
    }

    public int getLengthSentence() {
        return m_lengthSentence;
    }

    public void setLengthSentence(int lengthSentence) {
        m_lengthSentence = lengthSentence;
    }

    public boolean getIsRandomLengthSentence() {
        return m_isRandomLengthSentence;
    }

    public void setIsRandomLengthSentence(boolean isRandomLengthSentence) {
        m_isRandomLengthSentence = isRandomLengthSentence;
    }
}
