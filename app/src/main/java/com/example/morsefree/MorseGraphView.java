package com.example.morsefree;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import android.content.Context;

import android.util.AttributeSet;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import static com.example.morsefree.MorseGraphView.Position.*;
import static com.example.morsefree.MorseLanguage.MORSE_LATIN;

import kotlinx.coroutines.internal.Symbol;

public class MorseGraphView extends View {
    private static final int DEFAULT_HEIGHT_DP = 60;
    private int m_defaultHeightPx = dpToPx(DEFAULT_HEIGHT_DP);
    private int m_thicknessDp = 6;
    private int m_thicknessPx = dpToPx(m_thicknessDp);
    private int m_offsetPx = m_thicknessPx / 2;
    private ArrayList<Long> m_changePoints = new ArrayList<Long>();
    private boolean m_isStartUp = false;
    private Paint m_paint = new Paint();
    private boolean m_isRunning = false;
    private boolean m_isInput = false;
    private String m_text = null;
    private MorseTolerances m_tolerances = null;
    private char m_currentSymbol = '\0';
    private Morse m_morse = new Morse();
    private MorseLanguage m_language = MorseLanguage.defaultValue();
    private MorseLevel m_level = MorseLevel.defaultValue();
    private boolean m_isLess = false;
    private int m_lengthSentence = 1;
    private boolean m_isRandomLengthSentence;

    enum Position {
        BEGIN, MIDDLE, END,
    }

    public MorseGraphView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        m_defaultHeightPx = dpToPx(DEFAULT_HEIGHT_DP);

