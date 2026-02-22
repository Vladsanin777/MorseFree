package com.example.morsefree;

import static com.example.morsefree.Language.LATIN;
import static com.example.morsefree.Language.CYRILLIC;
import static com.example.morsefree.Morse.MORSE_EMPTY;

import android.annotation.SuppressLint;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

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
    private long m_intervalTimeForEpsilonInterWordHigh;
    private long m_intervalTimeForEpsilonInterWordLow;
    private Morse m_morse;
    private String m_word;
    private String m_sentence;
    private String m_answerSentence;
    private final Handler m_handler =
            new android.os.Handler(Looper.getMainLooper());
    private final Runnable m_idleRunnable = this::checkMessage;

    private MorseAudioPlayer m_sound = new MorseAudioPlayer();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_transmit);

        m_morse = MORSE_EMPTY;

        m_morse.setLanguage(LATIN);

        m_word = "";

        m_sentence = "";

        m_answerSentence = "E";

        m_timeBreakPoint = 0;

        m_intervalTimeForPoint = 120_000_000;

        m_intervalTimeForDash = 360_000_000;

        m_intervalTimeForInterBase = 120_000_000;

        m_intervalTimeForInterSymbol = 360_000_000;

        m_intervalTimeForInterWord = 840_000_000;

        m_intervalTimeForEpsilonPointHigh = 60_000_000;
        m_intervalTimeForEpsilonPointLow = 90_000_000;

        m_intervalTimeForEpsilonDashHigh = 90_000_000;
        m_intervalTimeForEpsilonDashLow = 120_000_000;

        m_intervalTimeForEpsilonInterBaseHigh = 60_000_000;
        m_intervalTimeForEpsilonInterBaseLow = 90_000_000;

        m_intervalTimeForEpsilonInterSymbolHigh = 90_000_000;
        m_intervalTimeForEpsilonInterSymbolLow = 120_000_000;

        m_intervalTimeForEpsilonInterWordHigh = 300_000_000;
        m_intervalTimeForEpsilonInterWordLow = 360_000_000;

        m_transmitButton = findViewById(R.id.button_transmit);
        m_transmitButton.setOnTouchListener(this::OnTouchTransmitButton);

        Log.d("MorseFree", "On create transmit lesson");
    }

    private void checkMessage() {
        applyWordRaw();
        if (m_sentence.equals(m_answerSentence))
            complitedStep();
        else
            notComplitedStep();
        m_timeBreakPoint = 0;
        clearSentence();
        updateAnswerSentence();
    }

    void updateAnswerSentence() {
        m_answerSentence = "T";
    }

    void clearSentence() {
        m_sentence = "";
    }

    private void complitedStep() {
        Log.d("MorseFree", "Complited: " + m_sentence
                + ", need: " + m_answerSentence);
    }

    private void notComplitedStep() {
        Log.d("MorseFree", "Not complited: " + m_sentence
                + ", need: " + m_answerSentence);
    }

    private boolean OnTouchTransmitButton(View view, MotionEvent event) {
        long newTimeBreakPoint = System.nanoTime();
        long diff = newTimeBreakPoint - m_timeBreakPoint;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                m_handler.removeCallbacks(m_idleRunnable);
                startSound();
                view.setPressed(true);
                if (m_timeBreakPoint == 0)
                    ;
                else if (isInterBase(diff))
                    ;
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
                m_handler.postDelayed(m_idleRunnable,
                        (m_intervalTimeForInterWord +
                        m_intervalTimeForEpsilonInterWordHigh) / 1_000_000);
                stopSound();
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

                m_timeBreakPoint = newTimeBreakPoint;

                return true;
        }
        return false;
    }

    void startSound() {
        m_sound.start();
    }

    void stopSound() {
        m_sound.stop();
    }
    private void applyPoint() {
        m_morse.addPoint();
    }

    private void applyDash() {
        m_morse.addDash();
    }

    private boolean isCorrectTiming(
            long diff, long intervalTime,
            long intervalTimeEpsilonHigh,
            long intervalTimeEpsilonLow) {
        long epsilon = diff - intervalTime;
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

    private void applySymbolRaw() {
        m_word += m_morse.getSymbol();
    }

    private void applySymbol() {
        applySymbolRaw();
        m_morse.clear();
    }

    private void applyWordRaw() {
        applySymbol();
        m_sentence += m_word;
        m_word = "";
    }

    private void applyWord() {
        applyWordRaw();
        m_sentence += ' ';
    }
}
