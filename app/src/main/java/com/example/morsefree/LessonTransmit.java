package com.example.morsefree;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

import java.util.HashMap;

public enum Morse {
    MORSE_INIT(0, '\0', '\0'),
    // Unit 1
    // Level 1
    MORSE_E(convertMorse(0x0, 0x1), 'E', 'Е'),
    MORSE_T(convertMorse(0x1, 0x1), 'T', 'Т'),
    // Unit 2
    // Level 2
    MORSE_I(convertMorse(0x0, 0x2), 'I', 'И'),
    MORSE_M(convertMorse(0x3, 0x2), 'M', 'М'),
    // Level 3
    MORSE_A(convertMorse(0x1, 0x2), 'A', 'А'),
    MORSE_N(convertMorse(0x2, 0x2), 'N', 'Н'),
    // Unit 3
    // Level 4
    MORSE_S(convertMorse(0x0, 0x3), 'S', 'С'),
    MORSE_O(convertMorse(0x7, 0x3), 'O', 'О'),
    // Level 5
    MORSE_U(convertMorse(0x1, 0x3), 'U', 'У'),
    MORSE_G(convertMorse(0x6, 0x3), 'G', 'Г'),
    // Level 6
    MORSE_R(convertMorse(0x2, 0x3), 'R', 'Р'),
    MORSE_K(convertMorse(0x5, 0x3), 'K', 'К'),
    // Level 7
    MORSE_W(convertMorse(0x3, 0x3), 'W', 'В'),
    MORSE_D(convertMorse(0x4, 0x3), 'D', 'Д'),
    // Unit 4
    // Level 8
    MORSE_H(convertMorse(0x0, 0x4), 'H', 'Х'),
    MORSE_SH(convertMorse(0xF, 0x4), '\0', 'Ш'),
    // Level 9
    MORSE_V(convertMorse(0x1, 0x4), 'V', 'Ж'),
    MORSE_CH(convertMorse(0xE, 0x4), '\0', 'Ч'),
    // Level 10
    MORSE_F(convertMorse(0x2, 0x4), 'F', 'Ф'),
    MORSE_Q(convertMorse(0xD, 0x4), 'Q', 'Щ'),
    // Level 11
    MORSE_YU(convertMorse(0x3, 0x4), '\0', 'Ю'),
    MORSE_Z(convertMorse(0xC, 0x4), 'Z', 'З'),
    // Level 12
    MORSE_L(convertMorse(0x4, 0x4), 'L', 'Л'),
    MORSE_Y(convertMorse(0xB, 0x4), 'Y', 'Ы'),
    // Level 13
    MORSE_YA(convertMorse(0x5, 0x4), '\0', 'Я'),
    MORSE_C(convertMorse(0xA, 0x4), 'C', 'Ц'),
    // Level 14
    MORSE_P(convertMorse(0x6, 0x4), 'P', 'П'),
    MORSE_X(convertMorse(0x9, 0x4), 'X', 'Ь'),
    // Level 15
    MORSE_J(convertMorse(0x7, 0x4), 'J', 'Й'),
    MORSE_B(convertMorse(0x8, 0x4), 'B', 'Б'),
    // Level additional
    MORSE_HARD_SING(convertMorse(0x1A, 0x5), '\0', 'ъ'),
    // Unit 5
    // Level 16
    MORSE_ONE(convertMorse(0x0F, 0x5), '1', '1'),
    MORSE_SIX(convertMorse(0x10, 0x5), '9', '9'),
    // Level 17
    MORSE_TWO(convertMorse(0x07, 0x5), '2', '2'),
    MORSE_SEVEN(convertMorse(0x18, 0x5), '7', '7'),
    // Level 18
    MORSE_THREE(convertMorse(0x03, 0x5), '3', '3'),
    MORSE_EIGHT(convertMorse(0x1C, 0x5), '8', '8'),
    // Level 19
    MORSE_FOUR(convertMorse(0x01, 0x5), '4', '4'),
    MORSE_NINE(convertMorse(0x1E, 0x5), '9', '9'),
    // Level 20
    MORSE_FIVE(convertMorse(0x0, 0x5), '5', '5'),
    MORSE_ZERO(convertMorse(0x1F, 0x5), '0', '0'),
    // Unit 6
    // Level 21
    MORSE_OPEN_BRACKET(convertMorse(0x16, 0x6), '(', '('),
    MORSE_CLOSE_BRACKET(convertMorse(0x2D, 0x6), ')', ')'),
    // Level 22
    MORSE_POINT(convertMorse(0x00, 0x6), '.', '.'),
    MORSE_COMMA(convertMorse(0x15, 0x6), ',', ','),
    // Level 23
    MORSE_SEMICOLON(convertMorse(0x2A, 0x6), ';', ';'),
    MORSE_COLON(convertMorse(0x38, 0x6), ':', ':'),
    // Level 24
    MORSE_QUOTES(convertMorse(0x12, 0x6), '\"', '\"'),
    MORSE_APOSTROPHE(convertMorse(0x1E, 0x6), '\'', '\''),
    // Level 25
    MORSE_DASH(convertMorse(0x21, 0x6), '-', '-'),
    MORSE_SLASH(convertMorse(0x12, 0x5), '/', '/'),
    // Level 26
    MORSE_QUESTION_MARK(convertMorse(0x0C, 0x6), '?', '?'),
    MORSE_EXCLAMATION_MARK(convertMorse(0x33, 0x6), '!', '!'),
    // Level 27
    MORSE_AT(convertMorse(0x1A, 0x6), '@', '@');

