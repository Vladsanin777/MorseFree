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
    private boolean m_isLess;
    private MorseLanguage m_language;
    private MorseLevel m_level;
    private String m_userSentence;
    private String m_sentence;
    private final Handler m_handler =
            new android.os.Handler(Looper.getMainLooper());
    private final Runnable m_idleRunnable = this::checkMessage;
    private TextView m_userSentenceTextView;
    private TextView m_sentenceTextView;
    private final MorseAudioPlayer m_sound = new MorseAudioPlayer();
    private ConstraintLayout m_lessonTransmitLayout;
    private GradientDrawable m_infoGradient;
    private int[] m_colorsInfoGradient;
    private boolean m_is_typing;

    @SuppressLint({"ClickableViewAccessibility", "ResourceType", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        m_language = MORSE_LATIN;
        m_level = MORSE_LEVEL_E_AND_T;
        m_isLess = false;

        setContentView(R.layout.lesson_transmit);

        if (intent != null) {
            m_language = MorseLanguage.values()[intent.getIntExtra("MORSE_LANGUAGE", 0)];
            m_level = MorseLevel.values()[intent.getIntExtra("MORSE_LEVEL", 0)];
            m_isLess = intent.getBooleanExtra("MORSE_IS_LESS", false);
            String nameLevel = intent.getStringExtra("LEVEL_NAME");

            TextView levelNameTextView = findViewById(R.id.title_transmit_level_name);
            levelNameTextView.setText(nameLevel);
        }

        updateSentence();
        clearUserSentence();

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

        m_is_typing = false;

        m_lessonTransmitLayout = findViewById(R.id.root_layout);

        m_lessonTransmitLayout.post(this::initInfoGradient);
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
        if (m_userSentence.equals(m_sentence))
            complitedStep();
        else
            notComplitedStep();
        m_timeBreakPoint = 0;
        updateSentence();
        clearUserSentence();
    }

    void clearSentence() {
        m_sentence = "";
        m_sentenceTextView.setText(m_sentence);
    }

    void updateSentence() {
        if (m_isRandomLengthSentense) {
            m_sentence = newSentence(int)(Math.random() * m_lengthSentence));
        } else {
            m_sentence += newSentence(m_lengthSentence);
        }
        clearUserSentence();
    }

    void clearUserSentence() {
        m_userSentence = "";
        m_userSentenceTextView.setText(m_userSentence);
    }

    private void complitedStep() {
        winGradient();
        Log.d("MorseFree", "Complited: " + m_userSentence
                + "\n need: " + m_sentence + '\n');
    }

    private void notComplitedStep() {
        failGradient();
        Log.d("MorseFree", "Not complited: " + m_userSentence
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

    char randomSymbolCurrentAndLessLevel() {
        return Morse.randomSymbolCurrentAndLessLevel(m_language, m_level).symbol();
    }

    char randomSymbolCurrentLevel() {
        return Morse.randomSymbolCurrentLevel(m_language, m_level).symbol();
    }
    void startSound() {
        m_sound.start();
    }

    void stopSound() {
        m_sound.stop();
    }


    private String newSentence(int length) {
        String sentence = "";
        if (m_isLess) {
            for (int i = 0; i < length; i++) {
                sentence += randomSymbolCurrentAndLessLevel();
            }
        } else {
            for (int i = 0; i < length; i++) {
                sentence += randomSymbolCurrentLevel();
            }
        }
        return sentence;
    }

    private void updateSymbol() {
        updateSymbolRaw();
        updateUserSentenceTextView();
    }

    private void applyPoint() {
        m_morse.addPoint();
        updateSymbol();
        m_is_typing = true;
    }

    private void applyDash() {
        m_morse.addDash();
        updateSymbol();
        m_is_typing = true;
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
        failGradient();
        Log.d("MorseFree", "Not correct interval");
    }

    private void notCorrectPointDash(long diff) {
        failGradient();
        Log.d("MorseFree", "Not correct interval");
    }

    private void applySymbolRaw() {
        m_is_typing = false;
        Log.d("MorseFree", "input symbol: " + m_morse.getSymbol());
        m_morse.clear();
    }

    private void applySymbol() {
        applySymbolRaw();
        updateUserSentenceTextView();
    }

    private void applyWord() {
        m_userSentence += ' ';
        updateUserSentenceTextView();
    }
}
