package tareas.com.j_a_f_r.actividad_4_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mp;
    private boolean bucle = false;
    private int posicion = 0;
    private ImageButton reproducir, pausar, continuar, parar, buclear;
    private Button elegir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creamos el objeto MediaPlayer
        mp = new MediaPlayer();
        mp=null;


        // Asignamos los botones a las variables
        reproducir = (ImageButton) findViewById(R.id.btReproducir);
        pausar = (ImageButton) findViewById(R.id.btPausa);
        continuar = (ImageButton) findViewById(R.id.btContinuar);
        parar = (ImageButton) findViewById(R.id.btParar);
        buclear = (ImageButton) findViewById(R.id.btBucle);
        elegir = findViewById(R.id.bt_elegir);

        //Métoto botón elegir que me permite elegir un archivo de audio de la tarjeta SD y cargarlo en el reproductor
        elegir.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("audio/*");
            startActivityForResult(intent, 10);

        });
        //Métoto boton reproducir
        reproducir.setOnClickListener(v -> {
            if (mp == null) {
                Toast.makeText(this, "Elige canción", Toast.LENGTH_SHORT).show();
            }else {
//                mp = MediaPlayer.create(this, R.raw.cancion);
                mp.start();
                mp.setLooping(bucle);
                Toast.makeText(this, "Reproduciendo", Toast.LENGTH_SHORT).show();
            }

        });

        //Métoto botón pausar
        pausar.setOnClickListener(v -> {
            if (mp != null && mp.isPlaying()) {
                posicion = mp.getCurrentPosition();
                mp.pause();
                Toast.makeText(this, "Pausa", Toast.LENGTH_SHORT).show();
            }
        });

        //Métoto botón continuar
        continuar.setOnClickListener(v -> {
            if (mp != null && !mp.isPlaying()) {
                mp.seekTo(posicion);
                mp.start();
                Toast.makeText(this, "Continuar", Toast.LENGTH_SHORT).show();
            }
        });

        //Métoto botón parar
        parar.setOnClickListener(v -> {
            if (mp != null) {
                mp.stop();
                posicion = 0;
                mp.release();   //ya lo hago al principio
                mp = null;
                Toast.makeText(this, "Parar", Toast.LENGTH_SHORT).show();
            }
        });

        //Métoto botón bucle
        buclear.setOnClickListener(v -> {
            if (mp != null) {
                if (bucle) {
                    bucle = false;
                    mp.setLooping(bucle);
                    Toast.makeText(this, "Reproducción solo una vez", Toast.LENGTH_SHORT).show();
                } else {
                    bucle = true;
                    mp.setLooping(bucle);
                    Toast.makeText(this, "Reproducción en bucle", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //Métoto que me permite elegir un archivo de audio de la tarjeta SD y cargarlo en el reproductor
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            Uri audioFileUri = data.getData();
            try {
                if (mp == null) {
                    mp = new MediaPlayer();
                } else {
                    mp.reset();
                }
                mp.setDataSource(getApplicationContext(), audioFileUri);
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}