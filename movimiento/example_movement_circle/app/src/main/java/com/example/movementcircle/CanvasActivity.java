package com.example.movementcircle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CanvasActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btClose = (Button) findViewById(R.id.btClose);

        //Evento para cerrar la actividad
        btClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                closeActivity();
            }
        });
    }

    public void closeActivity() {
        finish();
    }

}
