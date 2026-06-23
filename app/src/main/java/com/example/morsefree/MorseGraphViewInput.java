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
import android.util.Log;

import androidx.annotation.NonNull;

public class MorseGraphViewInput extends MorseGraphView {
    private char m_currentSymbol = '\0';
    private Morse m_morse = new Morse();
    private Language m_defaultLatCyr = LATIN;

    public MorseGraphViewInput(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void start() {
        m_morse.clear();
        clear();
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
        add(time());
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
        Log.d("morse", m_morse.toString());
        Const _const = Const.find(getLanguage(), m_morse, m_defaultLatCyr);

        Log.d("find", String.valueOf(_const));

        if (_const != null) {
            String oldString = getText();
            boolean isAdd = m_currentSymbol == '\0';
            m_currentSymbol = _const.getSymbol();
            if (isAdd) {
                setText(oldString != null ? oldString : "");
            } else {
                setText(oldString != null && !oldString.isEmpty() ?
                        oldString.substring(0, oldString.length() - 1) + m_currentSymbol
                        : String.valueOf(m_currentSymbol));
            }
        }
        Log.d("sentence", String.valueOf(getText()));
    }

    private void applySymbol() {
        m_currentSymbol = '\0';
        m_morse.clear();
    }

    private void applyWord() {
        applySymbol();
        setText(getText() + ' ');
    }

    public Language getDefaultLatCyr() {
        return m_defaultLatCyr;
    }

    public void setLanguage(Language language) {
        super.setLanguage(language);
        m_morse.setLanguage(language);
    }

    public void setDefaultLatCyr(Language language) {
        if (language == LATIN || language == CYRILLIC) {
            m_defaultLatCyr = language;
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        drawStandardView(canvas);

        if (isEmpty()) {
            return;
        }

        if (isRunning()) {
            drawTime(canvas, time(), END, true);
            postInvalidateOnAnimation();
        } else {
            drawTime(canvas, get(0), BEGIN, false);
        }
    }
}
