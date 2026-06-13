package com.example.morsefree;

import static com.example.morsefree.MorseLanguage.*;
import static com.example.morsefree.MorseLevel.*;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.widget.TextView;

public class LessonTransmit extends AppCompatActivity {
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
    private Morse m_morse = new Morse();
    private char m_currentSymbol = '\0';
    private MorseTolerances m_tolerances = new MorseTolerances();
    private boolean m_isRunning = true;

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

        m_currentSymbolTextView = findViewById(R.id.current_symbol);
        m_userSentenceTextView = findViewById(R.id.user_sentence);
        m_sentenceTextView = findViewById(R.id.sentence);
        m_userSentenceMorse = findViewById(R.id.user_sentence_morse);
        m_sentenceMorse = findViewById(R.id.sentence_morse);

        Button transmitButton = findViewById(R.id.button_transmit);
        transmitButton.setOnTouchListener(this::OnTouchTransmitButton);

        m_lessonTransmitLayout = findViewById(R.id.root_layout);

        m_lessonTransmitLayout.post(this::initInfoGradient);

        m_userSentenceMorse.setTolerances(m_tolerances);
        m_sentenceMorse.setTolerances(m_tolerances);

        m_userSentenceMorse.setIsStartUp(false);
        m_sentenceMorse.setIsStartUp(false);

        Button backButton = findViewById(R.id.button_back);

        backButton.setOnClickListener(this::onClickBack);

        updateSentence();
        clearUserSentence();
    }

    private void onClickBack(View view) {
        finish();
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
        ObjectAnimator.ofInt(m_infoGradient, "alpha",
                0xff, 0x00).setDuration(1000).start();
    }

    private void checkMessage() {
        checkMessage(false);
    }

    private void checkMessage(boolean isError) {
        applySymbol();

        m_handler.removeCallbacks(m_idleRunnable);
        m_userSentenceMorse.stop();
        m_sentenceMorse.stop();
        m_tolerances.clearBreakPoint();
        m_isRunning = false;

        if (!isError && m_userSentence.equals(m_sentence)) {
            winGradient();
        } else {
            failGradient();
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
        m_sentenceMorse.setText(m_sentence);
        clearUserSentence();
        m_userSentenceMorse.clear();
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

    private boolean OnTouchTransmitButton(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!m_isRunning) {
                    return true;
                }

                m_tolerances.createBreakPoint();

                m_handler.removeCallbacks(m_idleRunnable);
                m_sound.start();
                view.setPressed(true);
                if (m_tolerances.isDiff()) {
                    m_userSentenceMorse.press();

                    if (m_tolerances.isGapBase()) {
                        ;
                    } else if (m_tolerances.isGapSymbol()) {
                        applySymbol();
                    } else if (m_tolerances.isGapWord()) {
                        applyWord();
                    } else {
                        notCorrectInterval(m_tolerances.getDiff());
                        return true;
                    }
                } else {
                    m_tolerances.createStartPoint();
                    m_userSentenceMorse.start(true);
                    m_sentenceMorse.start(false);
                }

                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (!m_isRunning) {
                    updateSentence();
                    m_isRunning = true;
                    return true;
                }

                m_tolerances.createBreakPoint();

                m_handler.postDelayed(m_idleRunnable,
                        m_tolerances.getPeriodGapWord() +
                        m_tolerances.getPeriodGapWordEpsilonHigh());

                m_sound.stop();
                view.setPressed(false);
                m_userSentenceMorse.press();

                if (m_tolerances.isPoint()) {
                    applyPoint();
                } else if (m_tolerances.isDash()) {
                    applyDash();
                } else {
                    notCorrectInterval(m_tolerances.getDiff());

                    return true;
                }

                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
        }
        return false;
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
        MorseConst morse = MorseConst.find(m_language, m_morse);
        if (morse != null) {
            m_currentSymbol = morse.getSymbol();
        } else {
            m_currentSymbol = '\0';
        }
        m_currentSymbolTextView.setText(String.valueOf(m_currentSymbol));
    }

    private void applyPoint() {
        m_morse.addPoint();

        updateSymbol();
    }

    private void applyDash() {
        m_morse.addDash();

        updateSymbol();
    }

    private void notCorrectInterval(long diff) {
        checkMessage(true);
        Log.d("MorseFree", "Not correct interval");
    }

    private void applySymbol() {
        updateSymbol();
        if (m_currentSymbol != '\0') {
            updateUserSentence(m_currentSymbol);
        }
        m_morse.clear();
        m_currentSymbol = '\0';
        m_currentSymbolTextView.setText(String.valueOf(m_currentSymbol));
    }

    private void applyWord() {
        updateUserSentence(' ');
        m_morse.clear();
        m_currentSymbol = '\0';
        m_currentSymbolTextView.setText(String.valueOf(m_currentSymbol));
    }
}