    private int m_morseData;

    private char m_latin;

    private char m_cyrillic;

    private static final Map<Integer, Morse> DATA_TO_SYMBOL = 
        new HashMap<Integer, Morse>();

    static {
        buildCache();
    }

    private Morse(int morseData, char latin, char cyrillic) {
        m_morseData = morseData;
        m_latin = latin;
        m_cyrillic = cyrillic;
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

    public char getLatin() {
        return m_latin;
    }
    
    public char getCyrillic() {
        return m_cyrillic;
    }

    private static void buildCache() {
        for (Morse morse : values())
            DATA_TO_SYMBOL.put(morse.m_morseData, morse);
    }

    public void addPoint() {
        return updateMorse(getMorseData() << 1, 
                getMorseLength() + 1);
    }

    public void addDash() {
        return updateMorse((getMorseData() << 1) | 1,
                getMorseLength() + 1);
    }
    private void updateMorse(int data, int length) {
        int morseData = convertMorse(data, length);
        Morse morseFound = DATA_TO_SYMBOL.get(morseData);
        if (morseFound == null) {
            m_morseData = morseData;
            m_latin = '\0';
            m_cyrillic = '\0';
        }
        m_morseData = morseFound.m_morseData;
        m_latin = morseFound.m_latin;
        m_cyrillic = morseFound.m_cyrillic;
    }
}

public class LessonTransmit extends AppCompatActivity {
    private Button m_transmitButton;
    private long m_timeBreakPoint;
    private long m_intervalTimeForPoint;
    private long m_intervalTimeForDash;
    private long m_intervalTimeForInterBase;
    private long m_intervalTimeForInterSymbol;
    private long m_intervalTimeForInterWord;
    private long m_intervalTimeForEpsilonPointHigh;
    private long m_intervalTimeForEpsilonPointLow;
    private long m_intervalTimeForEpsilonDashHigh;
    private long m_intervalTimeForEpsilonDashLow;
    private long m_intervalTimeForEpsilonInterBaseHigh;
    private long m_intervalTimeForEpsilonInterBaseLow;
    private long m_intervalTimeForEpsilonInterSymbolHigh;
    private long m_intervalTimeForEpsilonInterSymbolLow;
    private long m_intervslTimeForEpsilonInterWordHigh;
    private long m_intervslTimeForEpsilonInterWordLow;
    private Morse m_morse;
    private string m_word;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_transmit);