        m_paint.setStyle(Paint.Style.STROKE);
        m_paint.setStrokeWidth(m_thicknessPx);
        m_paint.setStrokeCap(Paint.Cap.ROUND);
        m_paint.setAntiAlias(true);
        m_paint.setColor(0xFFFFFFFF);
    }

    public void setColor(int color) {
        m_paint.setColor(color);
    }

    public int getColor() {
        return m_paint.getColor();
    }

    public MorseTolerances getTolerances() {
        return m_tolerances;
    }

    public void setTolerances(MorseTolerances tolerances) {
        m_tolerances = tolerances;
    }

    public void clear() {
        m_text = null;
        m_changePoints.clear();
        postInvalidateOnAnimation();
    }

    public String getText() {
        return m_text;
    }

    public void setText(@NonNull String text) {
        if (text == null || text.isEmpty()
                || text.charAt(0) == ' ' || m_tolerances == null) {
            Log.d("setText", "error");
            return;
        }

        clear();

        m_text = text;

        long currentTime = 0;

        m_changePoints.add(currentTime);

        Log.d("setText", text);

        for (char symbol : text.toCharArray()) {
            if (symbol == ' ') {
                m_changePoints.set(m_changePoints.size() - 1,
                        currentTime += (m_tolerances.getPeriodGapWord() -
                                m_changePoints.get(m_changePoints.size() - 1)));
            }

            MorseConst morseConst = MorseConst.find(symbol);

            if (morseConst == null) {
                continue;
            }

            Morse morse = morseConst.getMorse();

            do {
                m_changePoints.add(currentTime +=
                        (morse.isPointOnTop() ? m_tolerances.getPeriodPoint()
                                : m_tolerances.getPeriodDash()));

                m_changePoints.add(currentTime += m_tolerances.getPeriodGapBase());
            } while (morse.pop());

            m_changePoints.set(m_changePoints.size() - 1,
                    currentTime += (m_tolerances.getPeriodGapSymbol() -
                            m_changePoints.get(m_changePoints.size() - 1)));
        }

        m_changePoints.remove(m_changePoints.size() - 1);

        postInvalidateOnAnimation();
    }

    public char getCurrentSymbol() {
        return m_currentSymbol;
    }

    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }

    private int pxToDp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density);
    }

    public void setThicknessDp(int dp) {
        m_thicknessPx = dpToPx(dp);
        m_thicknessDp = dp;
    }

    public void setThicknessPx(int px) {
        m_thicknessPx = px;
        m_thicknessDp = pxToDp(px);
    }

    public int getThicknessDp() {
        return m_thicknessDp;
    }

    public int getThicknessPx() {
        return m_thicknessPx;
    }

    public void start(boolean isInput) {
        m_isRunning = true;
        setIsInput(isInput);
        press();
        postInvalidateOnAnimation();
    }

    public void setIsStartUp(boolean isStartUp) {
        m_isStartUp = isStartUp;
    }

    public boolean getIsStartUp() {
        return m_isStartUp;
    }

    public void setIsInput(boolean isInput) {
        m_isInput = isInput;
    }

    public boolean getIsInput() {
        return m_isInput;
    }

    public void stop() {
        Log.d("stop", "stop");
        m_isRunning = false;
        if ((m_changePoints.size() & 1) == 1) {
            press();
        }
        postInvalidateOnAnimation();
    }

    public void startAgain() {
        m_isRunning = true;
        postInvalidateOnAnimation();
    }

    public void press() {
        if (m_isInput) {
            m_tolerances.point();
            m_changePoints.add(m_tolerances.getTime());
            Log.d("press", String.valueOf(m_changePoints.get(m_changePoints.size() - 1)));
            if (m_tolerances.isGapBase()) {
                ;
            } else if (m_tolerances.isGapSymbol()) {
                applySymbol();
            } else if (m_tolerances.isGapWord()) {
                applyWord();
            } else {
                notCorrectInterval(m_tolerances.getDiff());
            }

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
        if (m_isRandomLengthSentence) {
            m_text = newSentence(((int)(Math.random() * (m_lengthSentence - 1))) + 1);
        } else {
            m_text = newSentence(m_lengthSentence);
        }
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int height = resolveSize(m_defaultHeightPx, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    void drawTime(@NonNull Canvas canvas, long currentTime,
                  Position position, boolean isHalf) {
        int width = getWidth();
        int height = getHeight();
        float yCenter = height / 2f;

        if (isHalf) {
            width /= 2;
        }

        long widthTime = (width / m_thicknessPx) * m_tolerances.getPeriodPoint() + 1;

        long endPoint = 0;

        switch (position) {
            case BEGIN:
                endPoint = currentTime + widthTime;
                break;
            case MIDDLE:
                endPoint = currentTime + (widthTime / 2);
                break;
            case END:
                endPoint = currentTime;
                break;
        }

        long startTime = endPoint - widthTime;

        long lastPoint = endPoint;

        for (int index = m_changePoints.size() - 1; index >= 0; index--) {
            long currentPoint = m_changePoints.get(index);

            if (lastPoint < startTime) {
                break;
            }

            boolean shouldDraw = (((index & 0x01) == 1) && m_isStartUp)
                    || (((index & 0x01) == 0) && !m_isStartUp);

            if (shouldDraw) {
                float xStart = width - ((endPoint - currentPoint) /
                        (float) m_tolerances.getPeriodPoint()) * m_thicknessPx;

                float xEnd = width - ((endPoint - lastPoint) /
                        (float) m_tolerances.getPeriodPoint()) * m_thicknessPx;

                if (xStart < 0) xStart = 0;

                xStart += m_offsetPx;
                xEnd -= m_offsetPx;

                xEnd = Math.max(xStart, xEnd);

                // Log.d("xStart", String.valueOf(xStart));
                // Log.d("xEnd", String.valueOf(xEnd));

                canvas.drawLine(xStart, yCenter - m_offsetPx,
                        xEnd, yCenter - m_offsetPx, m_paint);
            }

            lastPoint = currentPoint;
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        if (m_changePoints.isEmpty()) {
            return;
        }

        if (m_isRunning) {
            if (m_isInput) {
                drawTime(canvas, m_tolerances.getTime(), END, true);
            } else {
                drawTime(canvas, m_tolerances.getTime(), MIDDLE, false);
            }
            postInvalidateOnAnimation();
        } else {
            drawTime(canvas, m_changePoints.get(0), BEGIN, false);
        }
    }
}