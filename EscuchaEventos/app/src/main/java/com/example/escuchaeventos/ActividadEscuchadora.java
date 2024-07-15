package com.example.escuchaeventos;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActividadEscuchadora extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_escuchador);

    }
    //Intercepta el evento de action_down
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP) {
            Log.d("Actividad", "Se ha pulsado en la actividad");
        }
        return super.dispatchTouchEvent(event);
    }
    //Intercepta el evento de pulsacion de teclado
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN) {
            Log.d("Actividad", "Se ha pulsado una tecla en la actividad");
        }
        return super.dispatchKeyEvent(event);

    }
}