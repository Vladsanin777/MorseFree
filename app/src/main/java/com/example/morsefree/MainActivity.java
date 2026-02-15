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

        // Внутри onCreate первой Activity
        Button btn = findViewById(R.id.learn_transmit_mode);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectLesonTransmit.class);
                startActivity(intent);
            }
        });
    }
}