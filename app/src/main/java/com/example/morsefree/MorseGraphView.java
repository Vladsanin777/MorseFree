package com.example.morsefree;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import android.content.Context;

import android.util.AttributeSet;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import static com.example.morsefree.MorseGraphView.Position.*;

import static com.example.morsefree.MorseTime.Action;

import static com.example.morsefree.Morse.Const;
import static com.example.morsefree.Morse.Const.*;

import static com.example.morsefree.Morse.Language;
import static com.example.morsefree.Morse.Language.*;

import static com.example.morsefree.Morse.Level;
import static com.example.morsefree.Morse.Level.*;

public abstract class MorseGraphView extends View {
    private static final int DEFAULT_HEIGHT_DP = 60;
    private int m_defaultHeightPx = dpToPx(DEFAULT_HEIGHT_DP);
    private int m_thicknessDp = 6;
    private int m_thicknessPx = dpToPx(m_thicknessDp);
    private int m_offsetPx = m_thicknessPx / 2;
    private ArrayList<Long> m_changePoints = new ArrayList<Long>();
    private boolean m_isStartUp = false;
    private Paint m_paint = new Paint();
    private boolean m_isRunning = false;
    private String m_text = null;
    private MorseTime m_time = null;

    private Language m_language = Language.defaultValue();
    private Level m_level = Level.defaultValue();

    enum Position {
        BEGIN, MIDDLE, END,
    }

    public MorseGraphView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        m_paint.setStyle(Paint.Style.STROKE);
        m_paint.setStrokeWidth(m_thicknessPx);
        m_paint.setStrokeCap(Paint.Cap.ROUND);
        m_paint.setAntiAlias(true);
        m_paint.setColor(0xFFFFFFFF);
    }

    public Language getLanguage() {
        return m_language;
    }

    public void setLanguage(Language language) {
        m_language = language;
    }

    public Level getLevel() {
        return m_level;
    }

    public void setLevel(Level level) {
        m_level = level;
    }

    public void setColor(int color) {
        m_paint.setColor(color);
    }

    public MorseTime getTime() {
        return m_time;
    }

    public void setTime(MorseTime time) {
        m_time = time;
    }

    public long time() {
        return m_time.getTime();
    }

    public void clear() {
        m_text = null;
        m_changePoints.clear();
        postInvalidateOnAnimation();
    }

    public String getText() {
        return m_text;
    }

    protected void setText(@NonNull String text) {
        m_text = text;
    }

    public int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }

    public int pxToDp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density);
    }

    protected void setTimePoint() {
        m_time.point();
    }

    public boolean isEmpty() {
        return m_changePoints.isEmpty();
    }

    public int size() {
        return m_changePoints.size();
    }

    public long get(int index) {
        return m_changePoints.get(index);
    }

    protected void add(long value) {
        m_changePoints.add(value);
    }

    protected void set(int index, long value) {
        m_changePoints.set(index, value);
    }

    protected void remove(int index) {
        m_changePoints.remove(index);
    }

    public boolean isRunning() {
        return m_isRunning;
    }

    protected final void drawStandardView(@NonNull Canvas canvas) {
        super.draw(canvas);
    }

    protected Action isPointDash() {
        return m_time.isPointDash();
    }

    protected Action isGap() {
        return m_time.isGap();
    }

    public void setThicknessDp(int dp) {
        m_thicknessPx = dpToPx(dp);
        m_thicknessDp = dp;
    }

    public void setThicknessPx(int px) {
        m_thicknessPx = px;
        m_thicknessDp = pxToDp(px);
    }

    public int getThicknessDp() {
        return m_thicknessDp;
    }

    public int getThicknessPx() {
        return m_thicknessPx;
    }

    public void start() {
        m_isRunning = true;
        postInvalidateOnAnimation();
    }

    public void setIsStartUp(boolean isStartUp) {
        m_isStartUp = isStartUp;
    }

    public boolean getIsStartUp() {
        return m_isStartUp;
    }

    public void stop() {
        m_isRunning = false;
        postInvalidateOnAnimation();
    }

    public void startAgain() {
        m_isRunning = true;
        postInvalidateOnAnimation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int height = resolveSize(m_defaultHeightPx, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    protected void drawTime(@NonNull Canvas canvas, long currentTime,
                  Position position, boolean isHalf) {
        int width = getWidth();
        int height = getHeight();
        float yCenter = height / 2f;

        if (isHalf) {
            width /= 2;
        }

        long widthTime = (width / m_thicknessPx) * m_time.getPeriodPoint() + 1;

        long endPoint = 0;

        switch (position) {
            case BEGIN:
                endPoint = currentTime + widthTime;
                break;
            case MIDDLE:
                endPoint = currentTime + (widthTime / 2);
                break;
            case END:
                endPoint = currentTime;
                break;
        }

        long startTime = endPoint - widthTime;

        long lastPoint = endPoint;

        for (int index = m_changePoints.size() - 1; index >= 0; index--) {
            long currentPoint = m_changePoints.get(index);

            if (lastPoint < startTime) {
                break;
            }

            boolean shouldDraw = (((index & 0x01) == 1) && m_isStartUp)
                    || (((index & 0x01) == 0) && !m_isStartUp);

            if (shouldDraw) {
                float xStart = width - ((endPoint - currentPoint) /
                        (float) m_time.getPeriodPoint()) * m_thicknessPx;

                float xEnd = width - ((endPoint - lastPoint) /
                        (float) m_time.getPeriodPoint()) * m_thicknessPx;

                if (xStart < 0) xStart = 0;

                xStart += m_offsetPx;
                xEnd -= m_offsetPx;

                xEnd = Math.max(xStart, xEnd);

                // Log.d("xStart", String.valueOf(xStart));
                // Log.d("xEnd", String.valueOf(xEnd));

                canvas.drawLine(xStart, yCenter - m_offsetPx,
                        xEnd, yCenter - m_offsetPx, m_paint);
            }

            lastPoint = currentPoint;
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        if (m_changePoints.isEmpty()) {
            return;
        }

        if (m_isRunning) {
            drawTime(canvas, time(), MIDDLE, false);
            postInvalidateOnAnimation();
        } else {
            drawTime(canvas, get(0), BEGIN, false);
        }
    }
}