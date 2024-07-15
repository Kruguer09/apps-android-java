package com.example.escuchaeventos;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class LayoutEscuchador extends ConstraintLayout {
    //public static final String DEBUG_TAG = "Layout";
    public LayoutEscuchador(@NonNull Context context) {
        super(context);
    }

    public LayoutEscuchador(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LayoutEscuchador(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LayoutEscuchador(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("Layout", "Se ha pulsado en el Layout");
        }
        return super.dispatchTouchEvent(event);


    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN) {
            Log.d("Layout", "Se ha pulsado tecla en el Layout");
        }
        return super.dispatchKeyEvent(event);

    }
}
