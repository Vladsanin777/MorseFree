package com.example.morsefree;

public class MorseTolerances {
    private long m_periodPoint = 120;
    private long m_periodDash = 360;
    private long m_periodGapBase = 120;
    private long m_periodGapSymbol = 360;
    private long m_periodGapWord = 840;

    private long m_periodPointEpsilonHigh = 60;
    private long m_periodDashEpsilonHigh = 90;
    private long m_periodGapBaseEpsilonHigh = 60;
    private long m_periodGapSymbolEpsilonHigh = 90;
    private long m_periodGapWordEpsilonHigh = 300;

    private long m_periodPointEpsilonLow = 90;
    private long m_periodDashEpsilonLow = 120;
    private long m_periodGapBaseEpsilonLow = 90;
    private long m_periodGapSymbolEpsilonLow = 120;
    private long m_periodGapWordEpsilonLow = 360;

    private byte m_periodPointEpsilonHighShare =
            (byte) ((m_periodPointEpsilonHigh * 255) / m_periodPoint);
    private byte m_periodDashEpsilonHighShare =
            (byte) ((m_periodDashEpsilonHigh  * 255) / m_periodDash);
    private byte m_periodGapBaseEpsilonHighShare =
            (byte) ((m_periodGapBaseEpsilonHigh * 255) / m_periodGapBase);
    private byte m_periodGapSymbolEpsilonHighShare =
            (byte) ((m_periodGapSymbolEpsilonHigh * 255) / m_periodGapSymbol);
    private byte m_periodGapWordEpsilonHighShare =
            (byte) ((m_periodGapWordEpsilonHigh * 255) / m_periodGapWord);

    private byte m_periodPointEpsilonLowShare =
            (byte) ((m_periodPointEpsilonLow * 255) / m_periodPoint);
    private byte m_periodDashEpsilonLowShare =
            (byte) ((m_periodDashEpsilonLow * 255) / m_periodDash);
    private byte m_periodGapBaseEpsilonLowShare =
            (byte) ((m_periodGapBaseEpsilonLow * 255) / m_periodGapBase);
    private byte m_periodGapSymbolEpsilonLowShare =
            (byte) ((m_periodGapSymbolEpsilonLow * 255) / m_periodGapSymbol);
    private byte m_periodGapWordEpsilonLowShare =
            (byte) ((m_periodGapWordEpsilonLow * 255) / m_periodGapWord);

    private long m_lastBreakPoint = 0;
    private long m_currentBreakPoint = 0;
    private long m_startPoint = 0;
    private long m_pause = 0;

