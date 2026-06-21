package com.example.morsefree;

import static com.example.morsefree.Morse.Const;
import static com.example.morsefree.Morse.Const.*;

import static com.example.morsefree.Morse.Language;
import static com.example.morsefree.Morse.Language.*;

import static com.example.morsefree.Morse.Level;
import static com.example.morsefree.Morse.Level.*;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;

public class MorseGraphViewOutput extends MorseGraphView {
    private boolean m_isLess = false;
    private int m_lengthSentence = 1;
    private boolean m_isRandomLengthSentence;

    public MorseGraphViewOutput(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setText(@NonNull String text) {
        if (text == null || text.isEmpty()
                || text.charAt(0) == ' ' || getTime() == null) {
            Log.d("setText", "error");
            return;
        }

        super.setText(text);

        clear();

        long currentTime = 0;

        add(currentTime);

        Log.d("setText", text);

        for (char symbol : text.toCharArray()) {
            if (symbol == ' ') {
                set(size() - 1, currentTime +=
                        (getTime().getPeriodGapWord() - get(size() - 1)));
            }

            Morse.Const _const = Morse.Const.find(symbol);

            if (_const == null) {
                continue;
            }

            Morse morse = _const.getMorse();

            do {
                add(currentTime +=
                        (morse.isPointOnTop() ? getTime().getPeriodPoint()
                                : getTime().getPeriodDash()));

                add(currentTime += getTime().getPeriodGapBase());
            } while (morse.pop());

            set(size() - 1, currentTime +=
                    (getTime().getPeriodGapSymbol() - get(size() - 1)));
        }

        remove(size() - 1);

        postInvalidateOnAnimation();
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
        Const _const = Const.randomSymbolCurrentAndLessLevel(getLanguage(), getLevel());
        if (_const != null) {
            return _const.getSymbol();
        }
        return '\0';
    }

    char randomSymbolCurrentLevel() {
        Const _const = Const.randomSymbolCurrentLevel(getLanguage(), getLevel());
        if (_const != null) {
            return _const.getSymbol();
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
