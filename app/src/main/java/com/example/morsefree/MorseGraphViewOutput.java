package com.example.morsefree;

import android.content.Context;
import android.util.AttributeSet;

public class MorseGraphViewOutput extends MorseGraphView {
    private boolean m_isLess = false;
    private int m_lengthSentence = 1;
    private boolean m_isRandomLengthSentence;

    public MorseGraphViewOutput(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
