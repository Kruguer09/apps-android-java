package iestrassierra.pmdm.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //Array que contiene los datos que se mostrarán en el RecyclerView.
    private ArrayList<Capital> datos = new ArrayList<>();
    private RecyclerView rv;
    private Button btCerrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        //Creamos el adaptador
        AdaptadorCapital adaptador = new AdaptadorCapital(this, datos);
        //Vinculamos el objeto java RecyclerView con el objeto correspondiente en el layout
        rv = findViewById(R.id.rvCapitales);
        //rv.setHasFixedSize(true);
        rv.setAdapter(adaptador);
        //Asignamos un LinearLayout vertical al RecyclerView de forma que los datos se vean en formato lista.
        rv.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        rv.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
                Toast.makeText(getBaseContext(), "Posicion: " + holder.getAdapterPosition(), Toast.LENGTH_LONG).show();
            }
        });

        btCerrar = findViewById(R.id.button);
        btCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }

    private void init(){
        //Incluimos datos de capitales usando ambos constructores
        datos.add(new Capital("Madrid", "6.800.000"));
        datos.add(new Capital("París", "11.400.000", true));
        datos.add(new Capital("Londres", "14.800.000", true));
        datos.add(new Capital("Roma", "3.575.000"));
        datos.add(new Capital("Tokio", "40.400.000",true));
        datos.add(new Capital("Nueva York","22.100.000",true));
        datos.add(new Capital("Moscú","17.300.000"));
        datos.add(new Capital("México DF","23.000.000",false));
        datos.add(new Capital("Seúl","24.900.000"));
        datos.add(new Capital("Rio de Janerio","13.200.000"));
    }

}