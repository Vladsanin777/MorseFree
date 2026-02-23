package com.example.morsefree;

import static com.example.morsefree.MorseLanguage.*;
import static com.example.morsefree.MorseLevel.*;

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
        String[] morseLanguageAndMorseLevel = button.getTag().toString().split(";");

        Log.d("MorseFree", level_name);

        MorseLanguage language = MorseLanguage.valueOf(morseLanguageAndMorseLevel[0]);
        MorseLevel level = MorseLevel.valueOf(morseLanguageAndMorseLevel[1]);

        Intent intent = new Intent(SelectLessonTransmit.this, LessonTransmit.class);
        intent.putExtra("MORSE_LANGUAGE", language.ordinal());
        intent.putExtra("MORSE_LEVEL", level.ordinal());
        intent.putExtra("LEVEL_NAME", level_name);
        startActivity(intent);
    }
}