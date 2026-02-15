package com.example.morsefree;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SelectLessonTransmit extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_lesson_transmit);

        Button btn = findViewById(R.id.e_and_t_transmit_lesson);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectLessonTransmit.this, LessonTransmit.class);
                startActivity(intent);
            }
        });
    }
}