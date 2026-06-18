package com.example.morsefree;

import static com.example.morsefree.MorseTime.Action.*;

public class MorseTime extends MorseTolerances {
    private long m_lastBreakPoint = 0;
    private long m_currentBreakPoint = 0;
    private long m_startPoint = 0;
    private long m_pause = 0;

    enum Action {
        NONE, POINT, DASH, GAP_BASE, GAP_SYMBOL, GAP_WORD,
    }

    public MorseTime() {
        super();
    }

    public void start() {
        m_startPoint = now();
        clearPoints();
        point();
    }

    public void stop() {
        m_pause = now();
    }

    public void restart() {
        if (m_pause == 0) {
            return;
        }
        m_startPoint += now() - m_pause;
        m_pause = 0;
    }
    public long getTime() {
        return now() - m_startPoint;
    }

    public void point() {
        m_lastBreakPoint = m_currentBreakPoint;
        m_currentBreakPoint = now();
    }

    public void clearPoints() {
        m_lastBreakPoint = 0;
        m_currentBreakPoint = 0;
    }

    public boolean isDiff() {
        return m_currentBreakPoint != 0 && m_lastBreakPoint != 0;
    }

    public long getDiff() {
        return m_currentBreakPoint - m_lastBreakPoint;
    }

    public Action isPointDash() {
        if (isPoint()) {
            return POINT;
        } else if (isDash()) {
            return DASH;
        }
        return NONE;
    }

    public Action isGap() {
        if (isGapBase()) {
            return GAP_BASE;
        } else if (isGapSymbol()) {
            return GAP_SYMBOL;
        } else if (isGapWord()) {
            return GAP_WORD;
        }
        return NONE;
    }

    public boolean isPoint() {
        long epsilon = getDiff() - getPeriodPoint();
        return epsilon > 0 ? getPeriodPointEpsilonHigh() > epsilon :
                getPeriodPointEpsilonLow() > -epsilon;
    }

    public boolean isDash() {
        long epsilon = getDiff() - getPeriodDash();
        return epsilon > 0 ? getPeriodDashEpsilonHigh() > epsilon :
                getPeriodDashEpsilonLow() > -epsilon;
    }

    public boolean isGapBase() {
        long epsilon = getDiff() - getPeriodGapBase();
        return epsilon > 0 ? getPeriodGapBaseEpsilonHigh() > epsilon :
                getPeriodGapBaseEpsilonLow() > -epsilon;
    }

    public boolean isGapSymbol() {
        long epsilon = getDiff() - getPeriodGapSymbol();
        return epsilon > 0 ? getPeriodGapSymbolEpsilonHigh() > epsilon :
                getPeriodGapSymbolEpsilonLow() > -epsilon;
    }

    public boolean isGapWord() {
        long epsilon = getDiff() - getPeriodGapWord();
        return epsilon > 0 ? getPeriodGapWordEpsilonHigh() > epsilon :
                getPeriodGapWordEpsilonLow() > -epsilon;
    }

    private static long now() {
        return System.currentTimeMillis();
    }
}
