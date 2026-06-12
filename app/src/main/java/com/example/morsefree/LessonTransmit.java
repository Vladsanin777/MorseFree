package com.example.morsefree;

import static com.example.morsefree.Morse.*;
import static com.example.morsefree.MorseLanguage.*;
import static com.example.morsefree.MorseLevel.*;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.widget.TextView;

import java.util.Map;

public class LessonTransmit extends AppCompatActivity {
    private long m_timeBreakPoint = 0;
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
    private boolean m_isLess;
    private MorseLanguage m_language;
    private MorseLevel m_level;
    private String m_userSentence;
    private String m_sentence;
    private boolean m_isRandomLengthSentence;
    private int m_lengthSentence;
    private final Handler m_handler =
            new android.os.Handler(Looper.getMainLooper());
    private final Runnable m_idleRunnable = this::checkMessage;
    private TextView m_currentSymbolTextView;
    private TextView m_userSentenceTextView;
    private TextView m_sentenceTextView;
    private MorseGraphView m_userSentenceMorse = null;
    private MorseGraphView m_sentenceMorse = null;
    private final MorseAudioPlayer m_sound = new MorseAudioPlayer();
    private ConstraintLayout m_lessonTransmitLayout;
    private GradientDrawable m_infoGradient;
    private int[] m_colorsInfoGradient;
    private int m_dataMorse;
    private char m_currentSymbol = '\0';

    @SuppressLint({"ClickableViewAccessibility", "ResourceType", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        m_language = MORSE_LATIN;
        m_level = MORSE_LEVEL_E_AND_T;
        m_isLess = false;
        m_lengthSentence = 1;
        m_isRandomLengthSentence = false;

        setContentView(R.layout.lesson_transmit);

        if (intent != null) {
            m_language = MorseLanguage.values()[intent.getIntExtra("MORSE_LANGUAGE", m_language.ordinal())];
            m_level = MorseLevel.values()[intent.getIntExtra("MORSE_LEVEL", m_level.ordinal())];
            m_isLess = intent.getBooleanExtra("MORSE_IS_LESS", m_isLess);
            m_lengthSentence = intent.getIntExtra("MORSE_LENGTH", 1);
            m_isRandomLengthSentence = intent.getBooleanExtra("MORSE_IS_RANDOM_LENGTH", m_isRandomLengthSentence);
            String nameLevel = intent.getStringExtra("LEVEL_NAME");

            TextView levelNameTextView = findViewById(R.id.title_transmit_level_name);
            levelNameTextView.setText(nameLevel);
        }

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

        m_currentSymbolTextView = findViewById(R.id.current_symbol);
        m_userSentenceTextView = findViewById(R.id.user_sentence);
        m_sentenceTextView = findViewById(R.id.sentence);
        m_userSentenceMorse = findViewById(R.id.user_sentence_morse);
        m_sentenceMorse = findViewById(R.id.sentence_morse);

        Button transmitButton = findViewById(R.id.button_transmit);
        transmitButton.setOnTouchListener(this::OnTouchTransmitButton);

        m_lessonTransmitLayout = findViewById(R.id.root_layout);

        m_lessonTransmitLayout.post(this::initInfoGradient);

        updateSentence();

        m_userSentenceMorse.setTimingPoint(m_intervalTimeForPoint);
    }

    void initInfoGradient() {
        m_infoGradient = new GradientDrawable();
        m_infoGradient.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        m_colorsInfoGradient = new int[] {0x00000000, 0x00000000};
        m_infoGradient.setColors(m_colorsInfoGradient);
        applyInfoGradient(0xffff00ff);
    }

    private void failGradient() {
        applyInfoGradient(0xffff0000);
    }

    private void winGradient() {
        applyInfoGradient(0xff00ff00);
    }

    private void applyInfoGradient(int finalColor) {
        m_colorsInfoGradient[1] = finalColor;
        float radius = Math.max(m_lessonTransmitLayout.getWidth(), m_lessonTransmitLayout.getHeight());
        m_infoGradient.setGradientRadius(radius);
        m_infoGradient.setColors(m_colorsInfoGradient);

        animationInfoGradient();
    }

    private void animationInfoGradient() {
        m_lessonTransmitLayout.setBackground(m_infoGradient);
        ObjectAnimator.ofInt(m_infoGradient, "alpha", 0xff, 0x00).setDuration(1000).start();
    }

    private void checkMessage() {
        applySymbol();
        m_userSentenceMorse.stop();
        if (m_userSentence.equals(m_sentence)) {
            completedStep();
        } else {
            notCompletedStep();
        }
    }

    void clearSentence() {
        m_sentence = "";
        m_sentenceTextView.setText(m_sentence);
    }

    void updateSentence() {
        if (m_isRandomLengthSentence) {
            m_sentence = newSentence(((int)(Math.random() * (m_lengthSentence - 1))) + 1);
        } else {
            m_sentence = newSentence(m_lengthSentence);
        }
        Log.d("Sentence", m_sentence);
        m_sentenceTextView.setText(m_sentence);
        clearUserSentence();
    }

    void clearUserSentence() {
        m_userSentence = "";
        m_userSentenceTextView.setText(m_userSentence);
    }

    void updateUserSentence(String string) {
        m_userSentence += string;
        m_userSentenceTextView.setText(m_userSentence);
    }

    void updateUserSentence(char symbol) {
        m_userSentence += symbol;
        m_userSentenceTextView.setText(m_userSentence);
    }

    void updateUserSentence(char[] string) {
        m_userSentence += string;
        m_userSentenceTextView.setText(m_userSentence);
    }

    private void completedStep() {
        winGradient();
        m_timeBreakPoint = 0;
        Log.d("MorseFree", "Completed: " + m_userSentence
                + "\n need: " + m_sentence + '\n');
    }

    private void notCompletedStep() {
        failGradient();
        m_timeBreakPoint = 0;
        Log.d("MorseFree", "Not completed: " + m_userSentence
                + "\n need: " + m_sentence + '\n');
    }

    private boolean OnTouchTransmitButton(View view, MotionEvent event) {
        long newTimeBreakPoint = System.nanoTime();
        long diff = newTimeBreakPoint - m_timeBreakPoint;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                m_handler.removeCallbacks(m_idleRunnable);
                startSound();
                view.setPressed(true);
                if (m_timeBreakPoint == 0) {
                    m_userSentenceMorse.startDown();
                } else {
                    m_userSentenceMorse.press();
                    if (isInterBase(diff))
                        ;
                    else if (isInterSymbol(diff))
                        applySymbol();
                    else if (isInterWord(diff))
                        applyWord();
                    else {
                        notCorrectInterval(diff);
                        return true;
                    }
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
                    notCorrectInterval(diff);
                    m_userSentenceMorse.stop();

                    return true;
                }
                m_userSentenceMorse.press();

                m_timeBreakPoint = newTimeBreakPoint;

                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
        }
        return false;
    }

