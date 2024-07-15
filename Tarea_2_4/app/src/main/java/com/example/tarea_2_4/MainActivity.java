package com.example.tarea_2_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText dirWeb;
    private Button btVer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dirWeb = findViewById(R.id.etDireWeb);
        btVer = findViewById(R.id.btVer);
        btVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentIda = new Intent(this, Activity2NavWeb.class);
                intentIda.putExtra("candena",dirWeb.getText().toString());



            }
        });

    }
}