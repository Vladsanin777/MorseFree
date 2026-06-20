package com.example.morsefree;

import static com.example.morsefree.Morse.Language;
import static com.example.morsefree.Morse.Language.*;

import static com.example.morsefree.Morse.Level;
import static com.example.morsefree.Morse.Level.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SelectLessonTransmit extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_lesson_transmit);
    }
    public void onClickSelectLessonTransmit(View view) {
        Button button = (Button)view;
        String level_name = button.getText().toString();
        Level level = null;
        Language language = null;

        if (button.getTag().toString().contains(";")) {
            String[] morseLanguageAndMorseLevel = button.getTag().toString().split(";");
            level = Level.valueOf(morseLanguageAndMorseLevel[0]);
            language = Language.valueOf(morseLanguageAndMorseLevel[1]);

            Log.d("MorseFree", level_name);

            Intent intent = new Intent(SelectLessonTransmit.this, LessonTransmit.class);
            intent.putExtra("MORSE_LANGUAGE", language.ordinal());
            intent.putExtra("MORSE_LEVEL", level.ordinal());
            intent.putExtra("LEVEL_NAME", level_name);
            startActivity(intent);
        }
    }
}