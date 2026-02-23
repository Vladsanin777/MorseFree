package com.example.morsefree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.morsefree.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'morsefree' library on application startup.
    static {
        System.loadLibrary("morsefree");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button btn = findViewById(R.id.learn_transmit_mode);

        btn.setOnClickListener(this::onClickButtonLearnTransmitMode);
    }
    private void onClickButtonLearnTransmitMode(View view) {
        Intent intent = new Intent(MainActivity.this, SelectLessonTransmit.class);
        startActivity(intent);
    }
}