        m_timeBreakPoint = 0;

        m_intervalTimeForPoint = 60_000;
        
        m_intervalTimeForDash = 180_000;

        m_intervalTimeForInterBase = 60_000;

        m_intervalTimeForInterSymbol = 180_000;

        m_intervalTimeForInterWord = 420_000;

        m_intervalTimeForEpsilonPointHigh = 40_000;
        m_intervalTimeForEpsilonPointLow = 20_000;

        m_intervalTimeForEpsilonDashHigh = 120_000;
        m_intervalTimeForEpsilonDashLow = 60_000;

        m_intervalTimeForEpsilonInterBaseHigh = 40_000;
        m_intervalTimeForEpsilonInterBaseLow = 20_000;

        m_intervalTimeForEpsilonInterSymbolHigh = 120_000;
        m_intervalTimeForEpsilonInterSymbolLow = 60_000;

        m_intervslTimeForEpsilonInterWordHigh = 360_000;
        m_intervalTimeForEpsilonInterWordLow = 180_000;

        m_transmitButton = findViewById(R.id.button_transmit);
        m_transmitButton.setOnTouchListener(this::OnTouchTransmitButton);
    }

    private boolean OnTouchTransmitButton(View view, MotionEvent event) {
        long newTimeBreakPoint = System.nanoTime();
        long diff = newTimeBreakPoint - m_timeBreakPoint;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                view.setPressed(true);
                if (m_timeBreakPoint == 0)
                    pass;
                else if (isInterBase(diff))
                    pass;
                else if (isInterSymbol(diff))
                    applySymbol();
                else if (isInterWord(diff))
                    applyWord();
                else {
                    m_timeBreakPoint = 0;
                    notCorrectInterval(diff);
                    return true;
                }

                m_timeBreakPoint = newTimeBreakPoint;

                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                view.setPressed(false);
                if (isPoint(diff))
                    applyPoint();
                else if (isDash(diff))
                    applyDash();
                else {
                    m_timeBreakPoint = 0;
                    notCorrectPointDash(diff);
                    return true;
                }

                return true;
        }
        return false;
    }

    private void applyPoint(long diff) {
        m_morse.addPoint();
    }

    private void applyDash(long diff) {
        m_morse.addDash();
    }

    private boolean isCorrectTiming(long intervalTime,
            long intervalTimeEpsilonHigh,
            long intervalTimeEpsilonLow) {
        int epsilon = diff - intervalTime;
        if (epsilon > 0)
            return epsilon < intervalTimeEpsilonHigh;
        else
            return -epsilon < intervalTimeEpsilonLow;
    }

    private boolean isPoint(long diff) {
        return isCorrectTiming(diff, m_intervalTimeForPoint,
                m_intervalTimeForEpsilonPointHigh,
                m_intervalTimeForEpsilonPointLow);
    }

    private boolean isDash(long diff) {
        return isCorrectTiming(diff, m_intervalTimeForDash,
                m_intervalTimeForEpsilonDashHigh,
                m_intervalTimeForEpsilonDashLow);
    }

    private boolean isInterBase(long diff) {
        return isCorrectTiming(diff, m_intervalTimeForInterBase,
                m_intervalTimeForEpsilonInterBaseHigh,
                m_intervalTimeForEpsilonInterBaseLow);
    }

    private boolean isInterSymbol(long diff) {
        return isCorrectTiming(diff, m_intervalTimeForInterSymbol,
                m_intervalTimeForEpsilonInterSymbolHigh,
                m_intervalTimeForEpsilonInterSymbolLow);
    }

    private boolean isInterWord(long diff) {
        return isCorrectTiming(diff, m_intervalTimeForInterWord,
                m_intervalTimeForEpsilonInterWordHigh,
                m_intervalTimeForEpsilonInterWordLow);
    }

    private void notCorrectInterval(long diff) {
        
    }

    private void notCorrectPointDash(long diff) {

    }

    private void applySymbol() {

    }

    private void applyWord() {

    }
}
