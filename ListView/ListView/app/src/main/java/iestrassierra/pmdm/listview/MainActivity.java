package iestrassierra.pmdm.listview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Array que contiene los datos que se mostrarán en el ListView.
    private Capital[] datos = new Capital[]{new Capital("Madrid", "4.500.000"),
            new Capital("París", "8.000.000"), new Capital("Londrés", "8.200.000"),
            new Capital("Roma", "5.000.000"), new Capital("Tokio", "18.000.000"),
            new Capital("Berlín", "3.600.000")};
    private ListView listCapitales;
    private Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contexto = this;

        //Vinculamos el objeto listview de Java con el del Layout de pantalla
        listCapitales = (ListView) findViewById(R.id.listCapitales);
        //Creamos el objeto adaptador que suministrará los datos al ListView
        AdaptadorCapitales adaptador = new AdaptadorCapitales(this, datos);
        listCapitales.setAdapter(adaptador);
        // Al hacer click en la celda, se muestra el valor del texto de la misma
        // en el TextView superior.
        listCapitales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
            //Extraemos los valores de los campos de la fila en la que hemos hecho click
                String resultado = "La capital seleccionada es: "+ (String) ((TextView) v
                        .findViewById(R.id.tvcapital)).getText() +" y tiene "+ ((TextView) v
                        .findViewById(R.id.tvhabitantes)).getText() + " habitantes.";
            //Mostramos la información en un diálogo.
                AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                builder.setTitle("Información");
                builder.setMessage(resultado);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }



}