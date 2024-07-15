package dam.pmdm.adaptadores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Provincia> andalucia = new ArrayList<>();
    private Spinner sp;
    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLocalidades();

        //Spinner
        sp = findViewById(R.id.sp_provincia);
        ArrayAdapter<Provincia> adaptadorProv = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                andalucia);
        adaptadorProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adaptadorProv);

        //Boton
        boton = findViewById(R.id.bt_provincia);
        boton.setOnClickListener(this::escuchador);
    }

    private void escuchador (View view){
        Toast toast = Toast.makeText(getApplicationContext(), sp.getSelectedItem().toString(), Toast.LENGTH_LONG);
        toast.show();

        /* Alternativo
        int posicion = sp.getSelectedItemPosition();
        Toast toast = Toast.makeText(getApplicationContext(), andalucia.get(posicion).toString(), Toast.LENGTH_LONG);
        toast.show();
         */
    }

    private void initLocalidades(){
        andalucia.add(new Provincia("Almería", "07"));
        andalucia.add(new Provincia("Cádiz", "11"));
        andalucia.add(new Provincia("Córdoba", "14"));
        andalucia.add(new Provincia("Granada", "18"));
        andalucia.add(new Provincia("Huelva","21"));
        andalucia.add(new Provincia("Jaén", "23"));
        andalucia.add(new Provincia("Málaga", "29"));
        andalucia.add(new Provincia("Sevilla", "41"));
    }
}