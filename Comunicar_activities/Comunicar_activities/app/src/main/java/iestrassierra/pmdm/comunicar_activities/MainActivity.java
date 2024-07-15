package iestrassierra.pmdm.comunicar_activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private EditText et1;

    protected static final int ACTIVIDAD2 = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Bindings
        et1 = findViewById(R.id.et1);

        Button btIr = findViewById(R.id.bt_ir);
        btIr.setOnClickListener(this::ir);

        Button btIrVolver = findViewById(R.id.bt_ir_volver);
        btIrVolver.setOnClickListener(this::irVolver);

        Button btIrVolver2 = findViewById(R.id.bt_ir_volver_2);
        btIrVolver2.setOnClickListener(this::irVolver2);
    }

    ///////////////////////////////////////////////Sin retorno///////////////////////////////////////
    public void ir(View view) {
        Intent intentIda = new Intent(this, Actividad2.class);
        intentIda.putExtra("direccion", et1.getText().toString());
        startActivity(intentIda);
    }


    ////////////////////////////////////////Con retorno (obsoleto)///////////////////////////////////
    public void irVolver(View view){
        Intent intentIda = new Intent(this,Actividad2.class);
        intentIda.putExtra("direccion", et1.getText().toString());
        startActivityForResult(intentIda,ACTIVIDAD2); //Mandamos el código de la actividad lanzada
    }

    //Método para gestionar el resultado
    @Override
    protected void onActivityResult(int codigoActividad,
                                    int codigoResultado, @Nullable Intent intentDevuelto) {
        super.onActivityResult(codigoActividad, codigoResultado, intentDevuelto);
        //Comprobamos el código de la actividad lanzada
        if (codigoActividad == ACTIVIDAD2){
            if(codigoResultado == RESULT_OK) {
                String valor = (String) intentDevuelto.getExtras().get("Resultado");
                Toast.makeText(getApplicationContext(), valor, Toast.LENGTH_LONG).show();
            }
        }
    }


    ////////////////////////////////////////Con retorno (nueva forma)////////////////////////////////
    public void irVolver2(View view) {
        Intent intentIda = new Intent(this, Actividad2.class);
        intentIda.putExtra("direccion", et1.getText().toString());
        lanzadorActividades.launch(intentIda);
    }


    ActivityResultLauncher<Intent> lanzadorActividades = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                //Método para gestionar el resultado
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        //No hay códigos de actividad
                        Intent intentDevuelto = result.getData();
                        String valor = (String) intentDevuelto.getExtras().get("Resultado");
                        Toast.makeText(getApplicationContext(), valor, Toast.LENGTH_LONG).show();
                        }
                    }
            });

}