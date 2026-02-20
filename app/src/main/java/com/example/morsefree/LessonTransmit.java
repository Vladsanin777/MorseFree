package com.example.morsefree;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LessonTransmit extends AppCompatActivity {
    private Button m_transmitButton;
    private long m_timeBreakPoint;
    private long m_intervalTimeForPoint;
    private long m_intervalTimeForDash;
    private long m_intervalTimeForInterBase;
    private long m_intervalTimeForInterSymbol;
    private long m_intervalTimeForInterWord;
    private long m_intervalTimeForEpsilonPoint;
    private long m_intervalTimeForEpsilonDash;
    private long m_intervalTimeForEpsilonInterBase;
    private long m_intervalTimeForEpsilonInterSymbol;
    private long m_intervslTimeForEpsilonInterWord;
    string m_symbol;
    string m_word;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_transmit);

        m_timeBreakPoint = 0;

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

    }

    private void applyDash(long diff) {

    }

    private boolean isDash(long diff) {

    }

    private boolean isPoint(long diff) {

    }

    private void applySymbol() {

    }

    private void applyWord() {

    }

    private boolean isInterBase(long diff) {

    }

    private boolean isInterSymbol(long diff) {

    }

    private boolean isInterWord(long diff) {

    }

    private void notCorrectInterval(long diff) {
        
    }

    private void notCorrectPointDash(long diff) {

    }
}
