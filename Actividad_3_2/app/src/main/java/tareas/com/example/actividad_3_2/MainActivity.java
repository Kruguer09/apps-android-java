package tareas.com.example.actividad_3_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button btGuardar, btLeer, btGuardarSD, btLeerSD, btBorrar;
    EditText etNombreArchivo, etNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btGuardar=findViewById(R.id.btGuardar);
        btBorrar=findViewById(R.id.btBorrar);
        btLeer=findViewById(R.id.btLeer);
        btGuardarSD=findViewById(R.id.btGuarSD);
        btLeerSD=findViewById(R.id.btLeerSD);
        etNombreArchivo=findViewById(R.id.etNombreArchivo);
        etNota=findViewById(R.id.etNota);

        btGuardar.setOnClickListener(this::guardarNota);
        btBorrar.setOnClickListener(this::borrarCampos);
        btLeer.setOnClickListener(this::abrirNota);
        btGuardarSD.setOnClickListener(this::guardarNotaSD);
        btLeerSD.setOnClickListener(this::abrirNotaSD);

    }

    private void abrirNotaSD(View view) {
    }

    private void guardarNotaSD(View view) {
    }

    private void abrirNota(View view) {
    }

    private void borrarCampos(View view) {
        etNota.setText("");
        etNombreArchivo.setText("");
    }

    private void guardarNota(View view) {
    }
}