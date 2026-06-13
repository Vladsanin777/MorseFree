package com.example.morsefree;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class MorseAudioPlayer {
    private final int sampleRate = 44100;
    private int freqHz = 700;
    private AudioTrack m_audioTrack;
    private Thread m_thread;

    private volatile boolean m_isPlaying = false;

    public MorseAudioPlayer() {
        int minBufferSize = AudioTrack.getMinBufferSize(sampleRate,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);

        m_audioTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                minBufferSize,
                AudioTrack.MODE_STREAM);
    }

    public synchronized void start() {
        if (m_isPlaying) return;

        m_isPlaying = true;
        m_audioTrack.play();
        m_thread = new Thread(this::loopAudio, "MorseAudioThread");
        m_thread.start();
    }

    private void loopAudio() {
        short[] buffer = new short[1024];
        double phase = 0.0;

        while (m_isPlaying && !Thread.currentThread().isInterrupted()) {
            for (int i = 0; i < buffer.length; i++) {
                buffer[i] = (short) (Math.sin(phase) * Short.MAX_VALUE);
                phase += 2.0 * Math.PI * freqHz / sampleRate;

                if (phase > 2.0 * Math.PI) phase -= 2.0 * Math.PI;
            }

            if (m_isPlaying) {
                m_audioTrack.write(buffer, 0, buffer.length);
            }
        }
    }

    public synchronized void stop() {
        m_isPlaying = false;

        if (m_thread != null) {
            m_thread.interrupt();
            try {
                m_thread.join(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            m_thread = null;
        }

        if (m_audioTrack != null && m_audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
            m_audioTrack.pause();
            m_audioTrack.flush();
        }
    }

    public synchronized void release() {
        stop();
        if (m_audioTrack != null) {
            m_audioTrack.release();
            m_audioTrack = null;
        }
    }
}