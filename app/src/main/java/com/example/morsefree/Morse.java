package com.example.morsefree;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Morse {
    private int m_data = 0;
    private byte m_length = 0;

    public Morse() {
        this(0, 0);
    }

    public Morse(int data, int length) {
        m_data = data;
        if (32 < length || length < 0) {
            clear();
            return;
        }
        m_length = (byte) length;
        m_data = data;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Morse morse = (Morse) object;
        return m_data == morse.m_data && m_length == morse.m_length;
    }

    public boolean equals(MorseConst morse) {
        if (morse == null) {
            return false;
        }

        return morse.equals(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_data, m_length);
    }

    public void clear() {
        m_data = 0;
        m_length = (byte) 0;
    }

    public void addPoint() {
        m_length++;
        m_data <<= 0x01;
    }

    public void addDash() {
        m_length++;
        m_data <<= 0x01;
        m_data |= 0x01;
    }

    public boolean pop() {
        if (m_length == 0) {
            return false;
        }

        m_length--;
        m_data >>= 1;

        return m_length != 0;
    }

    public boolean isPointOnTop() {
        return (m_data & 0x01) == 0;
    }

    public boolean isDashOnTop() {
        return (m_data & 0x01) == 1;
    }

    public byte getLength() {
        return m_length;
    }

    public int getData() {
        return m_data;
    }

    public Morse copy() {
        return  new Morse(m_data, m_length);
    }
}
