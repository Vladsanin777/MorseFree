package com.example.morsefree;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import android.content.Context;

import android.util.AttributeSet;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MorseGraphView extends View {
    private static final int DEFAULT_HEIGHT_DP = 60;
    private static final int DEFAULT_DIAMETER_LINE_DP = 6;
    private int m_defaultHeightPx = 0;
    private int m_strokeWidthPx = 0;
    private float m_offset = 0.0f;
    private ArrayList<Long> m_changePoints = new ArrayList<Long>();
    private boolean m_isStartUp = false;
    private Paint m_paint = new Paint();
    private boolean m_isRunning = false;
    private long m_timeShift = 0;
    private long m_timingPoint = 0;

    public MorseGraphView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        m_defaultHeightPx = dpToPx(DEFAULT_HEIGHT_DP);
        m_strokeWidthPx = dpToPx(DEFAULT_DIAMETER_LINE_DP);
        m_offset = m_strokeWidthPx / 2f;

        m_paint.setStyle(Paint.Style.STROKE);
        m_paint.setStrokeWidth(m_strokeWidthPx);
        m_paint.setStrokeCap(Paint.Cap.ROUND);
        m_paint.setAntiAlias(true);
        m_paint.setColor(0xFFFFFFFF);
    }

    public void setColor(int color) {
        m_paint.setColor(color);
    }

    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }

    private void start(boolean start) {
        m_isRunning = true;
        m_timeShift = 0;
        m_isStartUp = start;
        press();
        m_changePoints.clear();
        m_changePoints.add(System.nanoTime());
        postInvalidateOnAnimation();
    }

    public void startUp() {
        start(true);
    }

    public void startDown() {
        start(false);
    }

    public void stop() {
        m_isRunning = false;
    }

    public void setTimingPoint(long timingPoint) {
        m_timingPoint = timingPoint;
    }

    public void startAgain() {
        m_isRunning = true;
        m_timeShift = System.nanoTime() - m_changePoints.get(m_changePoints.size() - 1);
        press();
    }

    public void press() {
        m_changePoints.add(System.nanoTime() - m_timeShift);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int height = resolveSize(m_defaultHeightPx, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        if (m_changePoints.isEmpty()) {
            return;
        }

        int width = getWidth();
        int height = getHeight();
        float yCenter = height / 2f;

        long endPoint;

        if (m_isRunning) {
            endPoint = System.nanoTime();
            width /= 2;
        } else {
            endPoint = m_changePoints.get(m_changePoints.size() - 1);
        }

        long widthTime = (long) ((width / (float) m_strokeWidthPx) * m_timingPoint);
        long startTime = endPoint - widthTime;

        long lastPoint = endPoint;

        for (int index = m_changePoints.size() - 1; index >= 0; index--) {
            long currentPoint = m_changePoints.get(index);

            if (lastPoint < startTime) {
                break;
            }

            boolean shouldDraw = ((index % 2 == 1) && m_isStartUp) || ((index % 2 == 0) && !m_isStartUp);

            if (shouldDraw) {
                float xStart = width - ((endPoint - currentPoint) / (float) m_timingPoint) * m_strokeWidthPx;
                float xEnd = width - ((endPoint - lastPoint) / (float) m_timingPoint) * m_strokeWidthPx;

                if (xStart < 0) xStart = 0;

                canvas.drawLine(xStart - m_offset, yCenter - m_offset,
                        xEnd - m_offset, yCenter - m_offset, m_paint);
            }

            lastPoint = currentPoint;
        }

        if (m_isRunning) {
            postInvalidateOnAnimation();
        }
    }
}