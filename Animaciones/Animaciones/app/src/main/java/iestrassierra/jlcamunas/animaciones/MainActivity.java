package iestrassierra.jlcamunas.animaciones;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView tv1, tv2, tv3, tv4, tv5, tv6;
    private Executor executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executor = Executors.newFixedThreadPool(6);

        tv1 = findViewById(R.id.tv1);
        tv1.setOnClickListener(this::animador);
        tv2 = findViewById(R.id.tv2);
        tv2.setOnClickListener(this::animador);
        tv3 = findViewById(R.id.tv3);
        tv3.setOnClickListener(this::animador);
        tv4 = findViewById(R.id.tv4);
        tv4.setOnClickListener(this::animador);
        tv5 = findViewById(R.id.tv5);
        tv5.setOnClickListener(this::animador);
        tv6 = findViewById(R.id.tv6);
        tv6.setOnClickListener(this::animador);
    }

    public void animador(View v) {
        int id = v.getId();
        TextView tv;
        Animation animation;

        if (id == R.id.tv1) {
            tv = tv1;
            animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        } else if (id == R.id.tv2) {
            tv = tv2;
            animation = AnimationUtils.loadAnimation(this, R.anim.rotate_360);
        } else if (id == R.id.tv3) {
            tv = tv3;
            animation = AnimationUtils.loadAnimation(this, R.anim.scale_up_down);
        } else if (id == R.id.tv4) {
            tv = tv4;
            animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_up);
        } else if (id == R.id.tv5) {
            tv = tv5;
            animation = AnimationUtils.loadAnimation(this, R.anim.combined_animation);
        } else if (id == R.id.tv6) {
            tv = tv6;
            animation = AnimationUtils.loadAnimation(this, R.anim.complex_animation);
        } else {
            animation = null;
            tv = null;
        }
        if (tv != null)
            // Ejecutamos las animaciones en hilos secundarios
            // Para comunicarnos con el UIThread usamos el método post de la vista
            executor.execute(() -> tv.post(() -> tv.startAnimation(animation)));
    }
}