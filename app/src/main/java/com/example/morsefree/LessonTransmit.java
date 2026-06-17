package com.example.morsefree;

import static com.example.morsefree.MorseLanguage.*;
import static com.example.morsefree.MorseLevel.*;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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

import com.example.morsefree.databinding.ActivityMainBinding;
import com.example.morsefree.databinding.LessonTransmitBinding;

public class LessonTransmit extends AppCompatActivity {
    private LessonTransmitBinding m_binding;
    private String m_userSentence;
    private final Handler m_handler =
            new android.os.Handler(Looper.getMainLooper());
    private final Runnable m_idleRunnable = this::checkMessage;
    private final MorseAudioPlayer m_sound = new MorseAudioPlayer();
    private GradientDrawable m_infoGradient;
    private int[] m_colorsInfoGradient;
    private MorseTolerances m_tolerances = new MorseTolerances();
    private boolean m_isRunning = true;

    @SuppressLint({"ClickableViewAccessibility", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_binding = LessonTransmitBinding.inflate(getLayoutInflater());
        setContentView(m_binding.getRoot());

        Intent intent = getIntent();

        if (intent != null) {
            MorseLanguage language = MorseLanguage.values()
                    [intent.getIntExtra("MORSE_LANGUAGE",
                    MorseLanguage.defaultValue().ordinal())];

            MorseLevel level = MorseLevel.values()
                    [intent.getIntExtra("MORSE_LEVEL",
                    MorseLevel.defaultValue().ordinal())];

            boolean isLess = intent.getBooleanExtra("MORSE_IS_LESS", false);

            int lengthSentence = intent.getIntExtra("MORSE_LENGTH", 1);

            boolean isRandomLengthSentence =
                    intent.getBooleanExtra("MORSE_IS_RANDOM_LENGTH", false);

            String nameLevel = intent.getStringExtra("LEVEL_NAME");

            m_binding.userSentenceMorse.setLanguage(language);

            m_binding.userSentenceMorse.setLevel(level);

            m_binding.userSentenceMorse.setIsLess(isLess);

            m_binding.userSentenceMorse.setLengthSentence(lengthSentence);

            m_binding.titleTransmitLevelName.setText(nameLevel);
        }

        Button transmitButton = findViewById(R.id.button_transmit);
        transmitButton.setOnTouchListener(this::OnTouchTransmitButton);

        m_binding.rootLayout.post(this::initInfoGradient);

        m_binding.sentenceMorse.setTolerances(m_tolerances);
        m_binding.userSentenceMorse.setTolerances(m_tolerances);

        m_binding.sentenceMorse.setIsStartUp(false);
        m_binding.userSentenceMorse.setIsStartUp(false);

        m_binding.sentenceMorse.setColor(getColor(R.color.morse_secondary));
        m_binding.userSentenceMorse.setColor(getColor(R.color.morse_primary_variant));

        m_binding.buttonBack.setOnClickListener(this::onClickBack);

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
        float radius = Math.max(m_binding.rootLayout.getWidth(), m_binding.rootLayout.getHeight());
        m_infoGradient.setGradientRadius(radius);
        m_infoGradient.setColors(m_colorsInfoGradient);

        animationInfoGradient();
    }

    private void animationInfoGradient() {
        m_binding.rootLayout.setBackground(m_infoGradient);
        ObjectAnimator.ofInt(m_infoGradient, "alpha",
                0xff, 0x00).setDuration(1000).start();
    }

    private void checkMessage() {
        checkMessage(false);
    }

    private void checkMessage(boolean isError) {
        Log.d("chack", "CheckMessage");
        applySymbol();

        m_handler.removeCallbacks(m_idleRunnable);
        m_sound.stop();
        m_binding.sentenceMorse.stop();
        m_binding.userSentenceMorse.stop();
        m_tolerances.clearPoints();
        m_isRunning = false;

        m_binding.buttonTransmit.setText(R.string.again);

        if (!isError && m_userSentence.equals(m_sentence)) {
            winGradient();
        } else {
            failGradient();
        }
    }

    void clearSentence() {
        m_sentence = "";
        m_binding.sentence.setText(m_sentence);
    }

    void updateSentence() {
        m_binding.userSentenceMorse.updateSentence();
        String sentence = m_binding.userSentenceMorse.getText();
        m_binding.sentence.setText(sentence);
        m_binding.sentenceMorse.setText(sentence);
        clearUserSentence();
        m_binding.userSentenceMorse.clear();
    }

    void clearUserSentence() {
        m_userSentence = "";
        m_binding.userSentence.setText(m_userSentence);
    }

    void updateUserSentence(String string) {
        m_userSentence += string;
        m_binding.userSentence.setText(m_userSentence);
    }

    void updateUserSentence(char symbol) {
        m_userSentence += symbol;
        m_binding.userSentence.setText(m_userSentence);
    }

    void updateUserSentence(char[] string) {
        m_userSentence += string;
        m_binding.userSentence.setText(m_userSentence);
    }

    private boolean OnTouchTransmitButton(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!m_isRunning) {
                    return true;
                }

                m_handler.removeCallbacks(m_idleRunnable);
                m_sound.start();
                view.setPressed(true);
                if (m_tolerances.isDiff()) {
                    MorseGraphView.TypeAction action = m_binding.userSentenceMorse.press();

                    if (action == MorseGraphView.TypeAction.NONE) {
                        notCorrectInterval(m_tolerances.getDiff());
                        return true;
                    }
                } else {
                    m_tolerances.start();
                    m_binding.userSentenceMorse.start(true);
                    m_binding.sentenceMorse.start(false);
                    m_binding.buttonTransmit.setText(R.string.transmit);
                }

                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (!m_isRunning) {
                    updateSentence();
                    m_isRunning = true;
                    m_binding.buttonTransmit.setText(R.string.start);
                    return true;
                }

                m_handler.postDelayed(m_idleRunnable,
                        m_tolerances.getPeriodGapWord() +
                        m_tolerances.getPeriodGapWordEpsilonHigh());

                m_sound.stop();
                view.setPressed(false);
                m_binding.userSentenceMorse.press();

                MorseGraphView.TypeAction action = m_binding.userSentenceMorse.press();

                if (action == MorseGraphView.TypeAction.NONE) {
                    notCorrectInterval(m_tolerances.getDiff());
                    return true;
                }

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
        m_binding.currentSymbol.setText(String.valueOf(m_currentSymbol));
    }

    private void applyWord() {
        updateUserSentence(' ');
        m_morse.clear();
        m_currentSymbol = '\0';
        m_binding.currentSymbol.setText(String.valueOf(m_currentSymbol));
    }
}