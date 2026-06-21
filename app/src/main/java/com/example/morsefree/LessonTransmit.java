package com.example.morsefree;

import static com.example.morsefree.MorseTime.Action;
import static com.example.morsefree.MorseTime.Action.*;

import static com.example.morsefree.Morse.Const;
import static com.example.morsefree.Morse.Const.*;

import static com.example.morsefree.Morse.Level;
import static com.example.morsefree.Morse.Level.*;

import static com.example.morsefree.Morse.Language;
import static com.example.morsefree.Morse.Language.*;

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

import com.example.morsefree.databinding.LessonTransmitBinding;

public class LessonTransmit extends AppCompatActivity {
    private LessonTransmitBinding m_binding;
    private final Handler m_handler =
            new android.os.Handler(Looper.getMainLooper());
    private final Runnable m_idleRunnable = this::checkMessage;
    private final MorseAudioPlayer m_sound = new MorseAudioPlayer();
    private GradientDrawable m_infoGradient;
    private int[] m_colorsInfoGradient;
    private MorseTime m_time = new MorseTime();
    private boolean m_isRunning = true;

    @SuppressLint({"ClickableViewAccessibility", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_binding = LessonTransmitBinding.inflate(getLayoutInflater());
        setContentView(m_binding.getRoot());

        Intent intent = getIntent();

        if (intent != null) {
            Language language = Language.values()
                    [intent.getIntExtra("MORSE_LANGUAGE",
                    Language.defaultValue().ordinal())];

            Level level = Level.values()
                    [intent.getIntExtra("MORSE_LEVEL",
                    Level.defaultValue().ordinal())];

            boolean isLess = intent.getBooleanExtra("MORSE_IS_LESS", false);

            int lengthSentence = intent.getIntExtra("MORSE_LENGTH", 1);

            boolean isRandomLengthSentence =
                    intent.getBooleanExtra("MORSE_IS_RANDOM_LENGTH", false);

            String nameLevel = intent.getStringExtra("LEVEL_NAME");

            MorseGraphViewInput morseInput = m_binding.userSentenceMorse;

            MorseGraphViewOutput morseOutput = m_binding.sentenceMorse;

            morseInput.setLanguage(language);
            morseInput.setLevel(level);
            morseOutput.setIsLess(isLess);
            morseOutput.setLengthSentence(lengthSentence);
            morseOutput.setIsRandomLengthSentence(isRandomLengthSentence);

            m_binding.titleTransmitLevelName.setText(nameLevel);
        }

        Button transmitButton = findViewById(R.id.button_transmit);
        transmitButton.setOnTouchListener(this::OnTouchTransmitButton);

        m_binding.rootLayout.post(this::initInfoGradient);

        m_binding.sentenceMorse.setTime(m_time);
        m_binding.userSentenceMorse.setTime(m_time);

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

    private void endAction() {
        applySymbol();

        m_handler.removeCallbacks(m_idleRunnable);
        m_sound.stop();
        m_binding.sentenceMorse.stop();
        m_binding.userSentenceMorse.stop();
        m_time.clearPoints();
        m_isRunning = false;

        m_binding.buttonTransmit.setText(R.string.again);
    }

    private void error() {
        endAction();
        failGradient();
    }

    private void win() {
        endAction();
        winGradient();
    }

    private void checkMessage() {
        checkMessage(false);
    }

    private void checkMessage(boolean isError) {
        if (m_binding.sentence.getText().equals(m_binding.userSentence.getText())) {
            win();
        } else {
            error();
        }
    }

    void updateSentence() {
    }

    void clearUserSentence() {
        m_binding.userSentence.setText("");
        m_binding.userSentenceMorse.clear();
    }

    void clearSentence() {
        m_binding.sentence.setText("");
        m_binding.sentenceMorse.clear();
    }

    void updateUserSentence() {
        String sentence = m_binding.userSentenceMorse.getText();
        m_binding.userSentence.setText(sentence);
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
                if (m_time.isDiff()) {
                    Action action = m_binding.userSentenceMorse.press();

                    switch (action) {
                        case NONE:
                            error();
                            return true;
                        case POINT:

                    }
                    if (action == NONE) {

                    }
                } else {
                    m_time.start();
                    m_binding.userSentenceMorse.start();
                    m_binding.sentenceMorse.start();
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
                        m_time.getPeriodGapWord() +
                        m_time.getPeriodGapWordEpsilonHigh());

                m_sound.stop();
                view.setPressed(false);

                Action action = m_binding.userSentenceMorse.press();

                if (action == NONE) {
                    error();
                    return true;
                }

                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
        }
        return false;
    }

    private void applySymbol() {
        updateUserSentence();
        m_binding.currentSymbol.setText(String.valueOf(m_binding.userSentenceMorse.getCurrentSymbol()));
    }

    private void applyWord() {
        updateUserSentence();
        m_binding.currentSymbol.setText(String.valueOf(m_binding.userSentenceMorse.getCurrentSymbol()));
    }
}