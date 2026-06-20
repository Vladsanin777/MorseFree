package com.example.morsefree;

import static com.example.morsefree.MorseTime.Action.*;
import static com.example.morsefree.MorseTime.Action;

import static com.example.morsefree.Morse.Level;
import static com.example.morsefree.Morse.Level.*;

import static com.example.morsefree.Morse.Language;
import static com.example.morsefree.Morse.Language.*;

import static com.example.morsefree.Morse.Const;
import static com.example.morsefree.Morse.Const.*;

import static com.example.morsefree.MorseGraphView.Position.*;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

public class MorseGraphViewInput extends MorseGraphView {
    private char m_currentSymbol = '\0';
    private Morse m_morse = new Morse();
    private Language m_language = Language.defaultValue();
    private Level m_level = Level.defaultValue();
    private boolean m_isLess = false;

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
        Action action = NONE;
        setTimePoint();
        add(getTime());
        if ((size() & 0x01) == 0) {
            action = isPointDash();
        } else {
            action = isGap();
        }

        switch (action) {
            case NONE:
                break;
            case POINT:
                m_morse.addPoint();
                updateSymbol();
                break;
            case DASH:
                m_morse.addDash();
                updateSymbol();
                break;
            case GAP_BASE:
                break;
            case GAP_SYMBOL:
                applySymbol();
                break;
            case GAP_WORD:
                applyWord();
                break;
            default:
                break;
        }

        return action;
    }

    private void updateSymbol() {
        Const.find(m_language, m_morse);
    }

    public Language getLanguage() {
        return m_language;
    }

    public void setLanguage(Language language) {
        m_language = language;
    }

    public Level getLevel() {
        return m_level;
    }

    public void setLevel(Level level) {
        m_level = level;
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
