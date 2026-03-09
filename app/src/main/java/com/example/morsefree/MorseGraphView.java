package com.example.morsefree;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import android.content.Context;

import android.util.AttributeSet;

import static com.example.morsefree.ActionMorse.*;

public class MorseGraphView extends View {
    ActionMorse m_action = NOT_ACTION_MORSE;
    long m_lastTimeAction = 0;
    long m_timeIntervalPoint = 0;
    Paint m_paint = new Paint();

    boolean m_isPressed = false;

    public MorseGraphView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setTimeIntervalPoint(long timeIntervalPoint) {
        m_timeIntervalPoint = timeIntervalPoint;
    }

    public long  getTimeIntervalPoint() {
        return m_timeIntervalPoint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