    char randomSymbolCurrentAndLessLevel() {
        Morse morse = Morse.randomSymbolCurrentAndLessLevel(m_language, m_level);
        if (morse != null) {
            return morse.symbol();
        }
        return '\0';
    }

    char randomSymbolCurrentLevel() {
        Morse morse = Morse.randomSymbolCurrentLevel(m_language, m_level);
        if (morse != null) {
            return morse.symbol();
        }
        return '\0';
    }
    void startSound() {
        m_sound.start();
    }

    void stopSound() {
        m_sound.stop();
    }


    private String newSentence(int length) {
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

    private void updateSymbol() {
        Morse morse = Morse.findMorse(m_language, m_dataMorse);
        if (morse != null) {
            m_currentSymbol = morse.symbol();
        } else {
            m_currentSymbol = '\0';
        }
        m_currentSymbolTextView.setText(String.valueOf(m_currentSymbol));
    }

    private void applyPoint() {
        m_dataMorse = Morse.addPointRaw(m_dataMorse);

        updateSymbol();
    }

    private void applyDash() {
        m_dataMorse = Morse.addDashRaw(m_dataMorse);

        updateSymbol();
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
        notCompletedStep();
        Log.d("MorseFree", "Not correct interval");
    }

    private void applySymbol() {
        updateSymbol();
        updateUserSentence(m_currentSymbol);
        m_dataMorse = Morse.empty();
        m_currentSymbol = '\0';
    }

    private void applyWord() {
        updateUserSentence(' ');
        m_dataMorse = Morse.empty();
        m_currentSymbol = '\0';
    }
}
