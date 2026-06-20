package com.example.morsefree;

import static com.example.morsefree.MorseTime.Action.*;
import static com.example.morsefree.MorseTime.Action;

import static com.example.morsefree.MorseGraphView.Position.*;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

public class MorseGraphViewInput extends MorseGraphView {
    private char m_currentSymbol = '\0';
    private Morse m_morse = new Morse();
    private MorseLanguage m_language = MorseLanguage.defaultValue();
    private MorseLevel m_level = MorseLevel.defaultValue();
    private boolean m_isLess = false;
    private Morse.Language m_language = LATIN;
    private Morse.Level m_level = E_AND_T;

    public MorseGraphViewInput(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void start() {
        press();
        super.start();
    }

    public void stop() {
        if ((size() & 1) == 1) {
            press();
        }
        super.stop();
    }

    public char getCurrentSymbol() {
        return m_currentSymbol;
    }

    public Action press() {
        setTimePoint();
        add(getTime());
        if ((size() & 0x01) == 0) {
            return isPointDash();
        } else {
            return isGap();
        }

        MorseConst morse = MorseConst.find(m_language, m_morse);
        if (morse != null) {
            m_currentSymbol = morse.getSymbol();
        } else {
            m_currentSymbol = '\0';
        }
    }

    public MorseLanguage getLanguage() {
        return m_language;
    }

    public void setLanguage(MorseLanguage language) {
        m_language = language;
    }

    public MorseLevel getLevel() {
        return m_level;
    }

    public void setLevel(MorseLevel level) {
        m_level = level;
    }



    char randomSymbolCurrentAndLessLevel() {
        MorseConst morse = MorseConst.randomSymbolCurrentAndLessLevel(m_language, m_level);
        if (morse != null) {
            return morse.getSymbol();
        }
        return '\0';
    }

    char randomSymbolCurrentLevel() {
        MorseConst morse = MorseConst.randomSymbolCurrentLevel(m_language, m_level);
        if (morse != null) {
            return morse.getSymbol();
        }
        return '\0';
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

    private void applyPoint() {
        m_morse.addPoint();
    }

    private void applyDash() {
        m_morse.addDash();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        drawStandardView(canvas);

        if (isEmpty()) {
            return;
        }

        if (isRunning()) {
            drawTime(canvas, getTime(), END, true);
            postInvalidateOnAnimation();
        } else {
            drawTime(canvas, get(0), BEGIN, false);
        }
    }
}