    public MorseTolerances() {

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

    public boolean isPoint() {
        long epsilon = getDiff() - m_periodPoint;
        return epsilon > 0 ? m_periodPointEpsilonHigh > epsilon :
                m_periodPointEpsilonLow > -epsilon;
    }

    public boolean isDash() {
        long epsilon = getDiff() - m_periodDash;
        return epsilon > 0 ? m_periodDashEpsilonHigh > epsilon :
                m_periodDashEpsilonLow > -epsilon;
    }

    public boolean isGapBase() {
        long epsilon = getDiff() - m_periodGapBase;
        return epsilon > 0 ? m_periodGapBaseEpsilonHigh > epsilon :
                m_periodGapBaseEpsilonLow > -epsilon;
    }

    public boolean isGapSymbol() {
        long epsilon = getDiff() - m_periodGapSymbol;
        return epsilon > 0 ? m_periodGapSymbolEpsilonHigh > epsilon :
                m_periodGapSymbolEpsilonLow > -epsilon;
    }

    public boolean isGapWord() {
        long epsilon = getDiff() - m_periodGapWord;
        return epsilon > 0 ? m_periodGapWordEpsilonHigh > epsilon :
                m_periodGapWordEpsilonLow > -epsilon;
    }

    public long getPeriodPoint() {
        return m_periodPoint;
    }

    public void setPeriodPoint(long periodPoint) {
        m_periodPoint = periodPoint;
        m_periodPointEpsilonHigh = (periodPoint * m_periodPointEpsilonHighShare) / 255;
        m_periodPointEpsilonLow = (periodPoint * m_periodPointEpsilonLowShare) / 255;
    }

    public long getPeriodDash() {
        return m_periodDash;
    }

    public void setPeriodDash(long periodDash) {
        m_periodDash = periodDash;
        m_periodDashEpsilonHigh = (periodDash * m_periodDashEpsilonHighShare) / 255;
        m_periodDashEpsilonLow = (periodDash * m_periodDashEpsilonLowShare) / 255;
    }

    public long getPeriodGapBase() {
        return m_periodGapBase;
    }

    public void setPeriodGapBase(long periodGapBase) {
        m_periodGapBase = periodGapBase;
        m_periodGapBaseEpsilonHigh = (periodGapBase * m_periodGapBaseEpsilonHighShare) / 255;
        m_periodGapBaseEpsilonLow = (periodGapBase * m_periodGapBaseEpsilonLowShare) / 255;
    }

    public long getPeriodGapSymbol() {
        return m_periodGapSymbol;
    }

    public void setPeriodGapSymbol(long periodGapSymbol) {
        m_periodGapSymbol = periodGapSymbol;
        m_periodGapSymbolEpsilonHigh = (periodGapSymbol * m_periodGapSymbolEpsilonHighShare) / 255;
        m_periodGapSymbolEpsilonLow = (periodGapSymbol * m_periodGapSymbolEpsilonLowShare) / 255;
    }

    public long getPeriodGapWord() {
        return m_periodGapWord;
    }

    public void setPeriodGapWord(long periodGapWord) {
        m_periodGapWord = periodGapWord;
        m_periodGapWordEpsilonHigh = (periodGapWord * m_periodGapWordEpsilonHighShare) / 255;
        m_periodGapWordEpsilonLow = (periodGapWord * m_periodGapWordEpsilonLowShare) / 255;
    }

    public long getPeriodPointEpsilonHigh() {
        return m_periodPointEpsilonHigh;
    }

    public void setPeriodPointEpsilonHigh(long periodPointEpsilonHigh) {
        m_periodPointEpsilonHigh = periodPointEpsilonHigh;
        m_periodPointEpsilonHighShare = (byte) ((periodPointEpsilonHigh * 255) / m_periodPoint);
    }

    public long getPeriodDashEpsilonHigh() {
        return m_periodDashEpsilonHigh;
    }

    public void setPeriodDashEpsilonHigh(long periodDashEpsilonHigh) {
        m_periodDashEpsilonHigh = periodDashEpsilonHigh;
        m_periodDashEpsilonHighShare = (byte) ((periodDashEpsilonHigh * 255) / m_periodDash);
    }

    public long getPeriodGapBaseEpsilonHigh() {
        return m_periodGapBaseEpsilonHigh;
    }

    public void setPeriodGapBaseEpsilonHigh(long periodGapBaseEpsilonHigh) {
        m_periodGapBaseEpsilonHigh = periodGapBaseEpsilonHigh;
        m_periodGapBaseEpsilonHighShare = (byte) ((periodGapBaseEpsilonHigh * 255) / m_periodGapBase);
    }

    public long getPeriodGapSymbolEpsilonHigh() {
        return m_periodGapSymbolEpsilonHigh;
    }

    public void setPeriodGapSymbolEpsilonHigh(long periodGapSymbolEpsilonHigh) {
        m_periodGapSymbolEpsilonHigh = periodGapSymbolEpsilonHigh;
        m_periodGapSymbolEpsilonHighShare = (byte) ((periodGapSymbolEpsilonHigh * 255) / m_periodGapSymbol);
    }

    public long getPeriodGapWordEpsilonHigh() {
        return m_periodGapWordEpsilonHigh;
    }

    public void setPeriodGapWordEpsilonHigh(long periodGapWordEpsilonHigh) {
        m_periodGapWordEpsilonHigh = periodGapWordEpsilonHigh;
        m_periodGapWordEpsilonHighShare = (byte) ((periodGapWordEpsilonHigh * 255) / m_periodGapWord);
    }
    public long getPeriodPointEpsilonLow() {
        return m_periodPointEpsilonLow;
    }

    public void setPeriodPointEpsilonLow(long periodPointEpsilonLow) {
        m_periodPointEpsilonLow = periodPointEpsilonLow;
        m_periodPointEpsilonLowShare = (byte) ((periodPointEpsilonLow * 255) / m_periodPoint);
    }

    public long getPeriodDashEpsilonLow() {
        return m_periodDashEpsilonLow;
    }

    public void setPeriodDashEpsilonLow(long periodDashEpsilonLow) {
        m_periodDashEpsilonLow = periodDashEpsilonLow;
        m_periodDashEpsilonLowShare = (byte) ((periodDashEpsilonLow * 255) / m_periodDash);
    }

    public long getPeriodGapBaseEpsilonLow() {
        return m_periodGapBaseEpsilonLow;
    }

    public void setPeriodGapBaseEpsilonLow(long periodGapBaseEpsilonLow) {
        m_periodGapBaseEpsilonLow = periodGapBaseEpsilonLow;
        m_periodGapBaseEpsilonLowShare = (byte) ((periodGapBaseEpsilonLow * 255) / m_periodGapBase);
    }

    public long getPeriodGapSymbolEpsilonLow() {
        return m_periodGapSymbolEpsilonLow;
    }

    public void setPeriodGapSymbolEpsilonLow(long periodGapSymbolEpsilonLow) {
        m_periodGapSymbolEpsilonLow = periodGapSymbolEpsilonLow;
        m_periodGapSymbolEpsilonLowShare = (byte) ((periodGapSymbolEpsilonLow * 255) / m_periodGapSymbol);
    }

    public long getPeriodGapWordEpsilonLow() {
        return m_periodGapWordEpsilonLow;
    }

    public void setPeriodGapWordEpsilonLow(long periodGapWordEpsilonLow) {
        m_periodGapWordEpsilonLow = periodGapWordEpsilonLow;
        m_periodGapWordEpsilonLowShare = (byte) ((periodGapWordEpsilonLow * 255) / m_periodGapWord);
    }

    public byte getPeriodPointEpsilonHighShare() {
        return m_periodPointEpsilonHighShare;
    }

    public void setPeriodPointEpsilonHighShare(byte periodPointEpsilonHighShare) {
        m_periodPointEpsilonHighShare = periodPointEpsilonHighShare;
        m_periodPointEpsilonHigh = (periodPointEpsilonHighShare * m_periodPoint) / 255;
    }

    public byte getPeriodDashEpsilonHighShare() {
        return m_periodDashEpsilonHighShare;
    }

    public void setPeriodDashEpsilonHighShare(byte periodDashEpsilonHighShare) {
        m_periodDashEpsilonHighShare = periodDashEpsilonHighShare;
        m_periodDashEpsilonHigh = (periodDashEpsilonHighShare * m_periodDash) / 255;
    }

    public byte getPeriodGapBaseEpsilonHighShare() {
        return m_periodGapBaseEpsilonHighShare;
    }

    public void setPeriodGapBaseEpsilonHighShare(byte periodGapBaseEpsilonHighShare) {
        m_periodGapBaseEpsilonHighShare = periodGapBaseEpsilonHighShare;
        m_periodGapBaseEpsilonHigh = (periodGapBaseEpsilonHighShare * m_periodGapBase) / 255;
    }

    public byte getPeriodGapSymbolEpsilonHighShare() {
        return m_periodGapSymbolEpsilonHighShare;
    }

    public void setPeriodGapSymbolEpsilonHighShare(byte periodGapSymbolEpsilonHighShare) {
        m_periodGapSymbolEpsilonHighShare = periodGapSymbolEpsilonHighShare;
        m_periodGapSymbolEpsilonHigh = (periodGapSymbolEpsilonHighShare * m_periodGapSymbol) / 255;
    }

    public byte getPeriodGapWordEpsilonHighShare() {
        return m_periodGapWordEpsilonHighShare;
    }

    public void setPeriodGapWordEpsilonHighShare(byte periodGapWordEpsilonHighShare) {
        m_periodGapWordEpsilonHighShare = periodGapWordEpsilonHighShare;
        m_periodGapWordEpsilonHigh = (periodGapWordEpsilonHighShare * m_periodGapWord) / 255;
    }
    public byte getPeriodPointEpsilonLowShare() {
        return m_periodPointEpsilonLowShare;
    }

    public void setPeriodPointEpsilonLowShare(byte periodPointEpsilonLowShare) {
        m_periodPointEpsilonLowShare = periodPointEpsilonLowShare;
        m_periodPointEpsilonLow = (periodPointEpsilonLowShare * m_periodPoint) / 255;
    }

    public byte getPeriodDashEpsilonLowShare() {
        return m_periodDashEpsilonLowShare;
    }

    public void setPeriodDashEpsilonLowShare(byte periodDashEpsilonLowShare) {
        m_periodDashEpsilonLowShare = periodDashEpsilonLowShare;
        m_periodDashEpsilonLow = (periodDashEpsilonLowShare * m_periodDash) / 255;
    }

    public byte getPeriodGapBaseEpsilonLowShare() {
        return m_periodGapBaseEpsilonLowShare;
    }

    public void setPeriodGapBaseEpsilonLowShare(byte periodGapBaseEpsilonLowShare) {
        m_periodGapBaseEpsilonLowShare = periodGapBaseEpsilonLowShare;
        m_periodGapBaseEpsilonLow = (periodGapBaseEpsilonLowShare * m_periodGapBase) / 255;
    }

    public byte getPeriodGapSymbolEpsilonLowShare() {
        return m_periodGapSymbolEpsilonLowShare;
    }

    public void setPeriodGapSymbolEpsilonLowShare(byte periodGapSymbolEpsilonLowShare) {
        m_periodGapSymbolEpsilonLowShare = periodGapSymbolEpsilonLowShare;
        m_periodGapSymbolEpsilonLow = (periodGapSymbolEpsilonLowShare * m_periodGapSymbol) / 255;
    }

    public byte getPeriodGapWordEpsilonLowShare() {
        return m_periodGapWordEpsilonLowShare;
    }

    public void setPeriodGapWordEpsilonLowShare(byte periodGapWordEpsilonLowShare) {
        m_periodGapWordEpsilonLowShare = periodGapWordEpsilonLowShare;
        m_periodGapWordEpsilonLow = (periodGapWordEpsilonLowShare * m_periodGapWord) / 255;
    }

    private static long now() {
        return System.currentTimeMillis();
    }
}