package iestrassierra.pmdm.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Fragment> preguntas;
    private Button botonCambiar, botonCerrar;
    private FragmentManager miManejador;
    private int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preguntas = new ArrayList<>();
        preguntas.add(new Fragmento1());
        preguntas.add(new Fragmento2());
        preguntas.add(new Fragmento3());

        botonCambiar = findViewById(R.id.btCambiar);
        botonCambiar.setOnClickListener(this);

        botonCerrar = findViewById(R.id.btCerrar);
        botonCerrar.setOnClickListener(v -> finishAffinity());

        miManejador = getSupportFragmentManager();
        cargaFragmento(contador);
    }

    private void cargaFragmento(int cuenta){
        FragmentTransaction transaccion;
        transaccion = miManejador.beginTransaction();
        transaccion.replace(R.id.contenedor, preguntas.get(cuenta));
        transaccion.commit();
    }

    @Override
    public void onClick(View v) {
        contador++;
        if(contador < preguntas.size()){
            cargaFragmento(contador);
        }else
            botonCambiar.setEnabled(false);
    }
}