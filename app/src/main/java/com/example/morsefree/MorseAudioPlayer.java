package com.example.morsefree;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class MorseAudioPlayer {
    private final int sampleRate = 44100;
    private int freqHz = 700;
    private AudioTrack m_audioTrack;
    private Thread m_thread;

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

    public void start() {
        m_audioTrack.play();
        m_thread = new Thread(this::loopAudio);
        m_thread.start();
    }

    void loopAudio() {
        short buffer[] = new short[1024];
        double phase = 0.0;
        while (!m_thread.isInterrupted()) {
            for (int i = 0; i < buffer.length; i++) {
                // Вычисляем синус с учетом накопленной фазы
                buffer[i] = (short) (Math.sin(phase) * Short.MAX_VALUE);
                phase += 2.0 * Math.PI * freqHz / sampleRate;

                // Чтобы фаза не росла до бесконечности (защита от ошибок float)
                if (phase > 2.0 * Math.PI) phase -= 2.0 * Math.PI;
            }
            m_audioTrack.write(buffer, 0, buffer.length);
        }
    }
    public void stop() {
        m_thread.interrupt();
        m_audioTrack.pause();
        m_audioTrack.flush();
    }

    public void release() {
        stop();
        m_audioTrack.release();
    }
}
