package com.example.escuchaeventos;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class VistaEscuchadora extends androidx.appcompat.widget.AppCompatEditText {

    public VistaEscuchadora(@NonNull Context context) {
        super(context);
    }

    public VistaEscuchadora(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VistaEscuchadora(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // Catpuro que hace un click sobre la vista
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("Vista", "Se ha pulsado en la vista");
        // Capturo el tiempo de pulsacion
        if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP){
            if(event.getEventTime() - event.getDownTime() > 500)
                Log.d("Vista", "La pulsación ha sido larga");
            else
                Log.d("Vista", "La pulsación ha sido corta");

            Log.d("Vista", "La duración de la pulsación ha sido "
                    + (event.getEventTime() - event.getDownTime()) + "ms");
        }


            return super.dispatchTouchEvent(event);
    }
    // Calpturo cuando gana y pierde el foco
    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            Log.d("Vista", "La vista ha obtenido el foco");
        } else {
            Log.d("Vista", "La vista ha perdido el foco");
        }
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            //Log.d("Layout", "Se ha pulsado tecla en el Layout");
            // Capturo la tecla que se ha pulsado en un tipo char
            char c = (char) event.getUnicodeChar();
            // reviso que la tecla sea una vocal
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
                Log.d("Vista", "Se ha pulsado una vocal");


        }
        return super.dispatchKeyEvent(event);
    }


}
